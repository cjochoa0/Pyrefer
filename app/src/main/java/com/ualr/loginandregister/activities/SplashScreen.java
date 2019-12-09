package com.ualr.loginandregister.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private UserDatabase uDatabase;
    private UserDao userDao;
    private PageDatabase pDatabase;
    private PageDao pageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        pDatabase = Room.databaseBuilder(this, PageDatabase.class, "page_database.db")
                .allowMainThreadQueries().build();
        pageDao = pDatabase.getPageDao();
        List<Page> pages = pageDao.getAll();
        if(pages.isEmpty()) {
            InputStream inStream = getResources().openRawResource(R.raw.page_database);
            BufferedReader fin = new BufferedReader(
                    new InputStreamReader(inStream, Charset.forName("UTF-8"))
            );
            String line = "";
            String category= "";
            String topic= "";
            String pageName= "";
            String pageContents= "";
            int buffer = 0;
            try {
                while((line = fin.readLine()) != null){
                    if(buffer == 4){
                        Page newPage = new Page(category, topic, pageName, pageContents);
                        pageDao.insert(newPage);
                        buffer = 0;
                    }
                    switch (buffer){
                        case 0:
                            category = line;
                            break;
                        case 1:
                            topic = line;
                            break;
                        case 2:
                            pageName = line;
                            break;
                        case 3:
                            pageContents = line;
                            break;
                    }
                    buffer++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        uDatabase = Room.databaseBuilder(this, UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build();
        userDao = uDatabase.getUserDao();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User loggedIn = userDao.getLoggedIn();
                if(loggedIn != null){
                    Intent moveToHome = new Intent(SplashScreen.this, HomeActivity.class);
                    moveToHome.putExtra("User", loggedIn);
                    startActivity(moveToHome);
                    finish();
                }
                else{
                    Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }
}
