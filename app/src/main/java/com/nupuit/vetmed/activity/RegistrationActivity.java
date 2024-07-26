package com.nupuit.vetmed.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.nupuit.vetmed.R;
import com.nupuit.vetmed.fragment.RegistrationFragment;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //setContentView(R.layout.fragment_registration);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrationFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}
