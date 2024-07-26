package com.nupuit.vetmed.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nupuit.vetmed.R;
import com.nupuit.vetmed.adapter.MockupListAdapter;
import com.nupuit.vetmed.admanager.AdManager;
import com.nupuit.vetmed.model.MockupModel;
import com.nupuit.vetmed.utils.SharePreferenceSingleton;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MockupFragment extends Fragment {

    ArrayList<MockupModel> mockupList;
    Realm realm;

    SharePreferenceSingleton preferenceSingleton;

    public MockupFragment() {
        // Required empty public constructor
    }

    AdManager adManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mockup, container, false);
        Realm.init(getActivity());
        adManager = AdManager.getInstance(getActivity());
        adManager.loadAd();
        realm = Realm.getDefaultInstance();
        preferenceSingleton = SharePreferenceSingleton.getInstance(getActivity());
        mockupList = new ArrayList<MockupModel>();

        if (mockupList.size() > 0) {
            mockupList.clear();
        }
        mockupList = populatefromRealm();

        MockupListAdapter mockupListAdapter = new MockupListAdapter(getActivity(), mockupList, adManager);
        RecyclerView mockupRecyclerView = (RecyclerView) view.findViewById(R.id.mockup_recyclerview);
        mockupRecyclerView.setHasFixedSize(true);
        mockupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mockupRecyclerView.setAdapter(mockupListAdapter);
        mockupRecyclerView.setItemViewCacheSize(200);

        return view;
    }

    private ArrayList<MockupModel> populatefromRealm() {

        RealmResults<MockupModel> notiList = realm.where(MockupModel.class).findAll();
        ArrayList<MockupModel> mockupList = new ArrayList<MockupModel>();

        for (int i = 0; i < notiList.size(); i++) {
            mockupList.add(notiList.get(i));

        }

        return mockupList;
    }


}
