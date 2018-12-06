package custom;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import mangalorexpress.com.R;

public class WebDialog extends Dialog
{

    static final int                      BLUE                  = 0xFF6D84B4;
    static final float[]                  DIMENSIONS_DIFF_LANDSCAPE =
                                                                    { 20, 60 };
    static final float[]                  DIMENSIONS_DIFF_PORTRAIT  =
                                                                    { 40, 60 };
    static final FrameLayout.LayoutParams   FILL                    = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    static final int                      MARGIN                    = 4;
    static final int                      PADDING                   = 2;
    static final String                   DISPLAY_STRING            = "touch";

    private String                        mUrl;

    private WebView mWebView;
    private LinearLayout mContent;
    private ProgressBar mProgressBar;
    private Context mContext;

    public WebDialog(Context context, String url)
    {
        super(context);
        this.mContext = context;
        mUrl = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        mContent = new LinearLayout(getContext());
        mContent.setBackgroundColor(Color.WHITE);
        mContent.setOrientation(LinearLayout.VERTICAL);
        setUpTitle();
        setUpWebView();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int orientation = getContext().getResources().getConfiguration().orientation;
        float[] dimensions = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? DIMENSIONS_DIFF_LANDSCAPE : DIMENSIONS_DIFF_PORTRAIT;
        addContentView(mContent, new LinearLayout.LayoutParams(display.getWidth() - ((int) (dimensions[0] * scale + 0.5f)), display.getHeight() - ((int) (dimensions[1] * scale + 0.5f))));
    }

    private void setUpTitle()
    {
        mProgressBar = new ProgressBar(getContext(),null,
                android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,7));
        mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progressbar_states));
        mProgressBar.setIndeterminate(false);
        mContent.addView(mProgressBar);
    }

    private void setUpWebView()
    {
        mWebView = new WebView(getContext());
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setWebViewClient(new WebDialog.DialogWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);

        System.out.println(" mURL = "+mUrl);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something on page loading started
                // Visible the progressbar
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                // Do something when page loading finished

            }

        });


        mWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                mProgressBar.setProgress(newProgress);
                if(newProgress == 100){
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(mUrl);
        mWebView.setLayoutParams(FILL);
        mContent.addView(mWebView);
    }

    private class DialogWebViewClient extends WebViewClient
    {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
        {
            super.onReceivedError(view, errorCode, description, failingUrl);
            WebDialog.this.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);
            String title = mWebView.getTitle();

        }

    }
}