package com.ualr.loginandregister.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.adapter.IntroViewPageAdapter;
import com.ualr.loginandregister.model.ScreenItem;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPageAdapter mAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // when this activity is about to be launch we need to check  if its opened before or not

        if(restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_intro);

        //initialize values
        btnNext = findViewById(R.id.next_btn);
        btnGetStarted = findViewById(R.id.getStartedBtn);
        tabIndicator = findViewById(R.id.tab_indicator);

        //fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Tutorials", getResources().getString(R.string.tutorials) , R.drawable.tutorial));
        mList.add(new ScreenItem("Code Examples", getResources().getString(R.string.code_examples), R.drawable.codeinjection));
        mList.add(new ScreenItem("Exams", getResources().getString(R.string.exams), R.drawable.codeex));

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager);
        mAdapter = new IntroViewPageAdapter(this, mList);
        screenPager.setAdapter(mAdapter);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        //setup tabLayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        // next button click Listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if(position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                //where we reach to the last screen
                if (position == mList.size() - 1){
                    // TODO : show the GETSTARTED Button and hide the indicator and the next button

                    loadLastScreen();
                }
            }
        });

        //tablayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

             if(tab.getPosition() == mList.size()-1){
                 loadLastScreen();
             } else{
                 loadPrevScreen();
             }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get Started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open main activity
                Intent moveToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(moveToLogin);

                //also we need to save boolean value to storage so next time when the user runs the app
                // we could know that he is already checked the intro screen activity
                // I'm going to use shared preferences to that process

                savePresData();
                finish();
            }
        });
    }

    private void loadPrevScreen() {
        btnNext.setVisibility(View.VISIBLE);
        btnGetStarted.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroOpenedBefore;
    }

    private void savePresData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();

    }

    //show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        // TODO : Add an animation to the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }
}
