package com.hins.reader.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hins.reader.R;
import com.hins.reader.model.StoryDetail;
import com.hins.reader.network.ZhihuHttpHelper;
import com.hins.reader.ui.bigimage.BigImageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StoryDetailActivity extends AppCompatActivity {

    private static final String TAG = "StoryDetailActivity";

    public static final String STORY_ID = "com.hins.reader.ui.detail.story_id";

    @BindView(R.id.zhihu_story_image)
    ImageView mZhihuStoryImage;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.web_view)
    WebView mWebView;

    private int mStoryId;
    private StoryDetail mStoryDetail;



    public static void start(Context context, int id) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(STORY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupWebView();

        int mStoryId = getIntent().getIntExtra(STORY_ID, 0);

        initData(mStoryId);

    }

     public void initData(int id) {
        ZhihuHttpHelper.getInstance().getStoryDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoryDetail>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull StoryDetail storyDetail) {
                        mStoryDetail = storyDetail;

                        updateUI(storyDetail);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e(TAG, "onError: ", e);

                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");

                    }
                });

    }

    private void setupWebView() {
        WebSettings settings = mWebView.getSettings();
        //设置js交互的权限
        settings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这段js函数的功能就是注册监听，遍历所有的img标签，并添加onClick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
                mWebView.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "    objs[i].onclick=function()  " + "    {  "
                        + "        window.imagelistner.openImage(this.src);  "
                        + "    }  " + "}" + "})()");
            }
        });
    }

    class JavascriptInterface {
        Context mContext;

        JavascriptInterface(Context context) {
            mContext = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            BigImageActivity.start(mContext, img);
        }

    }

    private void updateUI(StoryDetail storyDetail) {
        mCollapsingToolbar.setTitle(storyDetail.getTitle());
        Glide.with(this).load(storyDetail.getImage()).into(mZhihuStoryImage);

        String html = converContent(storyDetail.getBody());

        mWebView.loadDataWithBaseURL("file:///android_asset", html, "text/html", "utf-8", null);

    }

    private String converContent(String content) {
        content = content.replaceAll("<div class=\"headline\">", "");
        content = content.replaceAll("<div class=\"img-place-holder\">", "");

        // 加载本地assets文件夹中的css,可在知乎日报中获取
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu.css\" type=\"text/css\">";

        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html>")
                .append("<html lang=\"zh\">")
                .append("<head>")
                .append("<meta charset=\"utf-8\"/>")
                .append(css)
                .append("</head>")
                .append("<body>")
                .append(content)
                .append("</body>")
                .append("</html>");

        return builder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}
