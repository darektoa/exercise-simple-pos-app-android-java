package com.example.simplepos.model;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String date;
    private double totalPrice;
    private String itemsSummary;

    public Order(int id, String date, double totalPrice, String itemsSummary) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.itemsSummary = itemsSummary;
    }

    public Order(String date, double totalPrice, String itemsSummary) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.itemsSummary = itemsSummary;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public double getTotalPrice() { return totalPrice; }
    public String getItemsSummary() { return itemsSummary; }
}
