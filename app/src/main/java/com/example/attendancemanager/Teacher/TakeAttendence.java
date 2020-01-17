package com.example.attendancemanager.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.attendancemanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TakeAttendence extends AppCompatActivity {
    ProgressBar progressbarTake;
    ImageView imageView;
    private TextView tv,tclass;
    private SurfaceView sv;
    private CameraSource cs;
    private BarcodeDetector bd;
    ImageButton imageButtonapprove,imageButtondisapprove;
    TextView line;
    String class1,subject;
    String s="";
    SharedPreferences sp;
    int flag=0;

    private StorageReference mStorageRef;
    String studentRegistrationno="",studentname,data;
    String url="jdbc:mysql://remotemysql.com/isQU3iyl7C";
    String user="isQU3iyl7C";
    String pass="RaMsV9OFn1";

    public class Close extends AsyncTask<String,String,String >
    {
        @Override
        protected String doInBackground(String... strings) {
            String query="update "+strings[0]+" set "+subject+"_today = 0";

            Log.i("status1111",query);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);

            }
            catch(Exception e)
            {
                Log.i("status333","failed333");
            }

            return " ";
        }
    }
    public class SendAttendence extends AsyncTask<String, String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            String query="update "+strings[0]+" set "+subject+" = "+subject+"+1 where registrationno = "+studentRegistrationno+" and "+subject+"_today = 0";
            String query2="update "+strings[0]+" set "+subject+"_today = 1 where registrationno = "+studentRegistrationno;

            Log.i("status1111",query);
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement statement = con.createStatement();
                statement.executeUpdate(query);
                statement.executeUpdate(query2);
            }
            catch(Exception e)
            {
                Log.i("status330","failed330");
            }
            return " ";
        }

        @Override
        protected void onPostExecute(String s) {
            tv.setText("");
            studentRegistrationno = "";
            imageView.setImageDrawable(null);
            imageButtonapprove.setVisibility(View.INVISIBLE);
            imageButtondisapprove.setVisibility(View.INVISIBLE);
            super.onPostExecute(s);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        progressbarTake=findViewById(R.id.progressBarTake);
        imageButtonapprove = (ImageButton)findViewById(R.id.buttonApproveAttendence);
        imageButtondisapprove = (ImageButton)findViewById(R.id.buttonDisApproveAttendence);
        line=(TextView)findViewById(R.id.line1);
        imageView=findViewById(R.id.imageOfStudent);
        tv=(TextView)findViewById(R.id.tv1);
        tclass=(TextView)findViewById(R.id.textViewClassheader);
        sp=getSharedPreferences("com.example.attendancemanager",MODE_PRIVATE);
        subject=sp.getString("subject","no");
        Intent intent=getIntent();
        class1=intent.getStringExtra("class");
        progressbarTake.setVisibility(View.INVISIBLE);
        tclass.setText(class1);
        sv=(SurfaceView)findViewById(R.id.sv1);
     /*  try {
           vp = line.animate();
           final float scale = getResources().getDisplayMetrics().density;
           final float pixels = (int) (232 * scale + 0.5f);
           new Timer().scheduleAtFixedRate(new TimerTask() {
               @Override
               public void run() {
                   if (flag == 0) {
                       vp.translationYBy(pixels).setDuration(700);
                       flag = 1;
                   } else {
                       vp.translationYBy(-pixels).setDuration(700);
                       flag = 0;
                   }

               }
           }, 0, 2500);
       }
       catch (Exception e)
       {
           //e.getMessage();
           //e.printStackTrace();
       }finally {
*/

        bd=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        cs=new CameraSource.Builder(this,bd).setRequestedPreviewSize(640,480).build();

        sv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder Holder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                try {

                    cs.start(Holder);
                }catch(IOException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                cs.stop();
            }
        });

        bd.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qr=detections.getDetectedItems();

                if(qr.size() != 0)
                {
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vib=(Vibrator)getApplicationContext().getSystemService(VIBRATOR_SERVICE);
                            progressbarTake.setVisibility(View.VISIBLE);
                            vib.vibrate(300);
                            data =qr.valueAt(0).displayValue;
                            Log.i("data",data);
                            String[] split = data.split("%",2);
                            studentname=split[0];
                            studentRegistrationno=split[1];
                            tv.setText("NAME : "+studentname+"\nReg_No. : "+studentRegistrationno);
                            imageButtonapprove.setVisibility(View.VISIBLE);
                            imageButtondisapprove.setVisibility(View.VISIBLE);

                            mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
                            mStorageRef.child(studentRegistrationno+"photo").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    //s=""+mStorageRef.child("gs://studentphoto-80f91.appspot.com/uploads/20188003").getDownloadUrl().getResult();
                                    Picasso.with(TakeAttendence.this).load(uri).resize(4096,4096).onlyScaleDown().into(imageView);
                                    progressbarTake.setVisibility(View.INVISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors

                                }
                            });
                            //mStorageRef.child(intent.getStringExtra("registrationNo"))
                            //String s="https://firebasestorage.googleapis.com/v0/b/studentphoto-80f91.appspot.com/o/uploads%2F1567884774346.jpg?alt=media&token=37384a44-3f4e-403f-8e09-e590474f8615";

                        }
                    });
                }
            }
        });}

    public void approve(View view)
    {
        if(!studentRegistrationno.equals("")) {
            SendAttendence send = new SendAttendence();
            send.execute(class1);

        }


    }
    public void disapprove(View view)
    {
        tv.setText("");
        studentRegistrationno = "";
        imageView.setImageDrawable(null);
        imageButtonapprove.setVisibility(View.INVISIBLE);
        imageButtondisapprove.setVisibility(View.INVISIBLE);

    }
    public void onBackPressed(){

        LayoutInflater layoutInflater = getLayoutInflater();
        final View rowview=layoutInflater.inflate(R.layout.alert_dialog_absentees,null,false);

        AlertDialog.Builder builder = new AlertDialog.Builder(TakeAttendence.this);
        builder.setView(rowview).setTitle("Are you sure?").setMessage("You want to end Attendance.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioButton radioButton =rowview.findViewById(R.id.radioButtonlistOfAbsentees);
                if(radioButton.isChecked())
                {
                    Intent intent2 = new Intent(TakeAttendence.this, ListOfAbsentees.class);
                    intent2.putExtra("class1",class1);
                    startActivity(intent2);
                    finishAffinity();
                }
                else {
                    Intent intent2 = new Intent(TakeAttendence.this, TeacherSchedule.class);

                    startActivity(intent2);
                    finishAffinity();
                    Log.i("statusactivity2", "true2");
                    Close close = new Close();
                    close.execute(class1);

                }
            }

        }).setNegativeButton("Cancel",null).setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void closeAttendence(View view)
    {
        LayoutInflater layoutInflater = getLayoutInflater();
        final View rowview=layoutInflater.inflate(R.layout.alert_dialog_absentees,null,false);

        AlertDialog.Builder builder = new AlertDialog.Builder(TakeAttendence.this);
        builder.setView(rowview).setTitle("Are you sure?").setMessage("You want to end Attendance.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 RadioButton radioButton =rowview.findViewById(R.id.radioButtonlistOfAbsentees);
                 if(radioButton.isChecked())
                 {
                     Intent intent2 = new Intent(TakeAttendence.this,ListOfAbsentees.class);
                     intent2.putExtra("class1",class1);
                     startActivity(intent2);
                     finishAffinity();
                 }
                 else {
                     Intent intent2 = new Intent(TakeAttendence.this, TeacherSchedule.class);

                     startActivity(intent2);
                     finishAffinity();
                     Log.i("statusactivity2", "true2");
                     Close close = new Close();
                     close.execute(class1);

                 }
            }

        }).setNegativeButton("Cancel",null).setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        }
    }

