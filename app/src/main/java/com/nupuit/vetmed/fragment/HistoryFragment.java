package com.nupuit.vetmed.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.adapter.HistoryAdapter;
import com.nupuit.vetmed.model.MockupHistory;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    RecyclerView historyRecycler;
    HistoryAdapter adapter;
    LinearLayoutManager layoutManager;
    ImageView clearHistory;

    ArrayList<MockupHistory> mockupList;

    Realm realm;
    AdView mAdView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();

        MainActivity.currentPage = "historyfragment";
//        mAdView = (AdView) view.findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        historyRecycler = (RecyclerView) view.findViewById(R.id.recycler_history);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        historyRecycler.setLayoutManager(layoutManager);
        mockupList = new ArrayList<MockupHistory>();
        clearHistory = (ImageView) view.findViewById(R.id.clear_history);

//        if (realm.where(MockupHistory.class).count() == 0){
//            mockupList = populateFromFirebase();
//        }else {
            mockupList = populateFromRealm();
//        }

        adapter = new HistoryAdapter(getActivity(),getActivity(), mockupList);
        historyRecycler.setAdapter(adapter);


        historyRecycler.addOnItemTouchListener(new RecyclerTOuchListener(getActivity(), historyRecycler, new ClickListener() {
            @Override
            public void onCLick(View v, int position) {

                HistoryDetailsFragment mFragment = new HistoryDetailsFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("id",mockupList.get(position).getId());
                mFragment.setArguments(mBundle);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Clear History");
                alertDialogBuilder.setMessage("Do you want to clear all history?");
                alertDialogBuilder.setPositiveButton("Clear",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.where(MockupHistory.class).findAll().deleteAllFromRealm();
                                    }
                                });

                                mockupList = populateFromRealm();
                                adapter.swap(mockupList);
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        return view;
    }

    private ArrayList<MockupHistory> populateFromRealm() {


        RealmResults<MockupHistory> notiList = realm.where(MockupHistory.class).findAll();
        ArrayList<MockupHistory> mockupList = new ArrayList<MockupHistory>();

        mockupList.addAll(notiList);

//        for (int i=0; i<notiList.size(); i++){
//            mockupList.add(notiList.get(i));
//        }

        return mockupList;

    }

    private ArrayList<MockupHistory> populateFromFirebase() {

        ArrayList<MockupHistory> mockupList = new ArrayList<MockupHistory>();

        for (int i=0;i<10;i++){

            final MockupHistory mockupHistory = new MockupHistory("Mockup "+i, i+"", ""+(i%2),"20","50","30", "10 sec", "14/5/2017 01:15:50");
            mockupList.add(mockupHistory);

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    MockupHistory mockupModel1 = realm.copyToRealm(mockupHistory);
                }
            });
        }

        return mockupList;

    }

    public HistoryFragment() {
        // Required empty public constructor
    }

    public interface ClickListener {

        void onCLick(View v, int position);

        void onLongClick(View v, int position);

    }


    static class RecyclerTOuchListener implements RecyclerView.OnItemTouchListener {

        GestureDetector gestureDetector;
        ClickListener clickListener;

        public RecyclerTOuchListener(Context context, final RecyclerView rv, final ClickListener clickListener) {

            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, rv.getChildPosition(child));
                    }

                }
            });


        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

                clickListener.onCLick(child, rv.getChildPosition(child));

            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }


}
