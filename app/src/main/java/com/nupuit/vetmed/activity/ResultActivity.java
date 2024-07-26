package com.nupuit.vetmed.activity;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.nupuit.vetmed.fragment.ResultFragment;
import com.nupuit.vetmed.model.MarkQuestion;
import com.nupuit.vetmed.model.MockupHistory;
import com.nupuit.vetmed.model.MockupModel;
import com.nupuit.vetmed.utils.RememberSingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import io.realm.Realm;

public class ResultActivity extends AppCompatActivity {

    public static String totalQuestion;
    public static String time_taken;
    public static int mark;
    String id;
    AdView mAdView;
    Realm realm;

    public static int skipped = 0;
    public static int wrong = 0;
    public static int correct = 0;
    public static float markFour = 0;

    public static InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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


        Realm.init(this);
        realm = Realm.getDefaultInstance();

//        mAdView = (AdView) findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

//        mAdView.setAdListener(
 //               new AdListener() {
//            @Override
 //           public void onAdFailedToLoad(int i) {
 //               super.onAdFailedToLoad(i);
//                mAdView.setVisibility(View.GONE);
 //           }
 //       });

        int mark10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[0]);
        int qsns10 = Integer.parseInt(RememberSingleton.getInstance().getResultFour().split(":")[1]);

//        skipped = Integer.parseInt(resultFour[0]);
//        wrong = Integer.parseInt(resultFour[1]);
//        correct = Integer.parseInt(resultFour[2]);
     //   markFour = Float.parseFloat(resultFour[0]);




        totalQuestion = getIntent().getStringExtra("total_questions");
        time_taken = getIntent().getStringExtra("time_taken");
        mark = getIntent().getIntExtra("mark", 0);
        id = getIntent().getStringExtra("id");

        if (id != null && !id.equals("-1")) {


            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm aa");
            String date = df.format(java.util.Calendar.getInstance().getTime());

            MarkQuestion correctResult = RememberSingleton.getInstance().getResult(5);

            float totalMark = Integer.parseInt(correctResult.getMark()) + mark10;
            int correctQsn = Integer.parseInt(correctResult.getQuestion()) + correct;
            int actualMark = mark;
            float percent = ((float) totalMark / (float) actualMark) * 100;
            String result;
            if (percent >= 50f) {
                result = "Passed";
            } else {
                result = "Failed";
            }

            final MockupHistory mockupHistory = new MockupHistory();
            mockupHistory.setMockupName("Mockup " + String.valueOf(Integer.parseInt(id) + 1));
            mockupHistory.setDate(date);
            mockupHistory.setTotalQsn(totalQuestion);
            mockupHistory.setCorrectQsn(String.valueOf(correctQsn));
            mockupHistory.setPercentage(String.valueOf(percent));
            mockupHistory.setResult(result);
            mockupHistory.setTime(time_taken + " " + "min");
            mockupHistory.setId(id);

            realm.executeTransaction(
                    new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    realm.where(MockupModel.class).equalTo("id", id).findFirst().setLock(false);
                    realm.copyToRealm(mockupHistory);
//                    MockupModelFirebase mockupModelFirebase = new MockupModelFirebase("Mockup " + id, "120 min", "100", id + "", false, "52");

//                    DataFromFirebase dataFromFirebase = new DataFromFirebase(ResultActivity.this);
//                    dataFromFirebase.updateToFirebase(id);

                }
            });
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ResultFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultActivity.this, MainActivity.class));
        finish();
        showAd();
    }

    public void showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
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
                        ResultActivity.this.mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //           Toast.makeText(PracticeActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ResultActivity.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        ResultActivity.this.mInterstitialAd = null;
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
                        mInterstitialAd = null;

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
}
