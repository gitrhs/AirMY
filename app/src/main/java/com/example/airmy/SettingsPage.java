package com.example.airmy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);


//
//        // Bottom nav bar
//        final ImageView home = findViewById(R.id.homePageSettings);
//        final ImageView news = findViewById(R.id.NewsSettings);
//        final ImageView settings = findViewById(R.id.SettingSettings);
//
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsPage.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
//                startActivity(intent);
//
//            }
//        });
//        news.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsPage.this, NewsPage.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
//
//                startActivity(intent);
//            }
//        });
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingsPage.this, SettingsPage.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); // remove animation
//
//                startActivity(intent);
//            }
//        });
    }
}