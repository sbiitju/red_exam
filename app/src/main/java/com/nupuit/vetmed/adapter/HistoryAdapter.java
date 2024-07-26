package com.nupuit.vetmed.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.fragment.HistoryDetailsFragment;
import com.nupuit.vetmed.model.MockupHistory;

import java.util.ArrayList;

import io.realm.Realm;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    ArrayList<MockupHistory> mockupList = new ArrayList<MockupHistory>();
    Realm realm;

    Context context;
    Activity activity;

    public HistoryAdapter(Activity activity, Context context, ArrayList<MockupHistory> mockupList) {
        this.context = context;
        this.activity = activity;
        this.mockupList = mockupList;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final MockupHistory mockupHistory = mockupList.get(position);

        holder.name.setText(mockupHistory.getMockupName());
        holder.date.setText(mockupHistory.getDate());
        holder.result.setText(mockupHistory.getResult());
        holder.correctQns.setText(mockupHistory.getCorrectQsn());
        holder.totalQsn.setText(mockupHistory.getTotalQsn());
        holder.percentage.setText(mockupHistory.getPercentage());
        holder.time.setText(mockupHistory.getTime());


        holder.parentLayout.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // (AppCompatActivity)context.getFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
               // fragmentJump(mockupHistory);
            }
        });


    }

    private void fragmentJump(MockupHistory mockupHistory) {
        HistoryDetailsFragment mFragment = new HistoryDetailsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("item",mockupHistory.getId());
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

    @Override
    public int getItemCount() {
        return mockupList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView date;
        TextView result;
        TextView correctQns;
        TextView totalQsn;
        TextView percentage;
        TextView time;

        LinearLayout parentLayout;

        MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            result = (TextView) itemView.findViewById(R.id.result);
            correctQns = (TextView) itemView.findViewById(R.id.correctQsn);
            totalQsn = (TextView) itemView.findViewById(R.id.totalQsn);
            percentage = (TextView) itemView.findViewById(R.id.percentage);
            time = (TextView) itemView.findViewById(R.id.time);

            parentLayout = (LinearLayout) itemView.findViewById(R.id.parent);

        }
    }

    public void swap(ArrayList<MockupHistory> datas){
        mockupList.clear();
        mockupList.addAll(datas);
        notifyDataSetChanged();
    }

}
