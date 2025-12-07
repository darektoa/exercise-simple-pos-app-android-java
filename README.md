# 🛒 Simple POS (Point of Sale) Application

![App Banner](https://via.placeholder.com/1200x300?text=Simple+POS+Android+Application)

**Simple POS** is a robust, native Android application built with **Java** and **XML**. It serves as a comprehensive simulation of a real-world Point of Sale system, designed to demonstrate modern Android development practices, offline data persistence with SQLite, and intuitive User Experience (UX) design.

This project is fully self-contained, requiring no external backend server, making it perfect for educational purposes, portfolio demonstrations, or as a foundation for small business tools.

---

## 🌟 Table of Contents

1. [Key Features](#-key-features)
2. [Technology Stack](#-technology-stack)
3. [Architecture Overview](#-architecture-overview)
4. [Prerequisites](#-prerequisites)
5. [Installation &amp; Setup](#-installation--setup)
6. [Usage Guide](#-usage-guide)
7. [Project Structure in Detail](#-project-structure-in-detail)
8. [Codebase Deep Dive](#-codebase-deep-dive)
9. [Troubleshooting](#-troubleshooting)
10. [License](#-license)

---

## 🚀 Key Features

### 1. 🛍️ Dynamic Product Catalog

- **Visual Grid**: Products are displayed in a clean, scrollable list (RecyclerView) with high-quality images.
- **Detailed View**: Tapping a product reveals a dedicated details screen with a larger image and full description.
- **Immediate Feedback**: Adding items to the cart triggers a custom **Snackbar** anchored above the navigation bar, verifying the action without obstructing UI.

### 2. 🛒 Smart Shopping Cart

- **Singleton Management**: The cart state is maintained globally across the app using a Singleton pattern, ensuring data consistency between tabs and activities.
- **Real-time Totals**: Prices are calculated instantly as items are added or removed.
- **Item Management**: Users can remove individual items directly from the cart using the **Trash/Remove** button.

### 3. 🧾 Digital Receipt System

- **Ticket UI**: Upon checkout, the app generates a beautiful "Digital Receipt" screen designed to look like a physical thermal printout.
- **Visual Polish**: Includes dashed separator lines, semi-circular cutouts ("ticket notch" effect), and a simulated barcode.
- **Order Summary**: Clearly lists Order ID, Date, and Final Amount.

### 4. 📅 Order History & Persistence

- **SQLite Database**: Every confirmed order is serialized and stored locally.
- **History Log**: Users can browse a chronological list of all past transactions.
- **Persistent Storage**: Data survives app restarts and device reboots.

### 5. 🎨 Modern UI/UX

- **Splash Screen**: A branded launch experience.
- **Dark Mode Support**: Uses flexible theme attributes (`?attr/colorSurface`) to support system dark themes.
- **Vector Graphics**: App icon and UI elements use scalable Vector Drawables (SVG) for crisp rendering on any screen size.

---

## 🛠 Technology Stack

- **Language**: Java 17+
- **Minimum SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Build System**: Gradle 8.0+
- **Architecture**: Single Activity (mostly) with Fragments
- **Database**: SQLite (via `SQLiteOpenHelper`)
- **Main Libraries**:
  - `androidx.appcompat`: For backward-compatible UI components.
  - `com.google.android.material`: For Material Design components (BottomNavigation, FloatingActionButton, Snackbar).
  - `androidx.constraintlayout`: For complex, responsive layouts.
  - `androidx.recyclerview`: For high-performance lists.

---

## 🏗 Architecture Overview

The application follows a **Modular / Fragment-based** architecture hosted within a single `MainActivity`.

- **Singleton Pattern**: formatting data logic (`Cart.java`) is decoupled from UI logic.
- **Database Helper**: A centralized class (`DatabaseHelper.java`) manages the SQLite connection, ensuring thread safety and simplified data access.
- **Utilities**: Helper classes (like `CurrencyUtils.java`) abstract common logic (formatting money) to prevent code duplication.

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Koala, Jellyfish, or newer.
- **Java Development Kit (JDK)**: Version 17 is required for recent Android Gradle Plugins.
- **Android Virtual Device (AVD)**: A pixel-based emulator ensuring "Google Play Services" is available is recommended, though not strictly required. Memory: 4GB+ recommended.

---

## 📥 Installation & Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/darektoa/exercise-simple-pos-app-android-java.git
   ```
2. **Open in Android Studio**:
   - Launch Android Studio.
   - Select **File > Open**.
   - Navigate to the `simple-e-business` folder and click OK.
3. **Sync Gradle**:
   - Android Studio will automatically attempt to sync dependencies. If it fails, check your internet connection and try **File > Sync Project with Gradle Files**.
4. **Create a Virtual Device**:
   - Open **Device Manager**.
   - Create a new device (e.g., Pixel 6 API 34).
5. **Run the App**:
   - Click the **Run** (Green Play) button in the top toolbar.

### Command Line Building

If you prefer the terminal:

- **Build APK**:

  ```bash
  ./gradlew assembleDebug
  ```

  *Output: `app/build/outputs/apk/debug/app-debug.apk`*
- **Install directly**:

  ```bash
  ./gradlew installDebug
  ```

---

## 📖 Usage Guide

1. **Splash & Launch**: Wait 2 seconds for the Splash screen to transition to the Dashboard.
2. **Browsing**: Scroll through the list of Coffees, Snacks, and Drinks.
3. **Adding to Cart**:
   - Tap the "Cart" icon on any product card.
   - OR Tap the product to view details, then tap "Add to Cart".
   - Watch the Snackbar appear at the bottom. You can tap **UNDO** to revert.
4. **Managing Cart**:
   - Tap the **Cart** tab in the bottom navigation.
   - View your items.
   - Tap the **Red Trash Icon** to remove an item you changed your mind about.
   - Tap **Checkout** to proceed.
5. **Checkout**:
   - Review the total.
   - Tap **Confirm Order**.
6. **Receipt**:
   - Admire the ticket receipt!
   - Tap **Back to Home** or use the Back button to return to the catalog.
7. **History**:
   - Tap the **History** tab to see the record of the checkout you just performed.

---

## 📂 Project Structure in Detail

```text
simple-e-business/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/simplepos/
│   │   │   ├── database/       # Logic for SQLite Database interactions
│   │   │   ├── fragment/       # UI Fragments for the Bottom Navigation tabs
│   │   │   ├── model/          # POJO (Plain Old Java Objects) Data Models
│   │   │   ├── utils/          # Static helper methods
│   │   │   └── [Activities]    # Java Activities (Screens)
│   │   ├── res/
│   │   │   ├── drawable/       # Images, Vector Icons, and XML shapes
│   │   │   ├── layout/         # XML UI Layout definitions
│   │   │   ├── menu/           # Menu definitions (Bottom Nav)
│   │   │   └── values/         # Colors, Strings, Themes
│   │   └── AndroidManifest.xml # App configuration and permissions
├── build.gradle                # Project build script
└── gradle.properties           # JVM args and gradle settings
```

---

## 🔍 Codebase Deep Dive

Here is a comprehensive explanation of the source code files:

### 📱 Activities (The Screens)

1. **`SplashActivity.java`**

   - **Key Logic**: Uses a `Handler` with `postDelayed` to wait 2000ms (2 seconds) before creating an `Intent` to switch to `MainActivity`.
   - **Purpose**: Gives the app time to "load" (simulate loading) and branding.
2. **`MainActivity.java`**

   - **Key Logic**: Sets up the `BottomNavigationView`. Uses a `getSupportFragmentManager().beginTransaction().replace(...)` method to hot-swap fragments based on which tab (Products, Cart, History) is clicked.
   - **Behavior**: It remembers the selected fragment so navigation feels seamless.
3. **`ProductDetailActivity.java`**

   - **Key Logic**: Receives a `Product` object passed via `Intent.getSerializableExtra`. Binds this data to views (`ImageView`, `TextView`).
   - **Interaction**: Has a large Floating Action Button (or standard button) to add to cart.
4. **`CartActivity.java`** (Legacy)

   - **Note**: This was the original cart screen. It is currently accessible via the Floating Action Button in `MainActivity`, but the `CartFragment` is the primary modern view.
5. **`CheckoutActivity.java`**

   - **Key Logic**: Calculates the final total from the `Cart` singleton. On confirmation, it constructs an `Order` object, persists it via `databaseHelper.addOrder()`, clears the cart, and forwards the user to the Success screen.
6. **`OrderSuccessActivity.java`**

   - **Key Logic**: Retrieves order details passed via Intent.
   - **Design**: Implements a custom layout design mimicking a physical paper receipt.

### 🧩 Fragments (The Tabs)

1. **`ProductsFragment.java`**

   - **Data Source**: Fetches product list from `DatabaseHelper.getAllProducts()`.
   - **UI**: Sets up a `RecyclerView` with a `GridLayoutManager` (2 columns) to display products grid-style.
2. **`CartFragment.java`**

   - **Observation**: Updates its UI inside `onResume()`. This is crucial because if you modify the cart elsewhere, this tab needs to refresh when you return to it.
   - **Empty State**: Toggles visibility of an "Empty Cart" text view if `cart.getItemCount() == 0`.
3. **`HistoryFragment.java`**

   - **Data Source**: Calls `DatabaseHelper.getAllOrders()`.
   - **Sorting**: Orders are typically sorted DESC (newest first) by the SQL query.

### 💾 Database & Models

1. **`DatabaseHelper.java`**

   - **Role**: The brain of the data layer.
   - **`onCreate()`**: Executes SQL `CREATE TABLE` commands. Calls `seedData()` to insert the initial 20 products.
   - **`onUpgrade()`**: Handles version increments. Currently configured to **DROP** tables and re-create them if the version changes (useful for development resets).
   - **Seeding**: Contains hardcoded list of products (Coffee, Tea, etc.) to ensure the app isn't empty on first install.
   - **Critical Fix**: Uses `simplepos_v3.db` to ensure a fresh schema.
2. **`Cart.java`** (Singleton)

   - **Pattern**: `private static Cart instance`.
   - **Data Structure**: `Map<Product, Integer>`. Key is the product, Value is the quantity.
   - **Logic**: methods for `addItem`, `removeItem`, `clear`, and `getTotalPrice`.
3. **`Product.java`** & **`Order.java`**

   - Standard Java Beans with Getters/Setters and Constructors. `Product` implements `Serializable` to be passed between Activities.

### 🛠 Helpers & Adapters

1. **`CurrencyUtils.java`**

   - **Purpose**: Android's default formatting might not handle "Rp" correctly for Indonesia. This class forces `Locale("id", "ID")` to ensure prices look like `Rp 25.000` instead of `$25,000`.
2. **`ProductAdapter.java`**

   - **Holder Pattern**: Efficiently reuses views.
   - **Event Handling**: The `addToCart` click listener is defined here. It also creates the global `Snackbar`.
3. **`CartAdapter.java`**

   - **Interactivity**: Includes an interface `OnItemRemoveListener`. When the remove button is clicked, it calls this listener so the parent Activity/Fragment knows to update the "Total Price" text view immediately.

---

## ❓ Troubleshooting

**Q: The images are different than the names (e.g., Tea looks like Coke).**
A: This was a known issue in v1. The current version (v3 database) forces a clean slate with correct mappings. Uninstall the app and reinstall `installDebug` to ensure the new database `simplepos_v3.db` is created.

**Q: "App not installed" error.**
A: Ensure you are not trying to install a Debug APK on top of a Signed Release APK. Uninstall any previous version first.

**Q: Gradle Sync Failed.**
A: Check your internet connection. Gradle needs to download dependencies from Maven Central and Google repositories. Ensure you are not behind a corporate firewall blocking `.jar` downloads.

---

## 📜 License

This project is open-sourced under the **MIT License**.

```text
MIT License

Copyright (c) 2025 SimplePOS Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND...
```
