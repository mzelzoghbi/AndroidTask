package com.fekrasoftware.androidtask.controller;

import com.fekrasoftware.androidtask.model.Product;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by mohamedzakaria on 11/7/15.
 */
public interface ApiService {
    @GET("/products")
    Call<List<Product>> getProducts(@Query("count") int count , @Query("from") int from);
}
