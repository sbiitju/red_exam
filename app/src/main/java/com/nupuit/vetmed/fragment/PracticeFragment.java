package com.nupuit.vetmed.fragment;


//import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.PracticeActivity;
import com.nupuit.vetmed.model.MarkQuestion;
import com.nupuit.vetmed.model.RealmString;
import com.nupuit.vetmed.model.TwoMarkQuestions;
import com.nupuit.vetmed.utils.Practice;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class PracticeFragment extends Fragment {


    public PracticeFragment() {
        // Required empty public constructor
    }

    TextView questionText;
    TextView countText;
    TextView correctText;
    TextView explainationText;
    TextView markText;
    TextView suggestedTime;
    ImageView imageViewPractice;
    private RadioGroup radioGroup;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    CardView finish;
    private int answer;
    String totalQsn;
    ImageView imageViewHistory;
    private InterstitialAd interstitialAd;
    Button next_question;
    Realm realm;
    String mark, question;
    LinearLayout oneTwoQsn, fourQsn;
    String qsnNumber;
    private static final String TAG = "PracticeFragment";
//    public static InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
                for (String adapterClass : statusMap.keySet()) {
                    AdapterStatus status = statusMap.get(adapterClass);
                    Log.d("MyApp", String.format(
                            "Adapter name: %s, Description: %s, Latency: %d",
                            adapterClass, status.getDescription(), status.getLatency()));
                }

                loadAd();
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_practice, container, false);

        initializeFour(view);
        if (!SharedPrefsSingleton.getInstance(getContext()).getBoolean("mockMade")) {
            Realm.init(getContext());
            realm = Realm.getDefaultInstance();
            makeMockupSet(PracticeActivity.two, PracticeActivity.four);
        }

        questionText = (TextView) view.findViewById(R.id.oneMarkQuestion);
        suggestedTime = (TextView) view.findViewById(R.id.suggested_time);
        markText = (TextView) view.findViewById(R.id.mark);
        countText = (TextView) view.findViewById(R.id.count_text);
        correctText = (TextView) view.findViewById(R.id.correct_answer);
        explainationText = (TextView) view.findViewById(R.id.explaination);
        imageViewPractice = view.findViewById(R.id.imageViewPractice);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        option1 = (RadioButton) view.findViewById(R.id.option1);
        option2 = (RadioButton) view.findViewById(R.id.option2);
        option3 = (RadioButton) view.findViewById(R.id.option3);
        option4 = (RadioButton) view.findViewById(R.id.option4);
        next_question = (Button) view.findViewById(R.id.next_button);
        finish = (CardView) view.findViewById(R.id.finish);
        imageViewHistory = view.findViewById(R.id.imageViewHistory);




        initializeFourMarks(view);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Cancel Exam");
                alertDialogBuilder.setMessage("Do you want to quit?");
                alertDialogBuilder.setPositiveButton("Quit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                goToResult();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        fourQsn.setVisibility(View.GONE);

        if (PracticeActivity.practiceCount < PracticeActivity.two) {
            fourQsn.setVisibility(View.GONE);

            String sub1Main = PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getOp1();

            String sub2Main = PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getOp2();


            String sub3Main = PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getOp3();


            String sub4Main = PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getOp4();


            questionText.setText(PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getQsn());

            option1.setText(sub1Main);
            option2.setText(sub2Main);
            option3.setText(sub3Main);
            option4.setText(sub4Main);

            qsnNumber = PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getQsnNumber();


            String imageName = "q" + qsnNumber;
            String PACKAGE_NAME = getContext().getPackageName();
            int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + imageName, null, null);


            if (imgId == 0) {
                imageViewPractice.setVisibility(View.INVISIBLE);
                imageViewPractice.getLayoutParams().height = 0;
            } else {
                //imageViewMock.getLayoutParams().height=200;
                //imageViewMock.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                //imageViewMock.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewPractice.setVisibility(View.VISIBLE);
                imageViewPractice.setImageResource(imgId);
            }


            hideEmptyRadioButton();

            explainationText.setText(PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getExplaination());
            answer = Integer.parseInt(PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getCorrectAns());

            mark = PracticeActivity.mockupList.getQuestions2().get(PracticeActivity.practiceCount).getMark();
            question = "2";

            markText.setText("Mark: " + mark);
            suggestedTime.setText("Suggested Time: 108 seconds");

        }

        totalQsn = PracticeActivity.totalQuestion;

        countText.setText("Question: " + String.valueOf(PracticeActivity.practiceCount + 1) + "/" + totalQsn);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                next_question.setEnabled(true);
                next_question.setBackgroundResource(R.drawable.button);
                int pos = radioGroup.indexOfChild(view.findViewById(position));
                if ((pos + 1) == answer) {
                    ((RadioButton) radioGroup.getChildAt(pos)).setTextColor(getResources().getColor(R.color.green));
                    String correct = (String) ((RadioButton) radioGroup.getChildAt(pos)).getText();
                    correctText.setText("Correct Answer: " + correct);

                    Practice.getInstance().setValue(new MarkQuestion(mark, question));

                } else {
                    ((RadioButton) radioGroup.getChildAt(pos)).setTextColor(getResources().getColor(R.color.red));
                    String correct = (String) ((RadioButton) radioGroup.getChildAt(answer - 1)).getText();
                    correctText.setText("Correct Answer: " + correct);
                }

                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioGroup.getChildAt(i).setEnabled(false);
                }
                makeAnswerVisible();
            }
        });


        next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Toast.makeText(getActivity()
           //             , ".", Toast.LENGTH_SHORT).show();
                PracticeActivity.practiceCount = PracticeActivity.practiceCount + 1;
                int count = PracticeActivity.practiceCount;

                if (PracticeActivity.practiceCount >= Integer.parseInt(totalQsn)) {
                    goToResult();
                } else {
//
//                    if (count == 4) {
//                        showAd();
//                    } else if (count == 9) {
//                        showAd();
//                    }







                    countText.setText("Question: " + String.valueOf(PracticeActivity.practiceCount) + "/" + totalQsn);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left);
                    transaction.replace(R.id.fragment_container, new PracticeFragment());
                    transaction.commit();
                }

            }
        });


        return view;
    }




    private void initializeFourMarks(View view) {

    }


    private void initializeFour(View view) {

        oneTwoQsn = (LinearLayout) view.findViewById(R.id.oneTwoQsn);
        fourQsn = (LinearLayout) view.findViewById(R.id.fourQsn);


    }

    private void makeAnswerVisible() {
        correctText.setVisibility(View.VISIBLE);
        explainationText.setVisibility(View.VISIBLE);
    }

    private void makeMockupSet(int mark2, int mark4) {
        ProgressDialog loading = new ProgressDialog(getContext());
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();


        //  RealmResults<OneMarkQuestion> realmResults1 = realm.where(OneMarkQuestion.class).findAll();
        RealmResults<TwoMarkQuestions> realmResults2 = realm.where(TwoMarkQuestions.class).findAll();
        //  RealmResults<TenMarkQuestions> realmResults4 = realm.where(TenMarkQuestions.class).findAll();

        //  int size1 = realmResults1.size();
        int size2 = realmResults2.size();
        //   int size4 = realmResults4.size();

        //  RealmList<OneMarkQuestion> questions1 = new RealmList<OneMarkQuestion>();
        RealmList<TwoMarkQuestions> questions2 = new RealmList<TwoMarkQuestions>();
        //   RealmList<TenMarkQuestions> questions4 = new RealmList<TenMarkQuestions>();

        //   ArrayList<String> templist4 = new ArrayList<String>();
        ArrayList<String> templist = new ArrayList<String>();

        RealmList<RealmString> tempList = new RealmList<RealmString>();
        //  RealmList<RealmString> tempList4 = new RealmList<RealmString>();


        while (questions2.size() < mark2) {

            Random r = new Random();
            int randomNumber = r.nextInt((int) size2);

            final TwoMarkQuestions oneMarkQuestion = realmResults2.get(randomNumber);
            RealmString re = new RealmString(oneMarkQuestion.getQsnNumber());


            if (templist.size() == 0) {
                templist.add(oneMarkQuestion.getQsnNumber());
                questions2.add(oneMarkQuestion);
                tempList.add(re);
            } else {
                if (!templist.contains(oneMarkQuestion.getQsnNumber())) {
                    templist.add(oneMarkQuestion.getQsnNumber());
                    questions2.add(oneMarkQuestion);
                    tempList.add(re);
                } else continue;
            }

        }

        PracticeActivity.mockupList.setQuestions2(questions2);
        SharedPrefsSingleton.getInstance(getContext()).saveBoolean("mockMade", true);
        loading.dismiss();

    }

    public void hideEmptyRadioButton() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            if (((RadioButton) radioGroup.getChildAt(i)).getText().equals("#")) {
                ((RadioButton) radioGroup.getChildAt(i)).setVisibility(View.GONE);
            }
        }
    }

    public void goToResult() {

        PracticeActivity.hideHeader();

        SharedPrefsSingleton.getInstance(getContext()).saveBoolean("mockMade", false);
        PracticeActivity.practiceCount = 0;

        Bundle bundle = new Bundle();
        bundle.putInt("totalQsn", PracticeActivity.two);
        PracticeResultFragment practiceResultFragment = new PracticeResultFragment();
        practiceResultFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, practiceResultFragment).commit();
    }




    public void showAd() {
        if (interstitialAd != null) {
           interstitialAd.show(getActivity());
        } else {
        //    Toast.makeText(getContext(), "Ad did not load", Toast.LENGTH_SHORT).show();
        //    Log.d("TAG", "The interstitial ad wasn't ready yet.");
            loadAd();
        }
    }
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getContext(),
                getString(R.string.interstitial_ad_unit_id),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        PracticeFragment.this.interstitialAd = interstitialAd;
           //             Log.i(TAG, "onAdLoaded");
            //            Toast.makeText( getActivity(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        PracticeFragment.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        PracticeFragment.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                //        Toast.makeText(
               //                 getContext(),"onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                 //               .show();
                    }
                });
    }
}
