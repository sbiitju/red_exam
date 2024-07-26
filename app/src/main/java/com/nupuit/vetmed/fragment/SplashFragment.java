package com.nupuit.vetmed.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nupuit.vetmed.R;
import com.nupuit.vetmed.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    public SplashFragment() {
        // Required empty public constructor
    }

    Animation pulse;
    Animation pulse1;
    ImageView imageView;
    LinearLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        parent = (LinearLayout) view.findViewById(R.id.parent);

        pulse = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        pulse1 = AnimationUtils.loadAnimation(getContext(), R.anim.pulse);
        imageView.startAnimation(pulse1);


        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.setVisibility(View.VISIBLE);
                parent.startAnimation(pulse);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        Animation.AnimationListener animationListener1 = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
              //  if (user != null) {
                    // User is signed in
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
//                } else {
//                    // No user is signed in
//
//                    if (SharedPrefsSingleton.getInstance(getActivity()).getBoolean("skipped") || SharedPrefsSingleton.getInstance(getActivity()).getBoolean("manual_signout")) {
//                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
//                        getActivity().finish();
//                    } else {
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LogInFragment()).commit();
//
//                    }
//                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        pulse1.setAnimationListener(animationListener);
        pulse.setAnimationListener(animationListener1);

        return view;
    }

}
