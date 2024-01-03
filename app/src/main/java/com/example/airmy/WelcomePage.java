package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import Fragments.FragmentLogin;
import Fragments.FragmentRegisteration;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);



        Button loginBtn = findViewById(R.id.welcome_log);
        Button registerBtn = findViewById(R.id.welcome_reg);
        RelativeLayout welcomeRelative = findViewById(R.id.welcomeRelativeLayout);



        // Clicks
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.welcomePageFragment, FragmentLogin.class, null).addToBackStack(null)
                            .commit();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.welcomePageFragment, FragmentRegisteration.class, null).addToBackStack(null)
                            .commit();
                }
            }
        });



    }
}


