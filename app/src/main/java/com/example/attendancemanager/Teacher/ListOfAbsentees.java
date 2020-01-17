package com.example.attendancemanager.Teacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListOfAbsentees extends AppCompatActivity {
TextView textViewheading,textViewclass;
ListView lw;
String class1;
String subject;
SharedPreferences sp;
ArrayList<String> absentees;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";

    public class Close2 extends AsyncTask<String,String,String >
    {
        @Override
        protected String doInBackground(String... strings) {
            String query="update "+strings[0]+" set "+subject+"_today = 0";

            Log.i("status1111",query);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);

            }
            catch(Exception e)
            {
                Log.i("status333","failed333");
            }

            return " ";
        }
    }
public class  TakeData extends AsyncTask<ArrayList<String> ,String,ArrayList<String>>
{
    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... arrayLists) {

        ResultSet resultSet;

        String query="select * from "+class1+" where "+subject+"_today = 0";


        Log.i("status0011",query);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement statement = con.createStatement();
            resultSet=statement.executeQuery(query);
            while(resultSet.next())
            {
                Log.i("status22",resultSet.getString("name")+"-"+resultSet.getString("registrationno"));
                arrayLists[0].add(resultSet.getString("name")+"-"+resultSet.getString("registrationno"));
            }

        }
        catch(Exception e)
        {
            Log.i("status133","failed133");
        }

        return arrayLists[0];
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_absentees);
        textViewheading = findViewById(R.id.textView20);
        textViewclass = findViewById(R.id.textView21);
        lw=(ListView)findViewById(R.id.list_absent);
        absentees = new ArrayList<String>();
         sp= getSharedPreferences("com.example.attendancemanager",MODE_PRIVATE);
        subject = sp.getString("subject","no");
        Log.i("statusnn",subject);
        Intent intent= getIntent();
        class1=intent.getStringExtra("class1");
        textViewclass.setText(class1);
        TakeData takedata = new TakeData();
        try {
            absentees=takedata.execute(absentees).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(this,absentees);
        lw.setAdapter(myCustomAdapter);


    }
    public void onBackPressed()
    {
        AlertDialog.Builder builder  = new AlertDialog.Builder(ListOfAbsentees.this);
        builder.setTitle("Are you sure?").setMessage("You want to leave this page").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Close2 close = new Close2();
                close.execute(class1);
                Intent intent2 = new Intent(ListOfAbsentees.this, TeacherSchedule.class);
                startActivity(intent2);
                finishAffinity();
                Log.i("statusactivity","true");



            }
        }).setNegativeButton("Cancel",null).setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void closepage(View view)
    {

        AlertDialog.Builder builder  = new AlertDialog.Builder(ListOfAbsentees.this);
        builder.setTitle("Are you sure?").setMessage("You want to leave this page.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Close2 close = new Close2();
                close.execute(class1);
                Intent intent2 = new Intent(ListOfAbsentees.this,TeacherSchedule.class);
                startActivity(intent2);
                finishAffinity();
                Log.i("statusactivity","true");



            }
        }).setNegativeButton("Cancel",null).setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
