package com.example.app16.ui.main;
import android.content.Context;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.util.Log;
import android.util.Pair;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app16.MainActivity;

import java.util.*;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Date; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;
import android.database.Cursor;
import android.app.Fragment;

//a function similar to isCached that return true if (share, date) is in db, false otherwise
//a function to return price of a given (share, date) from db
//execution:
// 1. send string to makeFromCSV
// 2. make new DailyQuote via parseCSV

public class DailyQuote_DAO implements DAOInterface
{

  private static Context myContext;
  DatabaseStockInterface db;

  public void setContext(Context context){
    myContext = context;
  }

  public Cursor getCursor(String symbol, String date){

    db = new DatabaseStock(myContext);
    SQLiteDatabase database = db.getReadableDatabase();

    //String[] columns = new String[]{ db.STOCK_OPEN, db.STOCK_HIGH, db.STOCK_LOW, db.STOCK_CLOSE, db.STOCK_ADJCLOSE, db.STOCK_VOLUME};
    //String where = db.STOCK_SYMBOL + " LIKE ? AND "+ db.STOCK_DATE + " LIKE ? ";
    String[] columns = new String[]{ db.getOpenCol(), db.getHighCol(), db.getLowCol(), db.getCloseCol(), db.getAdjCloseCol(), db.getVolCol()};
    String where = db.getStockCol() + " LIKE ? AND "+ db.getDateCol() + " LIKE ? ";
    String[] whereArgs = new String[]{symbol, date};

    Cursor cursor = null;
    try {if (db == null) {

      db.getReadableDatabase();

    }
     // cursor = database.query(db.STOCKS_TABLE, columns, where, whereArgs, null, null, null);
      cursor = database.query(db.getTable(), columns, where, whereArgs, null, null, null);
    } catch (Exception e) {
      return null;
    }
    if(cursor.getCount() == 0) { return null; }
    else return cursor;
  }

  //what are prices? are they daily prices? what are daily prices?
  //put in a date -> gives out all prices of that day?
  //or put in a specific date and time -> gives out that specific one price for that period?
  public double getCachedClose(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_CLOSE);
      int indexLocation = cursor.getColumnIndex(db.getCloseCol());
      return cursor.getInt(indexLocation);
    }
    else {
      return -1;
    }
  }

  public String getCachedSymbol(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_SYMBOL);
      int indexLocation = cursor.getColumnIndex(db.getStockCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  public String getCachedOpen(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_OPEN);
      int indexLocation = cursor.getColumnIndex(db.getOpenCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  public String getCachedHigh(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_HIGH);
      int indexLocation = cursor.getColumnIndex(db.getHighCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  public String getCachedLow(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_LOW);
      int indexLocation = cursor.getColumnIndex(db.getLowCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  public String getCachedAdjClose(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_ADJCLOSE);
      int indexLocation = cursor.getColumnIndex(db.getAdjCloseCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  public String getCachedVolume(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_VOLUME);
      int indexLocation = cursor.getColumnIndex(db.getVolCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  public String getCachedDate(Cursor cursor)
  {
    if(cursor.moveToFirst()){
      cursor.moveToFirst();
      //int indexLocation = cursor.getColumnIndex(DatabaseStock.STOCK_DATE);
      int indexLocation = cursor.getColumnIndex(db.getDateCol());
      return cursor.getString(indexLocation);
    }
    else {
      return "";
    }
  }

  private static DailyQuoteInterface parseNewCSV(String line)
  {
    if (line == null) { return null; }
    String[] separated = line.split(" ");

    String symbol = separated[0];
    String date = separated[1];
    if(separated.length > 2){
      String open = separated[2];
      String high = separated[3];
      String low =separated[4];
      String close = separated[5];
      String adjclose = separated[6];
      String volume = separated[7];
      DailyQuoteInterface dq = new DailyQuote(symbol, date, open, high, low, close, adjclose, volume);
      return dq;
    }
    else{
      String open = "0";
      String high = "0";
      String low ="0";
      String close = "0";
      String adjclose = "0";
      String volume = "0";
      DailyQuoteInterface dq = new DailyQuote(symbol, date, open, high, low, close, adjclose, volume);
      return dq;
    }

    //DailyQuoteInterface dq = new DailyQuote(symbol, date, open, high, low, close, adjclose, volume);

  }


  public DailyQuoteInterface createEntry(String input){
    DailyQuoteInterface dq = parseNewCSV(input);
    db.newEntry(dq);
    return dq;
  }

}

interface DAOInterface {
  void setContext(Context context);
  Cursor getCursor(String symbol, String date);
  String getCachedSymbol(Cursor cursor);
  String getCachedDate(Cursor cursor);
  String getCachedOpen(Cursor cursor);
  String getCachedHigh(Cursor cursor);
  String getCachedLow(Cursor cursor);
  double getCachedClose(Cursor cursor);
  String getCachedAdjClose(Cursor cursor);
  String getCachedVolume(Cursor cursor);
  DailyQuoteInterface createEntry(String input);
}
