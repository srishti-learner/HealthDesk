package tcsproject.healthdesk.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.R;
import tcsproject.healthdesk.healthSeeker.Appointment;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Created by Srishti on 6/7/2017.
 */

public class RequestPage extends AppCompatActivity{
    TextView text, name, dob, date, reason;
    Button yes,no,cancel,prescription;
    String hospital,department,doctor,reqId,id;
    StringBuffer S_name,S_dob,S_date,S_reason;
    DatabaseReference db,mDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_request);
        text=(TextView) findViewById(R.id.text);
        name=(TextView) findViewById(R.id.name);
        dob=(TextView) findViewById(R.id.dob);
        date=(TextView) findViewById(R.id.date);
        reason=(TextView) findViewById(R.id.reason);
        yes=(Button) findViewById(R.id.yes);
        no=(Button) findViewById(R.id.no);
        cancel= (Button) findViewById(R.id.cancel);
        prescription =(Button) findViewById(R.id.prescription);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Request");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        text.setText("Hello "+DoctorHomePage.name+", would you like to fix this appointment?");
        S_name=new StringBuffer("Name: ");
        S_date=new StringBuffer("Date: ");
        S_dob=new StringBuffer("DOB: ");
        S_reason=new StringBuffer("Reason: ");

        final Bundle bundle= getIntent().getExtras();
        if(bundle.getString("type").contentEquals("request")){
            cancel.setVisibility(View.GONE);
            prescription.setVisibility(View.GONE);
        }
        else{
            yes.setVisibility(View.GONE);
            no.setVisibility(View.GONE);
        }
        name.setText(S_name.append(bundle.getString("Name")));
        date.setText(S_date.append(bundle.getString("Date")));
        dob.setText(S_dob.append(bundle.getString("Dob")));
        reason.setText(S_reason.append(bundle.getString("Reason")));

        id=bundle.getString("Id");
        reqId=bundle.getString("ReqId");

        hospital=DoctorHomePage.hospital;
        department=DoctorHomePage.department;
        doctor=DoctorHomePage.name;

        db=FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(id).child("Appointments");
        mDatabase=FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospital).child(department).child(DoctorHomePage.docId).child("Requests").child(reqId);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment appointment=new Appointment(hospital,department,doctor,
                        bundle.getString("Date"),reason.getText().toString());
                try {
                    db.child(reqId).setValue(appointment);
                    mDatabase.child("grant").setValue("true");
                    Toast.makeText(getApplicationContext(),"Done! Now,you have a new appointment on "+date.getText().toString(),Toast.LENGTH_LONG).show();
                }
                catch (DatabaseException dbe){
                    Log.d("AppointmentDBError:",""+dbe);
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDatabase.child("grant").setValue("false");
                    Appointment appointment=new Appointment(hospital,department,doctor,
                            bundle.getString("Date"),"Request was cancelled");
                    db.child(reqId).setValue(appointment);
                    Toast.makeText(getApplicationContext(),"Request Cancelled! ",Toast.LENGTH_LONG).show();
                }
                catch (DatabaseException dbe){
                    Log.d("AppointmentDBError:",""+dbe);
                }
            }
        });
    cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Appointment appointment=new Appointment(hospital,department,doctor,
                    bundle.getString("Date"),"Appointment cancelled by the Doctor.");
            try {
                db.child(reqId).setValue(appointment);
                mDatabase.child("grant").setValue("false");
                Toast.makeText(getApplicationContext(),"Appointment Cancelled!",Toast.LENGTH_LONG).show();
                finish();
            }
            catch (DatabaseException dbe){
                Log.d("AppointmentDBError:",""+dbe);
            }
        }
    });
        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RequestPage.this,Prescribe.class);
                Bundle PrescribeBundle=new Bundle();
                PrescribeBundle.putString("Id",id);
                PrescribeBundle.putString("AppId",reqId);
                intent.putExtras(PrescribeBundle);
                startActivity(intent);
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){

            case KeyEvent.KEYCODE_BACK:
                //  onBackPressed();
                Intent intent = new Intent(this, DoctorHomePage.class);
                startActivity(intent);
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}