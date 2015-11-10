package com.fekrasoftware.androidtask.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fekrasoftware.androidtask.R;
import com.fekrasoftware.androidtask.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mohamedzakaria on 11/10/15.
 */
public class DetailFragment extends Fragment {
    View detailFragment;
    @Bind(R.id.detailImg)
    ImageView detailImg;
    @Bind(R.id.descriptionTv)
    TextView title;
    @Bind(R.id.tv)
    TextView price;
    @Bind(R.id.progress)
    ProgressBar loading;

    private Product product;

    public static DetailFragment newInstance(Product product) {
        Bundle bundle = new Bundle();
        DetailFragment fragment = new DetailFragment();
        bundle.putParcelable("product", product);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = getArguments().getParcelable("product");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailFragment = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, detailFragment);
        // set image height
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp(product.getImage().getWidth())
                , dp(product.getImage().getHeight()));
        detailImg.setLayoutParams(params);

        title.setText(product.getProductDescription());
        price.setText("$" + product.getPrice());
        Picasso.with(getContext()).load(product.getImage().getUrl()).into(detailImg, new Callback() {
            @Override
            public void onSuccess() {
                detailImg.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        return detailFragment;
    }

    private int dp(int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getActivity().getResources().getDisplayMetrics());
    }
}
