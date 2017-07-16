package tcsproject.healthdesk.healthSeeker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.AboutUs;
import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.UserSelection;

/**
 * Created by Srishti on 27/06/2017.
 */

public class AppointDetails extends AppCompatActivity {
    private static final String DEFAULT_STRING = "N/A";
    TextView doctor, hospital, date, department, reason, prescription;
    Button tests;
    String S_doctor, S_hospital, S_date, S_department, S_reason,S_prescription,S_id;
    Bundle extraBundle;
    FirebaseHelper helper;
    DatabaseReference db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Appointment");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        doctor = (TextView) findViewById(R.id.doctor);
        hospital = (TextView) findViewById(R.id.hospital);
        date = (TextView) findViewById(R.id.date);
        department = (TextView) findViewById(R.id.department);
        reason = (TextView) findViewById(R.id.reason);
        prescription=(TextView) findViewById(R.id.prescription);
        tests= (Button) findViewById(R.id.tests);
        prescription.setText("");

        extraBundle = getIntent().getExtras();
        S_id=extraBundle.getString("Id");
        S_doctor="Doctor: "+extraBundle.getString("Doctor");
        S_prescription="Prescription:"+extraBundle.getString("Prescription");
        S_hospital="Hospital: "+extraBundle.getString("Hospital");
        S_date="Date: "+extraBundle.getString("Date");
        S_department="Department: "+extraBundle.getString("Department");
        S_reason="Reason: "+extraBundle.getString("Reason");
        doctor.setText(S_doctor);
        hospital.setText(S_hospital);
        date.setText(S_date);
        department.setText(S_department);
        reason.setText(S_reason);

        if (extraBundle.getString("From").contentEquals("past")){
            prescription.setText(S_prescription);
        }
        else{
            prescription.setVisibility(View.GONE);
            tests.setVisibility(View.GONE);
        }
        db = FirebaseDatabase.getInstance().getReference().child("HealthSeeker");
        db.keepSynced(true);
        helper = new FirebaseHelper(db);



    tests.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(AppointDetails.this,TestsList.class);
            Bundle bundle=new Bundle();
            bundle.putString("Id",S_id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
        }
        if (id == R.id.action_settings) {
            SharedPreferences sharedPreferences =getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(this, UserSelection.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.about){
            Intent i = new Intent(this, AboutUs.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:
                onBackPressed();

                return true;

        }
        return super.onKeyDown(keyCode, event);
    }
}