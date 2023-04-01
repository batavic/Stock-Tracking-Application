package com.example.app16.ui.main;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

public class  DoubleSymbolBean implements  DoubleSymbolBeanInterface
{ ModelFacadeInterface model = null;
  DateComponentIntervalInterface DateComponentInterface = new DateComponent();

  private String firstShare = "";

  private String secondShare = "";

  private ArrayList<String> errors = new ArrayList();
  ArrayList<String> dateInterval = new ArrayList();

  public  DoubleSymbolBean(Context _c) { model = new ModelFacade(_c); }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void setdata(String... list){
    firstShare = list[0];
    secondShare = list[1];
    dateInterval = DateComponentInterface.getInterval(list[2], list[3]);
  }

  public void resetData()
  {
    firstShare = "";
    secondShare = "";
    dateInterval.clear();
    model.cancel();
    errors.clear();
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean isError()
  {
    if(dateInterval.isEmpty()) errors.add("Invalid Date Interval");
    if(!checkSymbols()) errors.add("Invalid Symbol");
    return errors.size() > 0;
  }

  public ArrayList<String> errors() { return errors; }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private boolean checkSymbols(){
    return model.searchSymbols(new ArrayList<String>(){{add(firstShare); add(secondShare);}}, dateInterval);
  }

}

interface DoubleSymbolBeanInterface{
  void setdata(String... param);
  void resetData();
  boolean isError();
  ArrayList<String> errors();
}

