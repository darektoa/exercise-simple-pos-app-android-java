package com.example.simplepos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "products")
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "image_res_id")
    private int imageResId;

    public Product(@NonNull String id, String name, double price, String description, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResId = imageResId;
    }

    @NonNull
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}
