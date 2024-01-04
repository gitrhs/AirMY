package com.example.airmy;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    // notification generator
    public void generateNotification(String title, String desc)
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pend = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),getString(R.string.channel_id))
                .setSmallIcon(R.drawable.app_logo)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(pend)
                .setContent(getRemoteView(title,desc));

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notifChannel = new NotificationChannel(getString(R.string.channel_id), getString(R.string.channel_name),NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notifChannel);
        }

        manager.notify(0,builder.build());


    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        generateNotification(message.getNotification().getTitle(),message.getNotification().getBody());
    }

    public RemoteViews getRemoteView(String title, String desc)
    {
        @SuppressLint("RemoteViewLayout") RemoteViews remote = new RemoteViews("com.example.airmy", R.layout.notification_layout);
        remote.setTextViewText(R.id.notifTitle, title);
        remote.setTextViewText(R.id.notifDesc, desc);
        remote.setImageViewResource(R.id.app_logo,R.drawable.app_logo);

        return remote;
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}