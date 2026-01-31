package com.example.simplepos.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.simplepos.database.dao.OrderDao;
import com.example.simplepos.database.dao.ProductDao;
import com.example.simplepos.model.Order;
import com.example.simplepos.model.Product;

import java.util.List;

public class ProductRepository {

    private ProductDao mProductDao;
    private OrderDao mOrderDao;
    private LiveData<List<Product>> mAllProducts;
    private LiveData<List<Order>> mAllOrders;

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mOrderDao = db.orderDao();
        mAllProducts = mProductDao.getAllProducts();
        mAllOrders = mOrderDao.getAllOrders();
    }

    public LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    public Product getProductById(String id) {
        // Must be called on background thread
        return mProductDao.getProductById(id);
    }
    
    public LiveData<List<Order>> getAllOrders() {
        return mAllOrders;
    }

    public void insert(Order order) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.insert(order);
        });
    }
}
