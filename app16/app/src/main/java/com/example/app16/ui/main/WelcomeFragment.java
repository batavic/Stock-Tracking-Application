package com.example.app16.ui.main;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app16.R;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;


public class WelcomeFragment extends Fragment implements WelcomeInterface
{   View root;
    Context myContext;

    ImageView _result;
    WelcomeVOInterface welcomeVOInterface;

    public WelcomeFragment(Context c) {
        Bundle args = new Bundle();
        this.setArguments(args);
        this.myContext = c;
    }

    public WelcomeFragment getInstance() { return this; }

    @Override
    public void onCreate(Bundle savedInstanceState)
    { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { root = inflater.inflate(R.layout.welcome_layout, container, false);
        Bundle data = getArguments();
        _result = root.findViewById(R.id.welcomePanel);
        welcomeVOInterface = new WelcomeVO();
        displayWelcome();
        return root;
    }

    private void displayWelcome() {
        GraphDisplay graph = welcomeVOInterface.getGraph();
        _result.invalidate();
        _result.refreshDrawableState();
        _result.setImageDrawable(graph);
    }
}

interface WelcomeInterface{
    WelcomeFragment getInstance();
}