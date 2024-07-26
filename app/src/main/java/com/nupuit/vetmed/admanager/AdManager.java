package com.nupuit.vetmed.admanager;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.nupuit.vetmed.R;

public class AdManager {

    private static volatile AdManager instance;
    private Activity activity;
    private InterstitialAd interstitialAd;
    private AdCallback adCallback;

    public interface AdCallback {
        void onAdShown();
        void onAdDismissed();
    }

    private AdManager(Activity activity) {
        this.activity = activity;
        loadAd();
    }

    public static AdManager getInstance(Activity activity) {
        if (instance == null) {
            synchronized (AdManager.class) {
                if (instance == null) {
                    instance = new AdManager(activity);
                }
            }
        } else {
            instance.activity = activity; // Update the activity reference
        }
        return instance;
    }

    public void setAdCallback(AdCallback adCallback) {
        this.adCallback = adCallback;
    }

    public void showAd() {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
            if (adCallback != null) {
                adCallback.onAdShown();
            }
            Log.d("AdManager", "The interstitial ad was shown.");
        } else {
            Log.d("AdManager", "The interstitial ad wasn't ready yet.");
            loadAd();
        }
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                activity,
                activity.getString(R.string.interstitial_ad_unit_id),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd1) {
                        interstitialAd = interstitialAd1;
                        Log.i("AdManager", "onAdLoaded");
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        interstitialAd = null;
                                        Log.d("AdManager", "The ad was dismissed.");
                                        if (adCallback != null) {
                                            adCallback.onAdDismissed();
                                        }
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        interstitialAd = null;
                                        Log.d("AdManager", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        Log.d("AdManager", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i("AdManager", loadAdError.getMessage());
                        interstitialAd = null;
                    }
                });
    }
}