package com.ualr.loginandregister.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.User;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UserDatabase uDatabase = Room.databaseBuilder(this, UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build();
        final UserDao userDao = uDatabase.getUserDao();
        final User user = userDao.getLoggedIn();
        final EditText firstName = findViewById(R.id.firstName);
        final EditText lastName = findViewById(R.id.lastName);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        firstName.setText(user.getName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());

        Button saveUser = findViewById(R.id.saveChanges);
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEmpty(email, firstName, lastName) && isValid(email)){
                    user.setName(firstName.getText().toString());
                    user.setLastName(lastName.getText().toString());
                    user.setEmail(email.getText().toString());
                    userDao.update(user);
                    Toast.makeText(getApplication(), "User Updated", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Button changePassword = findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.length() > 7){
                    user.setPassword(password.getText().toString());
                    userDao.update(user);
                    Toast.makeText(getApplication(), "Password Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplication(), "Password too short", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private boolean isValid(EditText email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            return true;
        }
        Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isEmpty(EditText email, EditText firstName, EditText lastName) {
        if(email.length() == 0 || firstName.length() == 0 || lastName.length() == 0){
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
            return true;
        } else{
            return false;
        }
    }

}
