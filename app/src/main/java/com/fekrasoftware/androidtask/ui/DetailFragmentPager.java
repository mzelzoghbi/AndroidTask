package com.fekrasoftware.androidtask.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fekrasoftware.androidtask.R;
import com.fekrasoftware.androidtask.adapter.ProductDetailsAdapter;
import com.fekrasoftware.androidtask.model.Product;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamedzakaria on 11/10/15.
 */
public class DetailFragmentPager extends Fragment {
    View detailFragmentPager;
    ArrayList<Product> products;
    private ProductDetailsAdapter adapter;
    @Bind(R.id.pager)
    ViewPager pager;
    private int pos;

    public static DetailFragmentPager newInstance(ArrayList<Product> products, int pos) {
        Bundle bundle = new Bundle();
        DetailFragmentPager fragment = new DetailFragmentPager();
        bundle.putParcelableArrayList("data", products);
        bundle.putInt("pos", pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = getArguments().getParcelableArrayList("data");
        pos = getArguments().getInt("pos");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailFragmentPager = inflater.inflate(R.layout.fragment_detailpager, container, false);
        ButterKnife.bind(this, detailFragmentPager);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        adapter = new ProductDetailsAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        adapter.setData(products);
        pager.setAdapter(adapter);
        pager.setCurrentItem(pos);
        adapter.notifyDataSetChanged();

        return detailFragmentPager;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
