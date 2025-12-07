package com.example.simplepos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.simplepos.R;
import com.example.simplepos.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simplepos_v3.db"; // Reset to V3 to ensure clean slate
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_ORDERS = "orders";
    
    // Product Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE_RES_ID = "image_res_id";
    
    // Order Columns
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_TOTAL_PRICE = "total_price";
    private static final String COLUMN_ITEMS_SUMMARY = "items_summary";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE_RES_ID + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ORDER_DATE + " TEXT,"
                + COLUMN_TOTAL_PRICE + " REAL,"
                + COLUMN_ITEMS_SUMMARY + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
        
        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing table and re-create to re-seed with new images
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        List<Product> products = new ArrayList<>();
        // Seed 20 Dummy Products with UNIQUE images and IDR Prices
        products.add(new Product("1", "Espresso", 25000, "Rich and bold espresso shot", R.drawable.img_coffee));
        products.add(new Product("2", "Green Tea", 20000, "Refreshing green tea", R.drawable.img_tea));
        products.add(new Product("3", "Croissant", 28000, "Buttery french croissant", R.drawable.img_bakery));
        products.add(new Product("4", "Chocolate Cake", 35000, "Decadent chocolate cake", R.drawable.img_cake));
        products.add(new Product("5", "Orange Juice", 22000, "Freshly squeezed juice", R.drawable.img_juice));
        
        products.add(new Product("6", "Cappuccino", 30000, "Espresso with steamed milk foam", R.drawable.img_cappuccino));
        products.add(new Product("7", "Latte", 30000, "Espresso with steamed milk", R.drawable.img_latte));
        products.add(new Product("8", "Bagel", 15000, "Toasted bagel with cream cheese", R.drawable.img_bagel));
        products.add(new Product("9", "Donut", 12000, "Glazed sweet donut", R.drawable.img_donut));
        products.add(new Product("10", "Mineral Water", 5000, "Pure mineral water", R.drawable.img_water));
        
        products.add(new Product("11", "Burger", 55000, "Classic beef burger", R.drawable.img_fastfood));
        products.add(new Product("12", "Fries", 20000, "Crispy golden fries", R.drawable.img_fries));
        products.add(new Product("13", "Pizza Slice", 30000, "Pepperoni pizza slice", R.drawable.img_pizza));
        products.add(new Product("14", "Soda", 10000, "Carbonated soft drink", R.drawable.img_soda));
        products.add(new Product("15", "Salad", 45000, "Fresh garden salad", R.drawable.img_salad));
        
        products.add(new Product("16", "Iced Coffee", 32000, "Cold brewed coffee", R.drawable.img_iced_coffee));
        products.add(new Product("17", "Milkshake", 38000, "Vanilla milkshake", R.drawable.img_milkshake));
        products.add(new Product("18", "Muffin", 18000, "Blueberry muffin", R.drawable.img_muffin));
        products.add(new Product("19", "Cookie", 12000, "Chocolate chip cookie", R.drawable.img_cookie));
        products.add(new Product("20", "Sandwich", 40000, "Club sandwich", R.drawable.img_sandwich));
        
        for (Product product : products) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, product.getId());
            values.put(COLUMN_NAME, product.getName());
            values.put(COLUMN_PRICE, product.getPrice());
            values.put(COLUMN_DESCRIPTION, product.getDescription());
            values.put(COLUMN_IMAGE_RES_ID, product.getImageResId());
            db.insert(TABLE_PRODUCTS, null, values);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID))
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }
    
    public void addOrder(com.example.simplepos.model.Order order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_DATE, order.getDate());
        values.put(COLUMN_TOTAL_PRICE, order.getTotalPrice());
        values.put(COLUMN_ITEMS_SUMMARY, order.getItemsSummary());
        
        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }
    
    public List<com.example.simplepos.model.Order> getAllOrders() {
        List<com.example.simplepos.model.Order> orders = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS + " ORDER BY " + COLUMN_ORDER_ID + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                com.example.simplepos.model.Order order = new com.example.simplepos.model.Order(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ORDER_DATE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEMS_SUMMARY))
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }
}
