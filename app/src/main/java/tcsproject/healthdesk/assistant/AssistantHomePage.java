package tcsproject.healthdesk.assistant;

import android.content.Context;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tcsproject.healthdesk.AboutUs;
import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.UserSelection;
import tcsproject.healthdesk.recyclerView.TestDataAdapter;

/**
 * Created by Srishti on 26/6/2017.
 */

public class AssistantHomePage extends AppCompatActivity{
    RecyclerView recyclerView;
    TestDataAdapter mAdapter;
    DatabaseReference db,rootRef;
    FirebaseHelper helper;
    String id;
    public static String hospital;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lab Assistant");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        id= sharedPreferences.getString("Id","Xi");
        rootRef= FirebaseDatabase.getInstance().getReference();
        db= FirebaseDatabase.getInstance().getReference().child("LabAssistant").child(id);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hospital=dataSnapshot.child("hospital").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                db = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospital).child("LabAssistant").child(id).child("Tests");
                db.keepSynced(true);
                helper = new FirebaseHelper(db);
                mAdapter = new TestDataAdapter(getApplicationContext(), helper.testDataRetrieve());
                Log.d("Data","patientAdapter");
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    Boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.exit(0);

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}

