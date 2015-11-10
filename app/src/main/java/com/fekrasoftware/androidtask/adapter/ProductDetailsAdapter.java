package com.fekrasoftware.androidtask.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fekrasoftware.androidtask.model.Product;
import com.fekrasoftware.androidtask.ui.DetailFragment;

import java.util.ArrayList;

/**
 * Created by mohamedzakaria on 11/10/15.
 */
public class ProductDetailsAdapter extends FragmentStatePagerAdapter {
    ArrayList<Product> products = new ArrayList<Product>();

    public ProductDetailsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailFragment.newInstance(products.get(position));
    }


    @Override
    public int getCount() {
        return products.size();
    }

    public ArrayList<Product> getData() {
        return products;
    }

    public void setData(ArrayList<Product> data) {
        this.products = data;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
