package com.example.saketh.add2numbers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{

    private EditText et1,et2;
    private Button b;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText) findViewById(R.id.EditTextOne);
        et2 = (EditText) findViewById(R.id.EditTextTwo);
        b = (Button) findViewById(R.id.ButtonOne);
        tv = (TextView) findViewById(R.id.TextViewThree);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int firstNumber = Integer.parseInt(et1.getText().toString());
                int secondNumber = Integer.parseInt(et2.getText().toString());
                int result = firstNumber + secondNumber;

                tv.setText("Result : " + result);
            }
        });

    }
}
