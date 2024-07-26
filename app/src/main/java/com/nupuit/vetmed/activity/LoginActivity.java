package com.nupuit.vetmed.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.nupuit.vetmed.fragment.LogInFragment;
import com.nupuit.vetmed.utils.SharedPrefsSingleton;

import static com.nupuit.vetmed.R.id.fragment_container;
import static com.nupuit.vetmed.R.layout.activity_login;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_login);

        getSupportFragmentManager().beginTransaction().replace(fragment_container, new LogInFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (SharedPrefsSingleton.getInstance(this).getBoolean("entered")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            finish();
        }
    }
}
