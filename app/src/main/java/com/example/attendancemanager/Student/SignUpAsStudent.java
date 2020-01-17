package com.example.attendancemanager.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SignUpAsStudent extends AppCompatActivity {
    EditText editText3,editText4,editText5,editText6;
    Button b1;
   //int value;
    String result="";
    private ProgressBar progressBar;
   public void validate() {
        Send obj=new Send();

        try {
         obj.execute("");

        }
        catch (Exception e)
        {
            result="c";
        }
    }

    class Send extends AsyncTask<String,Integer,String>
    {

        String querry="select * from student" +
                " where username='"+editText4.getText().toString()+"'";
        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection
                        ("jdbc:mysql://remotemysql.com/isQU3iyl7C",
                                "isQU3iyl7C","RaMsV9OFn1");
                if(con==null) {
                    result= "c";
                }
                else
                {   Statement smt=con.createStatement();

                    if(smt.executeQuery(querry).next())
                    {
                        result="true";
                        }
                    else
                    { // Log.i("return","false");
                        Log.i("return","true4");
                        result="false";

                    }

                }
            }
            catch (Exception e)
            {

                result= "c";
            }
return null;
        }

        @Override
        protected void onPostExecute(String s) {
            editText3.setEnabled(true);
            editText4.setEnabled(true);
            editText5.setEnabled(true);
            editText6.setEnabled(true);
            b1.setEnabled(true);

            progressBar.setVisibility(View.INVISIBLE);
            if(result.equals("true"))
            {
                (findViewById(R.id.editText4)).requestFocus();
                ((EditText) findViewById(R.id.editText4)).setError("User Name already Exists");
            }
            else if(result.equals("c"))
            {
                Toast.makeText(SignUpAsStudent.this,"Check Your Connection",Toast.LENGTH_SHORT).show();
            }

            else if(result.equals("false")) {
                Intent intent = new Intent(SignUpAsStudent.this, SignUpAsStudent2.class);
                intent.putExtra("name", editText3.getText().toString());
                intent.putExtra("username", editText4.getText().toString());
                intent.putExtra("password", editText5.getText().toString());
                startActivity(intent);
            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
            editText3.setEnabled(false);
            editText4.setEnabled(false);
            editText5.setEnabled(false);
            editText6.setEnabled(false);
            b1.setEnabled(false);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_student);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        editText3=(EditText)findViewById(R.id.editText3);
        editText4=(EditText)findViewById(R.id.editText4);
        editText5=(EditText)findViewById(R.id.editText5);
        editText6=(EditText)findViewById(R.id.editText6);

        b1=(Button)findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(editText3.getText().toString().equals(""))
{
    (findViewById(R.id.editText3)).requestFocus();
    ((EditText) findViewById(R.id.editText3)).setError("Plz Enter Name");
}
               else if(editText4.getText().toString().equals(""))
                {
                    (findViewById(R.id.editText4)).requestFocus();
                    ((EditText) findViewById(R.id.editText4)).setError("Plz Enter UserName");
                }
               else if(editText5.getText().toString().equals(""))
                {
                    ( findViewById(R.id.editText5)).requestFocus();
                    ((EditText) findViewById(R.id.editText5)).setError("Plz Enter Password");
                }
               else if(editText6.getText().toString().equals(""))
                {
                    ( findViewById(R.id.editText6)).requestFocus();
                    ((EditText) findViewById(R.id.editText6)).setError("Plz Enter Password");
                }
               else if(!(editText6.getText().toString().equals(editText5.getText().toString())))
                {
                    ( findViewById(R.id.editText6)).requestFocus();
                    ((EditText) findViewById(R.id.editText6)).setError("Password do not match");
                }


else {
    progressBar.setVisibility(View.VISIBLE);
    validate();

    }

            }
        });
    }
}