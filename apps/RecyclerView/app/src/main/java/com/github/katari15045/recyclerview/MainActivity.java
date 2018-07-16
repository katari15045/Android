package com.github.katari15045.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView = null;
    private MyAdapter myAdapter = null;
    private ArrayList<ListItem> database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, database);
        recyclerView.setAdapter(myAdapter);
    }

    private void fillDatabase(){
        database = new ArrayList<>(100);
        int count = 1;
        while(count <= 100){
            ListItem listItem = new ListItem();
            listItem.setTitle(getResources().getString(R.string.book_authors_title));
            listItem.setAuthors(getResources().getString(R.string.book_authors_names));
            listItem.setImage(R.drawable.book_cormen);
            database.add(listItem);
            count = count + 1;
        }
    }
}
