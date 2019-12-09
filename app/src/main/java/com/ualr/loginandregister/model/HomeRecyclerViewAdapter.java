package com.ualr.loginandregister.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.loginandregister.R;
import com.ualr.loginandregister.activities.HomeActivity;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter{

    private static final int PAGE_VIEW = 0;

    private List<Page> mPages;
    private Context mContext;

    public HomeRecyclerViewAdapter(List<Page> pages, Context context) {
        this.mPages = pages;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return PAGE_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_category_listing, parent, false);
        RecyclerView.ViewHolder holder = new PageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        final Page p = mPages.get(position);
        final PageViewHolder holder = (PageViewHolder)viewHolder;
        holder.background.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
        holder.pageName.setText(p.getPageName());

        if(p.getCategory().equals("Basic")){
            holder.basic.setVisibility(View.VISIBLE);
            holder.programming1.setVisibility(View.GONE);
            holder.programming2.setVisibility(View.GONE);
            holder.test.setVisibility(View.GONE);
        }
        else if(p.getCategory().equals("Programming")){
            holder.basic.setVisibility(View.GONE);
            holder.programming1.setVisibility(View.VISIBLE);
            holder.programming2.setVisibility(View.VISIBLE);
            holder.test.setVisibility(View.GONE);
        }
        else if(p.getCategory().equals("Test")){
            holder.basic.setVisibility(View.GONE);
            holder.programming1.setVisibility(View.GONE);
            holder.programming2.setVisibility(View.GONE);
            holder.test.setVisibility(View.VISIBLE);
        }
        holder.innerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getCategory().equals("Test")){
                    ((HomeActivity)mContext).viewPage(p, true);
                }
                else{
                    ((HomeActivity)mContext).viewPage(p, false);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() { return mPages.size(); }

    public class PageViewHolder extends RecyclerView.ViewHolder{

        public final TextView pageName;
        public final ImageView background;
        public final LinearLayout innerLayout;
        public final ImageView basic;
        public final ImageView programming1;
        public final ImageView programming2;
        public final ImageView test;

        public PageViewHolder(View itemView) {
            super(itemView);
            basic = itemView.findViewById(R.id.list);
            programming1 = itemView.findViewById(R.id.underScore);
            programming2 = itemView.findViewById(R.id.terminalArrow);
            test = itemView.findViewById(R.id.pencil);
            innerLayout = itemView.findViewById(R.id.innerCategoryLayout);
            pageName = itemView.findViewById(R.id.title);
            background = itemView.findViewById(R.id.iconBackground);
        }
    }
}
