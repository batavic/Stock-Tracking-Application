package com.example.app16.ui.main;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseStock extends SQLiteOpenHelper implements DatabaseStockInterface{

    public static final String LOG_TAG = DatabaseStock.class.getSimpleName();

    public static final String STOCKS_TABLE = "STOCKS_TABLE";
    public static final String STOCK_SYMBOL = "STOCK_SYMBOL";
    public static final String STOCK_DATE = "STOCK_DATE";
    public static final String STOCK_OPEN = "STOCK_OPEN";
    public static final String STOCK_HIGH = "STOCK_HIGH";
    public static final String STOCK_LOW = "STOCK_LOW";
    public static final String STOCK_CLOSE = "STOCK_CLOSE";
    public static final String STOCK_ADJCLOSE = "STOCK_ADJCLOSE";
    public static final String STOCK_VOLUME = "STOCK_VOLUME";
    public static final String STOCK_ID = "STOCK_ID";

    public DatabaseStock(@Nullable Context context) {
        super(context, "StockDatabase.db", null, 1);
        //this.context=context;
    }

    //first time database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String createTableStatement = "CREATE TABLE " + STOCKS_TABLE + " (" + STOCK_SYMBOL + " INTEGER, " + STOCK_DATE + " TEXT, " + STOCK_OPEN + " TEXT, " + STOCK_HIGH + " TEXT, " + STOCK_LOW + " TEXT, " + STOCK_CLOSE + " TEXT, " + STOCK_ADJCLOSE + " TEXT, " + STOCK_VOLUME + " TEXT)";
            db.execSQL(createTableStatement);
        } catch (Exception e) {
            System.out.println("Sorry :(");
        }

    }

    //forward compatibility

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void newEntry(DailyQuoteInterface quote){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(STOCK_SYMBOL, quote.getSymbol());
        cv.put(STOCK_DATE, quote.getDate());
        cv.put(STOCK_OPEN, quote.getOpen());
        cv.put(STOCK_HIGH, quote.getHigh());
        cv.put(STOCK_LOW, quote.getLow());
        cv.put(STOCK_CLOSE, quote.getClose());
        cv.put(STOCK_ADJCLOSE, quote.getAdjClose());
        cv.put(STOCK_VOLUME, quote.getVolume());

        db.insert(STOCKS_TABLE, null, cv);

    }

    public String getStockCol(){return STOCK_SYMBOL;}
    public String getDateCol(){return STOCK_DATE;}
    public String getOpenCol(){return STOCK_OPEN;}
    public String getHighCol(){return STOCK_HIGH;}
    public String getLowCol(){return STOCK_LOW;}
    public String getCloseCol(){return STOCK_CLOSE;}
    public String getAdjCloseCol(){return STOCK_ADJCLOSE;}
    public String getVolCol(){return STOCK_VOLUME;}
    public String getTable(){return STOCKS_TABLE; }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }
}

interface DatabaseStockInterface{
    String getStockCol();
    String getDateCol();
    String getOpenCol();
    String getHighCol();
    String getLowCol();
    String getCloseCol();
    String getAdjCloseCol();
    String getVolCol();
    String getTable();
    SQLiteDatabase getReadableDatabase();
    void newEntry(DailyQuoteInterface quote);
}
