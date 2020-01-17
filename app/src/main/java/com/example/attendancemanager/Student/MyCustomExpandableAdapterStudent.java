package com.example.attendancemanager.Student;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.attendancemanager.R;

import java.util.ArrayList;
import java.util.Map;

public class MyCustomExpandableAdapterStudent extends BaseExpandableListAdapter
{
    Activity context;
    ArrayList<String> days;
    Map<String,ArrayList<String>> classes;

    public MyCustomExpandableAdapterStudent(Activity context, ArrayList<String> days, Map<String, ArrayList<String>> classes) {
        this.context = context;
        this.days = days;
        this.classes = classes;
    }

    @Override
    public int getGroupCount() {
        return days.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return classes.get(days.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return days.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return classes.get(days.get(i)).get(i1);
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
        View rowview=layoutInflater.inflate(R.layout.list_group_student,null,false);
          TextView t= rowview.findViewById(R.id.textViewStudentday);
          t.setText((String)getGroup(i));
          return  rowview;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=layoutInflater.inflate(R.layout.list_child_student,null,false);
        TextView t= rowview.findViewById(R.id.textViewStudentclass1);
        String str = (String) getChild(i,i1);
        String[] split1=str.split("/",2);
        t.setText(split1[0]);
        return  rowview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
