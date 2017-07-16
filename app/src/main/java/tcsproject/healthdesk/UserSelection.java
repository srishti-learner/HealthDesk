package tcsproject.healthdesk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Srishti on 11/06/2017
 */

public class UserSelection extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//if(getIntent().getBooleanExtra("Exit",false)){
  //  System.exit(0);

        setContentView(R.layout.activity_user_selection);

        final Button healthSeeker = (Button) findViewById(R.id.healthSeeker);
        Button doctor = (Button) findViewById(R.id.doctor);
        Button assistant = (Button) findViewById(R.id.assistant);
        healthSeeker.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
               Intent intent = new Intent(UserSelection.this, Login.class);
                    intent.putExtra("User",'h');
                    startActivity(intent);
                    UserSelection.this.finish();
            }
        });
        doctor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(UserSelection.this, Login.class);
                inten.putExtra("User",'d');
                startActivity(inten);
                UserSelection.this.finish();
            }
        });
        assistant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(UserSelection.this, Login.class);
                inte.putExtra("User",'a');
                startActivity(inte);
                UserSelection.this.finish();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }
    Boolean doubleBackToExitPressedOnce = false;
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.putExtra("Exit",true);
            startActivity(intent);
            // this.finish();


            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();

    }
    
}

