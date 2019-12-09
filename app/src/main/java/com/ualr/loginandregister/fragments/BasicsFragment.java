package com.ualr.loginandregister.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.Databases.UserDao;
import com.ualr.loginandregister.Databases.UserDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.model.Page;
import com.ualr.loginandregister.model.ProgRecyclerViewAdapter;
import com.ualr.loginandregister.model.User;
import com.ualr.loginandregister.model.topic;

import java.util.ArrayList;
import java.util.List;


public class BasicsFragment extends Fragment {
    private PageDatabase pDatabase;
    private PageDao pageDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_basics, container, false);
        RecyclerView rv = view.findViewById(R.id.BasicRecycle);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<topic> topics = new ArrayList<>();

        pDatabase = Room.databaseBuilder(getActivity(), PageDatabase.class, "page_database.db")
                .allowMainThreadQueries().build();
        pageDao = pDatabase.getPageDao();

        List<Page> pages = pageDao.getBasic();
        for(String t : pageDao.getTopics("Basic")){
            List<Page> newPages = new ArrayList<>();
            for(Page p : pages){
                if(p.getTopic().equals(t)){
                    newPages.add(p);
                }
            }
            topic newTopic = new topic(t, newPages);
            topics.add(newTopic);
        }


        ProgRecyclerViewAdapter adapter = new ProgRecyclerViewAdapter(topics, getActivity(), "Basic");
        rv.setAdapter(adapter);
        return view;
    }

}
