package com.example.root.customgridview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by root on 24/2/17.
 */

public class MyAdapter extends ArrayAdapter<String>
{
    ArrayList<String> listButtonLabels;
    ArrayList<Integer> listColors;
    Activity activity;
    Context context;

    Button buttonOne;
    Button buttonTwo;

    public MyAdapter(Activity inpActivity, Context inpContext, ArrayList<String> inpListButtonLabels, ArrayList<Integer> inpListColors )
    {
        super(inpActivity, R.layout.grid_item, inpListButtonLabels);
        activity = inpActivity;
        context = inpContext;
        listButtonLabels = inpListButtonLabels;
        listColors = inpListColors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater myLayoutInflater = activity.getLayoutInflater();
        View gridItemView = myLayoutInflater.inflate(R.layout.grid_item, null, true);
        final int finalPosition = position;
        final View finalGridItem = gridItemView;


        buttonOne = (Button) gridItemView.findViewById(R.id.buttonOne);
        buttonTwo = (Button) gridItemView.findViewById(R.id.buttonTwo);

        buttonOne.setText( listButtonLabels.get(position) );
        buttonTwo.setText( buttonOne.getText() );
        gridItemView.setBackgroundColor( listColors.get(position) );

        handleButtonClicks(finalPosition, gridItemView);

        return  gridItemView;
    }

    private void handleButtonClicks(final int position, final View gridItemView)
    {
        buttonOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridItemView.setBackgroundColor( listColors.get(position) );
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gridItemView.setBackgroundColor( ContextCompat.getColor(context, R.color.white) );
            }
        });
    }

}
