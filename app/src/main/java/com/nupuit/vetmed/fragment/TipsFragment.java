package com.nupuit.vetmed.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applovin.mediation.ads.MaxAdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nupuit.vetmed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment {


    public TipsFragment() {
        // Required empty public constructor
    }

    AdView mAdView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tips, container, false);
//        mAdView = view.findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        return  view;
    }

}
