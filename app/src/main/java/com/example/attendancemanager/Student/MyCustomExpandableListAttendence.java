package com.example.attendancemanager.Student;

import android.app.Activity;
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

public class MyCustomExpandableListAttendence extends BaseExpandableListAdapter
{
    Activity context;
    ArrayList<String> subjects;
    Map<String,String> attendence ;

    public MyCustomExpandableListAttendence(Activity context, ArrayList<String> subjects, Map<String, String> attendence) {
        this.context = context;
        this.subjects = subjects;
        this.attendence = attendence;
    }

    @Override
    public int getGroupCount() {
        return subjects.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return subjects.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return attendence.get(subjects.get(i));
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
        View rowview=layoutInflater.inflate(R.layout.list_student_group_attendence,null,false);
        TextView t= rowview.findViewById(R.id.textViewstudentgroupattendence);
        t.setText((String)getGroup(i));
        Log.i("statusddf",t.getText().toString());
        return  rowview;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=layoutInflater.inflate(R.layout.list_student_child_attendence,null,false);
        TextView t= rowview.findViewById(R.id.textViewstudentchildattendence);
        ProgressBar progresssafe = rowview.findViewById(R.id.progresssafe);
        ProgressBar progressunsafe =  rowview.findViewById(R.id.progressunsafe);
        TextView t1= rowview.findViewById(R.id.textviewpercentage);


        String str=(String)getChild(i,i1);
        String[] split = str.split("/",2);
        t.setText("Class Attended: "+split[0]+"\n"+"Total Class Held: "+split[1]);
        int a= Integer.valueOf(split[0]);
        int c= Integer.valueOf(split[1]);
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


        return  rowview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
