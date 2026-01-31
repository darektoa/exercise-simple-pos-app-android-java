package com.example.simplepos.background.legacy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.simplepos.database.AppDatabase;
import com.example.simplepos.model.Order;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class ReportGenerationTask extends AsyncTask<Void, String, String> {

    private final WeakReference<Context> contextRef;

    public ReportGenerationTask(Context context) {
        this.contextRef = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = contextRef.get();
        if (context != null) {
            Toast.makeText(context, "Generating Report...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        publishProgress("Fetching data...");
        // Simulate heavy processing
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Context context = contextRef.get();
        if (context == null) return "Error";

        List<Order> orders = AppDatabase.getDatabase(context).orderDao().getAllOrdersList(); // We need a non-LiveData method

        double totalSales = 0;
        int totalOrders = orders.size();

        publishProgress("Calculating totals...");
        for (Order order : orders) {
            totalSales += order.getTotalPrice();
             // Simulate processing per item
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
        
        return String.format(Locale.getDefault(), "Total Sales: $%.2f\nTotal Orders: %d", totalSales, totalOrders);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Context context = contextRef.get();
        if (context != null && values.length > 0) {
            // Optional: Update a progress bar or text view if we had reference to one
             Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Context context = contextRef.get();
        if (context != null) {
             new android.app.AlertDialog.Builder(context)
                    .setTitle("Daily Sales Report")
                    .setMessage(result)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
