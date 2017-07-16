package tcsproject.healthdesk.healthSeeker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.assistant.Test;
import tcsproject.healthdesk.recyclerView.AssistantPatientAdapter;
import tcsproject.healthdesk.recyclerView.HospitalAdapter;

/**
 * Created by Srishti on 7/7/2017.
 */

public class TestsList extends AppCompatActivity{
    String id;
    RecyclerView recyclerView;
    DatabaseReference db;
    FirebaseHelper helper;
    AssistantPatientAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("Id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Assistant");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        db= FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(HealthSeekerHomePage.Student).child("Appointments").child(id).child("Tests");
        db.keepSynced(true);
        helper = new FirebaseHelper(db);
        mAdapter = new AssistantPatientAdapter(getApplicationContext(), helper.testRetrieve());
        Log.d("Data","hospitalAdapter");
        recyclerView.setAdapter(mAdapter);
    }
}
