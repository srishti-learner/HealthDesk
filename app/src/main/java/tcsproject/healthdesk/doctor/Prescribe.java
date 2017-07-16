package tcsproject.healthdesk.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.healthSeeker.HospitalList;

/**
 * Created by Srishti on 6/7/2017.
 */

public class Prescribe extends AppCompatActivity{
    Button assistant,upload;
    EditText prescription;
    TextInputLayout T_Prescription;
    TextView display;
    String id,appId;
    DatabaseReference db;
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescribe);
        prescription= (EditText) findViewById(R.id.prescribe_text);
        T_Prescription= (TextInputLayout) findViewById(R.id.input_layout_prescribe);
        assistant= (Button) findViewById(R.id.assistant);
        T_Prescription.setHint("Enter Prescription/tests");
        upload= (Button) findViewById(R.id.upload);
        display=(TextView)findViewById(R.id.displayText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Prescription");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        bundle=getIntent().getExtras();
        id=bundle.getString("Id");
        appId=bundle.getString("AppId");
        db=FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(id).child("Appointments").child(appId);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             try{
                 if(prescription.getText().toString().contentEquals("")){
                     Toast.makeText(getApplicationContext(),"Prescription should not be blank!",Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Log.e("prescribe",id+"  "+appId);
                     db.child("prescription").setValue(prescription.getText().toString());
                     display.setVisibility(View.VISIBLE);
                     assistant.setVisibility(View.VISIBLE);
                     Toast.makeText(getApplicationContext(),"Prescription added successfully!",Toast.LENGTH_SHORT).show();
                 }
             }
             catch (DatabaseException exception){
                 Toast.makeText(getApplicationContext(),"Not successful!",Toast.LENGTH_SHORT).show();
             }
            }
        });
        assistant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Prescribe.this, HospitalList.class);
                Bundle bundle=new Bundle();
                bundle.putString("User","Doctor");
                bundle.putString("HId",id);
                bundle.putString("AppId",appId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
