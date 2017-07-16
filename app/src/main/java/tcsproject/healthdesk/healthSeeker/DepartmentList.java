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
import tcsproject.healthdesk.doctor.Doctor;
import tcsproject.healthdesk.recyclerView.DeptAdapter;
import tcsproject.healthdesk.recyclerView.DoctorAdapter;
import tcsproject.healthdesk.recyclerView.RecyclerTouchListener;

/**
 * Created by Srishti on 04/07/2017.
 */

public class DepartmentList extends AppCompatActivity{
    RecyclerView recyclerView;
    DeptAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Specialization");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DeptAdapter(getApplicationContext(),FirebaseHelper.depList);
        Log.d("Data","docAdapter");
        recyclerView.setAdapter(mAdapter);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                    String department = FirebaseHelper.depList[position];
                    Log.d("Dept", department);

                    Intent intent = new Intent(DepartmentList.this, Confirm.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Department", department);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
