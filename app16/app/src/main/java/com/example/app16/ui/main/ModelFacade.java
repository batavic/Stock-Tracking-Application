package com.example.app16.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.AsyncTask.Status;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ModelFacade
        implements InternetCallback, ModelFacadeInterface {
  Context myContext;
  static ModelFacade instance = null;
  DAOInterface dao = new DailyQuote_DAO();
  GraphDisplayInterface _result = GraphDisplay.defaultInstance();
  SingleSymbolInterface singleSymbolInterface = new SingleSymbolFragment(myContext);
  DoubleSymbolInterface doubleSymbolInterface = new DoubleSymbolFragment(myContext);
  ServiceActivatorInterface serviceActivatorInterface;

  boolean running = true;


  public static ModelFacade getInstance(Context context) {
    if (instance == null) {
      instance = new ModelFacade(context);
    }
    return instance;
  }

  public ModelFacade(Context context) {
    myContext = context;
    dao.setContext(myContext);
  }

  public void internetAccessCompleted(String response) {
    if (response != "") {
      DailyQuoteInterface dailyQuote = dao.createEntry(response);
      _result.addY(Double.parseDouble(dailyQuote.getClose()));
      updateScreen();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private boolean getSymbol(String symbol, String date) {
    Cursor cursor = dao.getCursor(symbol, date);
    if (cursor == null) {
      try {
        serviceActivatorInterface = new InternetAccessor(this);
        serviceActivatorInterface.execute(symbol, date);
      } catch (Exception e) { return false; }
    }
    else {
      double close = dao.getCachedClose(cursor);
      cursor.close();
      _result.addY(close);
      updateScreen();
    }

    return true;
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean searchSymbols(ArrayList<String> symbols, ArrayList<String> dateInterval) {
    _result.setSize(dateInterval.size());
    for (String date : dateInterval) {
      _result.addXLabel(date);
      for (String symbol : symbols) {
        if (!getSymbol(symbol, date)) return false;
      }
    }
    return true;
  }

  public void cancel() {
    running = false;
  }

  private void updateScreen() {
    if (_result.full()) {
      if (_result.symbols_number()) updateGraph2();
      else updateGraph1();
    }
  }

  private void updateGraph1() {
    if (running) {
      singleSymbolInterface.getInstance().updateImage(_result.getInstance());
    }
  }

  private void updateGraph2() {
    if (running) {
      for (int i = 0; i < _result.Ysize(); i++) {
        if (i % 2 == 1) {
          _result.addZ(_result.getY(i));
          _result.removeY(i);
        }
      }
      doubleSymbolInterface.getInstance().updateImage(_result.getInstance());
    }
  }
}

interface ModelFacadeInterface{
  void cancel();
  boolean searchSymbols(ArrayList<String> symbols, ArrayList<String> dateInterval);
}

interface InternetCallback
{ void internetAccessCompleted(String response); }