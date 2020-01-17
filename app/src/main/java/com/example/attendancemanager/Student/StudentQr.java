package com.example.attendancemanager.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.attendancemanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

public class StudentQr extends AppCompatActivity {
ImageView imageView;
    Bitmap QrImage;
    String s="";
    String registrationno,name;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_qr);
        SharedPreferences sp = getSharedPreferences("com.example.attendancemanager", MODE_PRIVATE);
        registrationno = sp.getString("registrationno", "no");
        name = sp.getString("name","no");
        imageView = findViewById(R.id.qrcode);
        try {
            MultiFormatWriter m1 = new MultiFormatWriter();

            BitMatrix bm = m1.encode(name+"%"+registrationno, BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder be = new BarcodeEncoder();
            QrImage = be.createBitmap(bm);
            imageView.setImageBitmap(QrImage);
        } catch (Exception e) {

        }
    }
}
