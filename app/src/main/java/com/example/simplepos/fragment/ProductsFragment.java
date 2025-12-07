package com.example.simplepos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplepos.ProductAdapter;
import com.example.simplepos.R;
import com.example.simplepos.database.DatabaseHelper;
import com.example.simplepos.model.Product;

import java.util.List;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        loadProducts();
    }

    private void loadProducts() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        List<Product> productList = dbHelper.getAllProducts();
        adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);
    }
}
