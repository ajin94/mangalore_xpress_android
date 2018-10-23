package com.news;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import custom.WebDialog;
import mangalorexpress.com.R;

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> product_list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView img;
        public TextView description;
        public TextView read_more;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.news_thumb);
            description = (TextView) view.findViewById(R.id.description);
            read_more = (TextView) view.findViewById(R.id.read_more);
        }
    }

    public class AdViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;

        public AdViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(product_list.get(position).is_ad()){
            return 0;
        }else{
            return 1;
        }
    }

    public NewsListAdapter(Context mContext,List<News> product_list) {
        this.product_list = product_list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ad_image, parent, false);

            return new NewsListAdapter.AdViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_row, parent, false);

            return new NewsListAdapter.MyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mholder, int position) {
        final News product = product_list.get(position);
        if(product.is_ad()){
            AdViewHolder holder = (AdViewHolder) mholder;
            Glide.with(mContext).load(product.getImage_url())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);
        }else {
            MyViewHolder holder = (MyViewHolder) mholder;
            holder.title.setText(product.getTitle());
            holder.description.setText(Html.fromHtml(product.getDescription().replaceAll("<br>", "")));
            if (product.getImage_url().equals("")) {
                holder.img.setVisibility(View.GONE);
            } else {
                Glide.with(mContext).load(product.getImage_url())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img);
            }
            holder.read_more.setText("Read More @ " + product.getSource());
            holder.read_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebDialog w = new WebDialog(mContext,product.getSource_url());
                    w.show();
                }
            });
            holder.read_more.setPaintFlags(holder.read_more.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }
}
