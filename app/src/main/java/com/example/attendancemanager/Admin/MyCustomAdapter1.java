package com.example.attendancemanager.Admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.attendancemanager.R;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter1 extends ArrayAdapter
{
    Activity context;
    ArrayList arrayList;

    public MyCustomAdapter1(Activity context,int resource, ArrayList arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View rowview=layoutInflater.inflate(R.layout.teacherrequest_list,null,false);
        TextView t=(TextView)rowview.findViewById(R.id.textView6);
        t.setText((String)arrayList.get(position));
        return rowview;


    }
}
