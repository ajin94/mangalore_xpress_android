package com.news;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

/**
 * Created by akhil on 30/9/18.
 */

public class NewsCategoryFragment extends Fragment  {

    MainActivityNew main_activity;


    public NewsCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.news_category_fragment, container, false);


        CardView mangalore = (CardView) view.findViewById(R.id.mangalore);
        CardView karnataka = (CardView) view.findViewById(R.id.karnataka);
        CardView india = (CardView) view.findViewById(R.id.india);
        CardView international = (CardView) view.findViewById(R.id.international);
        CardView automobile = (CardView) view.findViewById(R.id.auto);
        CardView bollywood = (CardView) view.findViewById(R.id.bollywood);
        CardView sports = (CardView) view.findViewById(R.id.sports);
        CardView gadgets = (CardView) view.findViewById(R.id.gadgets);
        CardView beauty = (CardView) view.findViewById(R.id.beauty);

        mangalore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(1);
            }
        });

        karnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(2);
            }
        });

        india.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(3);
            }
        });

        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(4);
            }
        });

        automobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(5);
            }
        });

        bollywood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(6);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(7);
            }
        });

        gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(8);
            }
        });

        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_news(9);
            }
        });

        return view;
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
