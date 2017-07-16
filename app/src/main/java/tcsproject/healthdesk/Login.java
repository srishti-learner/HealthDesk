package tcsproject.healthdesk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tcsproject.healthdesk.assistant.AssistantHomePage;
import tcsproject.healthdesk.doctor.DoctorHomePage;
import tcsproject.healthdesk.healthSeeker.HealthSeekerHomePage;

/*
*Created by Srishti on 13/06/2017.
 */

public class Login extends AppCompatActivity {
    Intent i;
    EditText id,password;
    char user;
    Button login,signUp;
    DatabaseReference db;
    String S_phNo, S_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  prefManager = new PrefManager(getApplicationContext());
        user= getIntent().getExtras().getChar("User");

        setContentView(R.layout.activity_login);
        id= (EditText) findViewById(R.id.phone);
        password= (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.loginbutton);
        signUp = (Button) findViewById(R.id.signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(user=='h'){
            toolbar.setTitle("Health Seeker");
            db= FirebaseDatabase.getInstance().getReference().child("HealthSeeker");
        }
        else if(user=='d'){
            toolbar.setTitle("Doctor");
            db= FirebaseDatabase.getInstance().getReference().child("Doctor");
        }
        else if(user=='a'){
            toolbar.setTitle("Lab Assistant");
            db= FirebaseDatabase.getInstance().getReference().child("LabAssistant");
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //  actionBar.setIcon(R.drawable.k4logo);
        if((user=='d')||(user=='a')){
            signUp.setVisibility(View.GONE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // After Verification
                S_phNo =id.getText().toString().trim();
                S_pass=password.getText().toString().trim();
                if((S_phNo.contentEquals(""))||(S_pass.contentEquals(""))){
                    Toast.makeText(getApplicationContext(),"Fields Required*",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Connecting...",Toast.LENGTH_LONG).show();
                    db.child(S_phNo).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String FirePass = dataSnapshot.child("pass").getValue(String.class);
                                if (FirePass.contentEquals(S_pass)) {
                                    Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
                                    doIntent();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Id doesn't exists!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Login.this, Signup.class);
                in.putExtra("Use",user);
                startActivity(in);
                Login.this.finish();
            }
        });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(item.getItemId())
        {
            case android.R.id.home:
                // onBackPressed();
                Intent intent = new Intent(this, UserSelection.class);
                startActivity(intent);
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
        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:
                //  onBackPressed();
                Intent intent = new Intent(this, UserSelection.class);
                startActivity(intent);
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    public void doIntent(){
       SharedPreferences sharedPreferences = this.getSharedPreferences("UserData",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LoggedIn",true);
        editor.putString("Id", S_phNo);
        editor.putString("User",user+"");
        editor.apply();
        if(user=='d'){
            i = new Intent(Login.this, DoctorHomePage.class);
        }
        else if(user=='h')
            i = new Intent(Login.this, HealthSeekerHomePage.class);
        else if(user=='a')
            i = new Intent(Login.this, AssistantHomePage.class);
        //  i.putExtra("Student",c);

        startActivity(i);
        Login.this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
