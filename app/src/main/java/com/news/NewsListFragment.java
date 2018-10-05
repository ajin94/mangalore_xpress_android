package com.news;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.store.Store;
import com.store.StoreListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import custom.EndlessRecyclerViewScrollListener;
import custom.RequestQueueSingleton;
import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

public class NewsListFragment extends Fragment  {

    MainActivityNew main_activity;

    private RecyclerView recyclerView;
    private NewsListAdapter mAdapter;
    private ArrayList<News> news = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private  String category;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int page_no = 1;
    public NewsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();
        category = getArguments().getString("category");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.news_list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                page_no = page_no+1;
                download_stores(true);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page_no = 1;
                download_stores(true);
            }
        });

        download_stores(true);
        return  view;
    }

    public void download_stores(final boolean show_progress){
        String url = "http://www.mangalorexpress.com/cards.json?category="+category+"&page="+page_no;

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for(int i=0;i<response.length();i++){
                                JSONObject store = response.getJSONObject(i);
                                News s = new News();
                                s.setCategory(category);
                                s.setTitle(store.getString("title"));
                                s.setDescription(new String(Base64.decode(store.getString("desc"),Base64.DEFAULT)));
                                if(store.isNull("image_url")){
                                    s.setImage_url("");
                                }else {
                                    s.setImage_url(store.getString("image_url"));
                                }
                                s.setSource(store.getString("news_source"));
                                s.setSource_url(store.getString("src_url"));
                                news.add(s);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(show_progress) {
                            swipeRefreshLayout.setRefreshing(false);
                            if(page_no>1){
                                mAdapter.notifyDataSetChanged();
                            }else {
                                show_stores();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(show_progress) {
                            Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        );
        if(show_progress) {
            swipeRefreshLayout.setRefreshing(true);
        }
        jsonObjectRequest.setTag("Download course");
        RequestQueueSingleton.getInstance(getContext()).getRequestQueue().add(jsonObjectRequest);
    }

    public  void show_stores(){
        Log.d("Akhil","NEWS SIZE "+news.size());
        mAdapter = new NewsListAdapter(getContext(),news);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);

        // HW layer support only exists on API 11+
        if (Build.VERSION.SDK_INT >= 11) {
            if (animation == null && nextAnim != 0) {
                animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            }

            if (animation != null) {
                getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    public void onAnimationEnd(Animation animation) {
                        getView().setLayerType(View.LAYER_TYPE_NONE, null);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                });
            }
        }

        return animation;
    }

}