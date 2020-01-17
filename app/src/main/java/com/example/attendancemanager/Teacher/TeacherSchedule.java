package com.example.attendancemanager.Teacher;

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
import java.util.concurrent.ExecutionException;

public class TeacherSchedule extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Menu menuNav;
    TextView textViewTN,textViewTE, firstLetter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    String username;
    ExpandableListView lw;
    ArrayList<String> days;
    Map<String,ArrayList<String>> classes;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
   public void onBackPressed()
    { finishAffinity();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        menuNav.findItem(R.id.Account).setEnabled(true);
        menuNav.findItem(R.id.Attendance).setEnabled(true);
        menuNav.findItem(R.id.logOUT).setEnabled(true);
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    public  class DeleteItem extends  AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            Log.i("st1",strings[0]);
            Log.i("st2",strings[1]);
            Log.i("st3",strings[2]);
            Log.i("st4",strings[3]);
            Log.i("st5",strings[4]);
            Log.i("st6",strings[5]);
            String query = "delete from timetable where teacherusername = '"+strings[0]+"' and day = '"+strings[1]+"' and  fromtime = "+Integer.parseInt((strings[2]))+" and totime = "+Integer.parseInt((strings[3]))+" and groupno = '"+strings[4]+"' and subgroupno = '"+strings[5]+"' and year = '"+strings[6]+"'";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                int i = statement.executeUpdate(query);
            }
            catch(Exception e)
            {
                Log.i("status33","failed33");
            }

            return "kdso";
        }
    }
    public class Recieve extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            ResultSet resultSet;

            String query1="select * from timetable where teacherusername = '"+username+"' and day='Monday'" ;
           String query2="select * from timetable where teacherusername = '"+username+"' and day='Tuesday'" ;
            String query3="select * from timetable where teacherusername = '"+username+"' and day='Wednesday'" ;
            String query4="select * from timetable where teacherusername = '"+username+"' and day='Thursday'" ;
            String query5="select * from timetable where teacherusername = '"+username+"'and day='Friday'" ;
            String query6="select * from timetable where teacherusername = '"+username+"'and day='Saturday'" ;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                  resultSet=statement.executeQuery(query1);
                while(resultSet.next())
                {

                    classes.get("Monday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Group-"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno")+" "+resultSet.getString("year")+" Year"+"/"+resultSet.getString("venue"));
                    //Log.i("status11",String.valueOf(resultSet.getInt("from")));
                 //   Log.i("status14",String.valueOf(classes.get("Monday").size()));
                   // Log.i("status111",(String) classes.get("Monday").get(0));
                }
                resultSet=statement.executeQuery(query2);
                while(resultSet.next())
                {
                    classes.get("Tuesday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Group-"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno")+" "+resultSet.getString("year")+" Year"+"/"+resultSet.getString("venue"));
                }
                resultSet=statement.executeQuery(query3);
                while(resultSet.next())
                {
                    classes.get("Wednesday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Group-"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno")+" "+resultSet.getString("year")+" Year"+"/"+resultSet.getString("venue"));
                }
                resultSet=statement.executeQuery(query4);
                while(resultSet.next())
                {
                    classes.get("Thursday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Group-"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno")+" "+resultSet.getString("year")+" Year"+"/"+resultSet.getString("venue"));
                }
                 resultSet=statement.executeQuery(query5);
                while(resultSet.next())
                {
                    classes.get("Friday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Group-"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno")+" "+resultSet.getString("year")+" Year"+"/"+resultSet.getString("venue"));
                }
                resultSet=statement.executeQuery(query6);
                while(resultSet.next())
                {
                    classes.get("Saturday").add(resultSet.getString("fromtime")+"-"+resultSet.getString("totime")+" Group-"+resultSet.getString("groupno")+"_"+resultSet.getString("subgroupno")+" "+resultSet.getString("year")+" Year"+"/"+resultSet.getString("venue"));
                }


            }
            catch(Exception e)
            {
                Log.i("status15","failed15");
            }



            return "recieved";
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        menuNav.findItem(R.id.Account).setEnabled(true);
        menuNav.findItem(R.id.Attendance).setEnabled(true);
        menuNav.findItem(R.id.logOUT).setEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_schedule);
      sp = getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
        username=sp.getString("username","nousername");
         editor = sp.edit();
        editor.putString("Logged","TLogin").apply();
        Log.i("status12",username);


        dl = (DrawerLayout)findViewById(R.id.draw1);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        menuNav=nv.getMenu();
        View hView =  nv.getHeaderView(0);
        textViewTN=hView.findViewById(R.id.textViewTN);
        textViewTE=hView.findViewById(R.id.textViewTE);
        firstLetter=hView.findViewById(R.id.firstletter);
       firstLetter.setText((sp.getString("name","no")).substring(0,1).toUpperCase());

        textViewTN.setText(sp.getString("name","no"));
       textViewTE.setText(sp.getString("email","no"));
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.Account:
                        Intent intent1=new Intent(TeacherSchedule.this, TeacherActivity.class);
                        startActivity(intent1);
                        return true;

                        case R.id.Attendance:
                      //  Toast.makeText(TeacherSchedule.this, "UnderConstruction",Toast.LENGTH_SHORT).show();
                            menuNav.findItem(R.id.Account).setEnabled(false);
                            menuNav.findItem(R.id.Attendance).setEnabled(false);
                            menuNav.findItem(R.id.logOUT).setEnabled(false);
                           Intent intent2=new Intent(TeacherSchedule.this, SeeAttendence.class);
                           startActivity(intent2);
                            return true;
                    case R.id.logOUT:
                        //  Toast.makeText(TeacherSchedule.this, "UnderConstruction",Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder  = new AlertDialog.Builder(TeacherSchedule.this);
                        builder.setTitle("Are you sure?").setMessage("You want to Log out.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent3=new Intent(TeacherSchedule.this, MainActivity.class);
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


        fillData();
       // Log.i("status13",(String)classes.get("Monday").get(0));
        lw=(ExpandableListView)findViewById(R.id.listViewex);
        MyCustomExpandableListAdapter myCustomExpandableListAdapter=new MyCustomExpandableListAdapter(days,classes,this);
        lw.setAdapter(myCustomExpandableListAdapter);
        lw.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent(TeacherSchedule.this, StartAttendence.class);
                TextView t = view.findViewById(R.id.textViewclass1);
                String str=   t.getText().toString();
                String[] split1=str.split(" ",2);
                intent.putExtra("class",split1[1]);


                startActivity(intent);




                return true;
            }
        });

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


        Recieve recieve = new Recieve();
        String str= null;
        try {
            str = recieve.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}
