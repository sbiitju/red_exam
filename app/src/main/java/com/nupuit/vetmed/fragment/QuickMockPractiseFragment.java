package com.nupuit.vetmed.fragment;


import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.activity.MockupActivity;
import com.nupuit.vetmed.activity.PracticeActivity;
import com.nupuit.vetmed.adapter.CustomAdapter;
import com.nupuit.vetmed.admanager.AdManager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickMockPractiseFragment extends Fragment implements AdManager.AdCallback {

    // Integer[] ones = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    Integer[] twos = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    //  Integer[] fours = {0, 1, 2};

    Spinner spinner1, spinner2, spinner3;
    CustomAdapter aa1, aa2, aa3;

    AdView mAdView;


    String mark, time, qsn;
    int totalMark = 0, totalQsn = 0, twoQsn = 0, oneQsn = 0, fourQsn = 0;
    double totalTime = 0;

    TextView tvMark, tvTime, tvTotalQsn, tvAlert, tvheader;
    Button btnStart;
    AdManager adManager;

    String type = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quick_mock_practise, container, false);
        MainActivity.currentPage = "quickmock";
//        mAdView = view.findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        adManager=AdManager.getInstance(getActivity());
        adManager.loadAd();
        adManager.setAdCallback(this);
        initialization(view);

        type = getArguments().getString("type");

        if (type.equals("0")) {
            tvheader.setText("Practice");

        } else {
            tvheader.setText("Quick Mockup");
        }

        //  spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        //  aa1 = new CustomAdapter(getActivity(), ones);
        //  aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner1.setAdapter(aa1);


        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        aa2 = new CustomAdapter(getActivity(), twos);
        ///   aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(aa2);


//        spinner3 = (Spinner) view.findViewById(R.id.spinner3);
//        aa3 = new CustomAdapter(getActivity(), fours);
//        //   aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner3.setAdapter(aa3);


//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                oneQsn = (int) parent.getItemAtPosition(position);
//                int mark = oneQsn * 1;
//                double time = ((double) mark * 90);
//
//                handleAll(oneQsn, twoQsn, fourQsn);
//
//                //     handleNextActivity(mark, time, oneQsn);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//                oneQsn = 0;
//
//            }
//        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                twoQsn = (int) parent.getItemAtPosition(position);
                int mark = twoQsn * 2;
                double time = ((double) mark * 54);

                handleAll(oneQsn, twoQsn, fourQsn);

                //  handleNextActivity(mark, time, twoQsn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                twoQsn = 0;

            }
        });
//        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                fourQsn = (int) parent.getItemAtPosition(position);
//                int mark = twoQsn * 10;
//                double time = ((double) mark * 90);
//
//                handleAll(oneQsn, twoQsn, fourQsn);
//
//                //  handleNextActivity(mark, time, fourQsn);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//                fourQsn = 0;
//
//            }
//        });


        return view;
    }

    private void handleAll(final int oneQsn, final int twoQsn, final int fourQsn) {

        final int mark = ((oneQsn * 1) + (2 * twoQsn) + (10 * fourQsn));
        final long time = ((long) mark * 54);

        double min = 0;
        long sec = 0;


        final long minutes = TimeUnit.SECONDS
                .toMinutes(time);
        sec = time - (minutes * 60);

        tvTime.setText(minutes + "." + sec + " min");

        totalQsn = oneQsn + twoQsn + fourQsn;


        tvMark.setText(mark + "");
        tvTotalQsn.setText((totalQsn) + "");

        if (mark > 0) {
            btnStart.setEnabled(true);
            btnStart.setBackgroundResource(R.drawable.button);
            tvAlert.setVisibility(View.GONE);

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showAd();
                    adManager.showAd();

                    PracticeActivity.totalQuestion = String.valueOf(totalQsn);
                    PracticeActivity.one = oneQsn;
                    PracticeActivity.two = twoQsn;
                    PracticeActivity.four = fourQsn;


                }
            });


        } else {
            btnStart.setEnabled(false);
            btnStart.setBackgroundResource(R.drawable.button_grey);
            //  MyDynamicToast.errorMessage(getActivity(), "You have to select some number of questions");
            //  tvAlert.setVisibility(View.VISIBLE);
        }

    }

    private void initialization(View view) {

        tvAlert = (TextView) view.findViewById(R.id.tvAlert);
        tvMark = (TextView) view.findViewById(R.id.mark);
        tvTime = (TextView) view.findViewById(R.id.time);
        btnStart = (Button) view.findViewById(R.id.start);
        tvTotalQsn = (TextView) view.findViewById(R.id.totalQsn);

        tvheader = (TextView) view.findViewById(R.id.header);

    }


    public QuickMockPractiseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAdShown() {

    }

    @Override
    public void onAdDismissed() {
        final int mark = ((oneQsn * 1) + (2 * twoQsn) + (10 * fourQsn));
        final long time = ((long) mark * 54);

        double min = 0;
        long sec = 0;


        final long minutes = TimeUnit.SECONDS
                .toMinutes(time);
        sec = time - (minutes * 60);
        if (type.equals("0")) {

            startActivity(new Intent(getActivity(), PracticeActivity.class).putExtra("time", time));
            getActivity().finish();

//                        PracticeFragment practiceFragment = new PracticeFragment();
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left);
//                        transaction.replace(R.id.fragment_container, practiceFragment);
//                        transaction.commit();

        } else {

            MockupActivity.totalQuestion = String.valueOf(totalQsn);
            //  MockupActivity.one = oneQsn;
            MockupActivity.two = twoQsn;
            // MockupActivity.four = fourQsn;

            getActivity().startActivity(new Intent(getActivity(), MockupActivity.class)
                    .putExtra("total_mark", mark)
                    .putExtra("total_time", time)
                    .putExtra("time_in_mnt", minutes + "." + sec + " min")
                    //   .putExtra("1", oneQsn)
                    .putExtra("2", twoQsn)
                    .putExtra("4", fourQsn)
                    .putExtra("total_question1", String.valueOf(totalQsn)));

            getActivity().finish();



//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuickMockFragment()).addToBackStack(null).commit();

        }
    }
}
