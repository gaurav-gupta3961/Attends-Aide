package com.example.attendancemanager.Teacher;

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

public class MyCustomAdapter extends ArrayAdapter
{
   Activity context;
   ArrayList<String> absentees;

    public MyCustomAdapter(Activity context, ArrayList<String> absentees) {
        super(context, R.layout.list_absentees,absentees);
        this.context = context;
        this.absentees = absentees;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater  = context.getLayoutInflater();
        View rowview=layoutInflater.inflate(R.layout.list_absentees,null,false);
        TextView t=  rowview.findViewById(R.id.textView22);
        t.setText(absentees.get(position));
        return rowview;
    }
}
