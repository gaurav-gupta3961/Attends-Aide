package com.example.attendancemanager.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancemanager.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StartAttendence extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 1;
    TextView textMessage;
    Button buttonMessage;
LinearLayout LinearPer;
    TextView textViewclass;
 String class1;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";
    String[] split1;
    String[] split2;
    String subject;
    SharedPreferences sp;
    public void goToSettings(View view)
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 1000);

    }
    public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);


        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

                LinearPer.setVisibility(View.INVISIBLE);
                findViewById(R.id.buttonstartAttendence).setEnabled(true);
                findViewById(R.id.buttonstartAttendence).setVisibility(View.VISIBLE);
                AlertDialog.Builder builder  = new AlertDialog.Builder(StartAttendence.this);
                builder.setTitle("Are you sure?").setMessage("Start Attendance.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IncreaseNoOfClass increaseNoOfClass = new IncreaseNoOfClass();
                        increaseNoOfClass.execute(split1[1]+"_"+split2[1]);
                        Intent intent2 = new Intent(StartAttendence.this, TakeAttendence.class);
                        intent2.putExtra("class",split1[1]+"_"+split2[1]);
                        startActivity(intent2);
                        Log.i("statusactivity","true");



                    }
                }).setNegativeButton("Cancel",null).setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            /*else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }*/
            else {
                boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA);
                if (!showRationale) {

                    LinearPer.setVisibility(View.VISIBLE);
                    findViewById(R.id.buttonstartAttendence).setEnabled(false);
                    findViewById(R.id.buttonstartAttendence).setVisibility(View.INVISIBLE);

                } else  {
                   Toast.makeText(this, "Permission DENIED22", Toast.LENGTH_SHORT).show();

                    // user did NOT check "never ask again"
                    // this is a good place to explain the user
                    // why you need the permission and ask if he wants
                    // to accept it (the rationale)
                }
            }
        }
    }
public class IncreaseNoOfClass extends AsyncTask<String,String ,String >
{
    @Override
    protected String doInBackground(String... strings) {
        String query="update "+strings[0]+" set "+subject+"_total = "+subject+"_total+1";
        Log.i("status1100",query);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        }
        catch(Exception e)
        {
            Log.i("status310","failed310");
        }

        return " ";
    }
}
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            LinearPer.setVisibility(View.INVISIBLE);
            findViewById(R.id.buttonstartAttendence).setEnabled(true);
            findViewById(R.id.buttonstartAttendence).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_attendence);
textMessage=findViewById(R.id.textMessage);
        buttonMessage=findViewById(R.id.buttonMessage);
        LinearPer=findViewById(R.id.linearlayoutPer);
        LinearPer.setVisibility(View.INVISIBLE);

        textViewclass = (TextView)findViewById(R.id.textViewTodayClass);
        SharedPreferences sp = getSharedPreferences("com.example.attendancemanager",MODE_PRIVATE);
        subject=sp.getString("subject","no");
        Intent intent = getIntent();
        class1=intent.getStringExtra("class");
        split1=class1.split(" ",3);
        split2=split1[0].split("Group-",2);

        textViewclass.setText(class1);


    }
    public  void startAttendence(View view)
    {

            requestStoragePermission();




    }
    public void takeAttendence()
    {
        Intent intent2 = new Intent(StartAttendence.this,TakeAttendence.class);
        intent2.putExtra("class",split1[1]+"_"+split2[1]);
        Log.i("statusactivity","true");
    }
}
