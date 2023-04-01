package com.example.app16;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void checkLaunchFindWelcome(){
        onView(withText("Welcome to Shares Analyzer")).check(matches(isDisplayed()));
    }

    @Test
    public void checkLaunchFindSharesTab(){

        onView(withText("Double Share Graph")).check(matches(isDisplayed()));
    }

    @Test
    public void checkLaunchFindSingleShareTab(){

        onView(withText("Single Share Graph")).check(matches(isDisplayed()));
    }

    @Test
    public void checkLaunchFindHistoricalTab(){

        onView(withText("Historical Data")).check(matches(isDisplayed()));
    }

    @Test
    public void checkLaunchClickHistoricalTab(){

        onView(withText("Historical Data")).perform(click());
    }

    @Test
    public void checkLaunchClickShareTab(){

        onView(withText("Single Share Graph")).perform(click());
    }

    @Test
    public void checkLaunchClickSharesTab(){

        onView(withText("Double Share Graph")).perform(click());
    }


}