package com.fekrasoftware.androidtask.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fekrasoftware.androidtask.ui.HomePageFragment;
import com.fekrasoftware.androidtask.SimpleTaskApp;
import com.fekrasoftware.androidtask.model.Product;
import com.fekrasoftware.androidtask.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by mohamedzakaria on 11/10/15.
 */
public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utility.isNetworkAvailable(context)) {
            // do some work
            Log.d("internet", "true");
            // prepare call in Retrofit 2.0
            ApiService apiService = SimpleTaskApp.retrofit.create(ApiService.class);

            Call<List<Product>> call = apiService.getProducts(10, 1);
            //asynchronous call
            try {
                call.enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Response<List<Product>> response, Retrofit retrofit) {
                        if (HomePageFragment.getInstance() != null &&
                                HomePageFragment.getProducts() != null) {
                            ProductController.saveProducts(response,
                                    HomePageFragment.getProducts());

                            HomePageFragment.getInstance().notifyDataSetChanged();
                        } else {
                            ProductController.saveProducts(response,
                                    new ArrayList<Product>());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
