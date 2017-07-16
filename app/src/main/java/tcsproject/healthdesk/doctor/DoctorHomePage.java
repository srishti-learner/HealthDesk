package tcsproject.healthdesk.doctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import tcsproject.healthdesk.recyclerView.ViewPagerAdapter;

/**
 * Created by Srishti on 26/6/2017.
 */

public class DoctorHomePage extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String hospital,department,docId,name;
    String Student;
    DatabaseReference db;
   FirebaseHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    ch= getIntent().getExtras().getChar("Student");
        setContentView(R.layout.activity_student_home_page);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
         docId= sharedPreferences.getString("Id","Xi");
        db= FirebaseDatabase.getInstance().getReference().child("Doctor").child(docId);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hospital=dataSnapshot.child("hospital").getValue().toString();
                department=dataSnapshot.child("department").getValue().toString();
                name=dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
           // Toast.makeText(getApplicationContext(), "Page:" + docId + hospital + " " + department + " " + name, Toast.LENGTH_LONG).show();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Doctor");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Appoints(), "Appointments");
        adapter.addFragment(new RequestsList(), "Requests");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflates menu;this adds item to action bar if present
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Toast.makeText(this,"you hit "+item.getTitle(),Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(DoctorHomePage.this, UserSelection.class);
            startActivity(i);
            DoctorHomePage.this.finish();
            return true;
        }

        if (id == R.id.about) {
            Intent i = new Intent(this, AboutUs.class);
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
            // intent.putExtra("Exit",true);
            startActivity(intent);
            System.exit(0);

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();

    }
}
