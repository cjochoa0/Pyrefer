package com.ualr.loginandregister.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.User;

public class ViewPageTest extends AppCompatActivity {

    private PageDatabase pDatabase;
    private PageDao pageDao;

    private Page p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page_test);
        Toolbar toolbar = findViewById(R.id.toolbarTest);
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

        TextView pageHeadingView = findViewById(R.id.pageHeadingViewTest);
        final TextView contentView = findViewById(R.id.contentViewTest);

        pageHeadingView.setText(p.getPageName());
        contentView.setText(p.getPageContents().replace("\\n", "\n").replace("\\t", "\t\t"));

        final EditText enterAnswer = findViewById(R.id.enterAnswer);
        final Button submitAnswer = findViewById(R.id.submitAnswer);
        final ScrollView scrollView = findViewById(R.id.TestScrollView);

        submitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actualAnswer = pageDao.getAnswer(p.getPageName());
                String givenAnswer = enterAnswer.getText().toString();
                enterAnswer.setVisibility(view.GONE);
                submitAnswer.setVisibility(View.GONE);
                contentView.setText("Your Solution:\n\n"+givenAnswer+"\n\nOur Solution:\n\n"+actualAnswer.replace("\\n", "\n").replace("\\t", "\t\t"));
                scrollView.setScrollY(0);
            }
        });
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
                    Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                    item.setIconTintMode(PorterDuff.Mode.SRC_IN);
                    p.setFavorite(true);
                    Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
                pageDao.update(p);
                return true;
            case R.id.share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "I've been learning about the topic "+p.getPageName()+" with PyRefer!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "You can't download PyRefer yet, but It's really neat!");
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
