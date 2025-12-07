package com.example.simplepos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.CartAdapter;
import com.example.simplepos.CheckoutActivity;
import com.example.simplepos.R;
import com.example.simplepos.model.Cart;
import com.example.simplepos.utils.CurrencyUtils;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private TextView totalText;
    private TextView emptyText;
    private Button checkoutButton;
    private LinearLayout checkoutLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recycler_cart);
        totalText = view.findViewById(R.id.text_total_price);
        emptyText = view.findViewById(R.id.text_cart_empty);
        checkoutButton = view.findViewById(R.id.button_checkout);
        checkoutLayout = view.findViewById(R.id.layout_checkout);

        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CheckoutActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        Cart cart = Cart.getInstance();
        if (cart.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            checkoutLayout.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            checkoutLayout.setVisibility(View.VISIBLE);
            
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new CartAdapter(cart.getCartItems(), () -> {
                // Refresh UI when item removed
                totalText.setText("Total: " + CurrencyUtils.toRupiah(cart.getTotalPrice()));
                if (cart.getItemCount() == 0) {
                    updateUI(); // Show empty state
                }
            });
            recyclerView.setAdapter(adapter);
            
            totalText.setText("Total: " + CurrencyUtils.toRupiah(cart.getTotalPrice()));
        }
    }
}
