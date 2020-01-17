package com.example.attendancemanager.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.attendancemanager.login.MainActivity;
import com.example.attendancemanager.R;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginAsAdministrator extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ListView lw;
    TextView textViewTEA;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    ArrayList[] arrayList;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
    public void onBackPressed()
    {
        finishAffinity();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    public class Send extends AsyncTask<String,String,ArrayList[]>
    {

        @Override
        protected ArrayList[] doInBackground(String... strings) {
            ArrayList arrayList[] = new ArrayList[2];
            arrayList[0]=new ArrayList();
            arrayList[1]=new ArrayList();
            String str;
            String query="select * from teacherrequest";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                ResultSet resultSet=statement.executeQuery(query);
                while(resultSet.next())
                {
                    str=resultSet.getString("name");
                    arrayList[0].add(str);
                    str=resultSet.getString("username");
                    arrayList[1].add(str);



                }

            }
            catch(Exception e)
            {
                Log.i("status4","failed");
            }
            return arrayList;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_administrator);
        sp = getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("Logged","ALogin").apply();
        lw=(ListView)findViewById(R.id.listview);

        dl = (DrawerLayout)findViewById(R.id.drawA);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {

                    case R.id.GenerateList:
                        //  Toast.makeText(TeacherSchedule.this, "UnderConstruction",Toast.LENGTH_SHORT).show();
                        Intent intent2=new Intent(LoginAsAdministrator.this, LessAttendence.class);
                        startActivity(intent2);
                        return true;
                    case R.id.logOUT:
                        //  Toast.makeText(TeacherSchedule.this, "UnderConstruction",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder  = new AlertDialog.Builder(LoginAsAdministrator.this);
                        builder.setTitle("Are you sure?").setMessage("You want to Log out.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent3=new Intent(LoginAsAdministrator.this, MainActivity.class);
                                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                editor.putString("Logged","NOT").apply();
                                startActivity(intent3);
                                finishAffinity();

                            }
                        }).setNegativeButton("Cancel",null).setCancelable(false);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        return true;
                    default:
                        return true;
                }
            }
        });
        Send send = new Send();


        try {
            arrayList = send.execute().get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        MyCustomAdapter1 myCustomAdapter1=new MyCustomAdapter1(this,R.layout.teacherrequest_list,arrayList[0]);
        lw.setAdapter(myCustomAdapter1);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LoginAsAdministrator.this, DetailsOfSelectedTeacher.class);
                Log.i("statusb",(String)arrayList[1].get(i));

                intent.putExtra("username",(String)arrayList[1].get(i));
                startActivity(intent);


            }
        });

    }
}
