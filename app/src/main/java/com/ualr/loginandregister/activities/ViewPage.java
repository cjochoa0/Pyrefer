package com.ualr.loginandregister.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.User;

public class ViewPage extends AppCompatActivity {

    private PageDatabase pDatabase;
    private PageDao pageDao;

    private Page p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle pageData = getIntent().getExtras();
        String category = pageData.getString(HomeActivity.CATEGORY);
        String topic = pageData.getString(HomeActivity.TOPIC);
        String pageName = pageData.getString(HomeActivity.PAGENAME);

        pDatabase = Room.databaseBuilder(this, PageDatabase.class, "page_database.db")
                .allowMainThreadQueries().build();
        pageDao = pDatabase.getPageDao();


        p = pageDao.getOne(category, topic, pageName);

        getSupportActionBar().setTitle(p.getTopic());

        TextView pageHeadingView = findViewById(R.id.pageHeadingView);
        TextView contentView = findViewById(R.id.contentView);

        pageHeadingView.setText(p.getPageName());
        contentView.setText(p.getPageContents().replace("\\n", "\n").replace("\\t", "\t\t"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite:
                if(p.isFavorite()){
                    item.setIcon(R.drawable.ic_favorite_not);
                    p.setFavorite(false);
                    pageDao.update(p);
                    Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                    p.setFavorite(true);
                    pageDao.update(p);
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "I've been learning about the topic "+p.getPageName()+" with PyRefer!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "You can't download it yet, but It's really neat!");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
