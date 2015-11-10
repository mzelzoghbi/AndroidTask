package com.fekrasoftware.androidtask.controller;

import com.fekrasoftware.androidtask.model.Image;
import com.fekrasoftware.androidtask.model.Product;

import java.util.List;

import retrofit.Response;

/**
 * Created by mohamedzakaria on 11/10/15.
 */
public class ProductController {
    public static void saveProducts(Response<List<Product>> response ,List<Product> products){
        for (int i = 0; i < response.body().size(); i++) {
            products.add(response.body().get(i));
            Product product = response.body().get(i);
            Image img = response.body().get(i).getImage();
            product.save();
            img.save();
            product.setImage(img);
            product.save();
        }
    }
}
