package tcsproject.healthdesk.healthSeeker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.AboutUs;
import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.UserSelection;
import tcsproject.healthdesk.doctor.Doctor;
import tcsproject.healthdesk.recyclerView.DoctorAdapter;
import tcsproject.healthdesk.recyclerView.RecyclerTouchListener;

/**
 * Created by Srishti on 04/07/2017.
 */

public class DoctorList extends AppCompatActivity{
    RecyclerView recyclerView;
    private DoctorAdapter mAdapter;
    DatabaseReference db;
    FirebaseHelper helper;
    Button notify;
    String hospital,doctor,department,id;
    public Bundle bundle = new Bundle();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        hospital = getIntent().getExtras().getString("Key");
        notify= (Button) findViewById(R.id.notify);
        notify.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Doctors");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        db = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospital); //hospital passed as hospital
        db.keepSynced(true);
        helper = new FirebaseHelper(db);
        mAdapter = new DoctorAdapter(getApplicationContext(), helper.docRetrieve());
        Log.d("Data","docAdapter");
        recyclerView.setAdapter(mAdapter);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(helper.docList.size()>0) {
                    Doctor data = helper.docList.get(position);
                    Log.d("Doctor:", position + " ");
                    doctor = data.getDoctor();
                    department=data.getDepartment();
                    id=data.getId();
                    Log.d("Data", hospital);

                    Intent intent = new Intent(DoctorList.this, Confirm.class);
                    bundle.putString("Hospital", hospital);
                    bundle.putString("Doctor", doctor);
                    bundle.putString("Department",department);
                    bundle.putString("Id",id);
                    Log.w("DoctorBundle", hospital +" "+doctor);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //RegEvents.this.finish();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id=item.getItemId();
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        if(id==R.id.action_settings){
            // Toast.makeText(this,"you hit "+item.getTitle(),Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(this,UserSelection.class);
            startActivity(i);
            this.finish();
            return true;
        }
        if(id==R.id.about){
            Intent i = new Intent(this,AboutUs.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
