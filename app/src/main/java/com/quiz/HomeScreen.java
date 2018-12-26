package com.quiz;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import custom.EndlessRecyclerViewScrollListener;
import custom.RequestQueueSingleton;
import mangalorexpress.com.R;

public class HomeScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuizListAdapter mAdapter;
    private ArrayList<Quiz> news = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int page_no = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                page_no = page_no + 1;
                download_stores(true);
            }
        };

        setTitle("MX QUIZ");

        recyclerView.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page_no = 1;
                news = new ArrayList<>();
                download_stores(true);
            }
        });

        download_stores(true);
    }

    public void download_stores(final boolean show_progress){

        String url = "http://www.mangalorexpress.com/quizzes.json"+"?page="+page_no;

        Log.d("Akhil",url);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            if(response.length()==0){
                                page_no = page_no - 1;
                            }

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject store = response.getJSONObject(i);
                                Quiz s = new Quiz();
                                s.setName(store.getString("quiz_name"));
                                s.setQuiz_id(store.getInt("id"));
                                news.add(s);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (show_progress) {
                            swipeRefreshLayout.setRefreshing(false);
                            if (page_no > 1) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                show_stores();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (show_progress) {
                            Toast.makeText(HomeScreen.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        );
        if (show_progress) {
            swipeRefreshLayout.setRefreshing(true);
        }
        jsonObjectRequest.setTag("Download course");
        RequestQueueSingleton.getInstance(HomeScreen.this).getRequestQueue().add(jsonObjectRequest);
    }

    public void show_stores() {
        Log.d("Akhil", "NEWS SIZE " + news.size());
        mAdapter = new QuizListAdapter(HomeScreen.this, news);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }


}
