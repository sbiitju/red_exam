package com.nupuit.vetmed.adapter;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.activity.MockupActivity;
import com.nupuit.vetmed.admanager.AdManager;
import com.nupuit.vetmed.fragment.MockupQuestionFragment;
import com.nupuit.vetmed.model.MockupModel;


import java.util.ArrayList;

import io.realm.Realm;


public class MockupListAdapter extends RecyclerView.Adapter<MockupListAdapter.MyViewHolder> implements AdManager.AdCallback {


    private static final int TYPE_ITEM_1 = 1;
    private static final int TYPE_ITEM_2 = 2;
    ArrayList<MockupModel> mockupList = new ArrayList<MockupModel>();
    Realm realm;
    Activity context;
    AdManager adManager;

    public MockupListAdapter(Activity context, ArrayList<MockupModel> mockupList,AdManager adManager) {
        this.context = context;
        this.mockupList = mockupList;
        this.adManager = adManager;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public MockupListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_1) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mockup_item1, parent, false);
            return new MyViewHolder(itemView);
        } else if (viewType == TYPE_ITEM_2) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mockup_item2, parent, false);
            return new MyViewHolder(itemView);
        } else {
            return null;
        }
    }
    MockupModel mockupModel;

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

      mockupModel    = mockupList.get(position);
        adManager.setAdCallback(this);

        holder.mockupName.setText("Fixed Mock " + String.valueOf(position + 1));
        holder.mark.setText(mockupModel.getTotalMark());
        holder.time.setText(mockupModel.getTime());
        holder.qsn.setText(mockupModel.getTotalQsn());

        holder.major_box.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              adManager.showAd();
            }
        });

        if (!mockupList.get(position).isLock()) {

            if (position + 1 < mockupList.size()) {
                if (!mockupList.get(position + 1).isLock()) {
                    holder.pipe.setImageResource(R.drawable.pipe_red);
                } else {
                    holder.pipe.setImageResource(R.drawable.pipe_gradient);
                }
            } else {
                holder.pipe.setImageResource(R.drawable.pipe_gradient);
            }
        }
    }

    private void fragmentJump(MockupModel mockupHistory) {
        MockupQuestionFragment mFragment = new MockupQuestionFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("item", mockupHistory.getId());
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mFragment);

    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }

    private void unlock(MyViewHolder holder, final int position) {
        if (position % 2 == 0)
            holder.major_box.setBackgroundResource(R.drawable.event_red_right_bg);
            if(!mockupList.get(position).isLock()){

            }
        else {
            holder.major_box.setBackgroundResource(R.drawable.event_red_left_bg);
        }

        if(!mockupList.get(position).isLock()){
            if(position + 1 < mockupList.size()){
                if(!mockupList.get(position + 1).isLock()){
                    holder.major_box.setBackgroundResource(R.drawable.event_blue_right_bg);
                }
                else{
                    holder.major_box.setBackgroundResource(R.drawable.event_blue_left_bg);
                }
            }
            else{
                holder.major_box.setBackgroundResource(R.drawable.event_blue_left_bg);
            }
        }

        holder.mockupName.setTextColor(Color.parseColor("#FFFFFF"));
        holder.mark.setTextColor(Color.parseColor("#FFFFFF"));
        holder.time.setTextColor(Color.parseColor("#FFFFFF"));
        holder.qsn.setTextColor(Color.parseColor("#FFFFFF"));

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MockupModel mockupModel1 = realm.where(MockupModel.class).equalTo("id", position + "").findFirst();
                mockupModel1.setLock(false);
            }
        });

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0)
            return TYPE_ITEM_1;
        else {
            return TYPE_ITEM_2;
        }

    }

    @Override
    public int getItemCount() {
        return mockupList.size();
    }

    @Override
    public void onAdShown() {

    }

    @Override
    public void onAdDismissed() {
        context.startActivity(new Intent(context, MockupActivity.class).putExtra("id", mockupModel.getId()).putExtra("total_question", "52").putExtra("total_time", Long.valueOf(7200)));
        context.finish();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mockupName;
        TextView time, mark, qsn;
        RelativeLayout major_box;
        ImageView pipe;


        MyViewHolder(View itemView) {
            super(itemView);
            mockupName = (TextView) itemView.findViewById(R.id.txt_mockup_name);
            time = (TextView) itemView.findViewById(R.id.time);
            mark = (TextView) itemView.findViewById(R.id.mark);
            qsn = (TextView) itemView.findViewById(R.id.totalQsn);
            major_box = (RelativeLayout) itemView.findViewById(R.id.major_event_box);
            pipe = (ImageView) itemView.findViewById(R.id.pipe);
        }
    }

}
