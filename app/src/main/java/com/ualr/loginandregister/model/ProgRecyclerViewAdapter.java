package com.ualr.loginandregister.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.ualr.loginandregister.Databases.PageDao;
import com.ualr.loginandregister.Databases.PageDatabase;
import com.ualr.loginandregister.R;
import com.ualr.loginandregister.activities.HomeActivity;

import java.util.List;

public class ProgRecyclerViewAdapter extends ExpandableRecyclerViewAdapter<ProgRecyclerViewAdapter.topicViewHolder, ProgRecyclerViewAdapter.pageViewHolder> {

    Context mContext;
    String mCategory;

    public ProgRecyclerViewAdapter(List<? extends ExpandableGroup> groups, Context context, String category) {
        super(groups);
        this.mContext = context;
        this.mCategory = category;
    }

    @Override
    public ProgRecyclerViewAdapter.topicViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_category_listing, parent, false);
        return new ProgRecyclerViewAdapter.topicViewHolder(view);
    }

    @Override
    public ProgRecyclerViewAdapter.pageViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prog_category_listing, parent, false);
        return new ProgRecyclerViewAdapter.pageViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ProgRecyclerViewAdapter.pageViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Page p = (Page) group.getItems().get(childIndex);
        holder.pageName.setText(p.getPageName());
        holder.background.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
        holder.layout.setPadding((int)mContext.getResources().getDimension(R.dimen.largeSpace),
                0, 0, (int)mContext.getResources().getDimension(R.dimen.smallSpace));
        holder.innerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p.getCategory().equals("Test")){
                    ((HomeActivity)mContext).viewPage(p, true);
                }
                else{
                    ((HomeActivity)mContext).viewPage(p, false);
                }
            }
        });
        if(mCategory.equals("Basic")){
            holder.basic.setVisibility(View.VISIBLE);
            holder.programming1.setVisibility(View.GONE);
            holder.programming2.setVisibility(View.GONE);
            holder.test.setVisibility(View.GONE);
        } else if(mCategory.equals("Programming")){
            holder.basic.setVisibility(View.GONE);
            holder.programming1.setVisibility(View.VISIBLE);
            holder.programming2.setVisibility(View.VISIBLE);
            holder.test.setVisibility(View.GONE);
        } else if(mCategory.equals("Test")){
            holder.basic.setVisibility(View.GONE);
            holder.programming1.setVisibility(View.GONE);
            holder.programming2.setVisibility(View.GONE);
            holder.test.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBindGroupViewHolder(topicViewHolder holder, int flatPosition, ExpandableGroup group) {
        final topic topic = (topic) group;
        holder.pageName.setText(topic.getTitle());
        holder.pageName.setTextColor(ContextCompat.getColor(mContext, R.color.textColorSecondary));
        holder.innerLayout.setClickable(false);
        if(mCategory.equals("Basic")){
            holder.basic.setVisibility(View.VISIBLE);
            holder.programming1.setVisibility(View.GONE);
            holder.programming2.setVisibility(View.GONE);
            holder.test.setVisibility(View.GONE);
        } else if(mCategory.equals("Programming")){
            holder.basic.setVisibility(View.GONE);
            holder.programming1.setVisibility(View.VISIBLE);
            holder.programming2.setVisibility(View.VISIBLE);
            holder.test.setVisibility(View.GONE);
        } else if(mCategory.equals("Test")){
            holder.basic.setVisibility(View.GONE);
            holder.programming1.setVisibility(View.GONE);
            holder.programming2.setVisibility(View.GONE);
            holder.test.setVisibility(View.VISIBLE);
        }
    }

    public class topicViewHolder extends GroupViewHolder {

        public final TextView pageName;
        public final LinearLayout innerLayout;
        public final ImageView basic;
        public final ImageView programming1;
        public final ImageView programming2;
        public final ImageView test;


        public topicViewHolder(View itemView) {
            super(itemView);
            basic = itemView.findViewById(R.id.list);
            programming1 = itemView.findViewById(R.id.underScore);
            programming2 = itemView.findViewById(R.id.terminalArrow);
            test = itemView.findViewById(R.id.pencil);
            innerLayout = itemView.findViewById(R.id.innerCategoryLayout);
            pageName = itemView.findViewById(R.id.title);
        }
    }
    public class pageViewHolder extends ChildViewHolder {

        public final TextView pageName;
        public final ImageView background;
        public final LinearLayout layout;
        public final LinearLayout innerLayout;
        public final ImageView basic;
        public final ImageView programming1;
        public final ImageView programming2;
        public final ImageView test;

        public pageViewHolder(View itemView) {
            super(itemView);
            basic = itemView.findViewById(R.id.list);
            programming1 = itemView.findViewById(R.id.underScore);
            programming2 = itemView.findViewById(R.id.terminalArrow);
            test = itemView.findViewById(R.id.pencil);
            innerLayout = itemView.findViewById(R.id.innerCategoryLayout);
            layout = itemView.findViewById(R.id.outerCategoryLayout);
            pageName = itemView.findViewById(R.id.title);
            background = itemView.findViewById(R.id.iconBackground);
        }
    }
}
