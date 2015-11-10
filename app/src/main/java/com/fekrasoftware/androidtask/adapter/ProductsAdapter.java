package com.fekrasoftware.androidtask.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fekrasoftware.androidtask.R;
import com.fekrasoftware.androidtask.model.Product;
import com.fekrasoftware.androidtask.ui.DetailFragmentPager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by mohamedzakaria on 11/7/15.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Activity activity;
    private ArrayList<Product> products;

    public ProductsAdapter(Activity activity, ArrayList<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    public final class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.productImg)
        ImageView productImg;
        @Bind(R.id.productTitle)
        TextView productTitle;
        @Bind(R.id.productPrice)
        TextView productPrice;
        @Bind(R.id.loading)
        ProgressBar loading;
        @Bind(R.id.productCard)
        CardView cardView;
        @Bind(R.id.layout)
        RelativeLayout layout;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.productCard:
                    ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .replace(R.id.container, DetailFragmentPager.newInstance(products , getAdapterPosition())).addToBackStack(null).commit();
                    break;
            }
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productTitle.setText(products.get(position).getProductDescription());
        holder.productPrice.setText("$" + products.get(position).getPrice());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , dp(product.getImage().getHeight()));
        holder.productImg.setLayoutParams(params);
        holder.loading.setVisibility(View.VISIBLE);
        Picasso.with(activity) //
                .load(products.get(position).getImage().getUrl())
                .resize(0, products.get(position).getImage().getHeight())
                .into(holder.productImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return (products == null) ? 0 : products.size();
    }

    private int dp(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, activity.getResources().getDisplayMetrics());
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
