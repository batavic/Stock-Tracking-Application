package com.example.app16.ui.main;

import java.util.ArrayList;

public class WelcomeVO implements WelcomeVOInterface{
    public WelcomeVO() {}

    public GraphDisplay getGraph(){
        OclInterface OclInterface = new Ocl();
        GraphDisplayInterface result = new GraphDisplay();
        ArrayList<Double> xlabels = null;
        xlabels = OclInterface.copySequence(OclInterface.initialiseSequence(10.0, 20.0, 30.0, 40.0, 50.0));
        ArrayList<Double> ypoints = null;
        ypoints = OclInterface.copySequence(OclInterface.initialiseSequence(10.0, 20.0, 30.0, 40.0, 50.0));
        result.setPoints(xlabels, ypoints);
        return result.getInstance();
    }
}

interface WelcomeVOInterface{
    GraphDisplay getGraph();
}