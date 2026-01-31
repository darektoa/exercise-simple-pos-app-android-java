package com.example.simplepos.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.simplepos.model.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insert(Order order);

    @Query("SELECT * FROM orders ORDER BY order_id DESC")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM orders ORDER BY order_id DESC")
    List<Order> getAllOrdersList();
}
