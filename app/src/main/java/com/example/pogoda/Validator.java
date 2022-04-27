package com.example.pogoda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validator {

    public static boolean validateLatitude(String input){
        if(input.length() == 0)
            return false;
        double value = Double.parseDouble(input);
        return !(value > 90) && !(value < -90);
    }

    public static boolean validateLongitude(String input){
        if(input.length() == 0)
            return false;
        double value = Double.parseDouble(input);
        return !(value > 180) && !(value < -180);
    }

    public static boolean validateRefreshRatio(String input){
        if(input.length() == 0)
            return false;
        double value = Double.parseDouble(input);
        return !(value > 6000) && !(value < 5);
    }

}
