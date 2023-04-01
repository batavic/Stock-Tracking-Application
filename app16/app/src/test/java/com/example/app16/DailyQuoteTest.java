package com.example.app16;



import com.example.app16.ui.main.DailyQuote;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DailyQuoteTest {

    DailyQuote dq = new DailyQuote("AAPL", "2020-10-09", "130", "189", "124", "150", "148", "114");

    @Test
    public void getSymbolCorrect() {

        assertEquals("AAPL", dq.getSymbol());
    }

    @Test
    public void getDateCorect() {
        assertEquals("2020-10-09", dq.getDate());
    }

    @Test
    public void getOpenCorect() {
        assertEquals("130", dq.getOpen());
    }

    @Test
    public void getHighCorect() {
        assertEquals("189", dq.getHigh());
    }

    @Test
    public void getLowCorect() {
        assertEquals("124", dq.getLow());
    }

    @Test
    public void getCloseCorect() {
        assertEquals("150", dq.getClose());
    }

    @Test
    public void getAdjcloseCorect() {
        assertEquals("148", dq.getAdjClose());
    }


    @Test
    public void getVolumeCorect() {
        assertEquals("114", dq.getVolume());
    }


}