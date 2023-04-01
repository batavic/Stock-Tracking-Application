package com.example.app16.ui.main;


import androidx.annotation.RequiresApi;


import android.os.Build;
import android.os.Bundle;

import java.io.IOException;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app16.R;
import android.content.Context;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;

import org.json.JSONException;


public class DoubleSymbolFragment extends Fragment implements OnClickListener, DoubleSymbolInterface
{ View root;
    Context myContext;
    DoubleSymbolBeanInterface bean;
    PopUpInterface popUpInterface;

    EditText firstShareField;
    EditText secondShareField;
    EditText firstdateField;
    EditText seconddateField;
    private static ImageView result;
    Button okButton;
    Button cancelButton;

    String firstShare;
    String secondShare;
    String firstdate;
    String seconddate;


    public  DoubleSymbolFragment(Context c) {
        Bundle args = new Bundle();
        this.setArguments(args);
        this.myContext = c;
    }

    public  DoubleSymbolFragment getInstance() { return this; }

    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { root = inflater.inflate(R.layout.doublesymbol_layout, container, false);
        Bundle data = getArguments();
        firstShareField = (EditText) root.findViewById(R.id.firstShareField);
        secondShareField = (EditText) root.findViewById(R.id.secondShareField);
        firstdateField = (EditText) root.findViewById(R.id.firstdateField);
        seconddateField = (EditText) root.findViewById(R.id.seconddateField);
        result = (ImageView) root.findViewById(R.id.analyseResult);
        bean = new  DoubleSymbolBean(myContext);
        okButton = root.findViewById(R.id.analyseOK);
        okButton.setOnClickListener(this);
        cancelButton = root.findViewById(R.id.analyseCancel);
        cancelButton.setOnClickListener(this);
        return root;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View _v)
    { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
        if (_v.getId() == R.id.analyseOK)
        {
            try {
                oK(_v);
            } catch (IOException e) {
                cancel(_v);
            } catch (JSONException e) {
                cancel(_v);
            }
        }
        else if (_v.getId() == R.id.analyseCancel)
        { cancel(_v); }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void oK(View _v) throws IOException, JSONException {
        firstShare = firstShareField.getText() + "";
        secondShare = secondShareField.getText() + "";
        firstdate = firstdateField.getText() + "";
        seconddate = seconddateField.getText() + "";
        bean.setdata(firstShare, secondShare, firstdate, seconddate);
        if (bean.isError())
        {
            ArrayList<String> errors = bean.errors();
            for(String error: errors){
                popUpInterface = new popUpActivity(myContext, _v);
                popUpInterface.showPopUp(error);
            }
            cancel(_v);
        }
    }

    public void updateImage(GraphDisplay graph){
        result.invalidate();
        result.refreshDrawableState();
        result.setImageDrawable(graph);
    }

    private void cancel(View _v)
    {   bean.resetData();
        firstShareField.setText("");
        secondShareField.setText("");
        firstdateField.setText("");
        seconddateField.setText("");
        result.setImageDrawable(null);
    }
}

interface DoubleSymbolInterface{
    DoubleSymbolFragment getInstance();
    void updateImage(GraphDisplay graph);
}
