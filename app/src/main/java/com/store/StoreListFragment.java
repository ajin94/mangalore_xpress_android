package com.store;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import custom.RequestQueueSingleton;
import custom.SliderAdapter;
import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

/**
 * Created by akhil on 3/10/18.
 */

public class StoreListFragment extends Fragment {

    MainActivityNew main_activity;

    private RecyclerView recyclerView;
    private StoreListAdapter mAdapter;
    private ArrayList<Store> stores;
    private SwipeRefreshLayout swipeRefreshLayout;
    ViewPager viewPager;
    TabLayout indicator;
    RelativeLayout ad_container;
    private String ad_imgs[];
    Timer timer;

    public StoreListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();

    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            main_activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < ad_imgs.length - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.store_list_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);
        indicator=(TabLayout)view.findViewById(R.id.indicator);
        ad_container = (RelativeLayout) view.findViewById(R.id.ad_container);
        download_ads();
        download_stores(true);
        return  view;
    }

    public  void download_ads(){
        String url = "http://www.mangalorexpress.com/ads.json?ad_type=Store";
        final JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("Akhil",response.toString());
                            ad_imgs = new String[response.length()];
                            for(int i=0;i<response.length();i++){
                                JSONObject obj = response.getJSONObject(i);
                                ad_imgs[i] = obj.getString("image_url");
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        // show ads
                        if(ad_imgs.length>0){
                            viewPager.setAdapter(new SliderAdapter(main_activity.getApplicationContext(),ad_imgs));
                            indicator.setupWithViewPager(viewPager, true);
                            ad_container.setVisibility(View.VISIBLE);

                            if(timer!=null){
                                timer.cancel();
                            }
                            timer = new Timer();
                            timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);
                        }else{
                            ad_container.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        jsonObjectRequest.setTag("Download Ads");
        RequestQueueSingleton.getInstance(getContext()).getRequestQueue().add(jsonObjectRequest);

    }

    public void download_stores(final boolean show_progress){
        String url = "http://www.mangalorexpress.com/stores.json";

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            stores = new ArrayList<>();
                            for(int i=0;i<response.length();i++){
                                JSONObject store = response.getJSONObject(i);
                                Store s = new Store();
                                s.setName(store.getString("name"));
                                s.setDescription(store.getString("description"));
                                s.setLogo_url(store.getString("logo_url"));
                                s.setEnable(store.getBoolean("enable"));
                                s.setId(store.getInt("id"));
                                stores.add(s);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        if(show_progress) {
                            swipeRefreshLayout.setRefreshing(false);
                            show_stores();
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
        mAdapter = new StoreListAdapter(getContext(),stores);
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
//                        getView().setLayerType(View.LAYER_TYPE_NONE, null);
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
