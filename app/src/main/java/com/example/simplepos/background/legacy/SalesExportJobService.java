package com.example.simplepos.background.legacy;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * Legacy JobScheduler implementation.
 */
public class SalesExportJobService extends JobService {
    private static final String TAG = "SalesExportJobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started: Exporting sales data...");
        // Simulate background work
        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate network or I/O
                Log.d(TAG, "Sales data exported successfully.");
                jobFinished(params, false); // false = no reschedule
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return true; // true = work is still running in background
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job stopped before completion.");
        return true; // true = reschedule needed
    }
}
