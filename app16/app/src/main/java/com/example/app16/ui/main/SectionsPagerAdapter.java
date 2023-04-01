package com.example.app16.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app16.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
  private static final String[] TAB_TITLES = new String[]{ "Welcome", "Single Share Graph", "Historical Data", "Double Share Graph" };
    private final Context myContext;
    SingleSymbolInterface singleSymbolInterface;
    DoubleSymbolInterface doubleSymbolInterface;
    WelcomeInterface welcomeInterface;
    HistoricalInterface historicalInterface;

  public SectionsPagerAdapter(Context context, FragmentManager fm)
  { super(fm);
    myContext = context;
    singleSymbolInterface = new SingleSymbolFragment(myContext);
    doubleSymbolInterface = new  DoubleSymbolFragment(myContext);
    welcomeInterface = new WelcomeFragment(myContext);
    historicalInterface = new HistoricalFragment(myContext);
  }

  @Override
  public Fragment getItem(int position)
  { // instantiate a fragment for the page.
    if (position == 0)
    { return welcomeInterface.getInstance(); }
    else if (position == 1)
    { return singleSymbolInterface.getInstance();}
    else if (position == 2)
    { return historicalInterface.getInstance(); }
    else
      return  doubleSymbolInterface.getInstance();
  }

  @Nullable
 @Override
  public CharSequence getPageTitle(int position) 
  { return TAB_TITLES[position]; }

  @Override
  public int getCount()
  { return 4; }
}
