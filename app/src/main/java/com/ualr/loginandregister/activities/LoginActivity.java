package com.ualr.loginandregister.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.User;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button signUpBtn;
    private EditText emailEdit;
    private EditText passwordEdit;
    private UserDatabase database;

    private UserDao userDao;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Check User...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        database = Room.databaseBuilder(this, UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build();
        userDao = database.getUserDao();

        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        emailEdit = findViewById(R.id.email_editText);
        passwordEdit = findViewById(R.id.passwordInput);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(moveToRegister);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emptyValidation()){
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.getUser(emailEdit.getText().toString(), passwordEdit.getText().toString());
                            if(user!=null){
                                user.setLoggedIn(true);
                                userDao.update(user);
                                Intent moveToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                moveToHome.putExtra("User", user);
                                startActivity(moveToHome);
                            }else {
                                Toast.makeText(LoginActivity.this, "Unregistered User or Incorrect", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    },1000 );
                } else{
                    Toast.makeText(LoginActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean emptyValidation() {
        if(TextUtils.isEmpty(emailEdit.getText().toString()) || TextUtils.isEmpty(passwordEdit.getText().toString())){
            return true;
        } else {
            return false;
        }
    }
}
