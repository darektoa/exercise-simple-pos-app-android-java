package com.example.simplepos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new com.example.simplepos.fragment.ProductsFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            androidx.fragment.app.Fragment selectedFragment = null;
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_products) {
                selectedFragment = new com.example.simplepos.fragment.ProductsFragment();
            } else if (itemId == R.id.navigation_cart) {
                selectedFragment = new com.example.simplepos.fragment.CartFragment();
            } else if (itemId == R.id.navigation_history) {
                selectedFragment = new com.example.simplepos.fragment.HistoryFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
