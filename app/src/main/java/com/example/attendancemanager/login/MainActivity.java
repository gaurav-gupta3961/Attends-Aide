package com.example.attendancemanager.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.attendancemanager.Admin.LoginAsAdministrator;
import com.example.attendancemanager.R;
import com.example.attendancemanager.Student.StudentSchedule;
import com.example.attendancemanager.Teacher.TeacherSchedule;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageButton buttonnext;
     Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp=getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);

        //editor.putString("Logged",username).apply();
        if(sp.getString("Logged","").equals("SLogin"))
        {
           Intent intentS =new Intent(MainActivity.this, StudentSchedule.class);
            startActivity(intentS);
        }
        else if(sp.getString("Logged","").equals("TLogin"))
        {
            Intent intentT =new Intent(MainActivity.this, TeacherSchedule.class);
            startActivity(intentT);
        }
        else if(sp.getString("Logged","").equals("ALogin"))
        {
            Intent intentA =new Intent(MainActivity.this, LoginAsAdministrator.class);
            startActivity(intentA);
        }
        else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            buttonnext = (ImageButton) findViewById(R.id.button);
            buttonnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = radioGroup.getCheckedRadioButtonId();
                    if (i != -1) {
                        RadioButton radioButton = (RadioButton) findViewById(i);
                        intent.putExtra("user", radioButton.getText().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Please Select any Option", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }
}
