package com.nupuit.vetmed.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nupuit.vetmed.R;

public class SplashActivity extends AppCompatActivity {

    Animation pulse;
    Animation pulse1;
    ImageView imageView;
    LinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = (ImageView) findViewById(R.id.image);
        parent = (LinearLayout) findViewById(R.id.parent);

        pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        pulse1 = AnimationUtils.loadAnimation(this, R.anim.pulse);
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
             //   if (user != null) {
                    // User is signed in
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
//                } else {
//                    // No user is signed in
//
//                    if (SharedPrefsSingleton.getInstance(SplashActivity.this).getBoolean("skipped") || SharedPrefsSingleton.getInstance(SplashActivity.this).getBoolean("manual_signout")) {
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                        finish();
//                    } else {
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                        finish();
//                    }
//                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        pulse1.setAnimationListener(animationListener);
        pulse.setAnimationListener(animationListener1);


    }
}
