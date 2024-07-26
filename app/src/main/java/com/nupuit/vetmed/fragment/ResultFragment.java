package com.nupuit.vetmed.fragment;


import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.ResultActivity;
import com.nupuit.vetmed.model.MarkQuestion;
import com.nupuit.vetmed.utils.RememberSingleton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {


    TextView mark, time, result;
    Button seeAnswers;

    public static int skipped = 0;
    public static int wrong = 0;
    public static int correct = 0;
    public static float markFour = 0;


    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mark = (TextView) view.findViewById(R.id.mark);
        time = (TextView) view.findViewById(R.id.time);
        result = (TextView) view.findViewById(R.id.result);
        seeAnswers = (Button) view.findViewById(R.id.see_answers);

        int mark10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[0]);
        int qsns10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[1]);

        Log.d("mark10", mark10+"");
      //  int qsns10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[1]);
//        skipped = Integer.parseInt(resultFour[0]);
//        wrong = Integer.parseInt(resultFour[1]);
//        correct = Integer.parseInt(resultFour[2]);

        MarkQuestion correctResult = RememberSingleton.getInstance().getResult(5);
        Log.d("mark10", correctResult.getMark());
       // MarkQuestion skippedResult = RememberSingleton.getInstance().getResult(-1);

        int totalQsn = Integer.parseInt(ResultActivity.totalQuestion);

        float totalMark = Float.parseFloat(correctResult.getMark())+mark10;
        int correctQsn = Integer.parseInt(correctResult.getQuestion())+correct;
      //  int skippedQsn = Integer.parseInt(skippedResult.getQuestion())+skipped;
      //  int wrongQsn = totalQsn - (correctQsn+skippedQsn);


        mark.setText(totalMark+"");
        time.setText(ResultActivity.time_taken + " " + "min");

        int actualMark = ResultActivity.mark;
        float percent = (totalMark/(float)actualMark) * 100;
        float wrongNumber = 100 - percent;

        worksOnChart(view, totalQsn, correctQsn, 0, 0, percent, wrongNumber);

        if (percent>=50f){
            result.setText("Passed");
            result.setTextColor(Color.GREEN);
        }else {
            result.setText("Failed");
            result.setTextColor(Color.RED);
        }

        RememberSingleton.getInstance().setListNull();
        RememberSingleton.getInstance().setListNullFour();

        seeAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryDetailsFragment()).commit();
            }
        });

        return view;
    }

    private void worksOnChart(View view, int total1, int correct1, int wrong1, int skipped1, float percent, float wrongNumber) {

//        float correct = correct1;
//        float wrong = wrong1;
//        float skipped = skipped1;
//
//        correct = (correct/ (float) total1) * 100;
//        wrong = (wrong/ (float) total1) * 100;
//        skipped = (skipped/ (float) total1) * 100;

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.chart);
        PieChart chart = new PieChart(getActivity());
        layout.addView(chart);
        chart.setMinimumHeight(800);

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
        entries.add(new Entry(percent, 0));
        entries.add(new Entry(wrongNumber, 0));
       // entries.add(new Entry(skipped, 0));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Correct");
        labels.add("Wrong");
      //  labels.add("Skipped");

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
    //    colors.add(Color.parseColor("#FFC107"));

        dataSet.setColors(colors);

        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        // update pie chart
        chart.invalidate();
    }

}
