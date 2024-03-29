package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import Fragments.FragmentLogin;
import Fragments.FragmentRegisteration;

public class WelcomePage extends AppCompatActivity {
    public static final String PREF_NAME = "AirMY_SDGHeroes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);



        Button loginBtn = findViewById(R.id.welcome_log);
        Button registerBtn = findViewById(R.id.welcome_reg);
        RelativeLayout welcomeRelative = findViewById(R.id.welcomeRelativeLayout);

        SharedPreferences prefs; // declare the sharedPreference
        boolean value = false; // default value if no value was found
        String key = "key"; // use this key to retrieve the value
        String sharedPrefName = "isMySwitchChecked"; // name of your sharedPreference


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
    public void saveData(String name, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public String getData(String name, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defaultValue);
    }

}

