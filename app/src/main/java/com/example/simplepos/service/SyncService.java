package com.example.simplepos.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SyncService extends Service {
    
    private static final String TAG = "SyncService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "SyncService started");
        Toast.makeText(this, "Sync Service Started", Toast.LENGTH_SHORT).show();
        
        // Simulate background work in a separate thread to avoid blocking UI
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Log.d(TAG, "Service background work finished");
                // Stop service after work is done
                stopSelf(); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Not binding
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "SyncService destroyed");
        Toast.makeText(this, "Sync Service Stopped", Toast.LENGTH_SHORT).show();
    }
}
