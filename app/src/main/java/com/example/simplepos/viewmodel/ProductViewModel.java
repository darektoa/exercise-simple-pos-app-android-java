package com.example.simplepos.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simplepos.database.ProductRepository;
import com.example.simplepos.model.Order;
import com.example.simplepos.model.Product;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private final LiveData<List<Product>> mAllProducts;
    private final LiveData<List<Order>> mAllOrders;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.getAllProducts();
        mAllOrders = mRepository.getAllOrders();
    }

    public LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }
    
    public LiveData<List<Order>> getAllOrders() {
        return mAllOrders;
    }

    public void insertOrder(Order order) {
        mRepository.insert(order);
    }
}
