package com.example.app16.ui.main;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;

import java.io.IOException;
import java.io.InputStream;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app16.R;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import android.view.View.OnClickListener;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONException;


public class SingleSymbolFragment extends Fragment implements OnClickListener, SingleSymbolInterface
{
    View root;
    Context myContext;
    SingleSymbolBeanInterface bean;
    PopUpInterface popUpInterface;

    EditText dateFromTextField;
    EditText dateToTextField;
    EditText symbolTextField;
    String dateFromData = "";
    String dateToData = "";
    String symbolData = "";
    private static ImageView result;
    Button okButton;
    Button cancelButton;


    public SingleSymbolFragment(Context c) {
        Bundle args = new Bundle();
        this.setArguments(args);
        this.myContext = c;
    }

    public SingleSymbolFragment getInstance() {return this;}

    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { root = inflater.inflate(R.layout.singlesymbol_layout, container, false);
        Bundle data = getArguments();
        dateFromTextField = (EditText) root.findViewById(R.id.dateFromField);
        dateToTextField = (EditText) root.findViewById(R.id.dateToField);
        symbolTextField = (EditText) root.findViewById(R.id.symbolField);
        result = (ImageView) root.findViewById(R.id.result);
        bean = new SingleSymbolBean(myContext);
        okButton = root.findViewById(R.id.oK);
        okButton.setOnClickListener(this);
        cancelButton = root.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
        return root;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View _v)
    { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
        if (_v.getId() == R.id.oK)
        {
            try {
                oK(_v);
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
    private void oK(View _v) throws IOException, JSONException {
        dateFromData = dateFromTextField.getText() + "";
        dateToData = dateToTextField.getText() + "";
        symbolData = symbolTextField.getText() + "";
        bean.setdata(symbolData, dateFromData, dateToData);
        if (bean.isError()) {
            for(String error: bean.errors()) {
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
        dateFromTextField.setText("");
        dateToTextField.setText("");
        symbolTextField.setText("");
        result.setImageDrawable(null);
    }
}

interface SingleSymbolInterface{
    SingleSymbolFragment getInstance();
    void updateImage(GraphDisplay graph);
}

