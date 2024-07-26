package com.nupuit.vetmed.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.activity.MockupActivity;
import com.nupuit.vetmed.adapter.AdapterHistoryDetails;
import com.nupuit.vetmed.model.MockupList;
import com.nupuit.vetmed.model.TwoMarkQuestions;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryDetailsFragment extends Fragment {

    RecyclerView recycler_history_details;
    LinearLayoutManager layoutManager;

    MockupList mockupList;

    String id;

    Realm realm;

//    RealmList<OneMarkQuestion> questions1;
    RealmList<TwoMarkQuestions> questions2;
 //   RealmList<TenMarkQuestions> questions4;

    AdapterHistoryDetails adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_details, container, false);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        MainActivity.currentPage = "historyfragmentDetails";


        recycler_history_details = (RecyclerView) view.findViewById(R.id.recycler_history_details);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_history_details.setLayoutManager(layoutManager);
//        questions1 = new RealmList<OneMarkQuestion>();
        questions2 = new RealmList<TwoMarkQuestions>();
       // questions4 = new RealmList<TenMarkQuestions>();

        if (getArguments() == null) {
            mockupList = MockupActivity.mockupList;
        } else {
            id = getArguments().getString("id");
            mockupList = realm.where(MockupList.class).equalTo("id", id).findFirst();
        }

        if (mockupList != null) {

          //  questions1 = mockupList.getQuestions1();
            questions2 = mockupList.getQuestions2();

         //   questions4 = mockupList.getQuestions10();

            adapter = new AdapterHistoryDetails(getActivity(), questions2);

            recycler_history_details.setAdapter(adapter);

        }


        return view;
    }

    public HistoryDetailsFragment() {
        // Required empty public constructor
    }

}
