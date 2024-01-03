package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import Fragments.SettingsPageFragment;

public class SettingsPage extends AppCompatActivity {

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

//        healthRec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, UserProfileAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });
    }

    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}