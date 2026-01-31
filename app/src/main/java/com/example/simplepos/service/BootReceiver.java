package com.example.simplepos.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("BootReceiver", "Boot completed received");
            Toast.makeText(context, "SimplePOS: Boot Completed", Toast.LENGTH_LONG).show();
            // Could schedule jobs or alarms here
        }
    }
}
