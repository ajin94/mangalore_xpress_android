package com.news;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.store.Product;
import com.store.ProductListAdapter;

import java.text.DecimalFormat;
import java.util.List;

import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.MyViewHolder> {

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


    public NewsListAdapter(Context mContext,List<News> product_list) {
        this.product_list = product_list;
        this.mContext = mContext;
    }

    @Override
    public NewsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);

        return new NewsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.MyViewHolder holder, int position) {
        final News product = product_list.get(position);
        holder.img.layout(0,0,0,0);
        holder.title.setText(product.getTitle());
        holder.description.setText(Html.fromHtml(product.getDescription().replaceAll("<br>","")));
        if(product.getImage_url().equals("")){
            holder.img.setVisibility(View.GONE);
        }else {
            Glide.with(mContext).load(product.getImage_url())
                    .fitCenter().placeholder(R.drawable.gl_thumb)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);
        }
        holder.read_more.setText("READ MORE @ "+product.getSource());
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }
}
