package com.example.app16.ui.main;

import java.io.IOException;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

public class SingleSymbolBean implements SingleSymbolBeanInterface
{ ModelFacadeInterface model;
  DateComponentIntervalInterface DateComponentInterface = new DateComponent();

  private String symbol = "";
  private ArrayList<String> errors = new ArrayList();
  ArrayList<String> dateInterval;

  public SingleSymbolBean(Context _c) { model = new ModelFacade(_c); }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public void setdata(String... list){
    this.symbol = list[0];
    dateInterval = DateComponentInterface.getInterval(list[1], list[2]);
  }

  public void resetData()
  { model.cancel();
    symbol = "";
    dateInterval.clear();
    errors.clear();}


  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean isError()
  {
    if(dateInterval.isEmpty()) {errors.add("Invalid Date");}
    if(!checkSymbols()) errors.add("Invalid Symbol");
    return errors.size() > 0;
  }

  public ArrayList<String> errors() { return errors; }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private boolean checkSymbols(){
    return model.searchSymbols(new ArrayList<String>(){{add(symbol);}}, dateInterval);
  }

}

interface SingleSymbolBeanInterface{
  void setdata(String... param);
  void resetData();
  boolean isError();
  ArrayList<String> errors();
}

