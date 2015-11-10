package com.fekrasoftware.androidtask.controller;

import com.activeandroid.query.Select;
import com.fekrasoftware.androidtask.model.Product;

import java.util.ArrayList;

/**
 * Created by mohamedzakaria on 11/7/15.
 */
public class Database {
    public static ArrayList<Product> getProducts() {
        return (ArrayList) new Select().from(Product.class).orderBy("product_id ASC").execute();
    }
}
