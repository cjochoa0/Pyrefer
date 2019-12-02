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

public class RegisterActivity extends AppCompatActivity {

    //initial values
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;

    private Button cancelBtn;
    private Button registerBtn;

    private UserDao userDao;

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
        emailEdit = findViewById(R.id.email_editText);
        passwordEdit = findViewById(R.id.password_editText);

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

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty()){
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(firstNameEdit.getText().toString(),
                                            lastNameEdit.getText().toString(),
                                            emailEdit.getText().toString(),
                                            passwordEdit.getText().toString());
                            userDao.insert(user);
                            progressDialog.dismiss();
                            Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(moveToLogin);
                        }
                    }, 1000);

                } else{
                    Toast.makeText(RegisterActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmpty() {
        if(TextUtils.isEmpty(emailEdit.getText().toString()) ||
            TextUtils.isEmpty(passwordEdit.getText().toString()) ||
            TextUtils.isEmpty(firstNameEdit.getText().toString()) ||
            TextUtils.isEmpty(lastNameEdit.getText().toString())){
            return true;
        } else{
            return false;
        }
    }

}
