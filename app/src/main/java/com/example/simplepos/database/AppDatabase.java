package com.example.simplepos.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.simplepos.R;
import com.example.simplepos.database.dao.OrderDao;
import com.example.simplepos.database.dao.ProductDao;
import com.example.simplepos.model.Order;
import com.example.simplepos.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, Order.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "simplepos_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Seed database in background
            databaseWriteExecutor.execute(() -> {
                ProductDao dao = INSTANCE.productDao();
                dao.deleteAll();

                List<Product> products = new ArrayList<>();
                // Seed 20 Dummy Products
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

                dao.insertAll(products);
            });
        }
    };
}
