package com.ualr.loginandregister.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.User;

public class HomeActivity extends AppCompatActivity {
    private TextView tvUser;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (User) getIntent().getSerializableExtra("User");

        tvUser = findViewById(R.id.tvUser);

        if(user != null){
            tvUser.setText("Welcome " + user.getName() + " " + user.getLastName());
        }
    }
}
