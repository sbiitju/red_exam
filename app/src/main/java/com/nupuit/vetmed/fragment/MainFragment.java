package com.nupuit.vetmed.fragment;


import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;
import com.nupuit.vetmed.activity.PracticeActivity;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {


    public MainFragment() {
        // Required empty public constructor
    }

    LinearLayout signout_button;
    LinearLayout share_button;
    LinearLayout rate_button;
    LinearLayout pro_button;
    CardView practice_button;
    CardView mockup_button;
    CardView quick_mockup_button;
    CardView tips_button;
    CardView history_button;
    LinearLayout about_button;
    TextView login_status;
    InterstitialAd interstitialAd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        MainActivity.currentPage = "menu";

        practice_button = (CardView) view.findViewById(R.id.practice);
        mockup_button = (CardView) view.findViewById(R.id.mockup);
        quick_mockup_button = (CardView) view.findViewById(R.id.quick_mock);
        signout_button = (LinearLayout) view.findViewById(R.id.sign_out);
        tips_button = (CardView) view.findViewById(R.id.tips);
        about_button = (LinearLayout) view.findViewById(R.id.about_us);
        share_button = (LinearLayout) view.findViewById(R.id.share);
        rate_button = (LinearLayout) view.findViewById(R.id.rate_us_us);
        pro_button = (LinearLayout) view.findViewById(R.id.pro_pro);
        history_button = (CardView) view.findViewById(R.id.history);
        login_status = (TextView) view.findViewById(R.id.login_status);


        practice_button.setOnClickListener(this);
        mockup_button.setOnClickListener(this);
        signout_button.setOnClickListener(this);
        tips_button.setOnClickListener(this);
        about_button.setOnClickListener(this);
        history_button.setOnClickListener(this);
        quick_mockup_button.setOnClickListener(this);
        share_button.setOnClickListener(this);
        rate_button.setOnClickListener (this);
        pro_button.setOnClickListener (this);
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
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.practice:
                if (isNetworkAvailable()) {
                    if (SharedPrefsSingleton.getInstance(getContext()).getBoolean("registered")) {
                        QuickMockPractiseFragment mFragment = new QuickMockPractiseFragment();
                        Bundle mBundle = new Bundle();
                        mBundle.putString("type", "0");
                        mFragment.setArguments(mBundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).addToBackStack(null).commit();
                    } else {
                        QuickMockPractiseFragment mFragment = new QuickMockPractiseFragment();
                        Bundle mBundle = new Bundle();
                        mBundle.putString("type", "0");
                        mFragment.setArguments(mBundle);
                        worksOnAuth(mFragment);
                    }
                } else {
                    showNoNetworkError();
                }
                break;
            case R.id.mockup:
                if (isNetworkAvailable()) {
                    if (SharedPrefsSingleton.getInstance(getContext()).getBoolean("registered")) {

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MockupFragment()).addToBackStack(null).commit();
                    } else {
                        worksOnAuth(new MockupFragment());
                    }


                } else {
                    showNoNetworkError();
                }
                break;
            case R.id.share:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Try This Awesome App") ;
                String sAux = "Get This Awesome App from\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "\n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Share With"));

                break;


            case R.id.rate_us_us:
                startActivity(new Intent (Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));

                break;

            case R.id.pro_pro:
                startActivity(new Intent (Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.pro_link))));

                break;

            case R.id.quick_mock:
                if (isNetworkAvailable()) {
                    if (SharedPrefsSingleton.getInstance(getContext()).getBoolean("registered")) {
                        QuickMockPractiseFragment mFragment1 = new QuickMockPractiseFragment();
                        Bundle mBundle1 = new Bundle();
                        mBundle1.putString("type", "1");
                        mFragment1.setArguments(mBundle1);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment1).addToBackStack(null).commit();
                    } else {
                        QuickMockPractiseFragment mFragment1 = new QuickMockPractiseFragment();
                        Bundle mBundle1 = new Bundle();
                        mBundle1.putString("type", "1");
                        mFragment1.setArguments(mBundle1);
                        worksOnAuth(mFragment1);                    }

                } else {
                    showNoNetworkError();
                }

                break;
            case R.id.history:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoryFragment()).addToBackStack(null).commit();
                break;
            case R.id.tips:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TipsFragment()).addToBackStack(null).commit();
                break;
            case R.id.about_us:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).addToBackStack(null).commit();
                break;
//            case R.id.sign_out:
//                if (isLoggedIn()) {
//                    FirebaseAuth.getInstance().signOut();
//                    SharedPrefsSingleton.getInstance(getContext()).saveBoolean("skipped", false);
//                    SharedPrefsSingleton.getInstance(getContext()).saveBoolean("manual_signout", true);
//
//                    login_status.setText("Sign In");
//                    Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
//                } else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                    getActivity().finish();
//                }
//                break;
        }
    }
    public void showAd() {

        if (interstitialAd != null) {
            interstitialAd.show(getActivity());
        } else {
            //   Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            loadAd();
        }
    }
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getActivity(),
                getString(R.string.interstitial_ad_unit_id),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd1) {
                        interstitialAd = interstitialAd1;
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        Log.i(TAG, "onAdLoaded");
                        //           Toast.makeText(PracticeActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        interstitialAd = null;
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        interstitialAd = null;
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
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

    private boolean isLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showNoNetworkError() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Internet Required");
        alertDialogBuilder.setMessage("Please connect to the internet and try again.");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

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

    private void worksOnAuth(final Fragment fragment) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_log_in, null);

        final Button btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
        final EditText edt_email = (EditText) view.findViewById(R.id.edt_email);
        Button skip_now = (Button) view.findViewById(R.id.skip);

        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edt_email.getText().toString();

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edt_email.setError("Enter a valid email");
                } else {

                    ProgressDialog loading;

                    loading = new ProgressDialog(getContext());
                    loading.setCancelable(false);
                    loading.setMessage("Signing in...");
                    loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    loading.show();

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    mDatabase.push().setValue(email);

                    loading.dismiss();

                    SharedPrefsSingleton.getInstance(getContext()).saveBoolean("registered", true);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                    alertDialog.dismiss();

                }

            }
        });

        skip_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefsSingleton.getInstance(getContext()).saveBoolean("skipped", true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                alertDialog.dismiss();
            }
        });


    }
}
