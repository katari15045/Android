package com.github.katari15045.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Screen screen = getScreenDimensions();
        if(screen.width >= 1000){
            addTwoFragments(new FragmentOne(), new FragmentTwo());
        }
        else {
            addOneFragment(new FragmentOne());
        }
        Log.d("SAK : ", "onCreate()");
    }

    private void addOneFragment(FragmentOne fragmentOne){
        // Don't use transaction.add(); It may result in duplicate fragments in some scenarios
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_layout, fragmentOne);
        transaction.commit();
    }

    private void addTwoFragments(FragmentOne fragmentOne, FragmentTwo fragmentTwo){
        // If you use transaction.add() and if screen is rotated -
        // you get 2 fragmentOnes & 1 fragmentTwo - undesirable
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_layout, fragmentOne);
        transaction.add(R.id.activity_main_layout, fragmentTwo);
        transaction.commit();
    }

    private Screen getScreenDimensions(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Screen screen = new Screen();
        screen.width = displayMetrics.widthPixels;
        screen.height = displayMetrics.heightPixels;
        return  screen;
    }
}

class Screen{
    int height;
    int width;
}
