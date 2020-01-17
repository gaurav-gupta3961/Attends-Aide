package com.example.attendancemanager.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TeacherActivity extends AppCompatActivity {
SharedPreferences sp;
    TextView t1,t2,t3,t4,t5,t6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        sp = getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
        t1=(TextView)findViewById(R.id.textView9);
        t2=(TextView)findViewById(R.id.textView11);
        t3=(TextView)findViewById(R.id.textView13);
        t4=(TextView)findViewById(R.id.textView15);
        t5=(TextView)findViewById(R.id.textView17);
        t6=(TextView)findViewById(R.id.textView19);
            t1.setText(sp.getString("name"," "));
            t2.setText(sp.getString("username"," "));
            t3.setText(sp.getString("email"," "));
            t4.setText(sp.getString("phone"," "));
            t5.setText(sp.getString("subject"," "));
            t6.setText(sp.getString("qualification"," "));



    }

}
