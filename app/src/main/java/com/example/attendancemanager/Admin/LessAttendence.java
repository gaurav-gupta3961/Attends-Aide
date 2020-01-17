package com.example.attendancemanager.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.attendancemanager.R;

import java.util.ArrayList;

public class LessAttendence extends AppCompatActivity {
    ListView lw;
    ArrayList<String> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_less_attendence);
        lw= (ListView)findViewById(R.id.list12);
        filldata();
        MyCustomListAdapterClasses myCustomListAdapterClasses = new MyCustomListAdapterClasses(this,classes);
        lw.setAdapter(myCustomListAdapterClasses);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LessAttendence.this, LessAttendenceEx.class);
                TextView t = (TextView)view.findViewById(R.id.textView25);

                intent.putExtra("class",t.getText().toString());
                startActivity(intent);
            }
        });


    }
    public void filldata()
    {
      classes= new ArrayList<String>();
      classes.add("First_A_1");
        classes.add("First_A_2");
        classes.add("First_B_1");
        classes.add("First_B_2");
        classes.add("First_C_1");
        classes.add("First_C_2");
        classes.add("First_D_1");
        classes.add("First_D_2");
        classes.add("First_E_1");
        classes.add("First_E_2");
        classes.add("First_F_1");
        classes.add("First_F_2");
        classes.add("First_G_1");
        classes.add("First_G_2");
        classes.add("First_H_1");
        classes.add("First_H_2");
        classes.add("Second_CS_A_1");
        classes.add("Second_CS_A_2");
        classes.add("Second_CS_B_1");
        classes.add("Second_CS_B_2");
        classes.add("Second_CS_C_1");
        classes.add("Second_CS_C_2");
        classes.add("Second_IT_A_1");
        classes.add("Second_IT_A_2");
        classes.add("Second_IT_B_1");
        classes.add("Second_IT_B_2");
        classes.add("Second_EC_A_1");
        classes.add("Second_EC_A_2");
        classes.add("Second_EC_B_1");
        classes.add("Second_EC_B_2");
        classes.add("Third_CS_A_1");
        classes.add("Third_CS_A_2");
        classes.add("Third_CS_B_1");
        classes.add("Third_CS_B_2");
        classes.add("Third_CS_C_1");
        classes.add("Third_CS_C_2");
        classes.add("Third_IT_A_1");
        classes.add("Third_IT_A_2");
        classes.add("Third_IT_B_1");
        classes.add("Third_IT_B_2");
        classes.add("Third_EC_A_1");
        classes.add("Third_EC_A_2");
        classes.add("Third_EC_B_1");
        classes.add("Third_EC_B_2");
        classes.add("Fourth_CS_A_1");
        classes.add("Fourth_CS_A_2");
        classes.add("Fourth_CS_B_1");
        classes.add("Fourth_CS_B_2");
        classes.add("Fourth_CS_C_1");
        classes.add("Fourth_CS_C_2");
        classes.add("Fourth_IT_A_1");
        classes.add("Fourth_IT_A_2");
        classes.add("Fourth_IT_B_1");
        classes.add("Fourth_IT_B_2");
        classes.add("Fourth_EC_A_1");
        classes.add("Fourth_EC_A_2");
        classes.add("Fourth_EC_B_1");
        classes.add("Fourth_EC_B_2");
    }
}
