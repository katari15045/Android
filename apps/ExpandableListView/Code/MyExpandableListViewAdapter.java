package com.example.saketh.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by saketh on 21/12/16.
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter
{

    List<String> listParent;
    Map< String,List<String> > mapChild;
    Context myContext;

    public MyExpandableListViewAdapter(Context inpContext,List<String> inpListParent,Map< String,List<String> > inpMapChild)
    {
        myContext = inpContext;
        listParent = inpListParent;
        mapChild = inpMapChild;
    }

    @Override
    public int getGroupCount()
    {
        return listParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return mapChild.get( listParent.get(groupPosition) ).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return listParent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return mapChild.get( listParent.get(groupPosition) ).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {

        String myParent = (String) getGroup(groupPosition);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent,null);
        }

        TextView myTextView = (TextView) convertView.findViewById(R.id.textViewParent);
        myTextView.setText(myParent);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        String child = (String) getChild(groupPosition,childPosition);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(myContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child,null);
        }

        TextView myTextView = (TextView) convertView.findViewById(R.id.textViewChild);
        myTextView.setText(child);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}
