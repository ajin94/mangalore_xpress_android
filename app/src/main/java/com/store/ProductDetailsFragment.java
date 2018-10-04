package com.store;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.text.DecimalFormat;

import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

/**
 * Created by akhil on 4/10/18.
 */

public class ProductDetailsFragment extends Fragment {
    MainActivityNew main_activity;
    String product_name,product_description,product_logo;
    String product_imgs[];
    int product_price,product_mrp,product_id;
    LinearLayout product_imgs_layout;
    ImageView logo;
    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();
        product_name = getArguments().getString("product_name");
        product_description = getArguments().getString("product_description");
        product_logo = getArguments().getString("product_logo");
        product_imgs = getArguments().getStringArray("product_images");
        product_id = getArguments().getInt("product_id");
        product_price = getArguments().getInt("product_price");
        product_mrp = getArguments().getInt("product_mrp");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_details_fragment, container, false);
        product_imgs_layout = (LinearLayout) view.findViewById(R.id.product_imgs);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        logo = (ImageView) view.findViewById(R.id.logo);
        name.setText(product_name);
        desc.setText(product_description);
        Glide.with(getContext()).load(product_logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);
        TextView price = (TextView) view.findViewById(R.id.price);
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###");
        price.setText("Rs."+IndianCurrencyFormat.format(product_price));

        for(final String s: product_imgs){
            final ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(130,
                    100));
            imageView.setPadding(5,5,5,5);
            product_imgs_layout.addView(imageView);
            Glide.with(getContext()).load(s)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(getContext()).load(s)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(logo);
                    product_logo = s;
                }
            });

        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String img[] = new String[]{product_logo};
                new ImageViewer.Builder(getContext(), img).show();
            }
        });

        return  view;
    }

}
