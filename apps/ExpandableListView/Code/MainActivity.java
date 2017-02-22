package com.example.saketh.expandablelistview;

import android.app.ExpandableListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private List<String> listParent;
    private HashMap< String,List<String> > mapChild;
    private ExpandableListView myExpandableListView;
    private ExpandableListAdapter myExpandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        fillList();
        fillMap();
        myExpandableListAdapter = new MyExpandableListViewAdapter(this,listParent,mapChild);

        myExpandableListView.setAdapter(myExpandableListAdapter);

        myExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                String tempParent = listParent.get(groupPosition);
                String tempChild = mapChild.get(tempParent).get(childPosition);
                String toastMessage = "Located in " + tempChild + ", " + tempParent + ".";
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();

                return false;
            }
        });
    }

    private void fillList()
    {
        listParent = new LinkedList<String>();

        listParent.add("South America");
        listParent.add("Europe");
        listParent.add("Africa");
    }

    private void fillMap()
    {
        mapChild = new HashMap< String,List<String> >();
        List<String> listSouthAmerica = new LinkedList<String>();
        List<String> listEurope = new LinkedList<String>();
        List<String> listAfrica = new LinkedList<String>();

        listSouthAmerica.add("Guyana");
        listSouthAmerica.add("Chile");
        listSouthAmerica.add("Ecuador");
        listSouthAmerica.add("Peru");
        listSouthAmerica.add("Brazil");

        listEurope.add("Austria");
        listEurope.add("Poland");
        listEurope.add("Sweden");
        listEurope.add("Spain");
        listEurope.add("Switzerland");

        listAfrica.add("Libya");
        listAfrica.add("Egypt");
        listAfrica.add("Ghana");
        listAfrica.add("Kenya");
        listAfrica.add("Congo");

        mapChild.put(listParent.get(0), listSouthAmerica);
        mapChild.put(listParent.get(1), listEurope);
        mapChild.put( listParent.get(2),listAfrica );
    }
}
