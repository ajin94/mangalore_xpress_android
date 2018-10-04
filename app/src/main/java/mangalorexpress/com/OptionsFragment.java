package mangalorexpress.com;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by akhil on 30/9/18.
 */

public class OptionsFragment extends Fragment  {

    MainActivityNew main_activity;


    public OptionsFragment() {
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
        View view = inflater.inflate(R.layout.options_fragment, container, false);


        CardView news = (CardView) view.findViewById(R.id.news);
        CardView store = (CardView) view.findViewById(R.id.stores);
        CardView articles = (CardView) view.findViewById(R.id.articles);
        CardView social = (CardView) view.findViewById(R.id.social);
        CardView real_estate = (CardView) view.findViewById(R.id.real_estate);
        CardView classified = (CardView) view.findViewById(R.id.classified);
        CardView health = (CardView) view.findViewById(R.id.health_care);
        CardView contact = (CardView) view.findViewById(R.id.contact);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(1);
            }
        });

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(2);
            }
        });

        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(3);
            }
        });

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(4);
            }
        });

        real_estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(5);
            }
        });

        classified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(6);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(7);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_activity.show_page(8);
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
