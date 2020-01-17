package com.example.attendancemanager.Student;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attendancemanager.login.MainActivity;
import com.example.attendancemanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StudentUploadImage extends AppCompatActivity {
    public String result="";
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    //private TextView mTextViewShowUploads;
    //private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar progressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
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
    {Intent intent = getIntent();
         String email=intent.getStringExtra("email");
        String phone=intent.getStringExtra("phone");
        String branch=intent.getStringExtra("branch");
        String year=intent.getStringExtra("year");

        String groupNo=intent.getStringExtra("groupno");
        String registrationNo=intent.getStringExtra("registrationNo");
        String SubGroup=intent.getStringExtra("subgroupno");;
        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");
        String password=intent.getStringExtra("password");
        String epassword="aes_encrypt('"+password+"','MVSBGG')";
        String querry2="insert into " +
                "student(name,username,password,registrationno,phone,email,branch,year,groupno,subgroupno) " +
                "values('"+name+"'," +
                "'"+username+"'," +
                epassword+"," +
                "'"+registrationNo+"'," +
                "'"+phone+"'," +
                "'"+email+"'," +
                "'"+branch+"'," +
                "'"+year+"'," +
                "'"+groupNo+"',"+
                "'"+SubGroup+" ')";
        String query3 = "insert into "+year+"_"+groupNo+"_"+SubGroup+"(registrationno,name) values('"+registrationNo+"','"+name+"')";

        @Override
        protected String doInBackground(String... strings) {
            Log.i("check",querry2);
            Log.i("check34",query3);
            try {
                uploadFile();
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection
                        ("jdbc:mysql://remotemysql.com/isQU3iyl7C",
                                "isQU3iyl7C","RaMsV9OFn1");
                Log.i("check","1");
                Statement smt=con.createStatement();
                if(con==null) {
                    result = "c";
                }
                else
                {
                    Log.i("check",querry2);
                             smt.executeUpdate(querry2);
                             smt.executeUpdate(query3);
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



            }


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    public class Upload {
        private String mName;
        private String mImageUrl;

        public Upload() {
            //empty constructor needed
        }

        public Upload(String name, String imageUrl) {
            if (name.trim().equals("")) {
                name = "No Name";
            }

            mName = name;
            mImageUrl = imageUrl;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_upload_image);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
      //  mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
       /// mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Toast.makeText(StudentUploadImage.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                validate();
                progressBar.setVisibility(View.VISIBLE);
                mImageView.setEnabled(false);
                mButtonChooseImage.setEnabled(false);
                mButtonUpload.setEnabled(false);


            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).resize(4096,4096).onlyScaleDown().into(mImageView);
        }
    }



    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(getIntent().getStringExtra("registrationNo")+"photo");
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(StudentUploadImage.this, "Upload successful", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            mImageView.setEnabled(true);
                            mButtonChooseImage.setEnabled(true);
                            mButtonUpload.setEnabled(true);
                            Upload upload = new Upload(getIntent().getStringExtra("registrationNo")+"photo",
                                    taskSnapshot.getMetadata().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                            Intent back =new Intent(StudentUploadImage.this, MainActivity.class);
                            startActivity(back);
                            finishAffinity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(StudentUploadImage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}