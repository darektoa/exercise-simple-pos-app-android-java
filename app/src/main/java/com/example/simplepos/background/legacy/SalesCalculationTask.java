package com.example.simplepos.background.legacy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Legacy AsyncTask implementation to simulate heavy background calculation.
 * Deprecated but implemented for requirement demonstration.
 */
@SuppressWarnings("deprecation")
public class SalesCalculationTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<Context> contextRef;

    public SalesCalculationTask(Context context) {
        this.contextRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = contextRef.get();
        if (context != null) {
            Toast.makeText(context, "Starting heavy calculation...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        // Simulate heavy work
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                publishProgress((i + 1) * 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Calculation Completed!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d("SalesCalculationTask", "Progress: " + values[0] + "%");
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Context context = contextRef.get();
        if (context != null) {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}
