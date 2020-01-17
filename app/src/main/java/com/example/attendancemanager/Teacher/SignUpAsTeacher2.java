package com.example.attendancemanager.Teacher;
import java.util.regex.Pattern;
import java.util.regex.*;
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

public class SignUpAsTeacher2 extends AppCompatActivity {

    public static EditText editText7,editText8,editText9,editText10;

    public static Button button4;
    public static String result="";
    public static ProgressBar progressBar;
    String subject;

    class MobileNumberValidation {
        public  boolean isValid(String s) {
            Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
            Matcher m = p.matcher(s);
            return (m.find() && m.group().equals(s));
        }
    }
    public void validate() {

        Send obj=new Send();

        try {
            obj.execute();

        }
        catch (Exception e)
        {
            result="c";
        }
    }

    class EmailValidation {
        public  boolean isValid(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@"+"gmail.com$";


            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
        }
    }
   public class Send extends AsyncTask<String,Integer,String>
    {
        int i;
        String email=""+editText7.getText();
        String phone=""+editText8.getText();
     //   String subject=""+editText10.getText();


        String qualification=""+editText9.getText();
        String querry1="select * from teacherrequest" +
                " where email='"+editText7.getText().toString()+"'";

        Intent intent = getIntent();

        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");
        String password=intent.getStringExtra("password");
        String epassword="aes_encrypt('"+password+"','MVSBGG')";
        String querry2="insert into " +
                "teacherrequest(name,username,password,phone,email,subject,qualification) " +
                "values('"+name+"'," +
                "'"+username+"'," +
                epassword+"," +
                "'"+phone+"'," +
                "'"+email+"'," +
                "'"+subject+"'," +
                "'"+qualification+"')";
        @Override
        protected String doInBackground(String... strings) {


            try {
                Log.i("check",""+querry1);
                Log.i("check",""+querry2);
                Class.forName("com.mysql.jdbc.Driver");
                //Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection
                        ("jdbc:mysql://remotemysql.com/isQU3iyl7C",
                                "isQU3iyl7C","RaMsV9OFn1");
                Log.i("check","1");
                if(con==null) {
                    Log.i("check",querry2);
                    result= "c";
                }
                else
                {Log.i("check","2");
                    Statement smt=con.createStatement();
                    if(smt.executeQuery(querry1).next())
                    {
                        Log.i("check","3");
                        result="true";

                    }
                    else
                    {   Log.i("check","4");
                        result="false";
                        smt.executeUpdate(querry2);


                    }

                }
            }
            catch (Exception e)
            {Log.i("check","5");
                result= "c";

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            editText7.setEnabled(true);
            editText8.setEnabled(true);
            editText9.setEnabled(true);
            // textView6.setEnabled(true);
            button4.setEnabled(true);
           editText10.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            if(result.equals("true"))
            {
                (findViewById(R.id.editText7)).requestFocus();
                ((EditText) findViewById(R.id.editText7)).setError("Email already Exists");
            }
            else if(result.equals("c"))
            {
                Toast.makeText(SignUpAsTeacher2.this,"Check Your Connection",Toast.LENGTH_SHORT).show();
            }

            else if(result.equals("false")) {
                editText7.setEnabled(false);
                editText8.setEnabled(false);
                editText9.setEnabled(false);
                //    textView6.setEnabled(false);
                button4.setEnabled(false);
                editText10.setEnabled(false);
                SendMail sm = new SendMail
                        (SignUpAsTeacher2.this,"666666madhav@gmail.com","Regarding Approval","You have a new request to approve");
                sm.execute();


            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
            editText7.setEnabled(false);
            editText8.setEnabled(false);
            editText9.setEnabled(false);

            button4.setEnabled(false);
            editText10.setEnabled(false);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_teacher2);
        final MobileNumberValidation mobile=new MobileNumberValidation();
        final EmailValidation email=new EmailValidation();
        editText7=(EditText)findViewById(R.id.editText7);
        editText8=(EditText)findViewById(R.id.editText8);
        editText9=(EditText)findViewById(R.id.editText9);
        editText10=(EditText)findViewById(R.id.editTextSTS);


        button4=(Button)findViewById(R.id.button4);
        progressBar=findViewById(R.id.progressBar2);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;
                if(editText7.getText().toString().equals(""))
                {
                    (findViewById(R.id.editText7)).requestFocus();
                    ((EditText) findViewById(R.id.editText7)).setError("Please Enter Email");
                }
                else if(editText10.getText().toString().equals(""))
                {
                    editText10.requestFocus();
                    editText10.setError("Please Enter Subject");
                }
                else if(email.isValid(editText7.getText().toString())==false)
                {
                    (findViewById(R.id.editText7)).requestFocus();
                    ((EditText) findViewById(R.id.editText7)).setError("Plz Enter your Gmail id");
                }
                else if(editText8.getText().toString().equals(""))
                {
                    (findViewById(R.id.editText8)).requestFocus();
                    ((EditText) findViewById(R.id.editText8)).setError("Plz Enter Phone no.");
                }
                else if(mobile.isValid(editText8.getText().toString())==false)
                {
                    (findViewById(R.id.editText8)).requestFocus();
                    ((EditText) findViewById(R.id.editText8)).setError("Plz Enter your Gmail id");
                }
                else if(editText9.getText().toString().equals(""))
                {
                    ( findViewById(R.id.editText9)).requestFocus();
                    ((EditText) findViewById(R.id.editText9)).setError("Plz Enter Qualification");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    subject = ""+editText10.getText().toString();
                    String subject2="";
                    for(i=0;i<subject.length();i++)
                    {
                        if(subject.charAt(i)==' ')
                            subject2 = subject2+"_";
                        else
                            subject2= subject2+subject.charAt(i);
                    }
                    subject=subject2;

                    Log.i("statussubject",subject);
                    validate();

                }

            }
        });
    }
}
