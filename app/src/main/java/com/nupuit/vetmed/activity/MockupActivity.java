package com.nupuit.vetmed.activity;



import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.dinuscxj.progressbar.CircleProgressBar;
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
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.fragment.MockupQuestionFragment;
import com.nupuit.vetmed.model.MarkQuestion;
import com.nupuit.vetmed.model.MockupList;
import com.nupuit.vetmed.model.OneMarkQuestion;
import com.nupuit.vetmed.model.RealmString;
import com.nupuit.vetmed.model.TenMarkQuestions;
import com.nupuit.vetmed.model.TwoMarkQuestions;
import com.nupuit.vetmed.utils.RememberSingleton;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MockupActivity extends AppCompatActivity {

    // public int count;
    TextView countText;
    String id;
    Integer flag;
    TextView tvMark;
    String totalQsn;
    CircleProgressBar circleProgressBar;
    LinearLayout bottom_bar;
    RelativeLayout header;
    AdView mAdView;

    private static final String TAG = "MockupActivity";
    private InterstitialAd interstitialAd;
    public static String currentPage = "";
    public static int mark;
    long time;
    // public static int one=0;
    public static int two;
    //  public static int four;
    public static int practiceCount = 0;
    public static String totalQuestion;
    public static MockupList mockupList;
    public static String suggested_time;
    public static String time_taken;
    private int progressStatus = 0;
    String singleMark, question;
    Realm realm;
    // public static String qsnNumberTen = "";
    CountDownTimer countDownTimer;

//    public static int opApos = -1;
//    public static int opBpos = -1;
//    public static int opCpos = -1;
//    public static int opDpos = -1;
//    public static int opEpos = -1;
//
//    public static int answerA, answerB, answerC, answerD, answerE;


    @Override
    protected void onResume() {
        super.onResume();
        loadAd();
        System.out.println("Show Add");
        showAd();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mockup);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        tvMark = (TextView) findViewById(R.id.mark);
        mockupList = new MockupList();
        final String app_id = "5930eda8cfa5ab3908001540";
        mAdView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
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



        final CardView next = (CardView) findViewById(R.id.next);
        final CardView previous = (CardView) findViewById(R.id.previous);
        final CardView finish = (CardView) findViewById(R.id.finish);
        countText = (TextView) findViewById(R.id.count_text);
        circleProgressBar = (CircleProgressBar) findViewById(R.id.circular_progress);
        bottom_bar = (LinearLayout) findViewById(R.id.bottom_bar);
        header = (RelativeLayout) findViewById(R.id.header);


        if (getIntent().getStringExtra("id") == null) {

            id = "-1";

            totalQsn = getIntent().getStringExtra("total_question1");
            mark = getIntent().getIntExtra("total_mark", 0);
            time = getIntent().getLongExtra("total_time", 0);

            mockupList = populateMockupList(0, two, 0);
            populateSingletonHash(mockupList);

        } else {

            totalQsn = "10";
            totalQuestion = "10";
            id = getIntent().getStringExtra("id");
            mockupList = realm.where(MockupList.class).equalTo("id", id).findFirst();

            mark = 20;

            //one = 0;
            two = 10;
            time = 10 * 108;
            // four = 3;

            populateSingletonHash(mockupList);

        }


        if (practiceCount < two) {
            singleMark = mockupList.getQuestions2().get(practiceCount).getMark();
            question = "2";
            suggested_time = "Suggested Time: " + 108 + " seconds";
            tvMark.setText("Mark: 2");

        }
//        else if (practiceCount >= two && practiceCount < (two + one)) {
//
//            int position = practiceCount - two;
//
//            singleMark = mockupList.getQuestions1().get(position).getMark();
//            question = "1";
//            suggested_time = "Suggested Time: " + 80 + " seconds";
//            tvMark.setText("Mark: 1");
//
//        }
//        else {
//
//            int position = practiceCount - (two);
//
//            // calculateMark();
//
//            singleMark = "10";
//            question = "10";
//            suggested_time = "Suggested Time: " + 1200 + " seconds";
//            tvMark.setText("Mark: 10");
//
//        }

        //Log.d("practiceCount", practiceCount + "");


        countText.setText("Question: " + String.valueOf(practiceCount + 1) + "/" + totalQsn);

        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  count++;
                        //  if (practiceCount<Integer.parseInt(totalQsn)-1){

                        //  }

                        practiceCount++;

                        if (practiceCount < Integer.parseInt(totalQsn)) {
                            countText.setText("Question: " + String.valueOf(practiceCount + 1) + "/" + totalQsn);

//                            /Counter for showing ads
//                            if (practiceCount == 3) {
//                                showAd();
//                            } else if (practiceCount == 4) {
//                                if (interstitialAd == null);
//                                loadAd();
//
//                            } else if (practiceCount == 7) {
//                                showAd();
//                            } else if (practiceCount == 8) {
//                                if (interstitialAd == null)
//                                    loadAd();
//                            }



                            if (practiceCount < two) {

                                singleMark = mockupList.getQuestions2().get(practiceCount).getMark();
                                question = "2";
                                suggested_time = "Suggested Time: " + 108 + " seconds";
                                tvMark.setText("Mark: 2");

                            }
//                    else if (practiceCount >= two && practiceCount < (two + one)) {
//
//                        int position = practiceCount - two;
//
//                        singleMark = mockupList.getQuestions1().get(position).getMark();
//                        question = "1";
//                        suggested_time = "Suggested Time: " + 80 + " seconds";
//                        tvMark.setText("Mark: 1");
//
//                    }
//                    else {
//
//                        calculateResult();
//
//                        int position = practiceCount - (two);
//
//                        singleMark = "10";
//                        question = "10";
//                        suggested_time = "Suggested Time: " + 1200 + " seconds";
//                        tvMark.setText("Mark: 10");
//
//                    }


                            Bundle bundle = new Bundle();
                            bundle.putInt("position", practiceCount);

                            MockupQuestionFragment mockupQuestionFragment = new MockupQuestionFragment();
                            mockupQuestionFragment.setArguments(bundle);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left);
                            transaction.replace(R.id.fragment_container, mockupQuestionFragment).addToBackStack(null);
                            transaction.commit();

                        } else if (practiceCount == Integer.parseInt(totalQsn)) {

                            practiceCount--;

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockupActivity.this);
                            alertDialogBuilder.setTitle("Question Finished");
                            alertDialogBuilder.setMessage("Do you want to see results?");
                            alertDialogBuilder.setPositiveButton("See Result",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

//                                    if (practiceCount > (two)) {
//                                        calculateResult();
//                                    }

                                            goToResult();
                                            showAd();


                                        }
                                    });

                            alertDialogBuilder.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {


//                            next.setEnabled(false);
//                            next.setAlpha(.4f);

                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                    }
                });

        previous.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                if (practiceCount == Integer.parseInt(totalQsn)){
//                    practiceCount--;
//                    next.setEnabled(true);
//                    next.setAlpha(1.0f);
//                }

                        if (practiceCount > 0) {
                            // practiceCount = practiceCount - 1;
                            practiceCount--;

                            countText.setText("Question: " + String.valueOf(practiceCount + 1) + "/" + totalQsn);

                            if (practiceCount < two) {

                                singleMark = mockupList.getQuestions2().get(practiceCount).getMark();
                                question = "2";
                                suggested_time = "Suggested Time: " + 108 + " seconds";
                                tvMark.setText("Mark: 2");

                            }
//                    else if (practiceCount >= two && practiceCount < (two + one)) {
//
//                        int position = practiceCount - two;
//                        singleMark = mockupList.getQuestions1().get(position).getMark();
//                        question = "1";
//                        suggested_time = "Suggested Time: " + 80 + " seconds";
//                        tvMark.setText("Mark: 1");
//
//                    }
//                    else {
//
//                        int position = practiceCount - (two);
//                        calculateResult();
//                        singleMark = "10";
//                        question = "10";
//                        suggested_time = "Suggested Time: " + 1200 + " seconds";
//                        tvMark.setText("Mark: 10");
//
//                    }

                            Bundle bundle = new Bundle();
                            bundle.putInt("position", practiceCount);

                            MockupQuestionFragment mockupQuestionFragment = new MockupQuestionFragment();
                            mockupQuestionFragment.setArguments(bundle);

                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right);
                            transaction.replace(R.id.fragment_container, mockupQuestionFragment);
                            transaction.commit();

                            // getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(MockupActivity.this, "There is no previous Question", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockupActivity.this);
                alertDialogBuilder.setTitle("Finish Exam");
                alertDialogBuilder.setMessage("Do you want to finish exam?");
                alertDialogBuilder.setPositiveButton("Finish",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

//                                if (practiceCount > (two)) {
//                                    calculateResult();
//                                }

                                goToResult();
                                showAd();
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

        Bundle bundle = new Bundle();
        bundle.putInt("position", practiceCount);

        MockupQuestionFragment mockupQuestionFragment = new MockupQuestionFragment();
        mockupQuestionFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mockupQuestionFragment);
        transaction.commit();

        //  final long time = getIntent().getLongExtra("total_time", 0);
//        final long time = 5;

        int progressBarMaximumValue = (int) (time);
        circleProgressBar.setMax(progressBarMaximumValue);
        circleProgressBar.setProgressTextFormatPattern(progressBarMaximumValue + "");

        countDownTimer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Another one second passed
                circleProgressBar.setProgressTextFormatPattern(millisUntilFinished / 1000 / 60 + ":" + (millisUntilFinished / 1000) % 60);
                //Each second ProgressBar progress counter added one
                progressStatus += 1;
                circleProgressBar.setProgress(progressStatus);

                long takenTime = (time * 1000) - millisUntilFinished;

                time_taken = (takenTime / 1000 / 60 + ":" + (takenTime / 1000) % 60);

            }

            public void onFinish() {

                loadAd();
                //Do something when count down end.
                progressStatus += 1;
                circleProgressBar.setProgress(progressStatus);
                circleProgressBar.setProgressTextFormatPattern(0 + "");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MockupActivity.this);
                alertDialogBuilder.setTitle("Times up");
                alertDialogBuilder.setMessage("You are out of time");
                alertDialogBuilder.setPositiveButton("See Result",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

//                                if (practiceCount > (two)) {
//                                    calculateResult();
//                                }

                                goToResult();
                                showAd();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                alertDialog.setOnDismissListener(
                        new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

//                        if (practiceCount > (two)) {
//                            calculateResult();
//                        }
                                goToResult();
                                showAd();
                            }
                        });

            }
        }.start();
    }




    private void populateSingletonHash(MockupList mockupList) {

        //  RealmList<OneMarkQuestion> questions1 = new RealmList<OneMarkQuestion>();
        RealmList<TwoMarkQuestions> questions2 = new RealmList<TwoMarkQuestions>();
        // RealmList<TenMarkQuestions> questions4 = new RealmList<TenMarkQuestions>();

        //  questions1 = mockupList.getQuestions1();
        questions2 = mockupList.getQuestions2();
        //  questions4 = mockupList.getQuestions10();

//        for (int i = 0; i < questions1.size(); i++) {
//
//            MarkQuestion markQuestion = new MarkQuestion("1", questions1.get(i).getQsnNumber());
//
//            RememberSingleton.getInstance().populateFromMockupList(markQuestion, "-1");
//
//        }

        for (int i = 0; i < questions2.size(); i++) {

            MarkQuestion markQuestion = new MarkQuestion("2", questions2.get(i).getQsnNumber());

            RememberSingleton.getInstance().populateFromMockupList(markQuestion, "-1");
        }

//        Log.e("dekhi ki id", questions4.size() + "");
//
//        for (int i = 0; i < questions4.size(); i++) {
//
//            UserAnsFour userAnsFour = new UserAnsFour(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
//
//            RememberSingleton.getInstance().populateFourMarkFromMockupList(questions4.get(i).getQsbNUmber(), userAnsFour);
//
//              Log.e("dekhi ki id", questions4.get(i).getQsbNUmber() + "");
//
//        }


    }

//    private void calculateResult() {
//
//        int total = 0;
//
//        UserAnsFour userAnsFour = new UserAnsFour();
//        if ((opApos + 1) == answerA) {
//            userAnsFour.setObtA(5);
//            total = total + 2;
//        } else {
//            userAnsFour.setObtA(opApos);
//        }
//        if ((opBpos + 1) == answerB) {
//            userAnsFour.setObtB(5);
//            total = total + 2;
//        } else {
//            userAnsFour.setObtB(opBpos);
//        }
//        if ((opCpos + 1) == answerC) {
//            userAnsFour.setObtC(5);
//            total = total + 2;
//        } else {
//            userAnsFour.setObtC(opCpos);
//        }
//        if ((opDpos + 1) == answerD) {
//            userAnsFour.setObtD(5);
//            total = total + 2;
//        } else {
//            userAnsFour.setObtD(opDpos);
//        }
//        if ((opEpos + 1) == answerE) {
//            userAnsFour.setObtE(5);
//            total = total + 2;
//        } else {
//            userAnsFour.setObtE(opEpos);
//        }
//
//        userAnsFour.setAnsA(answerA);
//        userAnsFour.setAnsB(answerB);
//        userAnsFour.setAnsC(answerC);
//        userAnsFour.setAnsD(answerD);
//        userAnsFour.setAnsE(answerE);
//        userAnsFour.setObtainedMark(total);
//
//        RememberSingleton.getInstance().updateCheckedValueFour(qsnNumberTen, userAnsFour);
//
////        opApos = -1;
////        opBpos = -1;
////        opCpos = -1;
////        opDpos = -1;
////        opEpos = -1;
//
//    }


    private MockupList populateMockupList(int oneQsn, int twoQsn, int fourQsn) {


        MockupList mockupList = new MockupList();


        ProgressDialog loading;

        loading = new ProgressDialog(MockupActivity.this);
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();


        //  RealmResults<OneMarkQuestion> realmResults1 = realm.where(OneMarkQuestion.class).findAll();
        RealmResults<TwoMarkQuestions> realmResults2 = realm.where(TwoMarkQuestions.class).findAll();
        //  RealmResults<TenMarkQuestions> realmResults4 = realm.where(TenMarkQuestions.class).findAll();

        //  int size1 = realmResults1.size();
        int size2 = realmResults2.size();
        // int size4 = realmResults4.size();


        RealmList<OneMarkQuestion> questions1 = new RealmList<OneMarkQuestion>();
        RealmList<TwoMarkQuestions> questions2 = new RealmList<TwoMarkQuestions>();
        RealmList<TenMarkQuestions> questions4 = new RealmList<TenMarkQuestions>();


        ArrayList<String> templist4 = new ArrayList<String>();
        ArrayList<String> templist = new ArrayList<String>();

        RealmList<RealmString> tempList = new RealmList<RealmString>();
        RealmList<RealmString> tempList4 = new RealmList<RealmString>();


//        while (questions1.size() < oneQsn) {
//
//            Random r = new Random();
//            int randomNumber = r.nextInt((int) size1);
//
//            final OneMarkQuestion oneMarkQuestion = realmResults1.get(randomNumber);
//
//            RealmString re = new RealmString(oneMarkQuestion.getQsnNumber());
//
//            if (templist.size() == 0) {
//                templist.add(oneMarkQuestion.getQsnNumber());
//                questions1.add(oneMarkQuestion);
//                tempList.add(re);
//            } else {
//                if (!templist.contains(oneMarkQuestion.getQsnNumber())) {
//                    templist.add(oneMarkQuestion.getQsnNumber());
//                    questions1.add(oneMarkQuestion);
//                    tempList.add(re);
//                } else continue;
//            }
//        }

        while (questions2.size() < twoQsn) {

            Random r = new Random();
            int randomNumber = r.nextInt(size2);

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


        // mockupList.setQuestions1(questions1);
        mockupList.setQuestions2(questions2);
        // mockupList.setQuestions10(questions4);

        SharedPrefsSingleton.getInstance(MockupActivity.this).saveBoolean("mockMade", true);

        loading.dismiss();
        return mockupList;
    }

    public void hideViews() {
        header.setVisibility(View.GONE);
        bottom_bar.setVisibility(View.GONE);
    }



    @Override
    public void onBackPressed() {
        showExit();

    }

    private void showExit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cancel Exam");
        alertDialogBuilder.setMessage("Do you want to quit?");
        alertDialogBuilder.setPositiveButton("Quit Exam",

                (arg0, arg1) -> {

//                        if (practiceCount > (two)) {
//                            calculateResult();
//                        }
                    //mRewardedVideoAd.show();

                    goToResult();
                    showAd();
                });

        alertDialogBuilder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void showAd() {

        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
      //      Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            loadAd();
        }
    }
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                getString(R.string.interstitial_ad_unit_id),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        MockupActivity.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
        //                Toast.makeText(MockupActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MockupActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MockupActivity.this.interstitialAd = null;
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
             //           Toast.makeText(
              //                          MockupActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
             //                   .show();
                    }
                });
    }
    public void goToResult() {


        practiceCount = 0;

        startActivity(new Intent(MockupActivity.this, ResultActivity.class).putExtra("total_questions", totalQuestion)
                .putExtra("time_taken", time_taken)
                .putExtra("mark", mark)
                .putExtra("id", id));

        countDownTimer.cancel();

        finish();
    }


//    public static void calculateMark() {
//
//        float totalSingleMark = 0f;
//        int dummyCount1 = count1;
//        int dummyCount2 = count2;
//        ArrayList<Integer> dummyAnswers1 = new ArrayList<Integer>();
//        ArrayList<Integer> dummyAnswers2 = new ArrayList<Integer>();
//
//        dummyAnswers1.addAll(answers1);
//        dummyAnswers2.addAll(answers2);
//
//
//        while (dummyCount1 > 0) {
//
//            if (dummyAnswers1.contains(ans1a)) {
//                totalSingleMark = (float) totalSingleMark + singleMark1;
//                dummyAnswers1.remove(dummyAnswers1.indexOf(ans1a));
//            } else if (dummyAnswers1.contains(ans1b)) {
//                totalSingleMark = (float) totalSingleMark + singleMark1;
//                dummyAnswers1.remove(dummyAnswers1.indexOf(ans1b));
//            } else if (dummyAnswers1.contains(ans1c)) {
//                totalSingleMark = (float) totalSingleMark + singleMark1;
//                dummyAnswers1.remove(dummyAnswers1.indexOf(ans1c));
//            } else if (dummyAnswers1.contains(ans1d)) {
//                totalSingleMark = (float) totalSingleMark + singleMark1;
//
//                dummyAnswers1.remove(dummyAnswers1.indexOf(ans1d));
//            }
//
//            dummyCount1--;
//
//        }
//
//        while (dummyCount2 > 0) {
//
//            if (dummyAnswers2.contains(ans2a)) {
//                totalSingleMark = (float) totalSingleMark + singleMark2;
//                dummyAnswers2.remove(dummyAnswers2.indexOf(ans2a));
//            } else if (dummyAnswers2.contains(ans2b)) {
//                totalSingleMark = (float) totalSingleMark + singleMark2;
//                dummyAnswers2.remove(dummyAnswers2.indexOf(ans2b));
//            } else if (dummyAnswers2.contains(ans2c)) {
//                totalSingleMark = (float) totalSingleMark + singleMark2;
//                dummyAnswers2.remove(dummyAnswers2.indexOf(ans2c));
//            } else if (dummyAnswers2.contains(ans2d)) {
//                totalSingleMark = (float) totalSingleMark + singleMark2;
//                dummyAnswers2.remove(dummyAnswers2.indexOf(ans2d));
//            }
//
//            dummyCount2--;
//
//        }
//
//        //Log.e("count", totalSingleMark + " " + count2);
//
//        UserAnsFour userAnsFour = new UserAnsFour(ans1a, ans1b, ans1c, ans1d, ans2a, ans2b, ans2c, ans2d,
//                totalSingleMark);
//
//        RememberSingleton.getInstance().updateCheckedValueFour(MockupActivity.qsnNumberFOur, userAnsFour);
//
//
//
//
//    }


}
