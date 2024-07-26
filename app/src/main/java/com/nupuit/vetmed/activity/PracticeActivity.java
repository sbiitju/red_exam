package com.nupuit.vetmed.activity;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.fragment.PracticeFragment;
import com.nupuit.vetmed.fragment.PracticeResultFragment;
import com.nupuit.vetmed.model.MockupList;
import com.nupuit.vetmed.utils.Practice;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import java.util.Map;

public class PracticeActivity extends AppCompatActivity {

    CircleProgressBar circleProgressBar;

    public static int practiceCount;
    public static String totalQuestion;
    public static int one = 0;
    public static int two;
    public static int four;
    public static MockupList mockupList = new MockupList();
    private int progressStatus = 0;
    AdView mAdView;

    public static CardView header;
    public static String currentPage;
    private InterstitialAd interstitialAd;

//    public static InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        initializeViews();
        currentPage = "";

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


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PracticeFragment()).commit();

        long time = getIntent().getLongExtra("time", 0);
//        long time = 5;

        int progressBarMaximumValue = (int) (time);
        circleProgressBar.setMax(progressBarMaximumValue);
        circleProgressBar.setProgressTextFormatPattern(progressBarMaximumValue + "");

        new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Another one second passed
                circleProgressBar.setProgressTextFormatPattern(millisUntilFinished / 1000 / 60 + ":" + (millisUntilFinished / 1000) % 60);
                //Each second ProgressBar progress counter added one
                progressStatus += 1;
                circleProgressBar.setProgress(progressStatus);
            }

            public void onFinish() {
                //Do something when count down end.
                progressStatus += 1;
                circleProgressBar.setProgress(progressStatus);
                circleProgressBar.setProgressTextFormatPattern(0 + "");

                if (!PracticeActivity.currentPage.equals("p_result")) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PracticeActivity.this);
                    alertDialogBuilder.setTitle("Times up");
                    alertDialogBuilder.setMessage("You are out of time");
                    alertDialogBuilder.setPositiveButton("See Result",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    goToResults();
                                    showAd();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                    alertDialog.setOnDismissListener(
                            new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                            goToResults();
                            showAd();
                        }
                    });

                }


            }
        }.start();


    }

    private void initializeViews() {
        circleProgressBar = (CircleProgressBar) findViewById(R.id.circular_progress);
        header = (CardView) findViewById(R.id.header);
    }

    @Override
    public void onBackPressed() {
        if (interstitialAd == null)
         loadAd();

        if (currentPage.equals("p_result")) {
            Practice.getInstance().setListNull();
            practiceCount = 0;
            startActivity(new Intent(PracticeActivity.this, MainActivity.class));



            finish();
            showAd();


        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PracticeActivity.this);
            alertDialogBuilder.setTitle("Cancel Exam");
            alertDialogBuilder.setMessage("Do you want to quit?");
            alertDialogBuilder.setPositiveButton("Quit",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            goToResults();
                            showAd();
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
    }

    public static void hideHeader() {
        header.setVisibility(View.GONE);
    }



    public void showAd() {

        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
         //   Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
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
                        PracticeActivity.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
             //           Toast.makeText(PracticeActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        PracticeActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        PracticeActivity.this.interstitialAd = null;
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
          //              Toast.makeText(
           //                             PracticeActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
            //                    .show();
                    }
                });
    }


    public void  goToResults() {
        SharedPrefsSingleton.getInstance(getApplicationContext()).saveBoolean("mockMade", false);

        hideHeader();

        Bundle bundle = new Bundle();
        bundle.putInt("totalQsn", Integer.parseInt(totalQuestion));
        PracticeResultFragment practiceResultFragment = new PracticeResultFragment();
        practiceResultFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, practiceResultFragment).commit();
    }
}
