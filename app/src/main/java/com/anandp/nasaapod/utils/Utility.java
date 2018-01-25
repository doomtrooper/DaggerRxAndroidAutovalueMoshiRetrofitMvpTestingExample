package com.anandp.nasaapod.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Anand Parshuramka on 25/01/18.
 */

public class Utility {

    public static int comapreDates(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            return sdf.parse(date1).compareTo(sdf.parse(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
