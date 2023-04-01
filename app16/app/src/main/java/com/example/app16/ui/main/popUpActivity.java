package com.example.app16.ui.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.app16.R;

public class popUpActivity implements PopUpInterface {
    private static Button cancel;
    private static EditText text;
    private static Context myContext;
    private static View _v;

    public popUpActivity(Context myContext, View _v){
        this.myContext = myContext;
        this._v = _v;
    }

    public void showPopUp(String error){

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View popUpView = inflater.inflate(R.layout.popup_window, null);
        cancel = popUpView.findViewById(R.id.cancel);
        text = popUpView.findViewById(R.id.text);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        text.setText(error);
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popUpView, width, height, focusable);

        popupWindow.showAtLocation(_v, Gravity.CENTER, 0, 0);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}

interface PopUpInterface{
    void showPopUp(String error);
}
