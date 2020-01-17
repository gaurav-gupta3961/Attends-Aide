package com.example.attendancemanager.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.attendancemanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class studentActivity extends AppCompatActivity {
    SharedPreferences sp;
    private StorageReference mStorageRef;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    ImageView imageview;
    ProgressBar progressbarI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        sp = getSharedPreferences("com.example.attendancemanager", Context.MODE_PRIVATE);
        imageview=findViewById(R.id.imageViewS);
        progressbarI=findViewById(R.id.progressBarI);
        t1=(TextView)findViewById(R.id.textViewSN);
        t2=(TextView)findViewById(R.id.textViewSU);
        t3=(TextView)findViewById(R.id.textViewSE);
        t4=(TextView)findViewById(R.id.textViewSP);
        t5=(TextView)findViewById(R.id.textViewSR);
        t6=(TextView)findViewById(R.id.textViewSB);
        t7=(TextView)findViewById(R.id.textViewSGN);
        t8=(TextView)findViewById(R.id.textViewSSGN);
        t9=(TextView)findViewById(R.id.textViewSY);
progressbarI.setVisibility(View.VISIBLE);
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mStorageRef.child((sp.getString("registrationno"," "))+"photo").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                //s=""+mStorageRef.child("gs://studentphoto-80f91.appspot.com/uploads/20188003").getDownloadUrl().getResult();
                Picasso.with(studentActivity.this).load(uri).resize(4096,4096).onlyScaleDown().into(imageview);
                progressbarI.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors

            }
        });
                t1.setText(sp.getString("name"," "));
        t2.setText(sp.getString("username"," "));
        t3.setText(sp.getString("email"," "));
        t4.setText(sp.getString("phone"," "));
        t5.setText(sp.getString("registrationno"," "));
        t6.setText(sp.getString("branch"," "));
        t7.setText(sp.getString("groupno"," "));
        t8.setText(sp.getString("subgroupno"," "));
        t9.setText(sp.getString("year"," "));

    }
}
