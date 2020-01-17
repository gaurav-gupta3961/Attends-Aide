package com.example.attendancemanager.Student;
import java.util.regex.Pattern;
import java.util.regex.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
import java.sql.DriverManager;
import java.sql.Statement;

public class SignUpAsStudent2 extends AppCompatActivity {
    public static Spinner myspinner,SpinnerYear,SpinnerGroup,SpinnerSubGroup;
    public static EditText editText7,editText8,editText9,editText10,editText11;
    TextView textView6;
    public static Button button4;
   public static String result="";
    public static ProgressBar progressBar;
    ArrayAdapter<String> adapterSG,adapterSG2;

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
            obj.execute("");

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
    class Send extends AsyncTask<String,Integer,String>
    {
       public String email=""+editText7.getText();
        String phone=""+editText8.getText();
        String branch=""+myspinner.getSelectedItem().toString();
        String year=""+SpinnerYear.getSelectedItem().toString();

        String groupNo=""+SpinnerGroup.getSelectedItem().toString();
        String registrationNo=""+editText10.getText();
        String SubGroup=""+SpinnerSubGroup.getSelectedItem().toString();

        String querry1="select * from student" +
                " where email='"+editText7.getText().toString()+"'";
        String querry3="select * from student" +
                " where registrationno='"+editText10.getText().toString()+"'";
        Intent intent = getIntent();
        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");
        String password=intent.getStringExtra("password");
        String querry2="insert into " +
                "student(name,username,password,registrationno,phone,email,branch,year,groupno,subgroupno) " +
                "values('"+name+"'," +
                "'"+username+"'," +
                "'"+password+"'," +
                "'"+registrationNo+"'," +
                "'"+phone+"'," +
                "'"+email+"'," +
                "'"+branch+"'," +
                "'"+year+"'," +
                "'"+groupNo+"',"+
        "'"+SubGroup+" ')";
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
                    result= "c";
                }
                else
                {
                    Log.i("check","2");
                    Statement smt=con.createStatement();
                    if(smt.executeQuery(querry1).next())
                    {

                        //Log.i("check","3");
                        result="true1";

                    }
                    else
                    {
                        if(smt.executeQuery(querry3).next())
                        {
                            result="true2";

                        }

                        else {
                            result = "false";

                           // smt.executeUpdate(querry2);
                        }

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

            editText10.setEnabled(true);

            // textView6.setEnabled(true);
            button4.setEnabled(true);
            myspinner.setEnabled(true);
            SpinnerYear.setEnabled(true);
            SpinnerGroup.setEnabled(true);
            SpinnerSubGroup.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            if(result.equals("true1"))
            {
                (findViewById(R.id.editTextSE)).requestFocus();
                ((EditText) findViewById(R.id.editTextSE)).setError("Email already Exists");
            }
            else if(result.equals("true2"))
            {
                (findViewById(R.id.editTextSR)).requestFocus();
                ((EditText) findViewById(R.id.editTextSR)).setError("Registration No. already Exists");
            }
            else if(result.equals("c"))
            {
                Toast.makeText(SignUpAsStudent2.this,"Check Your Connection",Toast.LENGTH_SHORT).show();
            }

            else if(result.equals("false")) {

                Intent upload = new Intent(SignUpAsStudent2.this, StudentUploadImage.class);
               Log.i("check",""+email+registrationNo);
               upload.putExtra("registrationNo",registrationNo);
                upload.putExtra("name",name);
                upload.putExtra("phone",phone);
                upload.putExtra("year",year);
                upload.putExtra("username",username);
                upload.putExtra("password",password);
                upload.putExtra("email",email);intent.putExtra("phone",phone);intent.putExtra("year",year);
                upload.putExtra("groupno",groupNo);
                upload.putExtra("subgroupno",SubGroup);
                upload.putExtra("branch",branch);
                startActivity(upload);



            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
            editText7.setEnabled(false);
            editText8.setEnabled(false);

            editText10.setEnabled(false);
            //textView6.setEnabled(false);
            button4.setEnabled(false);
            myspinner.setEnabled(false);
            SpinnerYear.setEnabled(false);
            SpinnerGroup.setEnabled(false);
            SpinnerSubGroup.setEnabled(false);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_student2);
        final MobileNumberValidation mobile=new MobileNumberValidation();
        final EmailValidation email=new EmailValidation();


        editText7=(EditText)findViewById(R.id.editTextSE);
        editText8=(EditText)findViewById(R.id.editTextSP);

        editText10=(EditText)findViewById(R.id.editTextSR);
        myspinner = (Spinner)findViewById(R.id.spinnerS);
        SpinnerYear = (Spinner)findViewById(R.id.spinnerSY);
        SpinnerGroup = (Spinner)findViewById(R.id.spinnerSG);
        SpinnerSubGroup = (Spinner)findViewById(R.id.spinnerSSG);
        textView6=(TextView)findViewById(R.id.TextViewSB);
        button4=(Button)findViewById(R.id.buttonS);
        progressBar=findViewById(R.id.progressBarS);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.branch));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(adapter);
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
                if(!(SpinnerYear.getSelectedItem().toString().equals("First")))
                { adapterSG2 = new ArrayAdapter<String>(SignUpAsStudent2.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.group));
                    adapterSG2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpinnerGroup.setAdapter(adapterSG2);
                }
                else {
                    adapterSG = new ArrayAdapter<String>(SignUpAsStudent2.this,
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
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText7.getText().toString().equals(""))
                {
                    editText7.requestFocus();
                    editText7.setError("Plz Enter Email");
                }
                else if(email.isValid(editText7.getText().toString())==false)
                {
                    editText7.requestFocus();
                    editText7.setError("Plz Enter valid Gmail id");
                }
                else if(editText8.getText().toString().equals(""))
                {
                    editText8.requestFocus();
                    editText8.setError("Plz Enter Phone no.");
                }
                else if(mobile.isValid(editText8.getText().toString())==false)
                {
                    editText8.requestFocus();
                    editText8.setError("Plz Enter your Gmail id");
                }
                else if(editText10.getText().toString().equals(""))
                {
                    editText10.requestFocus();
                    editText10.setError("Plz Enter Your Registration Number");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    validate();

                }

            }
        });
    }
}
