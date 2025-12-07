package com.example.simplepos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.model.Cart;
import com.example.simplepos.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(com.example.simplepos.utils.CurrencyUtils.toRupiah(product.getPrice()));
        holder.description.setText(product.getDescription());
        holder.image.setImageResource(product.getImageResId() != 0 ? product.getImageResId() : android.R.drawable.ic_menu_gallery);

        holder.addToCart.setOnClickListener(v -> {
            Cart.getInstance().addItem(product, 1);
            com.google.android.material.snackbar.Snackbar snackbar = com.google.android.material.snackbar.Snackbar.make(v, "Added " + product.getName() + " to cart", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
            
            snackbar.setAction("Undo", view -> {
                Cart.getInstance().removeItem(product);
                com.google.android.material.snackbar.Snackbar.make(view, "Removed from cart", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                        .setAnchorView(((android.app.Activity) context).findViewById(R.id.bottom_navigation))
                        .show();
            });

            View bottomNav = ((android.app.Activity) context).findViewById(R.id.bottom_navigation);
            if (bottomNav != null) {
                snackbar.setAnchorView(bottomNav);
            }
            
            snackbar.show();
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description;
        android.widget.ImageView image;
        android.widget.ImageButton addToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_product_name);
            price = itemView.findViewById(R.id.text_product_price);
            description = itemView.findViewById(R.id.text_product_desc);
            image = itemView.findViewById(R.id.image_product);
            addToCart = itemView.findViewById(R.id.button_add_to_cart);
        }
    }
}
