package mangalorexpress.com;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.news.NewsCategoryFragment;
import com.news.NewsListFragment;
import com.store.ProductDetailsFragment;
import com.store.StoreListFragment;
import com.store.StoreShowFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by akhil on 30/9/18.
 */

public class MainActivityNew extends AppCompatActivity  {



    private LinearLayout news,stores,articles,social,real_estate,classifieds,health,contact;

    public String mCM;
    public ValueCallback<Uri> mUM;
    public ValueCallback<Uri[]> mUMA;
    Fragment fragment = null;
    Toolbar toolbar;
    public final static int FCR = 1;

    private static final int PERMISSION_REQUEST_CODE = 200;


    String feed_url = "http://www.mangalorexpress.com/cards";
    String services_url = "http://www.mangalorexpress.com/services";
    String classified_url = "http://www.mangalorexpress.com/classifieds";
    String about_us_url = "http://www.mangalorexpress.com/about_us";
    String contact_us_url = "http://www.mangalorexpress.com/contact_us";
    String home_url = "http://www.mangalorexpress.com/";
    String health_url = "http://www.mangalorexpress.com/service_categories/doctors-hospitals";
    String real_estate_url = "http://www.mangalorexpress.com/mangalore-rent-real-estate";
    String article_url = "http://www.mangalorexpress.com/articles";
    String wall_url = "http://www.mangalorexpress.com/wall";

    String news_cats[] = {"Mangalore","Karnataka","India","International","Auto","Bollywood","Sports","Gadgets","Beauty"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(MainActivityNew.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, 1);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitle("MANGALORE XPRESS");
        setSupportActionBar(toolbar);

        news=(LinearLayout)findViewById(R.id.news);
        stores=(LinearLayout)findViewById(R.id.stores);
        articles=(LinearLayout)findViewById(R.id.articles);
        social=(LinearLayout)findViewById(R.id.social);
        real_estate=(LinearLayout)findViewById(R.id.real_estate);
        classifieds=(LinearLayout)findViewById(R.id.classified);
        health=(LinearLayout)findViewById(R.id.health_care);
        contact=(LinearLayout)findViewById(R.id.contact_us);

        Initializing();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        (new SendToken(this)).execute();
    }


    public void Initializing(){

        Class fragmentClass =  OptionsFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }// Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }


    public void show_article(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new NewsListFragment();
        String backStateName = "ArticleListFragment";
        Bundle bundle = new Bundle();
        toolbar.setTitle("ARTICLES");
        bundle.putBoolean("is_article",true);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame, fragment)
                .addToBackStack(backStateName)
                .commit();
    }


    public void show_news(int index){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new NewsListFragment();
        String backStateName = "NewsListFragment";
        Bundle bundle = new Bundle();
        toolbar.setTitle((news_cats[index-1]).toUpperCase()+" NEWS");
        bundle.putString("category",news_cats[index-1]);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame, fragment)
                .addToBackStack(backStateName)
                .commit();
    }

    public  void show_page(int index){
        fragment = new OptionsFragment();
        Bundle bundle = null;
        String back_stack_name=null;

        switch (index){
            case 1:
                back_stack_name = "FEED";
                fragment = new NewsCategoryFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("NEWS");
                break;
            case 2:
                fragment = new StoreListFragment();
                back_stack_name = "STORE";
                fragment.setArguments(bundle);
                toolbar.setTitle("STORES");
                break;
            case 3:
                bundle = new Bundle();
                back_stack_name = "ArticleListFragment";
                bundle.putString("page_url", article_url);
                fragment = new NewsListFragment();
                toolbar.setTitle("ARTICLES");
                bundle.putBoolean("is_article",true);
                fragment.setArguments(bundle);
                break;
            case 4:
                bundle = new Bundle();
                bundle.putString("page_url", wall_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("SOCIAL");
                break;
            case 5:
                bundle = new Bundle();
                bundle.putString("page_url", real_estate_url);
                fragment = new ContentFragment();
                toolbar.setTitle("REAL ESTATE");
                fragment.setArguments(bundle);
                break;
            case 6:
                bundle = new Bundle();
                bundle.putString("page_url", classified_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("CLASSIFIEDS");
                break;
            case 7:
                bundle = new Bundle();
                bundle.putString("page_url", health_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("HEALTH CARE");
                break;
            case 8:
                bundle = new Bundle();
                bundle.putString("page_url", contact_us_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("CONTACT US");
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(back_stack_name == null){
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.frame, fragment).commit();
        }else {
            fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.frame, fragment).addToBackStack(back_stack_name).commit();
        }
    }


    public void show_products(int store_id,String store_name,String store_desc,String store_logo){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new StoreShowFragment();
        String backStateName = "StoreShowFragment";
        Bundle bundle = new Bundle();
        bundle.putString("store_name",store_name);
        bundle.putString("store_desc",store_desc);
        bundle.putString("store_logo",store_logo);
        bundle.putInt("store_id",store_id);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame, fragment)
                .addToBackStack(backStateName)
                .commit();
    }

    public void show_product_details(int product_id,String product_name,String product_description,String product_logo,int price,int mrp,String product_imgs[]){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment = new ProductDetailsFragment();
        String backStateName = "ProductDetailsFragment";
        Bundle bundle = new Bundle();
        bundle.putString("product_name",product_name);
        bundle.putString("product_description",product_description);
        bundle.putString("product_logo",product_logo);
        bundle.putInt("product_mrp",mrp);
        bundle.putInt("product_price",price);
        bundle.putStringArray("product_images",product_imgs);
        bundle.putInt("product_id",product_id);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.frame, fragment)
                .addToBackStack(backStateName)
                .commit();

    }


    public void ClickNavigation(View view){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        fragment = new OptionsFragment();
        Bundle bundle = null;

        switch (view.getId()){
            case R.id.news:
                bundle = new Bundle();
                bundle.putString("page_url", feed_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("NEWS");
                break;
            case R.id.stores:
                fragment = new StoreListFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("STORES");
                break;
            case R.id.articles:
                bundle = new Bundle();
                bundle.putString("page_url", article_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("ARTICLES");
                break;
            case R.id.social:
                bundle = new Bundle();
                bundle.putString("page_url", wall_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("SOCIAL");
                break;
            case R.id.real_estate:
                bundle = new Bundle();
                bundle.putString("page_url", real_estate_url);
                fragment = new ContentFragment();
                toolbar.setTitle("REAL ESTATE");
                fragment.setArguments(bundle);
                break;
            case R.id.classified:
                bundle = new Bundle();
                bundle.putString("page_url", classified_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("CLASSIFIEDS");
                break;
            case R.id.health_care:
                bundle = new Bundle();
                bundle.putString("page_url", health_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("HEALTH CARE");
                break;
            case R.id.contact_us:
                bundle = new Bundle();
                bundle.putString("page_url", contact_us_url);
                fragment = new ContentFragment();
                fragment.setArguments(bundle);
                toolbar.setTitle("CONTACT US");
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.frame, fragment).commit();



    }


    private void checkPermission() {
        String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};
        if (!hasPermissions(MainActivityNew.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean read_ext = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted && read_ext)
                        Toast.makeText(MainActivityNew.this, "Thank you", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(MainActivityNew.this, "You must allow these permissions to use this app", Toast.LENGTH_LONG).show();
                        checkPermission();
                    }
                }

                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivityNew.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        requestCode = requestCode & 0x0000ffff;

        Log.d("Akhil","RE "+requestCode);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;

            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == MainActivityNew.FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == MainActivityNew.FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            super.onBackPressed();
            return;
        }

        if(fragment instanceof ContentFragment){
            ContentFragment c = (ContentFragment) fragment;
            if(c.canGoBack()){
                c.goBack();
                return;
            }else{
                Initializing();
                return;
            }
        }

        new AlertDialog.Builder(new android.view.ContextThemeWrapper(this, R.style.popup_theme))
                .setTitle("EXIT")
                .setMessage("Are you sure you want to close?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

}