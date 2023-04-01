package com.example.app16.ui.main; 

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Date; 
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 


public class DateComponent implements DateComponentIntervalInterface, DateComponentSingleInterface
{ public static long getEpochSeconds(String date)
  { DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    try
	{ Date d = df.parse(date); 
      long time = d.getTime()/1000; 
	  return time;
	} catch (Exception _e) { return -1; } 
  }

  public static long getEpochMilliseconds(String format, String date)
  { DateFormat df = new SimpleDateFormat(format); 
    try
	{ Date d = df.parse(date); 
      long time = d.getTime(); 
	  return time;
	} catch (Exception _e) { return -1; } 
  }

  public boolean valid(String date){

      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

      try {
          Calendar border = Calendar.getInstance();
          border.add(Calendar.YEAR, -2);
          border.add(Calendar.DATE, -1);

          Calendar Date = Calendar.getInstance();
          Date.setTime(df.parse(date));

          if(Date.after(border)) return true;
          else return false;
      } catch (Exception e) {return false;}

  }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getInterval(String dateFrom, String dateTo){

        ArrayList<String> weeklyInterval = new ArrayList<String>();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try
        {   Calendar border = Calendar.getInstance();
            border.add(Calendar.YEAR, -2);
            border.add(Calendar.DATE, -1);

            Calendar dFrom = Calendar.getInstance();
            dFrom.setTime(df.parse(dateFrom));

            Calendar dTo = Calendar.getInstance();
            dTo.setTime(df.parse(dateTo));

            if(dFrom.after(border) && dTo.after(border)){
                while (dFrom.before(dTo)) {
                    weeklyInterval.add(df.format(dFrom.getTime()));
                    if(dFrom.get(Calendar.MONTH) == Calendar.DECEMBER && dFrom.getActualMaximum(Calendar.DAY_OF_MONTH) == dFrom.get(Calendar.DATE)){
                        dFrom.add(Calendar.YEAR, 1);
                        dFrom.set(Calendar.MONTH, 1);
                        dFrom.set(Calendar.DATE, 1);
                    }
                    else if(dFrom.getActualMaximum(Calendar.DAY_OF_MONTH) == dFrom.get(Calendar.DATE)){
                        dFrom.add(Calendar.MONTH, 1);
                        dFrom.set(Calendar.DATE, 1);
                    }
                    else { dFrom.add(Calendar.DATE, 1); }
                }
            }
        } catch (Exception _e) { return weeklyInterval; }

        return weeklyInterval;
    }
}

interface DateComponentIntervalInterface{
    ArrayList<String> getInterval(String dateFrom, String dateTo);
}

interface DateComponentSingleInterface{
    boolean valid(String date);
}