package com.example.simplepos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplepos.model.Cart;
import com.example.simplepos.model.Product;

import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        TextView orderDetails = findViewById(R.id.text_order_details);
        Button confirmButton = findViewById(R.id.button_confirm_order);

        com.example.simplepos.model.Cart cart = com.example.simplepos.model.Cart.getInstance();
        StringBuilder summary = new StringBuilder();
        
        for (java.util.Map.Entry<com.example.simplepos.model.Product, Integer> entry : cart.getCartItems().entrySet()) {
            summary.append(entry.getKey().getName())
                   .append(" x ")
                   .append(entry.getValue())
                   .append(" = ")
                   .append(com.example.simplepos.utils.CurrencyUtils.toRupiah(entry.getKey().getPrice() * entry.getValue()))
                   .append("\n");
        }
        
        summary.append("\nTotal: ").append(com.example.simplepos.utils.CurrencyUtils.toRupiah(cart.getTotalPrice()));
        
        orderDetails.setText(summary.toString());

        confirmButton.setOnClickListener(v -> {
            // Save Order Logic
            com.example.simplepos.database.DatabaseHelper dbHelper = new com.example.simplepos.database.DatabaseHelper(this);
            String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());
            
            // Generate items summary
            StringBuilder itemsSummary = new StringBuilder();
            for (java.util.Map.Entry<com.example.simplepos.model.Product, Integer> entry : cart.getCartItems().entrySet()) {
                itemsSummary.append(entry.getKey().getName())
                       .append(" (x").append(entry.getValue()).append("), ");
            }
            if (itemsSummary.length() > 0) itemsSummary.setLength(itemsSummary.length() - 2); // Remove last comma
            
            com.example.simplepos.model.Order newOrder = new com.example.simplepos.model.Order(currentDate, cart.getTotalPrice(), itemsSummary.toString());
            dbHelper.addOrder(newOrder);

            double totalAmount = cart.getTotalPrice(); // Capture before clearing
            cart.clear();
            
            // Navigate to Success Screen
            Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
            intent.putExtra("date", currentDate);
            intent.putExtra("amount", totalAmount);
            startActivity(intent);
            finish(); 
        });
    }
}
