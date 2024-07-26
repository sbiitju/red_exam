package com.nupuit.vetmed.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MockupActivity;
import com.nupuit.vetmed.model.MarkQuestion;
import com.nupuit.vetmed.utils.RememberSingleton;

//import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class MockupQuestionFragment extends Fragment {


    public MockupQuestionFragment() {
        // Required empty public constructor
    }

    TextView questionText;
    ImageView imageViewMock;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    public RadioGroup radioGroup;
    int position;
    String mark, question;
    TextView suggested_time;
    private int answer;
    String qsnNumber;



    LinearLayout oneTwoQsn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_mockup_question, container, false);

        initializeFour(view);

        MockupActivity.currentPage = "mockup";
        position = getArguments().getInt("position");


        questionText = (TextView) view.findViewById(R.id.oneMarkQuestion);
        imageViewMock = view.findViewById(R.id.imageViewMock);
        option1 = (RadioButton) view.findViewById(R.id.option1);
        option2 = (RadioButton) view.findViewById(R.id.option2);
        option3 = (RadioButton) view.findViewById(R.id.option3);
        option4 = (RadioButton) view.findViewById(R.id.option4);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        suggested_time = (TextView) view.findViewById(R.id.suggested_time);


        if (MockupActivity.practiceCount < MockupActivity.two) {

            qsnNumber = MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getQsnNumber();

            MarkQuestion markQuestion = new MarkQuestion("2", qsnNumber);


            String value = RememberSingleton.getInstance().getCheckedValue(markQuestion);
            //Log.e("value", value + "");
            answer = Integer.parseInt(MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getCorrectAns());

            int val = Integer.parseInt(value);

            if (val == 5) {
                RadioButton b = (RadioButton) radioGroup.getChildAt(answer - 1);
                b.setChecked(true);
            } else if (val > -1 && val != 5) {
                RadioButton b = (RadioButton) radioGroup.getChildAt(val);
                b.setChecked(true);
            }

            questionText.setText(MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getQsn());

            String imageName = "q" + qsnNumber;
            String PACKAGE_NAME = getContext().getPackageName();
            int imgId = getResources().getIdentifier(PACKAGE_NAME+":drawable/"+imageName , null, null);

            if(imgId==0){
                imageViewMock.setVisibility(View.INVISIBLE);
                imageViewMock.getLayoutParams().height=0;
            }
            else {
                //imageViewMock.getLayoutParams().height=200;
                //imageViewMock.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                //imageViewMock.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewMock.setVisibility(View.VISIBLE);
                imageViewMock.setImageResource(imgId);
            }


            //Log.e("QsnNumber", MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getQsnNumber()+"");

            String sub1Main = MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getOp1();

            String sub2Main = MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getOp2();

            String sub3Main = MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getOp3();

            String sub4Main = MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getOp4();



            option1.setText(sub1Main);
            option2.setText(sub2Main);
            option3.setText(sub3Main);
            option4.setText(sub4Main);

            hideEmptyRadioButton();

            mark = MockupActivity.mockupList.getQuestions2().get(MockupActivity.practiceCount).getMark();
            question = "2";

            suggested_time.setText(MockupActivity.suggested_time);

        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                int pos = radioGroup.indexOfChild(view.findViewById(position));

                if ((pos + 1) == answer) {
                    MarkQuestion markQuestion1 = new MarkQuestion(mark, qsnNumber);
                    RememberSingleton.getInstance().updateCheckedValue(markQuestion1, String.valueOf(5));
                } else {
                    MarkQuestion markQuestion1 = new MarkQuestion(mark, qsnNumber);
                    RememberSingleton.getInstance().updateCheckedValue(markQuestion1, String.valueOf(pos));
                }
            }
        });


        return view;
    }

    private void initializeFour(View view) {

        oneTwoQsn = (LinearLayout) view.findViewById(R.id.oneTwoQsn);
       // fourQsn = (LinearLayout) view.findViewById(R.id.fourQsn);

    }

    public void hideEmptyRadioButton() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (((RadioButton) radioGroup.getChildAt(i)).getText().equals("#")) {
                ((RadioButton) radioGroup.getChildAt(i)).setVisibility(View.GONE);
            }
        }
    }
}
