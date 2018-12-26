package com.quiz;

import android.content.Context;
import android.content.Intent;
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

import mangalorexpress.com.R;

public class QuizListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Quiz> product_list;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView img;
        public CardView card;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.news_thumb);
            card = (CardView) view.findViewById(R.id.card);
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

    public QuizListAdapter(Context mContext,List<Quiz> product_list) {
        this.product_list = product_list;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ad_image, parent, false);

            return new QuizListAdapter.AdViewHolder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.quiz_row, parent, false);

            return new QuizListAdapter.MyViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder mholder, int position) {
        final Quiz product = product_list.get(position);

        if(product.is_ad()){
            AdViewHolder holder = (AdViewHolder) mholder;
            holder.img.layout(0,0,0,0);
            Glide.with(mContext).load(product.getIcon_url())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);
        }else {
            MyViewHolder holder = (MyViewHolder) mholder;
            holder.img.layout(0,0,0,0);
            holder.title.setText(product.getName());
            if (product.getIcon_url()==null || product.getIcon_url().equals("")) {
//                holder.img.setVisibility(View.GONE);
            } else {
                Glide.with(mContext).load(product.getIcon_url())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.img);
            }

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,MainGameActivity.class);
                    intent.putExtra("quiz_id",product.getQuiz_id());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }
}
