package com.ualr.loginandregister.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.fragments.BasicsFragment;
import com.ualr.loginandregister.fragments.HomeFragment;
import com.ualr.loginandregister.fragments.ProgrammingFragment;
import com.ualr.loginandregister.fragments.SettingsFragment;
import com.ualr.loginandregister.fragments.TestsFragment;
import com.ualr.loginandregister.model.HomeRecyclerViewAdapter;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.User;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final static int NUM_PAGES = 5;
    private final static int BASICS_FRAGMENT = 0;
    private final static int PROGRAMMING_FRAGMENT = 1;
    private final static int HOME_FRAGMENT = 2;
    private final static int TESTS_FRAGMENT = 3;
    private final static int SETTINGS_FRAGMENT = 4;
    private static int lastVisited = 2;

    public final static String CATEGORY = "category";
    public final static String TOPIC = "topic";
    public final static String PAGENAME = "pagename";


    private DrawerLayout drawerLayout;
    private UserDatabase uDatabase;
    private UserDao userDao;
    private BottomNavigationView bottomNavView;
    private NavigationView navView;
    private FragmentStateAdapter pageAdapter;
    private ViewPager2 viewPager;

    private PageDatabase pDatabase;
    private PageDao pageDao;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView = findViewById(R.id.nav_view);
        final TextView accountLabel = navView.getHeaderView(0).findViewById(R.id.accountLabel);
        TextView emailLabel = navView.getHeaderView(0).findViewById(R.id.accountEmail);
        accountLabel.setText(getUserName());
        emailLabel.setText(getEmail());

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (menuItem.getItemId()){
                    case R.id.accountSettings:
                        Intent accountSettings = new Intent(HomeActivity.this, AccountSettingsActivity.class);
                        startActivity(accountSettings);
                        return true;
                    case R.id.feedback:
                        Intent openEmail = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@pyrefer.net"));
                        startActivity(openEmail);
                        return true;
                    case R.id.rate:
                        Toast rateToast = Toast.makeText(HomeActivity.this, "We appreciate the enthusiasm, but this app isn't in the Play Store yet!", Toast.LENGTH_LONG);
                        rateToast.getView().getBackground().setColorFilter(getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_IN);
                        TextView rateText = rateToast.getView().findViewById(android.R.id.message);
                        rateText.setTextColor(getColor(R.color.colorWhite));
                        rateToast.show();
                        return true;
                    case R.id.logout:
                        uDatabase = Room.databaseBuilder(HomeActivity.this, UserDatabase.class, "user_database.db")
                                .allowMainThreadQueries().build();
                        userDao = uDatabase.getUserDao();
                        currentUser = userDao.getLoggedIn();
                        currentUser.setLoggedIn(false);
                        userDao.update(currentUser);
                        Intent logout = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(logout);
                        return true;
                    default:
                        Toast defaultToast = Toast.makeText(HomeActivity.this, "This functionality doesn't seem to exist", Toast.LENGTH_SHORT);
                        defaultToast.getView().getBackground().setColorFilter(getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_IN);
                        TextView defaultText = defaultToast.getView().findViewById(android.R.id.message);
                        defaultText.setTextColor(getColor(R.color.colorWhite));
                        defaultToast.show();
                        return false;
                }

            }
        });

        bottomNavView = findViewById(R.id.bottom_navigation);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.basics:
                        viewPager.setCurrentItem(BASICS_FRAGMENT);
                        return true;
                    case R.id.programming:
                        viewPager.setCurrentItem(PROGRAMMING_FRAGMENT);
                        return true;
                    case R.id.home:
                        viewPager.setCurrentItem(HOME_FRAGMENT);
                        return true;
                    case R.id.tests:
                        viewPager.setCurrentItem(TESTS_FRAGMENT);
                        return true;
                    case R.id.settings:
                        viewPager.setCurrentItem(SETTINGS_FRAGMENT);
                        return true;
                    default:
                        return false;
                }
            }
        });

        viewPager = findViewById(R.id.pager);
        pageAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pageAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNavView.setSelectedItemId(bottomNavView.getMenu().getItem(position).getItemId());
                lastVisited = position;
                switch (position) {
                    case BASICS_FRAGMENT:
                        setTitle(R.string.basics);
                        break;
                    case PROGRAMMING_FRAGMENT:
                        setTitle(R.string.programming);
                        break;
                    case HOME_FRAGMENT:
                        setTitle(R.string.home);
                        break;
                    case SETTINGS_FRAGMENT:
                        setTitle(R.string.settings);
                        break;
                    case TESTS_FRAGMENT:
                        setTitle(R.string.tests);
                        break;
                    default:
                        setTitle(String.valueOf(position));
                }
            }
        });
        viewPager.setCurrentItem(lastVisited, false);
    }


    public void viewPage(Page p, boolean isTest){
        pDatabase = Room.databaseBuilder(this, PageDatabase.class, "page_database.db")
                .allowMainThreadQueries().build();
        pageDao = pDatabase.getPageDao();
        p.setOrderViewed(Page.getTotalViews());
        pageDao.update(p);
        RecyclerView rv = findViewById(R.id.recentsRecycler);

        String category = p.getCategory();
        String topic = p.getTopic();
        String pageName = p.getPageName();
        Intent viewPage = null;
        if(isTest){
            viewPage = new Intent(HomeActivity.this, ViewPageTest.class);
        }
        else {
            viewPage = new Intent(HomeActivity.this, ViewPage.class);
        }

        viewPage.putExtra(CATEGORY,category);
        viewPage.putExtra(TOPIC,topic);
        viewPage.putExtra(PAGENAME,pageName);

        startActivity(viewPage);
    }


    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public String getUserName(){
        uDatabase = Room.databaseBuilder(this, UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build();
        userDao = uDatabase.getUserDao();
        User user = userDao.getLoggedIn();
        String fName = user.getName();
        String lName = user.getLastName();
        return fName + " " + lName;
    }

    public String getEmail(){
        uDatabase = Room.databaseBuilder(this, UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build();
        userDao = uDatabase.getUserDao();
        User user = userDao.getLoggedIn();
        return user.getEmail();
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case BASICS_FRAGMENT:
                    return new BasicsFragment();
                case PROGRAMMING_FRAGMENT:
                    return new ProgrammingFragment();
                case HOME_FRAGMENT:
                    return new HomeFragment();
                case SETTINGS_FRAGMENT:
                    return new SettingsFragment();
                case TESTS_FRAGMENT:
                    return new TestsFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }


    }
}
