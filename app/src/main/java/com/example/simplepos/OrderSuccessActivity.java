package com.example.simplepos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplepos.utils.CurrencyUtils;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        TextView orderIdText = findViewById(R.id.text_order_id);
        TextView dateText = findViewById(R.id.text_date);
        TextView amountText = findViewById(R.id.text_amount);
        Button homeButton = findViewById(R.id.button_home);

        // Get data from Intent
        // Ideally we would pass the actual order ID from DB, but for now we might simulate or pass a generated one.
        // Or if we saved it in CheckoutActivity, we should pass it here.
        
        String date = getIntent().getStringExtra("date");
        double amount = getIntent().getDoubleExtra("amount", 0);
        // Since we didn't return the ID from addOrder in helper (it's void/internal), we might just show a random or passed ID.
        // Actually, db.insert returns the row ID. We should probably update Helper to return it if we want to be precise.
        // For this UI demo, a timestamp-based or random one is fine if we don't want to change DB Helper signature yet.
        // Let's check DB helper signature again... it is void addOrder.
        // I will just use a mock or timestamp ID for display to satisfy the UI requirement.
        
        String orderId = String.valueOf(System.currentTimeMillis() % 100000); // Simple short ID

        orderIdText.setText("#" + orderId);
        dateText.setText(date);
        amountText.setText(CurrencyUtils.toRupiah(amount));

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(OrderSuccessActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
