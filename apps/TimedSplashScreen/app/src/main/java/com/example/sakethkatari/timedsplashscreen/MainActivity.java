package com.example.sakethkatari.timedsplashscreen;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customizeActionBar();
        customizeStatusBar();
        new Handler().postDelayed(new MyThread(), 5000);
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            int blue = ContextCompat.getColor(MainActivity.this, R.color.blue);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(blue);
        }
    }

    private class MyThread implements Runnable
    {
        private Intent intent;

        @Override
        public void run()
        {
            intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
