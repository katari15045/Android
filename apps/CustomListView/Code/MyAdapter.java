package com.example.saketh.customlistview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String>
{
    private Activity context;
    private List<Integer> listImages;
    private List<String> listNames;

    public MyAdapter(Activity inp_context,List<Integer> inp_images,List<String> inp_names)
    {
        super(inp_context,R.layout.activity_my_adapter,inp_names);
        context = inp_context;
        listImages = inp_images;
        listNames = inp_names;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater myLayoutInflater = context.getLayoutInflater();
        View singleRow = myLayoutInflater.inflate(R.layout.activity_my_adapter, null, true);

        TextView myTextView = (TextView) singleRow.findViewById(R.id.textView);
        ImageView myImageView = (ImageView) singleRow.findViewById(R.id.imageView);

        myTextView.setText( listNames.get(position) );
        myImageView.setImageResource( listImages.get(position) );

        return  singleRow;
    }
}
