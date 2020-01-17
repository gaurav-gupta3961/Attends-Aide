package com.example.attendancemanager.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.attendancemanager.login.MainActivity;
import com.example.attendancemanager.R;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentSchedule extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String username,name,registrationno,phone ,email,branch,groupno,subgroupno,year;
    TextView textViewTNS,textViewTES, firstLetterS;
    private DrawerLayout dl;
    View hView,fullPage;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    ExpandableListView lw;
    ArrayList<String> days;
    Map<String,ArrayList<String>> classes;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
    Menu menuNav;
    MenuItem nav_item2;
    public void onBackPressed()
    {
        finishAffinity();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuNav.findItem(R.id.Account).setEnabled(true);
        menuNav.findItem(R.id.QR).setEnabled(true);
        menuNav.findItem(R.id.myattendance).setEnabled(true);
        menuNav.findItem(R.id.logOUT).setEnabled(true);
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    public class RecieveData extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            ResultSet resultSet;
            String query1="select * from timetable where year = '"+year+"' and groupno = '"+groupno+"' and subgroupno = '"+subgroupno+"' and day='Monday'" ;
            String query2="select * from timetable where year = '"+year+"' and groupno = '"+groupno+"' and subgroupno = '"+subgroupno+"' and day='Tuesday'" ;
            String query3="select * from timetable where year = '"+year+"' and groupno = '"+groupno+"' and subgroupno = '"+subgroupno+"' and day='Wednesday'" ;
            String query4="select * from timetable where year = '"+year+"' and groupno = '"+groupno+"' and subgroupno = '"+subgroupno+"' and day='Thursday'" ;
            String query5="select * from timetable where year = '"+year+"' and groupno = '"+groupno+"' and subgroupno = '"+subgroupno+"' and day='Friday'" ;
            String query6="select * from timetable where year = '"+year+"' and groupno = '"+groupno+"' and subgroupno = '"+subgroupno+"' and day='Saturday'" ;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                resultSet=statement.executeQuery(query1);
                while(resultSet.next())
                {

                    classes.get("Monday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Subject-"+resultSet.getString("subject")+"/"+resultSet.getString("venue")+"&"+resultSet.getString("name"));
                    //Log.i("status11",String.valueOf(resultSet.getInt("from")));
                    //   Log.i("status14",String.valueOf(classes.get("Monday").size()));
                    // Log.i("status111",(String) classes.get("Monday").get(0));
                }
                resultSet=statement.executeQuery(query2);
                while(resultSet.next())
                {
                    classes.get("Tuesday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Subject-"+resultSet.getString("subject")+"/"+resultSet.getString("venue")+"&"+resultSet.getString("name"));
                }
                resultSet=statement.executeQuery(query3);
                while(resultSet.next())
                {
                    classes.get("Wednesday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Subject-"+resultSet.getString("subject")+"/"+resultSet.getString("venue")+"&"+resultSet.getString("name"));
                }
                resultSet=statement.executeQuery(query4);
                while(resultSet.next())
                {
                    classes.get("Thursday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Subject-"+resultSet.getString("subject")+"/"+resultSet.getString("venue")+"&"+resultSet.getString("name"));
                }
                resultSet=statement.executeQuery(query5);
                while(resultSet.next())
                {
                    classes.get("Friday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Subject-"+resultSet.getString("subject")+"/"+resultSet.getString("venue")+"&"+resultSet.getString("name"));
                }
                resultSet=statement.executeQuery(query6);
                while(resultSet.next())
                {
                    classes.get("Saturday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Subject-"+resultSet.getString("subject")+"/"+resultSet.getString("venue")+"&"+resultSet.getString("name"));
                }


            }
            catch(Exception e)
            {
                Log.i("status111","hiiii");
                Log.i("status150","failed150");
            }



            return "recieved";
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        menuNav.findItem(R.id.Account).setEnabled(true);
        menuNav.findItem(R.id.QR).setEnabled(true);
        menuNav.findItem(R.id.myattendance).setEnabled(true);
        menuNav.findItem(R.id.logOUT).setEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);
        lw= (ExpandableListView)findViewById(R.id.exlistView2);
        sp = getSharedPreferences("com.example.attendancemanager",MODE_PRIVATE);
        username=sp.getString("username","no");
        editor = sp.edit();
        editor.putString("Logged","SLogin").apply();
        Log.i("status12",username);


        dl = (DrawerLayout)findViewById(R.id.draw2);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv2);

        menuNav=nv.getMenu();
         nav_item2 = menuNav.findItem(R.id.menuStud);

        hView =  nv.getHeaderView(0);

        menuNav.findItem(R.id.Account).setEnabled(true);
        menuNav.findItem(R.id.QR).setEnabled(true);
        menuNav.findItem(R.id.myattendance).setEnabled(true);
        menuNav.findItem(R.id.logOUT).setEnabled(true);
        textViewTNS=hView.findViewById(R.id.textViewTNS);
        textViewTES=hView.findViewById(R.id.textViewTES);
        firstLetterS=hView.findViewById(R.id.firstletterS);
        firstLetterS.setText((sp.getString("name","no")).substring(0,1).toUpperCase());

        textViewTNS.setText(sp.getString("name","no"));
        textViewTES.setText(sp.getString("email","no"));
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               // menuNav.setEnabled(true);
                int id = item.getItemId();

                switch(id)
                {
                    case R.id.Account:
                        Intent intent1=new Intent(StudentSchedule.this, studentActivity.class);
                        startActivity(intent1);
                        return true;

                    case R.id.QR:
                        //  Toast.makeText(TeacherSchedule.this, "UnderConstruction",Toast.LENGTH_SHORT).show();
                        Intent intent2=new Intent(StudentSchedule.this, StudentQr.class);
                        startActivity(intent2);
                        return true;

                    case R.id.myattendance:
                //        nav_item2.setEnabled(false);
                       // item.setEnabled(false);
                        menuNav.findItem(R.id.Account).setEnabled(false);
                        menuNav.findItem(R.id.QR).setEnabled(false);
                        menuNav.findItem(R.id.myattendance).setEnabled(false);
                        menuNav.findItem(R.id.logOUT).setEnabled(false);
                        Intent intent3=new Intent(StudentSchedule.this, StudentAttendence.class);
                        startActivity(intent3);
                        return true;
                    case R.id.logOUT:
                        //  Toast.makeText(TeacherSchedule.this, "UnderConstruction",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder  = new AlertDialog.Builder(StudentSchedule.this);
                        builder.setTitle("Are you sure?").setMessage("You want to Log out.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent4=new Intent(StudentSchedule.this, MainActivity.class);
                                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                editor.putString("Logged","NOT").apply();
                                startActivity(intent4);
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
        name=sp.getString("name","no");
        registrationno=sp.getString("registrationno","no");
        phone=sp.getString("phone","no");
        email=sp.getString("email","no");
        branch=sp.getString("branch","no");
        groupno=sp.getString("groupno","no");
        subgroupno=sp.getString("subgroupno","no");
        year=sp.getString("year","no");
        Log.i("statusyear",year);
        Log.i("statusgroup",subgroupno);
        Log.i("statusbranch",branch);

        fillData();
        MyCustomExpandableAdapterStudent myCustomExpandableAdapterStudent = new MyCustomExpandableAdapterStudent(this,days,classes);
        lw.setAdapter(myCustomExpandableAdapterStudent);


    }
    public void fillData()
    {
        days= new ArrayList<>();
        classes = new HashMap<>();
        ArrayList<String> monday = new ArrayList();
        ArrayList<String> tuesday = new ArrayList();
        ArrayList<String> wednesday = new ArrayList();
        ArrayList<String> thursday = new ArrayList();
        ArrayList<String> friday = new ArrayList();
        ArrayList<String> saturday = new ArrayList();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        classes.put(days.get(0),monday);
        classes.put(days.get(1),tuesday);
        classes.put(days.get(2),wednesday);
        classes.put(days.get(3),thursday);
        classes.put(days.get(4),friday);
        classes.put(days.get(5),saturday);
        try {
            RecieveData recieveData = new RecieveData();
            recieveData.execute();
        }
        catch(Exception e)
        {
            Log.i("status90","failed");
        }

    }
}
