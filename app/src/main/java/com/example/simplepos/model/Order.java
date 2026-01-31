package com.example.simplepos.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "orders")
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private int id;

    @ColumnInfo(name = "order_date")
    private String date;

    @ColumnInfo(name = "total_price")
    private double totalPrice;

    @ColumnInfo(name = "items_summary")
    private String itemsSummary;

    public Order(int id, String date, double totalPrice, String itemsSummary) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.itemsSummary = itemsSummary;
    }

    @Ignore
    public Order(String date, double totalPrice, String itemsSummary) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.itemsSummary = itemsSummary;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDate() { return date; }
    public double getTotalPrice() { return totalPrice; }
    public String getItemsSummary() { return itemsSummary; }
}
