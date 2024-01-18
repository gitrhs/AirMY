package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Fragments.SettingsPageFragment;

public class SettingsPage extends AppCompatActivity {
    public static final String PREF_NAME = "AirMY_SDGHeroes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        //add SettingsPageFragment to fragment container
        addFragment(new SettingsPageFragment());

        // Bottom nav bar
        final ImageView home = findViewById(R.id.homePage);
        final ImageView news = findViewById(R.id.newsPage);
        final ImageView healthRec = findViewById(R.id.healthRecPage);
        final ImageView userPage = findViewById(R.id.userPage);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, NewsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });

        healthRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, HealthRecPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, UserProfileAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });

        String userLocation = getData("user_location", "Kuala Lumpur");
        // Update the TextView with the user_location
        TextView locationTextView = findViewById(R.id.locationTextId);
        locationTextView.setText(userLocation);
    }
    public String getData(String name, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defaultValue);
    }
    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}