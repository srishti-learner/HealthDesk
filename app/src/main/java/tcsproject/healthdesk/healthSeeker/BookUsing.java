package tcsproject.healthdesk.healthSeeker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import tcsproject.healthdesk.R;

/**
 * Created by Srishti on 05/07/2017.
 */

public class BookUsing extends AppCompatActivity {
    Button doctor,problem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_book_using);
        doctor= (Button) findViewById(R.id.doctor);
        problem= (Button) findViewById(R.id.problem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Appointment");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookUsing.this,HospitalList.class);
                startActivity(intent);
            }
        });

        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookUsing.this,DepartmentList.class);
                startActivity(intent);
            }
        });
    }
}