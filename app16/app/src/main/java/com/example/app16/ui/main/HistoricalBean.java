package com.example.app16.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class HistoricalBean implements HistoricalBeanInterface{

    private String symbol = "";
    private String date = "";
    private ArrayList<String> errors = new ArrayList();
    DAOInterface dao = new DailyQuote_DAO();
    DateComponentSingleInterface DateComponentInterface = new DateComponent();

    public HistoricalBean(Context _c) {}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setdata(String symbol, String date){
        this.symbol = symbol;
        this.date = date;
        errors.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isError()
    {
        if(!DateComponentInterface.valid(date)) {errors.add("Invalid Date");}
        if(dao.getCursor(symbol, date) == null) {errors.add("symbol Information not stored");}
        return errors.size() > 0;
    }

    public ArrayList<String> errors() { return errors; }
}

interface HistoricalBeanInterface{
    void setdata(String symbol, String date);
    boolean isError();
    ArrayList<String> errors();
}

