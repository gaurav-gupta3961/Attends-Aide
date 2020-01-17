package com.example.attendancemanager.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancemanager.Admin.LoginAsAdministrator;
import com.example.attendancemanager.R;
import com.example.attendancemanager.Student.SignUpAsStudent;
import com.example.attendancemanager.Student.StudentSchedule;
import com.example.attendancemanager.Teacher.SignUpAsTeacher;
import com.example.attendancemanager.Teacher.TeacherSchedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
 String result="";
    String usertype;
  ProgressBar progressBar;
    Button b1,b2;
    TextView t1,t2;
    EditText e1,e2;
 ArrayList<String> userdata;

    String username;
    String password;
    String dpassword;

    public  class Check extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/isQU3iyl7C","isQU3iyl7C","RaMsV9OFn1");
                String query="SELECT * from " + strings[0].toLowerCase() + " where "+  dpassword+"="+"'"+password+"'"+ "and "+ "username='"+username+"'";
               Log.i("query",query);
                Statement ps = con.createStatement();
                if(ps.executeQuery(query).next())
                { Log.i("return","true");
                    result="false";
                }
                else
                {  Log.i("return","false");
                    result="true";}
            }
            catch (Exception e)
            {
                Log.i("status","failed");
                result="c";
            }
            Log.i("return2","false");
        return null;
        }

        @Override
        protected void onPreExecute() {
            e1.setEnabled(false);
            e2.setEnabled(false);
            b1.setEnabled(false);
            b2.setEnabled(false);
            t1.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String string) {


            if(result.equals("false")) {

                if(usertype.equals("Teacher")||usertype.equals("Student"))
                {
                    LocalData localData = new LocalData();
                    try {
                        userdata = localData.execute(usertype).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(usertype.equals("Administrator")) {
                    //  progressBar.setVisibility(View.INVISIBLE);
                    Log.i("status", "found");
                    Intent intent3 = new Intent(LoginActivity.this, LoginAsAdministrator.class);
                    startActivity(intent3);
                    finishAffinity();

                }

            }
            else if(result.equals("true"))
            {
                  progressBar.setVisibility(View.INVISIBLE);
                t1.setVisibility(View.VISIBLE);
                e1.setEnabled(true);
                e2.setEnabled(true);
                b1.setEnabled(true);
                b2.setEnabled(true);
            }
            else if(result.equals("c"))
            { e1.setEnabled(true);
                e2.setEnabled(true);
                b1.setEnabled(true);
                b2.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this,"Check Your Connection",Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }
 public class LocalData extends AsyncTask<String,String,ArrayList<String>>
 {
     @Override
     protected void onPostExecute(ArrayList<String> strings) {
         progressBar.setVisibility(View.INVISIBLE);
         e1.setEnabled(true);
         e2.setEnabled(true);
         b1.setEnabled(true);
         b2.setEnabled(true);
         SharedPreferences sp=getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sp.edit();
         editor.putString("username",username).apply();
         Log.i("status", "found"+usertype);

          if(usertype.equals("Student")) {
             editor.putString("name",userdata.get(0));
             editor.putString("registrationno",userdata.get(1));
             editor.putString("phone",userdata.get(2));
             editor.putString("email",userdata.get(3));
             editor.putString("branch",userdata.get(4));
             editor.putString("groupno",userdata.get(5));
             editor.putString("subgroupno",userdata.get(6));
             editor.putString("year",userdata.get(7));
             editor.apply();
             // progressBar.setVisibility(View.INVISIBLE);
             Log.i("status999",sp.getString("email","notfound"));
             Intent intent3 = new Intent(LoginActivity.this, StudentSchedule.class);
             startActivity(intent3);
             Log.i("status", "found");
              finishAffinity();
         }
         else if(usertype.equals("Teacher")) {
             editor.putString("name",userdata.get(0));
             editor.putString("phone",userdata.get(1));
             editor.putString("email",userdata.get(2));
             editor.putString("subject",userdata.get(3));
             editor.putString("qualification",userdata.get(4));
             editor.apply();
             Log.i("status99",sp.getString("email","notfound"));
             Intent intent3 = new Intent(LoginActivity.this, TeacherSchedule.class);
             //  progressBar.setVisibility(View.INVISIBLE);
           //  intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

              startActivity(intent3);
             Log.i("status", "found");
              finishAffinity();
         }
     }

     @Override
     protected ArrayList<String> doInBackground(String... strings) {
         ResultSet resultSet;
         ArrayList<String> arrayList = new ArrayList<String>();
         try {
             Class.forName("com.mysql.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com/isQU3iyl7C", "isQU3iyl7C", "RaMsV9OFn1");
             Statement statement = con.createStatement();

             if (strings[0].equals("Teacher")) {
                 String query = "select * from teacher where username = '" + username + "'";
                 resultSet = statement.executeQuery(query);
                 resultSet.next();
                 Log.i("status01","01");
                 Log.i("statusname",resultSet.getString("name"));
                 arrayList.add(resultSet.getString("name"));
                 arrayList.add(resultSet.getString("phone"));
                 arrayList.add(resultSet.getString("email"));
                 arrayList.add(resultSet.getString("subject"));
                 arrayList.add(resultSet.getString("qualification"));

                 return arrayList;


             } else if (strings[0].equals("Student")) {
                 String query = "select * from student where username = '" + username + "'";
                 resultSet = statement.executeQuery(query);
                 resultSet.next();
                 arrayList.add(resultSet.getString("name"));
                // Log.i("status",arrayList.get(0));
                 arrayList.add(resultSet.getString("registrationno"));
                 arrayList.add(resultSet.getString("phone"));
                 arrayList.add(resultSet.getString("email"));
                 arrayList.add(resultSet.getString("branch"));
                 arrayList.add(resultSet.getString("groupno"));
                 arrayList.add(resultSet.getString("subgroupno"));
                 arrayList.add(resultSet.getString("year"));
                 return arrayList;

             }
         }
         catch (Exception e)
         {
             Log.i("status78","failed");
         }
         return null;
     }
 }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent =getIntent();
      progressBar=findViewById(R.id.progressBarL);
      progressBar.setVisibility(View.INVISIBLE);
      usertype= intent.getStringExtra("user");
        Log.i("user",usertype);

        b1=findViewById(R.id.buttonL);
        b2=(Button)findViewById(R.id.button4);
        t1=(TextView)findViewById(R.id.textView5);
        t2=(TextView)findViewById(R.id.textViewlast);
        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText2);
        t1.setVisibility(View.INVISIBLE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=e1.getText().toString();
                password=e2.getText().toString();
                 dpassword="AES_DECRYPT(`password`,'MVSBGG')";
                Check check = new Check();
                try {
                    check.execute(usertype);
                }
                 catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usertype.equals("Teacher"))
                {
                    Intent intent1=new Intent(LoginActivity.this, SignUpAsTeacher.class);
                    startActivity(intent1);

                }
                else if(usertype.equals("Student"))
                {
                    Intent intent2=new Intent(LoginActivity.this, SignUpAsStudent.class);
                    startActivity(intent2);
                }
                else
                {
             t2.setVisibility(View.VISIBLE);
                }
            }
        });


    }
}
