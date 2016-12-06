package com.android.hq.androidanimationdemo.touch3d;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-11-15.
 */
public class ThreeDimensTouchActivity extends Activity {
    private TopLayout mTopLayout;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private View mBottomLayout;
    private ProgressBar mBottomProgressBar;
    private WebView mBottomWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setElevation(0f);//取出ActionBar阴影
        setContentView(R.layout.activity_3d_touch_layout);
        mTopLayout = (TopLayout) findViewById(R.id.top_layout);
        mTopLayout.setVisibility(View.GONE);
        mWebView = (WebView) findViewById(R.id.top_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.loadUrl("http://m.sohu.com");
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return performLongClick(view);
            }
        });
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    dismiss3DTouchView();
                }
                return false;
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mBottomLayout = findViewById(R.id.bottom_layout);
        mBottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progress_bar);
        mBottomWebView = (WebView) findViewById(R.id.bottom_webview);
        mBottomWebView.getSettings().setJavaScriptEnabled(true);
        mBottomWebView.setWebViewClient(new WebViewClient());
        mBottomWebView.setWebChromeClient(mBottomWebChromeClient);
    }

    private boolean performLongClick(View v){
        if(!(v instanceof WebView)){
            return false;
        }
        final WebView webview = (WebView) v;
        WebView.HitTestResult result = webview.getHitTestResult();
        if (result == null) {
            return false;
        }

        int type = result.getType();
        switch (type){
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                final String url = result.getExtra();
                create3DTouchView(url);
                return true;
        }
        return false;
    }

    private void create3DTouchView(String url){
        mBottomProgressBar.setVisibility(View.VISIBLE);
        mTopLayout.setVisibility(View.VISIBLE);

        mTopLayout.setDrawView(mBottomLayout);
        mBottomWebView.clearView();
        mBottomWebView.loadUrl(url);
        mTopLayout.invalidate();
    }

    private void dismiss3DTouchView(){
        if(mTopLayout.isShown()){
            mBottomWebView.clearView();
            mBottomWebView.loadUrl("about:blank");
            mTopLayout.setVisibility(View.GONE);
            mTopLayout.setDrawView(null);
        }
    }


    private WebChromeClient mBottomWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(mTopLayout.isShown()){
                mBottomProgressBar.setProgress(newProgress);
                mTopLayout.invalidate();
                if(newProgress == 100 && mBottomProgressBar.getVisibility() == View.VISIBLE){
                    mBottomProgressBar.setVisibility(View.GONE);
                }
            }
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
            if(newProgress == 100 && mProgressBar.getVisibility() == View.VISIBLE){
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };
}
