package com.example.pogoda;

public class WeatherController {

    private static int selectedItem = 0;

    public static int getSelectedItem() {
        return selectedItem;
    }

    public static void setSelectedItem(int selectedItem) {
        WeatherController.selectedItem = selectedItem;
    }

    private static int selection = 0;
    private static String itemToDelete = null;
    private static City[] favouriteCities = new City[3];

    public static String getItemToDelete() {
        return itemToDelete;
    }

    public static void setItemToDelete(String itemToDelete) {
        WeatherController.itemToDelete = itemToDelete;
    }

    private final static String url = "http://api.openweathermap.org/data/2.5/weather";
    private final static String api_key = "efee5d88e8cbb8eaf5fadbddd4f39166";

    public static int getSelection() {
        return selection;
    }

    public static void setSelection(int selection) {
        WeatherController.selection = selection;
    }

    public static City[] getFavouriteCities() {
        return favouriteCities;
    }

    public static void setFavouriteCity(City city, int index) {
        WeatherController.favouriteCities[index] = city;
    }
}
