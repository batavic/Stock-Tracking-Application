package com.example.app16;

import com.example.app16.ui.main.DateComponent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateComponentTest {

    String date = "2021-10-09";
    DateComponent dc = new DateComponent();

    @Test
    public void getEpochsSecondsCorrect() {

        long newDc = dc.getEpochSeconds(date);

        assertEquals(1633726800, newDc);
    }

    @Test
    public void checkValidCorrect() {

        assertEquals(true, dc.valid(date));
    }

}