package com.example.simplepos.service;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.simplepos.MainActivity;
import com.example.simplepos.R;

public class ClosingReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ClosingReminder", "Closing time reminder received");

        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE);

        // Create Notification Channel for Android 8+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(
                    "default_channel",
                    "Daily Reminders",
                    android.app.NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for closing time reminders");
            context.getSystemService(android.app.NotificationManager.class).createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default_channel")
                .setSmallIcon(R.drawable.ic_app_logo)
                .setContentTitle("Closing Time")
                .setContentText("Don't forget to close the register and generate daily report!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
             notificationManager.notify(200, builder.build());
        }
    }
}
