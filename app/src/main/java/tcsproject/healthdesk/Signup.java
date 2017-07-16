package tcsproject.healthdesk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tcsproject.healthdesk.healthSeeker.HealthSeekerHomePage;
import tcsproject.healthdesk.healthSeeker.User;

public class Signup extends AppCompatActivity {
    private static final String REQUIRED ="Required*" ;
    EditText name, mailId, phoneNo, pass, dob;
    TextInputLayout T_name, T_mail, T_phone, T_pass, T_dob;
    String S_name, S_mailId, S_phoneNo, S_pass, S_dob;
    Button signUp;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Health Seeker");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        name = (EditText) findViewById(R.id.name);
        mailId = (EditText) findViewById(R.id.email_Id);
        phoneNo = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.password);
        dob= (EditText) findViewById(R.id.dob);

        T_name = (TextInputLayout) findViewById(R.id.input_layout_name);
        T_pass = (TextInputLayout) findViewById(R.id.input_layout_pass);
        T_mail = (TextInputLayout) findViewById(R.id.input_layout_mail);
        T_phone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        T_dob = (TextInputLayout) findViewById(R.id.input_layout_dob);

        signUp =(Button)findViewById(R.id.signup);

        name.addTextChangedListener(new CustomTextWatcher(name));
        pass.addTextChangedListener(new CustomTextWatcher(pass));
        mailId.addTextChangedListener(new CustomTextWatcher(mailId));
        phoneNo.addTextChangedListener(new CustomTextWatcher(phoneNo));
        T_name.setHint("Enter Name");
        T_pass.setHint("Enter Password");
        T_mail.setHint("Enter Email Id");
        T_phone.setHint("Enter Mobile No");
        T_dob.setHint("Enter Date of Birth(dd/mm/yyyy)");

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                //  onBackPressed();
                Intent intent = new Intent(this, UserSelection.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){

            case KeyEvent.KEYCODE_BACK:
                //  onBackPressed();
                Intent intent = new Intent(this, UserSelection.class);
                startActivity(intent);
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    public void signup(View view) {
        S_name = name.getText().toString();
        S_mailId = mailId.getText().toString();
        S_phoneNo = phoneNo.getText().toString();
        S_pass = pass.getText().toString();
        S_dob =dob.getText().toString();

        if ((S_name.contentEquals(""))||(S_mailId.contentEquals(""))||(S_phoneNo.contentEquals(""))||(S_pass.contentEquals(""))||(S_dob.contentEquals(""))) {
            Toast.makeText(this, "fill all the required fields!", Toast.LENGTH_SHORT).show();
            if (S_name.contentEquals("")) {
                name.setError(REQUIRED);
            }
            if (S_mailId.contentEquals("")) {
                mailId.setError(REQUIRED);
            }
            if (S_phoneNo.contentEquals("")) {
                phoneNo.setError(REQUIRED);
            }
            if (S_pass.contentEquals("")) {
                pass.setError(REQUIRED);
            }
            if (S_dob.contentEquals("")) {
                dob.setError(REQUIRED);
            }
        } else {
            //write to the db and intent to home page
            if(validateEmail()&&validatePassword()&&validatePhone()){
                setEditingEnabled(false);
                Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();
                User user= new User(S_name, S_mailId, S_phoneNo, S_pass, S_dob);
                try {
                   Intent in= new Intent(Signup.this, HealthSeekerHomePage.class);
                    mDatabase.child("HealthSeeker").child(S_phoneNo).setValue(user);
                    Toast.makeText(this, "Successfully sign in", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("LoggedIn",true);
                    editor.putString("Id",S_phoneNo);
                    editor.apply();
                    startActivity(in);
                    Signup.this.finish();

                } catch (DatabaseException e) {
                    Toast.makeText(this, "UnSuccessful "+e, Toast.LENGTH_LONG).show();
                    Log.e("FireTag","Unsuccessful: "+e);
                } finally {
                    setEditingEnabled(true);
                }
            }
        }
    }
    private void setEditingEnabled(boolean enabled) {
        name.setEnabled(enabled);
        mailId.setEnabled(enabled);
        phoneNo.setEnabled(enabled);
        pass.setEnabled(enabled);
        if (enabled) {
            signUp.setVisibility(View.VISIBLE);
        } else {
            signUp.setVisibility(View.GONE);
        }
    }


    private boolean validatePassword() {
        if (pass.getText().toString().trim().isEmpty()) {
            T_pass.setError(getString(R.string.err_msg_password));
            requestFocus(pass);
            return false;
        } else {
            T_pass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        S_mailId = mailId.getText().toString().trim();
        String pattern = "([\\w'.-]+)(@)([a-z]+)(\\.)([a-z]+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(S_mailId);
        if (m.matches() && !(S_mailId.isEmpty())) {
            T_mail.setErrorEnabled(false);
        } else {
            T_mail.setError(getString(R.string.err_msg_email_id));
            requestFocus(mailId);
            return false;
        }

        return true;
    }

    private boolean validatePhone() {
        S_phoneNo = phoneNo.getText().toString().trim();

        if (S_phoneNo.length() == 10)
            T_phone.setErrorEnabled(false);
        else {
            T_phone.setError(getString(R.string.err_msg_phone_no));
            requestFocus(phoneNo);
            return false;
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class CustomTextWatcher implements TextWatcher {
        private View view;

        CustomTextWatcher(View v) {
            view = v;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.phone:
                    validatePhone();
                    break;
                case R.id.email_Id:
                    validateEmail();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }
}