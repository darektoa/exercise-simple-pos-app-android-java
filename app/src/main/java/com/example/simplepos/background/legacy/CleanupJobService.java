package com.example.simplepos.background.legacy;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

import com.example.simplepos.database.AppDatabase;

import java.util.concurrent.Executors;

public class CleanupJobService extends JobService {
    private static final String TAG = "CleanupJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Cleanup job started");
        
        // Run cleanup in background
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Simulate database cleanup
                Thread.sleep(3000);
                Log.d(TAG, "Cleanup finished");
                
                // Show toast on main thread
                // Note: Toasts from background services/jobs might not always show if app is in background, 
                // but strictly for demo purposes:
                // android.os.Handler handler = new android.os.Handler(android.os.Looper.getMainLooper());
                // handler.post(() -> Toast.makeText(getApplicationContext(), "Nightly Cleanup Done", Toast.LENGTH_SHORT).show());

                jobFinished(params, false); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        return true; // Work is still running in background
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Cleanup job stopped before completion");
        return true; // Reschedule
    }
}
