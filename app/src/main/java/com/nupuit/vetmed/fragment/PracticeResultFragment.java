package com.nupuit.vetmed.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.activity.PracticeActivity;
import com.nupuit.vetmed.utils.Practice;
import com.nupuit.vetmed.utils.RememberSingleton;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import java.util.ArrayList;


public class PracticeResultFragment extends Fragment {


    int totalQuestion;
    int totalCorrect, totalWrong;
    float totalMark;

    TextView markObtained, correctAns, wrongAns, totalQsn;
    ImageView imageViewHistory;
    Button exit;
    private AdView mAdView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_practise_result, container, false);

        PracticeActivity.currentPage = "p_result";

        markObtained = (TextView) view.findViewById(R.id.totalMark);
        correctAns = (TextView) view.findViewById(R.id.correct_answer);
        wrongAns = (TextView) view.findViewById(R.id.wrong_answer);
        totalQsn = (TextView) view.findViewById(R.id.totalQsn);
        exit = (Button) view.findViewById(R.id.next_button);
        imageViewHistory = view.findViewById(R.id.imageViewHistory);
        mAdView = view.findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        int mark10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[0]);
        int qsns10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[1]);

        float wrong10 = (qsns10 * 10) - mark10;


        totalQuestion = getArguments().getInt("totalQsn");
        totalCorrect = (totalQuestion*2) + (qsns10 * 10); //Integer.parseInt(Practice.getInstance().getResult().getQuestion())+qsns10;
        totalWrong = totalQuestion - totalCorrect;
        totalMark = Float.parseFloat(Practice.getInstance().getResult().getMark())+mark10;


        markObtained.setText(totalMark + "");
        correctAns.setText(totalCorrect + "");
        wrongAns.setText(totalWrong + "");
        totalQsn.setText((totalQuestion + qsns10) + "");

        float correctPercent = (totalMark / totalCorrect) * 100;
        float wrongPercent = 100 - correctPercent;

        exit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                SharedPrefsSingleton.getInstance(getContext()).saveBoolean("mockMade", false);
                Practice.getInstance().setListNull();
                RememberSingleton.getInstance().setListNullFour();
                PracticeActivity.practiceCount = 0;
                startActivity(new Intent(getActivity(), MainActivity.class));

                getActivity().finish();


            }
        });

        worksOnChart(view, correctPercent, wrongPercent);

        return view;
    }

    private void worksOnChart(View view, float correct, float wrong) {

//        float cor = correct1;
//        float t = totalQuestion;
//
//        float correct = (float) (cor / t) * 100;
//        float wrong = 100 - correct;

        //Log.d("totalCOrrect", totalQuestion+"");
        //Log.d("totalCOrrect", correct1+"");
        //Log.d("totalCOrrect", correct+"");


//        float percent = 0f;
//        float remain = 0f;
//        float com = correct1;
//        percent = com;
//        remain = remain - percent;


        LinearLayout layout = (LinearLayout) view.findViewById(R.id.chart);
        PieChart chart = new PieChart(getActivity());
        layout.addView(chart);
        chart.setMinimumHeight(650);

        // configure pie chart
        chart.setUsePercentValues(false);
        chart.setDescription("");

        // enable hole and configure
        chart.setDrawHoleEnabled(true);
        chart.setHoleColorTransparent(true);
        chart.setHoleRadius(17);
        chart.setTransparentCircleRadius(4);


        // enable rotation of the chart by touch
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(correct, 0));
        entries.add(new Entry(wrong, 0));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Correct");
        labels.add("Wrong");

        PieDataSet dataSet = new PieDataSet(entries, " ");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5);
        PieData data = new PieData(labels, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);
        chart.getLegend().setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        chart.getLegend().setTextSize(12f);
        chart.setDrawSliceText(false);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#4CAF50"));
        colors.add(Color.parseColor("#f44336"));

        dataSet.setColors(colors);

        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        // update pie chart
        chart.invalidate();


    }


    public PracticeResultFragment() {
        // Required empty public constructor
    }


}
