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

import java.util.List;

import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHolder> {
 
    private List<Store> store_list;
    private Context mContext;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView logo;
        public CardView card;
 
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            logo = (ImageView) view.findViewById(R.id.logo);
            card = (CardView) view.findViewById(R.id.card);
        }
    }
 
 
    public StoreListAdapter(Context mContext,List<Store> store_list) {
        this.store_list = store_list;
        this.mContext = mContext;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_list_row, parent, false);

        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Store store = store_list.get(position);
        holder.name.setText(store.getName());
        Glide.with(mContext).load(store.getLogo_url())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.logo);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivityNew m = (MainActivityNew) mContext;
                m.show_products(store.getId(),store.getName(),store.getDescription(),store.getLogo_url());
            }
        });
    }
 
    @Override
    public int getItemCount() {
        return store_list.size();
    }
}