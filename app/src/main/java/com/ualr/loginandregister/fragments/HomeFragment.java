package com.ualr.loginandregister.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.HomeRecyclerViewAdapter;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.ProgRecyclerViewAdapter;
import com.ualr.loginandregister.model.User;
import com.ualr.loginandregister.model.topic;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private UserDatabase uDatabase;
    private UserDao userDao;

    private PageDatabase pDatabase;
    private PageDao pageDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView welcomeUser = view.findViewById(R.id.welcomeUser);

        uDatabase = Room.databaseBuilder(getActivity(), UserDatabase.class, "user_database.db")
                .allowMainThreadQueries().build();
        userDao = uDatabase.getUserDao();

        User user = userDao.getLoggedIn();
        welcomeUser.setText("Welcome, "+user.getName()+" "+user.getLastName());


        pDatabase = Room.databaseBuilder(getActivity(), PageDatabase.class, "page_database.db")
                .allowMainThreadQueries().build();
        pageDao = pDatabase.getPageDao();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Page> recents = pageDao.getRecents();
        if(!recents.isEmpty()){
            getActivity().findViewById(R.id.noRecents).setVisibility(View.GONE);
            RecyclerView rv = getActivity().findViewById(R.id.recentsRecycler);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(recents, getActivity());
            rv.setAdapter(adapter);
        }
        else{
            getActivity().findViewById(R.id.noRecents).setVisibility(View.VISIBLE);
        }

        List<Page> favorites = pageDao.getFavorites();
        if(!favorites.isEmpty()){
            getActivity().findViewById(R.id.noFavorites).setVisibility(View.GONE);
            RecyclerView rv = getActivity().findViewById(R.id.favesRecycler);
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(favorites, getActivity());
            rv.setAdapter(adapter);
        }
        else{
            getActivity().findViewById(R.id.noFavorites).setVisibility(View.VISIBLE);
        }
    }
}
