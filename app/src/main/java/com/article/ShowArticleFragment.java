package com.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

public class ShowArticleFragment extends Fragment {
    MainActivityNew main_activity;
    String title,image_path,description;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();
        title = getArguments().getString("title");
        image_path = getArguments().getString("image_path");
        description = getArguments().getString("description");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.article_fragment, container, false);
        TextView name = (TextView) view.findViewById(R.id.title);
        WebView desc = (WebView) view.findViewById(R.id.description);
        ImageView logo = (ImageView) view.findViewById(R.id.news_thumb);

        Glide.with(main_activity).load(image_path)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);
        name.setText(title);
        description = description.replaceAll("\u00a0","");
        description = description.replaceAll("src=\"/ckeditor_assets","src=\"http://www.mangalorexpress.com/ckeditor_assets");
        desc.loadData(description,"text/html", "UTF-8");
        return view;
    }


}
