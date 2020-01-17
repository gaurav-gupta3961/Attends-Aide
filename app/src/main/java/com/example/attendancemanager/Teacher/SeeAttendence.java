package com.example.attendancemanager.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SeeAttendence extends AppCompatActivity {
ExpandableListView expandableListView;
ArrayList<String> classes;
Map<String,ArrayList<String>> students;
SharedPreferences sp;
    public static ProgressBar progressBar;
String username,subject;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
public class FetchClasses extends AsyncTask<String,String, String>
{
    @Override
    protected String doInBackground(String... strings) {
        String query="select distinct year,groupno,subgroupno from timetable where teacherusername = '"+username+"'";
        ResultSet resultSet = null;
        Log.i("status101",query);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement statement = con.createStatement();
            resultSet=statement.executeQuery(query);
            while(resultSet.next())
            {
             classes.add(resultSet.getString("year")+"_"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno"));
             ArrayList<String> list  = new ArrayList<String>();
             students.put(resultSet.getString("year")+"_"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno"),list);
          //   Log.i("status344",classes.get(0));
            }

        }
        catch(Exception e)
        {
            Log.i("status133","failed133");
        }


        return "a1";
    }
}
public class FetchStudents extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... strings) {
        int i;
        String query;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement statement = con.createStatement();
            for(i=0;i<classes.size();i++)
            {
             query = "select * from "+classes.get(i);
                Log.i("status102",query);
              resultSet=  statement.executeQuery(query);
                while(resultSet.next())
                {
                    Log.i("statusii",resultSet.getString("name")+" : "+resultSet.getString(subject)+"/"+resultSet.getString(subject+"_total"));
                    students.get(classes.get(i)).add(resultSet.getString("name")+" : "+resultSet.getString(subject)+"/"+resultSet.getString(subject+"_total"));
                }
            }



        }
        catch(Exception e)
        {
            Log.i("status313","failed313");
        }


        return "a2";
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_attendence);
        progressBar=findViewById(R.id.progressBarTI);
        expandableListView  = findViewById(R.id.exListAttendence);
        sp= getSharedPreferences("com.example.attendancemanager",MODE_PRIVATE);
        username = sp.getString("username","no");
        subject = sp.getString("subject","no");
        filldata();
        MyAttendenceListAdapter myAttendenceListAdapter = new MyAttendenceListAdapter(this,classes,students);
        expandableListView.setAdapter(myAttendenceListAdapter);

    }
    public void filldata()
    {
        classes = new ArrayList<String>();
        students  = new HashMap<String ,ArrayList<String>>();
        FetchClasses fetchData = new FetchClasses();
        String a= null;
        try {
            a = fetchData.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("ststusa",a);
        FetchStudents fetchStudents  = new FetchStudents();
        String b= null;
        try {
            b = fetchStudents.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("statusb",b);


    }
}
