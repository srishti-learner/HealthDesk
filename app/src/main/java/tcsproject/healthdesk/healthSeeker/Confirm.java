package tcsproject.healthdesk.healthSeeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.doctor.Request;

/**
 * Created by Srishti on 05/07/2017.
 */

public class Confirm extends AppCompatActivity{
    EditText problem,date;
    Button confirm;
    String id,requestString,dateString,dob,name;
    DatabaseReference db;
    FirebaseHelper helper;
    String hospital,doctor,department,docId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_confirm_appointment);
        problem= (EditText) findViewById(R.id.problem);
        date= (EditText) findViewById(R.id.date);
        confirm= (Button) findViewById(R.id.confirm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Confirm");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("Id","Xi");

        Bundle bundle = getIntent().getExtras();
        doctor=bundle.getString("Doctor");
        hospital=bundle.getString("Hospital");
        department=bundle.getString("Department");
        docId=bundle.getString("Id");
        db= FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospital).child(department).child(docId);
        helper= new FirebaseHelper(db);

        confirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //add to database
            requestString=problem.getText().toString();
            dateString=date.getText().toString();
            FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dob=dataSnapshot.child("dob").getValue().toString();
                    name=dataSnapshot.child("name").getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Request request=new Request(id,name,dob,requestString,dateString); //doc,hos,date,department,reason
            try{
                db.child("Requests").push().setValue(request);
                Toast.makeText(getApplicationContext(),"Request sent successfully.",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Confirm.this,HealthSeekerHomePage.class);
                startActivity(intent);
                Confirm.this.finish();
            }
            catch (DatabaseException e){
                Log.d("appointment error"," "+e);
            }

           }
       });
    }
}