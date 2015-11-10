package com.fekrasoftware.androidtask.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fekrasoftware.androidtask.R;
import com.fekrasoftware.androidtask.SimpleTaskApp;
import com.fekrasoftware.androidtask.adapter.ProductsAdapter;
import com.fekrasoftware.androidtask.controller.ApiService;
import com.fekrasoftware.androidtask.controller.Database;
import com.fekrasoftware.androidtask.controller.ProductController;
import com.fekrasoftware.androidtask.model.Product;
import com.fekrasoftware.androidtask.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by mohamedzakaria on 11/9/15.
 */
public class HomePageFragment extends Fragment {
    View homePageFragment;
    @Bind(R.id.productsList)
    RecyclerView productsList;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private static ArrayList<Product> products;
    private ProductsAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;

    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;
    private boolean loading = false;

    private static HomePageFragment instance;
    // prepare call in Retrofit 2.0
    private ApiService apiService;
    private Call<List<Product>> call;
    private boolean firstTime = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setRetainInstance(true);
        firstTime = true;
    }

    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static HomePageFragment getInstance() {
        return instance;
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homePageFragment = inflater.inflate(R.layout.fragment_homepage, container, false);
        ButterKnife.bind(this, homePageFragment);
        //
        initalize();
        //


        return homePageFragment;

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);

        if (firstTime) {
            products = new ArrayList<Product>();
            productsList.setAdapter(adapter);
            getData();
        } else {
            adapter.setProducts(products);
            productsList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void initalize() {
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        productsList.setLayoutManager(layoutManager);
        adapter = new ProductsAdapter(getActivity(), null);
        productsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (!loading) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = true;
                        getData();
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void getData() {
        firstTime = false;
        if (Utility.isNetworkAvailable(getContext())) {
            // prepare call in Retrofit 2.0
            apiService = SimpleTaskApp.retrofit.create(ApiService.class);

            call = apiService.getProducts(10, products.size() + 1);
            //asynchronous call
            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(final Response<List<Product>> response, Retrofit retrofit) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ProductController.saveProducts(response, products);
                            adapter.setProducts(products);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    loading = false;
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });

                        }
                    }).start();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            swipeRefreshLayout.setRefreshing(true);
        } else {
            getCachedData();
        }
    }

    private void getCachedData() {
        ArrayList<Product> products = Database.getProducts();
        if (products != null && products.size() > 0) {
            adapter.setProducts(products);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (call != null)
            call.cancel();
    }
}
