package com.example.app16.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class InternetAccessor extends AsyncTask<String, Void, String> implements ServiceActivatorInterface
{  private InternetCallback delegate;
    private boolean running = true;

    public InternetAccessor(InternetCallback delegate){
        this.delegate = delegate;
    }

    public void execute(String symbol, String date){
        super.execute(symbol, date);
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onCancelled(){
        running = false;
    }

    public void cancel(){ onCancelled();}

    @Override
    protected String doInBackground(String... list)
    {
        String result = "";

        while(running) {

            String symbol = list[0];
            result = result + symbol + " ";

            String date = list[1];
            result = result + date + " ";

            String inputLine;

            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + symbol + "&outputsize=full&apikey=X2T69LY2NKPMJVT2";

            try {

                URL api = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) api.openConnection();
                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                String prelResult = stringBuilder.toString();
                JSONObject obj = new JSONObject(prelResult);

                String series = "Time Series (Daily)";

                JSONObject json = obj.getJSONObject(series).getJSONObject(date);

                String open = obj.getJSONObject(series).getJSONObject(date).get("1. open").toString();
                result = result + open + " ";

                String high = json.get("2. high").toString();
                result = result + high + " ";

                String low = json.get("3. low").toString();
                result = result + low + " ";

                String close = json.get("4. close").toString();
                result = result + close + " ";

                String adjclose = json.get("5. adjusted close").toString();
                result = result + adjclose + " ";

                String volume = json.get("6. volume").toString();
                result = result + volume;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result; // string with symbol, date, open, high, low, close, adjclose, volume
        }

        return result;
    }


    @Override
    protected void onPostExecute(String result) {
            delegate.internetAccessCompleted(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {}
}

interface ServiceActivatorInterface{
    void execute(String symbol, String date);
    void cancel();
}
