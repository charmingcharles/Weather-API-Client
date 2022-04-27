package com.example.pogoda;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_main);

        ListView listView = (ListView) findViewById(R.id.listView);
//        DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
//        List<City> arrayList = databaseHandler.getAllCities();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "test-database").build();
        List<DatabaseCity> cities = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DatabaseCity> temp = db.databaseCityDAO().getAll();
                cities.addAll(temp);
            }
        }).start();
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeatherController.setSelectedItem(position);
                System.out.println("Selection" + position);
            }
        });
        ArrayAdapter<DatabaseCity> cityAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(cityAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

                if(pos == WeatherController.getSelectedItem()){
                    WeatherController.setSelectedItem(-1);
                }

                new AlertDialog.Builder(ListViewActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure?")
                        .setMessage("Delete?")
                        .setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WeatherController.setItemToDelete(cities.get(pos).getCity_name());
                                System.out.println("hmmm: " + WeatherController.getItemToDelete());
                                cities.remove(pos);
                                cityAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Nope", null)
                        .show();
                return true;
            }
        });
    }
}