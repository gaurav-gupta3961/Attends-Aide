package com.example.attendancemanager.Teacher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddAClass extends AppCompatActivity {
    public static Spinner SpinnerYear,SpinnerGroup,SpinnerSubGroup;
    ArrayAdapter<String> adapterSG,adapterSG2;
    int t=0;
    String result="";
    String teacherNameExist="";
    String fromTimeExist="";
    String toTimeExist="";
    String YearExist="";
  String day,username;
  //String fromtime,totime,venue;
  TextView t1;
  EditText e1,e2,e5;
  Button b1,b2;
  Intent intent,intent2;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
    String fromtime;
    String totime;
    String groupNo;
    String subGroup;
    String venue;
    String subject;
    String name;
    String year;
    ProgressBar progressBarTimming;
    public class Update extends AsyncTask<String ,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            Log.i("status0","kkkk");
            String query="update timetable set fromtime = '"+strings[0]+"', totime = '"+strings[1]+"', venue ='"+strings[2]+"' where day ='"+day+"' and teacherusername = '"+username+"' and fromtime = '"+fromtime+"' and totime ='"+totime+"' and venue = '"+venue+"' and year = '"+year+"'";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                int result=statement.executeUpdate(query);
            }
            catch(Exception e)
            {
                Log.i("status77","failed77");
            }
            return "updated";
        }
    }


  public class Send extends AsyncTask<String ,Integer,String>
  {
      @Override
      protected void onPostExecute(String s) {

         // b1.setEnabled(true);
          if(result.equals("true"))
          {
              progressBarTimming.setVisibility(View.INVISIBLE);
              e1.setEnabled(true);
              e2.setEnabled(true);

              e5.setEnabled(true);
              e1.requestFocus();
              SpinnerYear.setEnabled(true);
              SpinnerSubGroup.setEnabled(true);
              SpinnerGroup.setEnabled(true);
               e1.setError("This Timing is already for "+teacherNameExist+" from "+fromTimeExist+" to "+toTimeExist+" of "+YearExist+" year");
             // e5.setError("This Venue is already for "+teacherNameExist+" from "+fromTimeExist+" to "+toTimeExist+" of "+YearExist+" year");

          }
          else if(result.equals("true2"))
          {
              progressBarTimming.setVisibility(View.INVISIBLE);
              e1.setEnabled(true);
              e2.setEnabled(true);
              SpinnerSubGroup.setEnabled(true);
              SpinnerGroup.setEnabled(true);
              e5.setEnabled(true);
              e1.requestFocus();
              SpinnerYear.setEnabled(true);
              //e1.setError("This Timing is already for "+teacherNameExist+" from "+fromTimeExist+" to "+toTimeExist+" of "+YearExist+" year");
              e5.setError("This Venue is already for "+teacherNameExist+" from "+fromTimeExist+" to "+toTimeExist+" of "+YearExist+" year");

          }
          else if(result.equals("c"))
          {  e1.setEnabled(true);
              e2.setEnabled(true);
              SpinnerSubGroup.setEnabled(true);
              SpinnerGroup.setEnabled(true);
              e5.setEnabled(true);
              progressBarTimming.setVisibility(View.INVISIBLE);
              SpinnerYear.setEnabled(true);
              Toast.makeText(AddAClass.this,"Check Your Connection",Toast.LENGTH_SHORT).show();
          }

          else if(result.equals("false")) {
              //e1.setEnabled(true);
              //e2.setEnabled(true);
              //e3.setEnabled(true);
              //e4.setEnabled(true);
              //e5.setEnabled(true);
              progressBarTimming.setVisibility(View.INVISIBLE);
              //Toast.makeText(AddAClass.this, "CHANGES SAVED", Toast.LENGTH_LONG).show();
              Intent intent = new Intent(AddAClass.this, TeacherSchedule.class);
              startActivity(intent);
          }
      }

      @Override
      protected void onPreExecute() {
          super.onPreExecute();
         progressBarTimming.setVisibility(View.VISIBLE);
          e1.setEnabled(false);
          e2.setEnabled(false);
          SpinnerSubGroup.setEnabled(false);
          SpinnerGroup.setEnabled(false);
          e5.setEnabled(false);
          SpinnerYear.setEnabled(false);

      }
      @Override
      protected void onProgressUpdate(Integer... values) {
          super.onProgressUpdate(values);

      }
      @Override
      protected String doInBackground(String... strings) {
          String querryCheck="select * from timetable where" +
                  " (day='"+day+"'&& groupno='"+groupNo+"'&& year='"+year+"')&&" +
                  "((fromtime<"+fromtime+"&& totime>"+fromtime+")" +
                  "||(fromtime<"+totime+"&& totime>"+totime+")" +
                  "||(fromtime="+fromtime+"&& totime="+totime+")" +
                  "||(fromtime>"+fromtime+"&& totime<"+totime+"))";
          String querryCheck1="select * from timetable where" +
                  " (day='"+day+"'&& venue='"+venue+"')&&" +
                  "((fromtime<"+fromtime+"&& totime>"+fromtime+")" +
                  "||(fromtime<"+totime+"&& totime>"+totime+")" +
                  "||(fromtime="+fromtime+"&& totime="+totime+")" +
                  "||(fromtime>"+fromtime+"&& totime<"+totime+"))";
          String query="insert into timetable values" +
                  "('"+day+"','"+username+"','"+name+"','"+subject+"','"+fromtime+"','"+totime+"','"+year+"','"+groupNo+"','"+subGroup+"','"+venue+"')";


          Log.i("q123",querryCheck);
          Log.i("q123",query);
          Log.i("q123",querryCheck1);

          try {
              Class.forName("com.mysql.jdbc.Driver");
              Connection con = DriverManager.getConnection(url, user, pass);
              DatabaseMetaData metadata = con.getMetaData();
              //Statement statement = con.createStatement();
              if(con==null) {
                  result= "c";
              }
              else
              {   Statement smt=con.createStatement();
                  ResultSet rs=smt.executeQuery(querryCheck);
                  //ResultSet rs1=smt.executeQuery(querryCheck1);

                  if(rs.next())
                  {
                      result="true";
                      Log.i("q123",rs.getString("fromtime"));
                      fromTimeExist=""+rs.getString("fromtime");
                      toTimeExist=""+rs.getString("totime");
                      YearExist=""+rs.getString("year");
                      String querry3="select * from teacher where" +
                          " username='"+rs.getString("teacherusername")+"'";
                  Log.i("q123",querry3);
                      ResultSet rs2=smt.executeQuery(querry3);
Log.i("q123","dsds");
                         rs2.next();
                      teacherNameExist=""+rs2.getString(1);

                      Log.i("q123",teacherNameExist);
                      Log.i("q123",fromTimeExist);
                      Log.i("q12    3",toTimeExist);

                  }
                    /*  else if(rs1.next())
                  {
                      result="true1";
                      Log.i("q123",rs1.getString("fromtime"));
                      fromTimeExist=""+rs1.getString("fromtime");
                      toTimeExist=""+rs1.getString("totime");
                      YearExist=""+rs1.getString("year");
                      String querry3="select * from teacher where" +
                              " username='"+rs.getString("teacherusername")+"'";
                      Log.i("q123",querry3);
                      ResultSet rs2=smt.executeQuery(querry3);
                      Log.i("q123","dsds");
                      rs2.next();
                      teacherNameExist=""+rs2.getString(1);

                      Log.i("q123",teacherNameExist);
                      Log.i("q123",fromTimeExist);
                      Log.i("q12    3",toTimeExist);

                  }
*/
                  else
                  {  Log.i("return","false");
                     // Log.i("return","true4");
                     // Toast.makeText(AddAClass.this, "CHANGES SAVED", Toast.LENGTH_LONG).show();

                      result="false";
                      smt.executeUpdate(query);
                      ResultSet resultSet;
                      resultSet = metadata.getColumns(null, null, year+"_"+groupNo+"_"+subGroup, subject);
                      if(!resultSet.next())
                      {
                          Log.i("status1234","true");
                         String query7="alter table "+year+"_"+groupNo+"_"+subGroup+" add "+subject+ " integer(4) NOT NULL DEFAULT(0)";
                         String query8 ="alter table "+year+"_"+groupNo+"_"+subGroup+" add "+subject+"_total  integer(4) NOT NULL DEFAULT(0)";
                         String query9="alter table "+year+"_"+groupNo+"_"+subGroup+" add "+subject+"_today integer(4) NOT NULL DEFAULT(0)";

                         Log.i("status33",query7);
                         Log.i("status33",query8);
                         Log.i("status33",query9);

                         smt.executeUpdate(query7);
                         smt.executeUpdate(query8);
                         smt.executeUpdate(query9);


                      }
                      else
                      {
                          Log.i("status2222","already exists");
                      }
                  }

              }

          }
          catch(Exception e)
          {
              result="c";
              Log.i("status10","failed10");
          }
          return null;


      }
  }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aclass);
        day=getIntent().getStringExtra("day");
        t1=(TextView)findViewById(R.id.textViewSelectedDAy);
        e1=(EditText)findViewById(R.id.editTextstart);
        e2=(EditText)findViewById(R.id.editTextend);


        SpinnerGroup = (Spinner)findViewById(R.id.spinnerTG);
        SpinnerSubGroup = (Spinner)findViewById(R.id.spinnerTSG);
        e5=(EditText)findViewById(R.id.editTextvenue);
        b1=(Button)findViewById(R.id.buttonsave);
        b2=(Button)findViewById(R.id.buttondiscard);
        SpinnerYear = (Spinner)findViewById(R.id.spinnerTY);
        ArrayAdapter<String> adapterY = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.year));
        adapterY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerYear.setAdapter(adapterY);

        ArrayAdapter<String> adapterSS = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.subgroup));
        adapterSS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSubGroup.setAdapter(adapterSS);
        SpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                  @Override
                                                  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                                      if (!(SpinnerYear.getSelectedItem().toString().equals("First"))) {
                                                          adapterSG2 = new ArrayAdapter<String>(AddAClass.this,
                                                                  android.R.layout.simple_spinner_dropdown_item,
                                                                  getResources().getStringArray(R.array.group));
                                                          adapterSG2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                          SpinnerGroup.setAdapter(adapterSG2);
                                                      } else {
                                                          adapterSG = new ArrayAdapter<String>(AddAClass.this,
                                                                  android.R.layout.simple_spinner_dropdown_item,
                                                                  getResources().getStringArray(R.array.fgroup));
                                                          adapterSG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                          SpinnerGroup.setAdapter(adapterSG);
                                                      }
                                                  }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        progressBarTimming=findViewById(R.id.progressBarTimming);
        t1.setText(day);
//progressBarTimming.setEnabled(false);
        if(getIntent().getStringExtra("i").equals("update"))
        {
            Intent intent =getIntent();
            e1.setText(intent.getStringExtra("fromtime"));
            fromtime=intent.getStringExtra("fromtime");
            e2.setText(intent.getStringExtra("totime"));
            totime=intent.getStringExtra("totime");
            e5.setText(intent.getStringExtra("venue"));
            int selectionPosition1= adapterY.getPosition(intent.getStringExtra("year"));
            SpinnerYear.setSelection(selectionPosition1);
            int selectionPosition2= adapterY.getPosition(intent.getStringExtra("groupno"));
            SpinnerGroup.setSelection(selectionPosition2);
            int selectionPosition3= adapterY.getPosition(intent.getStringExtra("subgroupno"));
            SpinnerSubGroup.setSelection(selectionPosition3);
            SpinnerSubGroup.setEnabled(false);
            SpinnerGroup.setEnabled(false);
            SpinnerYear.setEnabled(false);
           // year=""+SpinnerYear.getSelectedItem().toString();
            venue=intent.getStringExtra("venue");
            t=1;
        }
        SharedPreferences sp =getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
         username=sp.getString("username","nousername");
         subject = sp.getString("subject","no");
         name=sp.getString("name","no");
        intent2 = new Intent(AddAClass.this,TeacherSchedule.class);
    }
    public void save(View view) {
        if (e1.getText().toString().equals("")) {
            e1.requestFocus();
            e1.setError("please enter Start timming");
        } else if (e2.getText().toString().equals("")) {
            e2.requestFocus();
            e2.setError("please enter End timming");
        } else if (e5.getText().toString().equals("")) {
            e5.requestFocus();
            e5.setError("please enter Venue");

        } else {
            int start = Integer.parseInt(e1.getText().toString());
            int end = Integer.parseInt(e2.getText().toString());

            if (start > end) {
                e1.requestFocus();
                e1.setError("start timming cannot be greater than end timming");
            } else {
                if (t == 0) {
                    fromtime = e1.getText().toString();
                    totime = e2.getText().toString();
                    groupNo = SpinnerGroup.getSelectedItem().toString();
                    subGroup = SpinnerSubGroup.getSelectedItem().toString();
                    ;
                    venue = e5.getText().toString();
                    year = "" + SpinnerYear.getSelectedItem().toString();


                    Send send = new Send();
                    send.execute();
                } else {
                    Update update = new Update();
                    update.execute(e1.getText().toString(), e2.getText().toString(), e5.getText().toString());
                    Toast.makeText(this, "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();

                }
                startActivity(intent2);
            }
        }
    }
    public void  discard(View view)
    {
        Toast.makeText(this, "CHANGES DISCARDED", Toast.LENGTH_LONG).show();
       startActivity(intent2);
    }
}
