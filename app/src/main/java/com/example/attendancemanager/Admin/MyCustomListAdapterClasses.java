package com.example.attendancemanager.Admin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attendancemanager.R;

import java.util.ArrayList;

public class MyCustomListAdapterClasses extends ArrayAdapter
{
    Activity context;
    ArrayList<String> classes;

    public MyCustomListAdapterClasses(Activity context, ArrayList<String> classes) {
        super(context, R.layout.list_less_attendence_class,classes);
        this.context = context;
        this.classes = classes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater  = context.getLayoutInflater();
       View rowview= layoutInflater.inflate(R.layout.list_less_attendence_class,null,false);
       TextView t = (TextView)rowview.findViewById(R.id.textView25);
       t.setText(classes.get(position));
       return  rowview;
    }
}
