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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int violet;
    int darkViolet;
    ActionBar actionBar;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeClassObjects();

        customizeAppBar();
        customizeStatusBar();
    }

    private void initializeClassObjects()
    {
        violet = ContextCompat.getColor(MainActivity.this, R.color.violet);
        darkViolet = ContextCompat.getColor(MainActivity.this, R.color.darkViolet);
        actionBar = getSupportActionBar();
    }

    private void customizeAppBar()
    {
        actionBar.setTitle("Main Title");
        actionBar.setSubtitle("Subtitle");
        setBackgroundColor();
        setLogo();
    }

    private void customizeStatusBar()
    {
        if (android.os.Build.VERSION.SDK_INT >= 21)
        {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(darkViolet);
        }
    }

    private void setBackgroundColor()
    {
        actionBar.setBackgroundDrawable( new ColorDrawable(violet) );
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
