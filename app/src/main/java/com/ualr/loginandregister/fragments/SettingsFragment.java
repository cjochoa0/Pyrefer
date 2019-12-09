package com.ualr.loginandregister.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.ualr.loginandregister.Databases.SettingsDao;
import com.ualr.loginandregister.Databases.SettingsDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.Settings;
import com.ualr.loginandregister.model.User;


public class SettingsFragment extends Fragment {

    SettingsDao settingsDao;
    UserDao userDao;
    Settings settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Switch darkToggle = view.findViewById(R.id.darkToggle);
        settingsDao = Room.databaseBuilder(getActivity(), SettingsDatabase.class, "settings_database.db")
                .allowMainThreadQueries().build().getSettingsDao();
        userDao = Room.databaseBuilder(getActivity(), UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build().getUserDao();
        User user = userDao.getLoggedIn();
        settings = settingsDao.getSettings(user.getEmail(), user.getPassword());
        final FrameLayout layout = getActivity().findViewById(R.id.settingsOuterLayout);
        final TextView darkLabel = getActivity().findViewById(R.id.darkLabel);
        final TextView sizeLabel = getActivity().findViewById(R.id.sizeLabel);
        final TextView homePageLabel = getActivity().findViewById(R.id.homePageLabel);
        darkEnabled(settings.isDark(), layout, darkLabel, sizeLabel, homePageLabel);
        darkToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                settings.setDark(b);
                settingsDao.update(settings);
                darkEnabled(b, layout, darkLabel, sizeLabel, homePageLabel);
            }
        });
        return view;
    }

    private void darkEnabled(boolean b, FrameLayout layout, TextView darkLabel, TextView sizeLabel, TextView homePageLabel){

    }
}
