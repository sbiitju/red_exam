package com.nupuit.vetmed.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.admanager.AdManager;
import com.nupuit.vetmed.fragment.HistoryFragment;
import com.nupuit.vetmed.fragment.MainFragment;
import com.nupuit.vetmed.model.TwoMarkQuestions;
import com.nupuit.vetmed.utils.DataFromFirebase;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainActivity extends AppCompatActivity {

    Realm realm;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    public static String currentPage;
//    public static InterstitialAd mInterstitialAd;

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        AdManager adManager = AdManager.getInstance(this);
        adManager.loadAd();
        SharedPrefsSingleton.getInstance(this).saveBoolean("entered", true);
        SharedPrefsSingleton.getInstance(this).saveBoolean("mockMade", false);


        mAdView = findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
////        mAdView.loadAd(adRequest);

  //      mAdView.setAdListener(new AdListener() {
 //           @Override
 //           public void onAdFailedToLoad(int i) {
 //               super.onAdFailedToLoad(i);
  //              mAdView.setVisibility(View.GONE);
 //           }
 //       });

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

//                loadAd();
            }
        });

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });



 //       mInterstitialAd = new InterstitialAd(this);
 //       mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
 //       mInterstitialAd.loadAd(new AdRequest.Builder().build());

 //       mInterstitialAd.setAdListener(new AdListener() {
 //           @Override
 //           public void onAdClosed() {
 //               mInterstitialAd.loadAd(new AdRequest.Builder().build());
 //           }

 //       });

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();

        if (!isRealmEmpty()) {
            //Toast.makeText(this, "Database Exists", Toast.LENGTH_SHORT).show();
        } else {
                DataFromFirebase dataFromFirebase = new DataFromFirebase(MainActivity.this);
                dataFromFirebase.getDataFromFirebase();

         //   }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();

    }

    private boolean isRealmEmpty() {
        boolean empty = false;
        if (realm.where(TwoMarkQuestions.class).count() == 0) {
            empty = true;
        }
        return empty;
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                (getResources().getString(R.string.interstitial_ad_unit_id)),
                adRequest,
                new InterstitialAdLoadCallback() {

                });
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                if (currentPage.equals("historyfragment")){
//                    getSupportFragmentManager().popBackStack();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();

                } if (currentPage.equals("historyfragmentDetails")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).commit();
                }else {
                    getSupportFragmentManager().popBackStack();
                 //   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                }

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Exit");
                alertDialogBuilder.setMessage("Do you want to exit?");
                alertDialogBuilder.setPositiveButton("Exit",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finishAffinity();
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

    }
//    public static void showAd() {
//        if (mInterstitialAd.isLoaded()) {
 //           mInterstitialAd.show();
//        } else {
//            mInterstitialAd.loadAd(new AdRequest.Builder().build());
//        }
 //   }

}
