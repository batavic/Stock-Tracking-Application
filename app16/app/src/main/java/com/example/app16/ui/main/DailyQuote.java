package com.example.app16.ui.main;

import android.util.Pair;

import java.util.*;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collections;

public class DailyQuote implements DailyQuoteInterface{

  String date = "";
  String symbol = "";
  String open = "";
  String high = "";
  String low = "";
  String close = "";
  String adjclose = "";
  String volume = "";

  //static Map<Pair<String, String>, DailyQuote> DailyQuote_index = new HashMap<Pair<String, String>,DailyQuote>();

  public DailyQuote(String symbol, String date, String open, String high, String low, String close, String adjclose, String volume){
    this.symbol = symbol;
    this.date = date;
    this.open = open;
    this.high = high;
    this.low = low;
    this.close = close;
    this.adjclose = adjclose;
    this.volume = volume;

    //DailyQuote_index.put(Pair.create(symbol, date), this);
  }

  /*static DailyQuote createDailyQuote() {
    DailyQuote result = new DailyQuote();
    return result;
  }


  //static Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();

  static DailyQuote createByPKDailyQuote(String symbolx, String datex) {
    DailyQuote result = new DailyQuote();
    DailyQuote.DailyQuote_index.put(Pair.create(symbolx, datex), result);
    result.date = datex;
    result.symbol = symbolx;
    return result; }*/

  public String getSymbol(){ return symbol; }

  public String getDate(){ return date; }

  public String getOpen(){ return open;}

  public String getHigh(){ return high; }

  public String getLow(){ return low; }

  public String getClose(){ return close; }

  public String getAdjClose(){ return adjclose; }

  public String getVolume(){ return volume; }
}

interface DailyQuoteInterface{
  String getSymbol();
  String getDate();
  String getOpen();
  String getHigh();
  String getLow();
  String getClose();
  String getAdjClose();
  String getVolume();
}

