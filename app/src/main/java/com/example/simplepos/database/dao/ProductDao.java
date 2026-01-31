package com.example.simplepos.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.simplepos.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Product> products);

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products WHERE id = :productId")
    Product getProductById(String productId);

    @Query("DELETE FROM products")
    void deleteAll();
}
