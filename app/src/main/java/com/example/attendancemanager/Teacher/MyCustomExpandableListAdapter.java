package com.example.attendancemanager.Teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.attendancemanager.R;
import com.example.attendancemanager.Teacher.AddAClass;
import com.example.attendancemanager.Teacher.TeacherSchedule;

import java.util.ArrayList;
import java.util.Map;


public class MyCustomExpandableListAdapter extends BaseExpandableListAdapter
{

    ArrayList<String> days;
    Map<String,ArrayList<String>> classes;
    Activity context;


    public MyCustomExpandableListAdapter(ArrayList<String> days, Map<String, ArrayList<String>> classes, Activity context) {
        this.days = days;
        this.classes = classes;
        this.context = context;
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
        final int x=i;
        LayoutInflater layoutInflater= context.getLayoutInflater();
        View rowview= layoutInflater.inflate(R.layout.list_group,null,false);
        TextView t= (TextView)rowview.findViewById(R.id.textViewday);
        t.setText((String)getGroup(i));
        Button b1 =(Button) rowview.findViewById(R.id.buttonday);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAClass.class);
                intent.putExtra("day",days.get(x));
                intent.putExtra("i","insert");
                context.startActivity(intent);
            }
        });
        return rowview;

    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final int x=i;
        final LayoutInflater layoutInflater= context.getLayoutInflater();
        View rowview= layoutInflater.inflate(R.layout.list_child,null,false);
        final TextView t= (TextView)rowview.findViewById(R.id.textViewclass1);
        ImageView im = (ImageView)rowview.findViewById(R.id.imageviewSettings);

        final String str=(String)getChild(i,i1);
        Log.i("statussting",str);
        final String strsplit[] = str.split("/",2);
        final String str1[]  =  strsplit[0].split("-",2);
        final String str2[] = str1[1].split(" Group-",2);
        final String str3[]= str2[1].split("_",3);
        final String str6=str3[0]+"_"+str3[1];
        final String str4[]=str3[2].split(" ",3);



        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater1 = context.getLayoutInflater();
                final View view1 = layoutInflater.inflate(R.layout.alert_dialog_settings, null, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Select A Option to perform").setTitle(t.getText().toString()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RadioGroup rg = (RadioGroup)view1.findViewById(R.id.radioGroupSettings);
                        int buttonid=  rg.getCheckedRadioButtonId();
                        if(buttonid==R.id.radioButtondelete)
                        {
                            SharedPreferences sp = context.getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
                            String username= sp.getString("username","nousername");
                            TeacherSchedule teacherSchedule= new TeacherSchedule();
                            TeacherSchedule.DeleteItem deleteItem= teacherSchedule.new DeleteItem();

                            deleteItem.execute(username,days.get(x),str1[0],str2[0],str6,str4[0],str4[1]);


                            Intent intent = new Intent(context,TeacherSchedule.class);
                            context.startActivity(intent);

                        }
                        else if(buttonid==R.id.radioButtonupdate)
                        {
                            Intent intent = new Intent(context,AddAClass.class);
                            intent.putExtra("i","update");
                            intent.putExtra("fromtime",str1[0]);
                            intent.putExtra("totime",str2[0]);
                            intent.putExtra("groupno",str6);
                            intent.putExtra("day",days.get(x));
                            intent.putExtra("subgroupno",str4[0]);
                            intent.putExtra("venue",strsplit[1]);
                            intent.putExtra("year",str4[1]);
                            Log.i("status444",str4[1]);
                            context.startActivity(intent);


                        }

                    }
                }).setNegativeButton("Cancel",null).setCancelable(false).setView(view1);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        t.setText(strsplit[0]);
        return rowview;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }





}
