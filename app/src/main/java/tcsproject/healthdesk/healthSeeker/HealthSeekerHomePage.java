package tcsproject.healthdesk.healthSeeker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import tcsproject.healthdesk.AboutUs;
import tcsproject.healthdesk.IntroSlider;
import tcsproject.healthdesk.R;
import tcsproject.healthdesk.UserSelection;
import tcsproject.healthdesk.recyclerView.ViewPagerAdapter;

/**
 * Created by Srishti on 21/06/2017.
 */

public class HealthSeekerHomePage extends AppCompatActivity{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String Student;
    //  char ch;
    //PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Health Seeker");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        Student= sharedPreferences.getString("Id","Xi");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FutureAppoints(), "Appointments");
        adapter.addFragment(new PastAppoints(), "Past");
        Log.e("HomePage","It is home");
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflates menu;this adds item to action bar if present
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.action_settings){
            // Toast.makeText(this,"you hit "+item.getTitle(),Toast.LENGTH_SHORT).show();
            SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(HealthSeekerHomePage.this, UserSelection.class);
            startActivity(i);
            HealthSeekerHomePage.this.finish();
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