package tcsproject.healthdesk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import tcsproject.healthdesk.assistant.AssistantHomePage;
import tcsproject.healthdesk.doctor.DoctorHomePage;
import tcsproject.healthdesk.healthSeeker.HealthSeekerHomePage;


/**
 * Created by Srishti on 06/10/2017.
 */

public class SplashScreen extends Activity {
    ProgressBar bar;
    int total = 0;
    boolean isRunning = false;
    static final String DEFAULT = "N/A";

    // handler for the background updating
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            total = total + 5;
            String perc = String.valueOf(total).toString();


            if (perc.equalsIgnoreCase("100")) {
                Intent intent = new Intent(SplashScreen.this, UserSelection.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }
            bar.incrementProgressBy(5);
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void onStart() {
        super.onStart();
// reset the bar to the default value of 0
        bar.setProgress(0);
// create a thread for updating the progress bar
        Thread timer = new Thread() {
            public void run() {
                Looper.prepare();
                try {
                    haveNetworkConnection();
                    sleep(2000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                } finally {

                    Intent i = new Intent();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    if (sharedPreferences.getBoolean("LoggedIn", false)) {
                        Log.d("LoggedIn", "user");
                       if (sharedPreferences.getString("User", DEFAULT).contentEquals("h")) {
                            i = new Intent(SplashScreen.this, HealthSeekerHomePage.class);
                            Log.d("LoggedIn", "h");
                        } else if (sharedPreferences.getString("User", DEFAULT).contentEquals("d")) {
                            i = new Intent(SplashScreen.this, DoctorHomePage.class);
                            Log.d("LoggedIn", "d");
                        } else if (sharedPreferences.getString("User", DEFAULT).contentEquals("a")) {
                            i = new Intent(SplashScreen.this, AssistantHomePage.class);
                            Log.d("LoggedIn", "a");
                        }
                    }
                    else{
                        i = new Intent(SplashScreen.this, UserSelection.class);
                    }
                    startActivity(i);
                    finish();
                }

            }

        };
        isRunning = true;
// start the background thread
        timer.start();
    }

    public void onStop() {
        super.onStop();
    }

    public void haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if(haveConnectedWifi || haveConnectedMobile){
            Log.e("Sweet","tomato");
            Toast.makeText(this, "No Internet Connection... ", Toast.LENGTH_LONG).show();
            Log.e("Sweet","tomato");
        }
        else{
            Log.e("Sweet","tomatoes");
            Toast.makeText(getApplicationContext(), "Internet Connection successful... ", Toast.LENGTH_LONG).show();
        }
    }
}