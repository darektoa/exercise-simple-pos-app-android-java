package com.example.simplepos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.model.Cart;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalText;
    private TextView emptyText;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_cart);
        totalText = findViewById(R.id.text_total_price);
        emptyText = findViewById(R.id.text_cart_empty);
        checkoutButton = findViewById(R.id.button_checkout);

        updateUI();

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        Cart cart = Cart.getInstance();
        if (cart.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            checkoutButton.setEnabled(false);
            java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));
            totalText.setText("Total: " + format.format(0));
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            checkoutButton.setEnabled(true);
            
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CartAdapter(cart.getCartItems(), () -> {
                 // Refresh UI when item removed
                 com.example.simplepos.utils.CurrencyUtils utils = new com.example.simplepos.utils.CurrencyUtils();
                 totalText.setText("Total: " + com.example.simplepos.utils.CurrencyUtils.toRupiah(cart.getTotalPrice()));
                 if (cart.getItemCount() == 0) {
                     updateUI();
                 }
            });
            recyclerView.setAdapter(adapter);
            
            totalText.setText("Total: " + com.example.simplepos.utils.CurrencyUtils.toRupiah(cart.getTotalPrice()));
        }
    }
}
