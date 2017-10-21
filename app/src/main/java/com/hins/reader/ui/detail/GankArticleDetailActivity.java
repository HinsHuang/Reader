package com.hins.reader.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hins.reader.R;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GankArticleDetailActivity extends AppCompatActivity {

    private static final String TAG = "GankArticleDetailActivi";
    private static final String GANK_ARTICLE_URL = "com.hins.reader.ui.detail.gank_articl_url";
    private static final String GANK_ARTICLE_TITLE = "com.hins.reader.ui.detail.gank_articl_title";

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.web_view)
    WebView mWebView;

    private String mTitle;
    private String mUrl;


    public static void start(Context context, String title, String url) {
        Intent intent = new Intent(context, GankArticleDetailActivity.class);
        intent.putExtra(GANK_ARTICLE_TITLE, title);
        intent.putExtra(GANK_ARTICLE_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_article_detail);
        ButterKnife.bind(this);

        mTitle = getIntent().getStringExtra(GANK_ARTICLE_TITLE);
        mUrl = getIntent().getStringExtra(GANK_ARTICLE_URL);

        setSupportActionBar(mToolBar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }

        mProgressBar.setMax(100);

        mWebView.setBackgroundColor(0);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                actionBar.setTitle(title);
            }
        });
        mWebView.loadUrl(mUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.browse:
                openInBrowser();
                break;
            case R.id.share:
                share();
                break;
            default:
                break;
        }

        return true;
    }

    private void openInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
        startActivity(intent);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        StringBuilder builder = new StringBuilder();
        builder.append(mTitle)
                .append(" ")
                .append(mUrl)
                .append(" via by Reader");
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());
        startActivity(Intent.createChooser(intent, "分享至"));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /*
          设置menu的文字和图片同时显示
        */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}
