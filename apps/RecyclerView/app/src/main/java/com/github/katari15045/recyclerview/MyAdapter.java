package com.github.katari15045.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Saketh Katari on 22-02-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context = null;
    private LayoutInflater inflater = null;
    private View view = null;
    private MyViewHolder myViewHolder = null;
    private ArrayList<ListItem> database = null;

    public MyAdapter(Context context, ArrayList<ListItem> database){
        this.context = context;
        this.database = database;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.list_item, parent, false);
        myViewHolder = new MyViewHolder(view, context);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ListItem listItem = database.get(position);
        myViewHolder.textViewTitle.setText(listItem.getTitle());
        myViewHolder.textViewAuthors.setText(listItem.getAuthors());
        myViewHolder.imageViewBook.setImageResource(listItem.getImage());
    }

    @Override
    public int getItemCount() {
        return database.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{
    private View view = null;
    private Context context = null;
    TextView textViewTitle = null;
    TextView textViewAuthors = null;
    ImageView imageViewBook = null;

    public MyViewHolder(View view, Context context){
        super(view);
        this.view = view;
        this.context = context;
        textViewTitle = (TextView) view.findViewById(R.id.list_item_text_view_title);
        textViewAuthors = (TextView) view.findViewById(R.id.list_item_text_view_authors);
        imageViewBook = (ImageView) view.findViewById(R.id.list_item_image);
        view.setOnClickListener(new MyListener(context));
    }
}

class MyListener implements View.OnClickListener{

    private Context context = null;

    public MyListener(Context context){
        this.context = context;
    }

    public void onClick(View view){
        Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT).show();
    }
}
