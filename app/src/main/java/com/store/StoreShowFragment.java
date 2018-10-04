package com.store;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import custom.RequestQueueSingleton;
import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

/**
 * Created by akhil on 4/10/18.
 */

public class StoreShowFragment extends Fragment {
    MainActivityNew main_activity;
    private RecyclerView recyclerView;
    private ArrayList<Product> products;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProductListAdapter mAdapter;
    private String store_name,store_description,store_logo;
    private  int store_id;

    public StoreShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();
        store_name = getArguments().getString("store_name");
        store_description = getArguments().getString("store_desc");
        store_logo = getArguments().getString("store_logo");
        store_id = getArguments().getInt("store_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.store_show_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        ImageView logo = (ImageView) view.findViewById(R.id.logo);
        name.setText(store_name);
        desc.setText(store_description);
        Glide.with(getContext()).load(store_logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);

        download_products(true);
        return  view;
    }

    public void download_products(final boolean show_progress){
        String url = "http://www.mangalorexpress.com/stores/"+store_id+"/products.json";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            products = new ArrayList<>();
                            for(int i=0;i<response.length();i++){
                                JSONObject product = response.getJSONObject(i);
                                Product p = new Product();
                                p.setName(product.getString("name"));
                                p.setDescription(product.getString("description"));
                                p.setProduct_image(product.getString("image_url"));

                                JSONArray imgs = product.getJSONArray("product_pictures");
                                String urls[] = new String[imgs.length()+1];
                                urls[0] = p.getProduct_image();
                                for(int j=0;j<imgs.length();j++){
                                    JSONObject img_da = imgs.getJSONObject(j);
                                    urls[j+1] = img_da.getString("image_path");
                                }
                                p.setProduct_image_urls(urls);
                                p.setProduct_id(product.getInt("id"));
                                p.setPrice(product.getInt("price"));
                                p.setMrp(product.getInt("mrp"));
                                products.add(p);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(show_progress) {
                            swipeRefreshLayout.setRefreshing(false);
                            show_products();
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
        jsonObjectRequest.setTag("Download products");
        RequestQueueSingleton.getInstance(getContext()).getRequestQueue().add(jsonObjectRequest);
    }

    public  void show_products(){
        mAdapter = new ProductListAdapter(getContext(),products);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
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
