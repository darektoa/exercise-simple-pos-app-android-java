package com.example.simplepos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Map.Entry<Product, Integer>> cartItems;
    private OnItemRemoveListener listener;

    public interface OnItemRemoveListener {
        void onItemRemoved();
    }

    public CartAdapter(Map<Product, Integer> items, OnItemRemoveListener listener) {
        this.cartItems = new ArrayList<>(items.entrySet());
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Map.Entry<Product, Integer> entry = cartItems.get(position);
        Product product = entry.getKey();
        int quantity = entry.getValue();

        holder.name.setText(product.getName());
        holder.price.setText(com.example.simplepos.utils.CurrencyUtils.toRupiah(product.getPrice()));
        holder.qty.setText("Qty: " + quantity);
        // holder.total.setText(com.example.simplepos.utils.CurrencyUtils.toRupiah(product.getPrice() * quantity));
        holder.image.setImageResource(product.getImageResId() != 0 ? product.getImageResId() : android.R.drawable.ic_menu_gallery);

        holder.deleteButton.setOnClickListener(v -> {
            com.example.simplepos.model.Cart.getInstance().removeItem(product);
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            if (listener != null) listener.onItemRemoved();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, qty, total;
        android.widget.ImageView image;
        android.widget.ImageButton deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_item_name);
            price = itemView.findViewById(R.id.cart_item_price);
            qty = itemView.findViewById(R.id.cart_item_qty);
            // total = itemView.findViewById(R.id.cart_item_total);
            image = itemView.findViewById(R.id.cart_item_image);
            deleteButton = itemView.findViewById(R.id.cart_item_delete);
        }
    }
}
