package com.theappwelt.rmb.utilities;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Method {

    public int mYear;
    public int mMonth;
    public int mDay;
    public String months;

    //Normal convert
    public static String convertDateTimeFormatApp(String date, String formatFrom, String formatTo) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat(formatFrom, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getDefault());
        Date convertedDate = null;
        try {
            convertedDate = sdf.parse(date);
            convertedDate.setHours(convertedDate.getHours()+2);
          
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatTo, Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(convertedDate);
    }


    public static String cale(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date2 = c.get(Calendar.DATE);

        String value = year+"-"+month+"-"+date2;
        Log.i("TAG", "ytuiocale: "+year+"-"+month+"-"+date2);

    //    Log.i("cmndkkdm", "cale: "+c);

        return value;

    }



}
