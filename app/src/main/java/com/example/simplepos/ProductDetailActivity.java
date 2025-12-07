package com.example.simplepos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simplepos.model.Cart;
import com.example.simplepos.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product product = (Product) getIntent().getSerializableExtra("product");

        if (product == null) {
            finish();
            return;
        }

        TextView name = findViewById(R.id.detail_name);
        TextView price = findViewById(R.id.detail_price);
        TextView description = findViewById(R.id.detail_description);
        Button addToCart = findViewById(R.id.detail_add_to_cart);

        name.setText(product.getName());
        price.setText(com.example.simplepos.utils.CurrencyUtils.toRupiah(product.getPrice()));
        description.setText(product.getDescription());

        addToCart.setOnClickListener(v -> {
            Cart.getInstance().addItem(product, 1);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
