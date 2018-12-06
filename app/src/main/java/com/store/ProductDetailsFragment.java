package com.store;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import custom.RequestQueueSingleton;
import mangalorexpress.com.MainActivityNew;
import mangalorexpress.com.R;

/**
 * Created by akhil on 4/10/18.
 */

public class ProductDetailsFragment extends Fragment {
    MainActivityNew main_activity;
    String product_name,product_description,product_logo;
    String product_imgs[];
    int product_price,product_mrp,product_id;
    LinearLayout product_imgs_layout;
    ImageView logo;
    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main_activity = (MainActivityNew) getActivity();
        product_name = getArguments().getString("product_name");
        product_description = getArguments().getString("product_description");
        product_logo = getArguments().getString("product_logo");
        product_imgs = getArguments().getStringArray("product_images");
        product_id = getArguments().getInt("product_id");
        product_price = getArguments().getInt("product_price");
        product_mrp = getArguments().getInt("product_mrp");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product_details_fragment, container, false);
        product_imgs_layout = (LinearLayout) view.findViewById(R.id.product_imgs);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        logo = (ImageView) view.findViewById(R.id.logo);
        name.setText(product_name);
        desc.setText(product_description);
        Glide.with(getContext()).load(product_logo)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(logo);
        TextView price = (TextView) view.findViewById(R.id.price);
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###");
        price.setText("Rs. "+IndianCurrencyFormat.format(product_price));

        for(final String s: product_imgs){
            final ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(130,
                    100));
            imageView.setPadding(5,5,5,5);
            product_imgs_layout.addView(imageView);
            Glide.with(getContext()).load(s)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(getContext()).load(s)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(logo);
                    product_logo = s;
                }
            });

        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String img[] = new String[]{product_logo};
                new ImageViewer.Builder(getContext(), img).show();
            }
        });

        Button enq = (Button) view.findViewById(R.id.enquiry);
        enq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_enquiry_dialog();
            }
        });

        return  view;
    }

    public void show_enquiry_dialog(){
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.enquiry_dialog, null);
        ContextThemeWrapper themedContext;

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        // ToDo get user input here
                        String ph = userInputDialogEditText.getText().toString();
                        if(ph.equals("")){
                            Toast.makeText(getContext(),"Please Enter Phone number!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        submit_phone(product_id,ph);
                        Toast.makeText(getContext(),"Request submited. Thank You!",Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(true);
        alertDialogAndroid.show();
    }

    public void submit_phone(int product_id,String phone){
        try {
            JSONObject js = new JSONObject();
            JSONObject enq = new JSONObject();
            enq.put("product_id", product_id);
            js.put("user_phone",phone);
            enq.put("enquiry",js);

            final String mRequestBody = enq.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.mangalorexpress.com/enquiries.json", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.v("LOG_VOLLEY", response);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

            };
            stringRequest.setShouldCache(false);
            RequestQueueSingleton.getInstance(getContext()).getRequestQueue().add(stringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
