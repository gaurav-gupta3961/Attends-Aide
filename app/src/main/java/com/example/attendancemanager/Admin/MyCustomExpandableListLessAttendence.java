package com.example.attendancemanager.Admin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.attendancemanager.R;

import java.util.ArrayList;
import java.util.Map;

public class MyCustomExpandableListLessAttendence extends BaseExpandableListAdapter
{
    Activity context;
    ArrayList<String> subjects;
    Map<String,ArrayList<String>> students;

    public MyCustomExpandableListLessAttendence(Activity context, ArrayList<String> subjects, Map<String, ArrayList<String>> students) {
        this.context = context;
        this.subjects = subjects;
        this.students = students;
    }

    @Override
    public int getGroupCount() {
        return subjects.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return students.get(subjects.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return subjects.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return students.get(subjects.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview= layoutInflater.inflate(R.layout.list_group_less_attendence,null,false);
        TextView t = (TextView)rowview.findViewById(R.id.textView27);
        t.setText((String)getGroup(i));
        return rowview;


        }


    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview= layoutInflater.inflate(R.layout.list_child_less_attendence,null,false);
        TextView t = (TextView)rowview.findViewById(R.id.textView26);
        ProgressBar progresssafe = rowview.findViewById(R.id.progresssafe3);
        ProgressBar progressunsafe =  rowview.findViewById(R.id.progressunsafe3);
        TextView t1= rowview.findViewById(R.id.textviewpercentage3);
        t.setText((String)getChild(i,i1));
        Log.i("status66",(String)getChild(i,i1));

        String str=(String)getChild(i,i1);
        String[] split1 = str.split(" : ",2);
        String[] split2 = split1[1].split("/",2);
        int a= Integer.valueOf(split2[0]);
        int c= Integer.valueOf(split2[1]);
        int p;
        if(c==0)
        {p=100;}
        else
        { p= (a*100)/c;}
        if(p>=75)
        {
            progresssafe.setProgress(p);
            progresssafe.setVisibility(View.VISIBLE);
            t1.setText(p+"%");


        }
        else
        {
            progressunsafe.setProgress(p);
            progressunsafe.setVisibility(View.VISIBLE);
            t1.setText(p+"%");



        }
        return rowview;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
