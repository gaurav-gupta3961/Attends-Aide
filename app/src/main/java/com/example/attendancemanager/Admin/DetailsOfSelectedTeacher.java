package com.example.attendancemanager.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class DetailsOfSelectedTeacher extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5,t6;
    Button b1,b2;
    int t=1;
    ArrayList arrayList;
    String username;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";

    public class Approve extends AsyncTask<ArrayList,String,String>
    {
        @Override
        protected String doInBackground(ArrayList[] arrayLists) {
            String epassword="aes_encrypt('"+arrayLists[0].get(6)+"','MVSBGG')";
            String query="insert into teacher values('"+arrayLists[0].get(0)+"','"+arrayLists[0].get(1)+"',"+epassword+",'"+arrayLists[0].get(3)+"','"+arrayLists[0].get(2)+"','"+arrayLists[0].get(4)+"','"+arrayLists[0].get(5)+"')";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Log.i("query",query);
                Statement statement = con.createStatement();
                int result = statement.executeUpdate(query);
            }
            catch (Exception e)
            {
                Log.i("query",query);
                Log.i("statusd","failedd");
            }

            return "jj";
        }
    }
    public class Disapprove extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String[] strings ) {
            String query = "delete from teacherrequest where username = '"+strings[0]+"'";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                int result = statement.executeUpdate(query);
            }
            catch (Exception e)
            {
                Log.i("statuse","failede");
            }



            return "kk";
        }
    }
    public  class Send extends AsyncTask<String,String, ArrayList>
    {
        @Override
        protected ArrayList doInBackground(String... strings) {
            ArrayList arrayList = new ArrayList();

            String query="select name,username,aes_decrypt(`password`,'MVSBGG'),phone,email,subject,qualification from teacherrequest where username = '"+strings[0]+"'";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
                while(resultSet.next())
                {

                    arrayList.add(resultSet.getString(1));
                    arrayList.add(resultSet.getString(2));
                    arrayList.add(resultSet.getString(5));
                    arrayList.add(resultSet.getString(4));
                    arrayList.add(resultSet.getString(6));
                    arrayList.add(resultSet.getString(7));
                    arrayList.add(resultSet.getString(3));


                }

            }
            catch(Exception e)
            {
                Log.i("status5","failed5");
            }

            return arrayList;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_selected_teacher);
        t1=(TextView)findViewById(R.id.textView9);
        t2=(TextView)findViewById(R.id.textView11);
        t3=(TextView)findViewById(R.id.textView13);
        t4=(TextView)findViewById(R.id.textView15);
        t5=(TextView)findViewById(R.id.textView17);
        t6=(TextView)findViewById(R.id.textView19);
        b1=(Button)findViewById(R.id.buttona1);
        b2=(Button)findViewById(R.id.buttona2);

        try
        {
            Intent intent6 = getIntent();
            username=intent6.getStringExtra("username");
            Log.i("status1",username);
            Send send = new Send();
            arrayList=send.execute(username).get();

            Log.i("statusa",(String)arrayList.get(0));
            t1.setText((String)arrayList.get(0));
            t2.setText((String)arrayList.get(1));
            t3.setText((String)arrayList.get(2));
            t4.setText((String)arrayList.get(3));
            t5.setText((String)arrayList.get(4));
            t6.setText((String)arrayList.get(5));


        }
        catch(Exception e)
        { Log.i("status6","failed6");}

    }
    public void approve(View view)
    {
        Approve approve = new Approve();
        try {
            String str=approve.execute(arrayList).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"Approved",Toast.LENGTH_SHORT).show();
        t=0;
        disapprove(view);
        finish();
        Intent intent=new Intent(DetailsOfSelectedTeacher.this, LoginAsAdministrator.class);
        startActivity(intent);
    }
    public void disapprove(View view)
    {
        Disapprove disapprove = new Disapprove();
        disapprove.execute(username);
        if(t==1)
            Toast.makeText(this,"Disapproved",Toast.LENGTH_SHORT).show();
    }
}
