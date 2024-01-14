package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Fragments.HealthRecFragment;

public class HealthRecPage extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "AirMY_SDGHeroes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_rec_page);
        sharedPreferences = getSharedPreferences("json_data", MODE_PRIVATE);
        //add HealthRecFragment to fragment container
        addFragment(new HealthRecFragment());

        ImageView settingsIcon = findViewById(R.id.settingicon);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthRecPage.this, SettingsPage.class);
                startActivity(intent);
            }
        });

        // Bottom nav bar
        final ImageView home = findViewById(R.id.homePage);
        final ImageView news = findViewById(R.id.newsPage);
        final ImageView healthRec = findViewById(R.id.healthRecPage);
        final ImageView userPage = findViewById(R.id.userPage);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthRecPage.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthRecPage.this, NewsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });
        healthRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthRecPage.this, HealthRecPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });
        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthRecPage.this, UserProfileAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);
            }
        });

        String userLocation = getData("user_location", "Kuala Lumpur");
        // Update the TextView with the user_location
        TextView locationTextView = findViewById(R.id.locationTextId);
        locationTextView.setText(userLocation);
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
    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    public void openUrlInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}