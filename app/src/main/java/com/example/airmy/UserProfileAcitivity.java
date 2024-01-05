package com.example.airmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import Fragments.FragmentRegisteration;
import Fragments.UserEditProfile;
import com.example.airmy.WelcomePage;

public class UserProfileAcitivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager2 viewPage;
    ViewPageSwitcher3 switcherViewPage;
    private SharedPreferences sharedPreferences;

    //Storage File Name
    public static final String PREF_NAME = "AirMY_SDGHeroes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_acitivity);

        // Bottom nav bar
        final ImageView home = findViewById(R.id.homePage);
        final ImageView news = findViewById(R.id.newsPage);
        final ImageView healthRec = findViewById(R.id.healthRecPage);
        final ImageView userPage = findViewById(R.id.userPage);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileAcitivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
                startActivity(intent);

            }
        });
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileAcitivity.this, NewsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });
        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileAcitivity.this, UserProfileAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });

        healthRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileAcitivity.this, HealthRecPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });
        userPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileAcitivity.this, UserProfileAcitivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation

                startActivity(intent);
            }
        });

        ImageView settingsIcon = findViewById(R.id.settingicon);
        settingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileAcitivity.this, SettingsPage.class);
                startActivity(intent);
            }
        });


        Button editProfile = findViewById(R.id.userEditProfile);
        String sessionID = getData("sessionID", "");
        Button userLogout = findViewById(R.id.userLogout);
        if (!sessionID.isEmpty()){
            editProfile.setText("Edit Profile");
            //change the user name
            TextView userName = findViewById(R.id.userName);
            userName.setText(getData("loginUsername", "User"));
        } else {
            userLogout.setVisibility(View.GONE);
        }
        //change the location
        TextView userLocation = findViewById(R.id.userLocation);
        userLocation.setText(" " + getData("user_location", "Kuala Lumpur"));




        userLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteData("sessionID");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get session data
                if (sessionID.isEmpty()){
                    //go to activity_welcome_page.xml
                    Intent intent = new Intent(UserProfileAcitivity.this, WelcomePage.class);
                    startActivity(intent);
                } else {
                    //change the textview to edit profile
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.editProfileContainer,  UserEditProfile.class, null).addToBackStack(null)
                            .commit();
                }
            }
        });


        // Tab switching ( latest and this week )
        tab = findViewById(R.id.tabSwitcher);
        viewPage = findViewById(R.id.viewPageSwitcher3);
        switcherViewPage = new ViewPageSwitcher3(this);
        viewPage.setAdapter(switcherViewPage);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // for when pulling manually the tab changes with the view.
        viewPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tab.getTabAt(position).select();
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

    public void deleteData(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(name);
        editor.apply();
    }
}