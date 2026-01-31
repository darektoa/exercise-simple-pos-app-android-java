package com.example.simplepos.background.modern;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SyncWorker extends Worker {

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Simulate efficient data transfer / sync
        Log.d("SyncWorker", "Starting data sync...");
        try {
            Thread.sleep(2000); // Simulate network call
            Log.d("SyncWorker", "Data sync completed successfully.");
            return Result.success();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.retry();
        }
    }
}
