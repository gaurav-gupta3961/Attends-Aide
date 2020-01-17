package com.example.attendancemanager.Teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.attendancemanager.R;

import java.util.ArrayList;
import java.util.Map;

public class MyAttendenceListAdapter extends BaseExpandableListAdapter
{
    Activity context;
    ArrayList<String> classes;
    Map<String,ArrayList<String>> students;

    public MyAttendenceListAdapter(Activity context, ArrayList<String> classes, Map<String, ArrayList<String>> students) {
        this.context = context;
        this.classes = classes;
        this.students = students;
    }

    @Override
    public int getGroupCount() {
        return classes.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return students.get(classes.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return classes.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return students.get(classes.get(i)).get(i1);
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
        View rowview=layoutInflater.inflate(R.layout.list_group_attendence,null,false);
        TextView t= rowview.findViewById(R.id.textViewgroupclass);
        t.setText((String)getGroup(i));

        Log.i("status33",(String)getGroup(i));
        return  rowview;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=layoutInflater.inflate(R.layout.list_child_attendence,null,false);
        TextView t= rowview.findViewById(R.id.textViewchild);
        ProgressBar progresssafe = rowview.findViewById(R.id.progresssafe2);
        ProgressBar progressunsafe =  rowview.findViewById(R.id.progressunsafe2);
        TextView t1= rowview.findViewById(R.id.textviewpercentage2);
        ImageView im = rowview.findViewById(R.id.sendemail);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Really?").setMessage("Are you sure To send Mail\n which contains A warning").setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SendMailImage sm = new SendMailImage
                                (context,"666666madhav@gmail.com","Regarding Attendance","Dear Student Your Attendance is below 75% ");
                        sm.execute();
                    }
                }).setNegativeButton("Cancel",null).setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
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
            im.setVisibility(View.VISIBLE);


        }
        return  rowview;

    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
