package tcsproject.healthdesk.doctor;

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
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import tcsproject.healthdesk.AboutUs;
import tcsproject.healthdesk.FirebaseHelper;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.UserSelection;
import tcsproject.healthdesk.assistant.Assistant;
import tcsproject.healthdesk.assistant.Test;
import tcsproject.healthdesk.recyclerView.AssistantAdapter;

/**
 * Created by Srishti on 8/7/2017.
 */

public class AssistantList extends AppCompatActivity {
    RecyclerView recyclerView;
    AssistantAdapter mAdapter;
    DatabaseReference db,mDatabase;
    FirebaseHelper helper;
    ArrayList<Assistant> currentSelectedItems=new ArrayList<>();
    String hospital,hId,appId;
    Button notify;
    public Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        notify= (Button) findViewById(R.id.notify);
        notify.setVisibility(View.VISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Assistant");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        bundle=getIntent().getExtras();
        hospital = bundle.getString("Key");
        hId=bundle.getString("HId");
        appId=bundle.getString("AppId");
        db = FirebaseDatabase.getInstance().getReference().child("Hospitals").child(hospital).child("LabAssistant");
        mDatabase=FirebaseDatabase.getInstance().getReference().child("HealthSeeker").child(hId).child("Appointments").child(appId);
        db.keepSynced(true);
        helper = new FirebaseHelper(db);
        mAdapter = new AssistantAdapter(getApplicationContext(), helper.assistantRetrieve(),new AssistantAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Assistant data) {
                currentSelectedItems.add(data);
            }

            @Override
            public void onItemUncheck(Assistant data) {
                currentSelectedItems.remove(data);
            }
        });
        Log.d("Data","assistantAdapter");
        recyclerView.setAdapter(mAdapter);
     notify.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(currentSelectedItems.isEmpty()){
                 Toast.makeText(getApplicationContext(),"Select assistant",Toast.LENGTH_SHORT).show();
             }
             else {
                 for (Assistant assistant : currentSelectedItems) {
                     Test test=new Test(DoctorHomePage.docId,DoctorHomePage.department,hId);
                     Toast.makeText(getApplicationContext(),hospital+" "+test.toString(),Toast.LENGTH_SHORT).show();
                     try {
                         Log.e("assList",currentSelectedItems.toString());
                         Toast.makeText(getApplicationContext(),assistant.getId(),Toast.LENGTH_SHORT).show();
                         mDatabase.child("Tests").push().setValue(assistant.getName());
                         db.child(assistant.getId()).child("Tests").push().setValue(test); //add a test;
                     }
                     catch (DatabaseException e){
                         Toast.makeText(getApplicationContext(),"Error occurred for "+assistant.getName(),Toast.LENGTH_SHORT).show();
                         Log.e("notifyAssistant"," "+assistant.getId()+" "+ e);
                     }

                 }
                 Toast.makeText(getApplicationContext(),"Assistant Notified",Toast.LENGTH_SHORT).show();
             }
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
        finish();
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
}

