package com.ualr.loginandregister.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.SettingsDao;
import com.ualr.loginandregister.Databases.SettingsDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.Settings;
import com.ualr.loginandregister.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

public class RegisterActivity extends AppCompatActivity {

    //initial values
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;

    private Button cancelBtn;
    private Button registerBtn;

    private UserDao userDao;
    private SettingsDao settingsDao;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        firstNameEdit = findViewById(R.id.firstNameInput);
        lastNameEdit = findViewById(R.id.lastNameInput);
        emailEdit = findViewById(R.id.emailInput);
        passwordEdit = findViewById(R.id.passwordInput);

        cancelBtn = findViewById(R.id.cancelBtn);
        registerBtn = findViewById(R.id.registerBtn);

        userDao = Room.databaseBuilder(this, UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build().getUserDao();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
                finish();
            }
        });

        settingsDao = Room.databaseBuilder(this, SettingsDatabase.class, "settings_database.db")
                .allowMainThreadQueries().build().getSettingsDao();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty() && isValid()){
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Settings settings = new Settings(emailEdit.getText().toString(), passwordEdit.getText().toString());
                            settingsDao.insert(settings);
                            User user = new User(firstNameEdit.getText().toString(),
                                            lastNameEdit.getText().toString(),
                                            emailEdit.getText().toString(),
                                            passwordEdit.getText().toString());
                            userDao.insert(user);
                            progressDialog.dismiss();
                            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(moveToLogin);
                            finish();
                        }
                    }, 1000);

                }
            }
        });
    }

    private boolean isValid(){
        if(Patterns.EMAIL_ADDRESS.matcher(emailEdit.getText().toString()).matches()) {
            if (passwordEdit.length() > 7) {
                return true;
            } else {
                Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else if(passwordEdit.length() == 8){
            Toast.makeText(RegisterActivity.this, "Email address invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(RegisterActivity.this, "Email and password invalid", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isEmpty() {
        if(TextUtils.isEmpty(emailEdit.getText().toString()) ||
            TextUtils.isEmpty(passwordEdit.getText().toString()) ||
            TextUtils.isEmpty(firstNameEdit.getText().toString()) ||
            TextUtils.isEmpty(lastNameEdit.getText().toString())){
            Toast.makeText(RegisterActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            return true;
        } else{
            return false;
        }
    }

}
