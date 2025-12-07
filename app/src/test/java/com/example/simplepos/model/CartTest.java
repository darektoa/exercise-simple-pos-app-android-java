package com.example.simplepos.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartTest {

    private Cart cart;

    @Before
    public void setUp() {
        cart = Cart.getInstance();
        cart.clear();
    }

    @Test
    public void testAddItem() {
        Product p1 = new Product("1", "Test1", 10.0, "Desc", 0);
        cart.addItem(p1, 1);
        assertEquals(1, cart.getItemCount());
        assertEquals(10.0, cart.getTotalPrice(), 0.001);
    }

    @Test
    public void testAddMultipleItems() {
        Product p1 = new Product("1", "Test1", 10.0, "Desc", 0);
        Product p2 = new Product("2", "Test2", 20.0, "Desc", 0);
        
        cart.addItem(p1, 2);
        cart.addItem(p2, 1);
        
        assertEquals(3, cart.getItemCount());
        assertEquals(40.0, cart.getTotalPrice(), 0.001);
    }

    @Test
    public void testClearCart() {
        Product p1 = new Product("1", "Test1", 10.0, "Desc", 0);
        cart.addItem(p1, 1);
        cart.clear();
        assertEquals(0, cart.getItemCount());
        assertEquals(0.0, cart.getTotalPrice(), 0.001);
    }
}
