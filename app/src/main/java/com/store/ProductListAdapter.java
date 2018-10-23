package com.store;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.List;

import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private List<Product> product_list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView img;
        public TextView price;
        public CardView card;



        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            img = (ImageView) view.findViewById(R.id.logo);
            price = (TextView) view.findViewById(R.id.price);
            card = (CardView) view.findViewById(R.id.card);
        }
    }


    public ProductListAdapter(Context mContext,List<Product> product_list) {
        this.product_list = product_list;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Product product = product_list.get(position);
        holder.name.setText(product.getName());
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###");
        holder.price.setText("Rs. "+IndianCurrencyFormat.format(product.getPrice()));
        Glide.with(mContext).load(product.getProduct_image())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityNew m = (MainActivityNew) mContext;
                m.show_product_details(product.getProduct_id(),product.getName(),product.getDescription(),product.getProduct_image(),product.getPrice(),product.getMrp(),product.getProduct_image_urls());
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }
}