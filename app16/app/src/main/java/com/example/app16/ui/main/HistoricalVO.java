package com.example.app16.ui.main;

import android.database.Cursor;

public class HistoricalVO implements HistoricalVOInterface {

    private static String symbol;
    private static String date;

    private static String open;
    private static String high;
    private static String low;
    private static String close;
    private static String adjclose;
    private static String volume;

    private static Cursor cursor;

    DAOInterface dao = new DailyQuote_DAO();

    public HistoricalVO(String symbol, String date){
        this.symbol = symbol;
        this.date = date;
        cursor = setCursor();
        setAllData();
    }

    private Cursor setCursor(){
        return dao.getCursor(symbol, date);
    }

    private void setOpen(){
        open = dao.getCachedOpen(cursor);
    }

    private void setHigh(){
        high = dao.getCachedHigh(cursor);
    }

    private void setLow(){
        low = dao.getCachedLow(cursor);
    }

    private void setClose(){
        close = String.valueOf(dao.getCachedClose(cursor));
    }

    private void setAdjClose(){
        adjclose = String.valueOf(dao.getCachedAdjClose(cursor));
    }

    private void setVolume(){
        volume = String.valueOf(dao.getCachedVolume(cursor));
    }

    private void setAllData(){
        setOpen();
        setHigh();
        setLow();
        setClose();
        setAdjClose();
        setVolume();
        cursor.close();
    }

    public String getOpen(){
        return open;
    }

    public String getHigh(){
        return high;
    }

    public String getLow(){
        return low;
    }

    public String getClose(){
        return close;
    }

    public String getAdjClose(){
        return adjclose;
    }

    public String getVolume(){
        return volume;
    }
}

interface HistoricalVOInterface{
    String getOpen();
    String getHigh();
    String getLow();
    String getClose();
    String getAdjClose();
    String getVolume();
}
