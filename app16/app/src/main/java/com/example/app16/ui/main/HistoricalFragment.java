package com.example.app16.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.app16.R;

import org.json.JSONException;

import java.io.IOException;

public class HistoricalFragment extends Fragment implements View.OnClickListener, HistoricalInterface{

    View root;
    Context myContext;
    HistoricalBeanInterface historicalBean;
    HistoricalVOInterface historicalVO;
    PopUpInterface popUpInterface;

    EditText dateField;
    EditText symbolField;
    String date = "";
    String symbol = "";
    Button search;
    Button cancel;

    private static TextView open;
    private static TextView high;
    private static TextView low;
    private static TextView close;
    private static TextView adjclose;
    private static TextView volume;

    public HistoricalFragment(Context c) {
        Bundle args = new Bundle();
        this.setArguments(args);
        this.myContext = c;
    }

    public HistoricalFragment getInstance() { return this; }

    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { root = inflater.inflate(R.layout.historical_layout, container, false);
        Bundle data = getArguments();
        dateField = (EditText) root.findViewById(R.id.dateField);
        symbolField = (EditText) root.findViewById(R.id.symbolField);
        open = (TextView) root.findViewById(R.id.openField);
        high = (TextView) root.findViewById(R.id.highField);
        low = (TextView) root.findViewById(R.id.lowField);
        close = (TextView) root.findViewById(R.id.adjcloseField);
        adjclose = (TextView) root.findViewById(R.id.volumeField);
        volume = (TextView) root.findViewById(R.id.closeField);
        historicalBean = new HistoricalBean(myContext);
        search = root.findViewById(R.id.search);
        search.setOnClickListener(this);
        cancel = root.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View _v) {
        InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
        if (_v.getId() == R.id.search)
        {
            try {
                search(_v);
            } catch (IOException e) {
                cancel(_v);
            } catch (JSONException e) {
                cancel(_v);
            }
        }
        else if (_v.getId() == R.id.cancel)
        { cancel(_v); }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void search(View _v) throws IOException, JSONException {
        date = dateField.getText() + "";
        symbol = symbolField.getText() + "";
        historicalBean.setdata(symbol, date);
        if (historicalBean.isError()) {
            for(String error: historicalBean.errors()) {
                popUpInterface = new popUpActivity(myContext, _v);
                popUpInterface.showPopUp(error);
            }
            cancel(_v);
        }
        else {
            historicalVO = new HistoricalVO(symbol, date);
            updateText(historicalVO.getOpen(), historicalVO.getHigh(), historicalVO.getLow(), historicalVO.getClose(), historicalVO.getAdjClose(), historicalVO.getVolume());
        }
    }

    public static void updateText(String start, String end, String big, String small, String adj, String vol){
            open.setText(start);
            high.setText(big);
            low.setText(small);
            close.setText(end);
            adjclose.setText(adj);
            volume.setText(vol);
    }

    public void cancel(View _v)
    {
        dateField.setText("");
        symbolField.setText("");
        open.setText("");
        high.setText("");
        low.setText("");
        close.setText("");
        adjclose.setText("");
        volume.setText("");
    }
}

interface HistoricalInterface{
    HistoricalFragment getInstance();
}
