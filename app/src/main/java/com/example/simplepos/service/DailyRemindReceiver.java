package com.example.simplepos.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.simplepos.R;

public class DailyRemindReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DailyRemindReceiver", "Daily reminder received");
        Toast.makeText(context, "Daily Reminder: Check your sales!", Toast.LENGTH_LONG).show();
        
        // In a real app, we would show a Notification here using NotificationManager
    }
}
