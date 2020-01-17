package com.example.attendancemanager.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.attendancemanager.login.MainActivity;
import com.example.attendancemanager.R;

public class teacherWaitPage extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to return to main page?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        Intent back=new Intent(teacherWaitPage.this, MainActivity.class);
                        startActivity(back);
                        finishAffinity();                 //close();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
    public void Back(View view) {
        Intent back=new Intent(teacherWaitPage.this,MainActivity.class);
        startActivity(back);
        finishAffinity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_wait_page);
    }
}
