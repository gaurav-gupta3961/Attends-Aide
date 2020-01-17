package com.example.attendancemanager.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class StudentAttendence extends AppCompatActivity {
ExpandableListView lw;
    ArrayList<String> subjects;
    Map<String,String> attendence ;
    SharedPreferences sp;
    String username,year,groupno,subgroupno,registrationno;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";

        public class FetchAttendence extends AsyncTask<String,String,String>
        {@Override
        protected String doInBackground(String[] strings) {
            int i=3;
            String query="select * from "+year+"_"+groupno+"_"+subgroupno+"where registrationno = "+registrationno ;
            ResultSet resultSet = null;
            Log.i("status100",query);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                resultSet=statement.executeQuery(query);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                resultSet.next();
                Log.i("status09",String.valueOf(columnsNumber));
                while(i<=columnsNumber-2)
                {
                    subjects.add(rsmd.getColumnName(i));
                    attendence.put(rsmd.getColumnName(i),resultSet.getString(i)+"/"+resultSet.getString(i+1));
                    Log.i("statusrr",resultSet.getString(i)+"/"+resultSet.getString(i+1));
                    i=i+3;


                }

            }
            catch(Exception e)
            {
                Log.i("status133","failed133");
            }



            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendence);
        lw=(ExpandableListView)findViewById(R.id.expandablestudentattendence);
        sp= getSharedPreferences("com.example.attendancemanager",MODE_PRIVATE);
       username=  sp.getString("username","no");
       year= sp.getString("year","");
       groupno= sp.getString("groupno","");
       subgroupno= sp.getString("subgroupno","");
       registrationno=sp.getString("registrationno","");
       filldata();
       MyCustomExpandableListAttendence myCustomExpandableListAttendence = new MyCustomExpandableListAttendence(this,subjects,attendence);
        lw.setAdapter(myCustomExpandableListAttendence);
    }
    public void filldata()
    {
        subjects = new ArrayList<String>();
        attendence = new HashMap<String, String>();
        FetchAttendence fetchAttendence = new FetchAttendence();
        try {
            String str=fetchAttendence.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
