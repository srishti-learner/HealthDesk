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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tcsproject.healthdesk.AboutUs;
import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.UserSelection;
import tcsproject.healthdesk.assistant.Assistant;
import tcsproject.healthdesk.doctor.AssistantList;
import tcsproject.healthdesk.recyclerView.HospitalAdapter;
import tcsproject.healthdesk.recyclerView.RecyclerTouchListener;

/**
 * Created by Srishti on 03/07/2017.
 */

public class HospitalList extends AppCompatActivity{
    RecyclerView recyclerView;
    HospitalAdapter mAdapter;
    DatabaseReference db;
    FirebaseHelper helper;
    String user;
    public Bundle bundle = new Bundle();
    Bundle myBundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hospitals");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        myBundle=getIntent().getExtras();
        user=myBundle.getString("User");

        db = FirebaseDatabase.getInstance().getReference().child("Hospitals");
        db.keepSynced(true);
        helper = new FirebaseHelper(db);
        mAdapter = new HospitalAdapter(getApplicationContext(), helper.hospitalRetrieve());
        Log.d("Data","hospitalAdapter");
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(helper.hospitalList.size()>0) {
                    Intent intent;
                    String hospital = helper.hospitalList.get(position);
                    Log.d("HospitalData: ",hospital);
                    if(user.contentEquals("Doctor")) {
                        intent = new Intent(HospitalList.this, AssistantList.class);
                        myBundle.putString("Key",hospital);
                        intent.putExtras(myBundle);
                    }
                    else {
                        intent = new Intent(HospitalList.this, DoctorList.class);
                        bundle.putString("Key", hospital);
                        intent.putExtras(bundle);
                    }
                         Log.w("HospitalKey:",hospital);
                    startActivity(intent);
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

