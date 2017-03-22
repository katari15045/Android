package com.example.root.customappbar;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int violet;
    ActionBar actionBar;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeClassObjects();

        actionBar.setTitle("Main Title");
        actionBar.setSubtitle("Subtitle");

        setBackgroundColor();
        setLogo();
    }

    private void initializeClassObjects()
    {
        violet = ContextCompat.getColor(MainActivity.this, R.color.violet);
        actionBar = getSupportActionBar();
    }

    private void setBackgroundColor()
    {
        actionBar.setBackgroundDrawable( new ColorDrawable( violet ));
    }

    private void setLogo()
    {
        ActionBar.LayoutParams layoutParams;

        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);
        logo = new ImageView(actionBar.getThemedContext());
        logo.setImageResource(R.drawable.ic_star);
        layoutParams = new ActionBar.LayoutParams(Gravity.RIGHT);
        logo.setLayoutParams(layoutParams);
        actionBar.setCustomView(logo);
    }
}
