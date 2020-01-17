package com.example.attendancemanager.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LessAttendenceEx extends AppCompatActivity {
ExpandableListView lw;
ArrayList<String> subjects;
Map<String,ArrayList<String>> students;
String class1;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
public class FetchSubjects2 extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... strings) {
        int i=3;
        String query="select * from "+class1;
        ResultSet resultSet = null;
        Log.i("status1000",query);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement statement = con.createStatement();
            resultSet=statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            resultSet.next();
            Log.i("status096",String.valueOf(columnsNumber));
            while(i<=columnsNumber-2)
            {
                subjects.add(rsmd.getColumnName(i));
                ArrayList<String> list = new ArrayList<String>();
                students.put(rsmd.getColumnName(i),list);

                i=i+3;


            }

        }
        catch(Exception e)
        {
            Log.i("status193","failed193");
        }



        return null;
    }
}
public class FetchStudents2 extends AsyncTask<String,String,String>
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
            for(i=0;i<subjects.size();i++)
            {
                query = "select * from "+class1+" where ("+"("+subjects.get(i)+"*100)/"+subjects.get(i)+"_total < 75)";
                Log.i("status1028",query);
                resultSet=  statement.executeQuery(query);
                while(resultSet.next())
                {
                    Log.i("statusiii",resultSet.getString("name")+" : "+resultSet.getString(subjects.get(i))+"/"+resultSet.getString(subjects.get(i)+"_total"));
                    students.get(subjects.get(i)).add(resultSet.getString("name")+" : "+resultSet.getString(subjects.get(i))+"/"+resultSet.getString(subjects.get(i)+"_total"));
                }
            }



        }
        catch(Exception e)
        {
            Log.i("status3130","failed3130");
        }
        return null;
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_less_attendence_ex);
          Intent intent=getIntent();
          class1=intent.getStringExtra("class");
          lw= (ExpandableListView)findViewById(R.id.ex1);
          filldata();

          MyCustomExpandableListLessAttendence myCustomExpandableListLessAttendence = new MyCustomExpandableListLessAttendence(this,subjects,students);
          lw.setAdapter(myCustomExpandableListLessAttendence);
    }
    public void filldata()
    {
        subjects = new ArrayList<String>();
        students = new HashMap<String, ArrayList<String>>();
        FetchSubjects2 fetchSubjects2 = new FetchSubjects2();
        try {
            fetchSubjects2.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FetchStudents2 fetchStudents2 = new FetchStudents2();
        try {
            fetchStudents2.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
