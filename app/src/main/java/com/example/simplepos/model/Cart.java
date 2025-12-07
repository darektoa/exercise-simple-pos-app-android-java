package com.example.simplepos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private static Cart instance;
    private Map<String, Integer> items; // Product ID -> Quantity
    private Map<String, Product> products; // Cache of products in cart

    private Cart() {
        items = new HashMap<>();
        products = new HashMap<>();
    }

    public static synchronized Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(Product product, int quantity) {
        int currentQty = items.getOrDefault(product.getId(), 0);
        items.put(product.getId(), currentQty + quantity);
        products.put(product.getId(), product);
    }

    public void removeItem(Product product) {
        items.remove(product.getId());
        products.remove(product.getId());
    }

    public void clear() {
        items.clear();
        products.clear();
    }

    public Map<Product, Integer> getCartItems() {
        Map<Product, Integer> cartItems = new HashMap<>();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            cartItems.put(products.get(entry.getKey()), entry.getValue());
        }
        return cartItems;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            Product p = products.get(entry.getKey());
            if (p != null) {
                total += p.getPrice() * entry.getValue();
            }
        }
        return total;
    }
    
    public int getItemCount() {
        int count = 0;
        for (int qty : items.values()) {
            count += qty;
        }
        return count;
    }
}
