package com.nupuit.vetmed.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nupuit.vetmed.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalkthroughFragment extends Fragment {


    public WalkthroughFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_walkthrough, container, false);
    }

}
