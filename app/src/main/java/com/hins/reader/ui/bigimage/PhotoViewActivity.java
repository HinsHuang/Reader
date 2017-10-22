package com.hins.reader.ui.bigimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hins.reader.R;
import com.hins.reader.adapter.PhotoViewPagerAdapter;
import com.hins.reader.base.BaseActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewActivity extends BaseActivity {

    private static final String TAG = "PhotoViewActivity";

    private static final String IMAGE_URL_LIST = "com.hins.reader.ui.bigimage.image_url_list";
    private static final String IMAGE_POS = "com.hins.reader.ui.bigimage.image_pos";

    @BindView(R.id.photo_view_pager)
    ViewPager mPhotoViewPager;
    @BindView(R.id.cur_position)
    TextView mCurPosition;
    @BindView(R.id.photo_count)
    TextView mPhotoCount;
    @BindView(R.id.download_photo)
    ImageView mDownloadPhoto;

    private List<String> mImageUrls;
    private int mPosition;

    public static void start(Context context, Serializable imageUrls, int position) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(IMAGE_URL_LIST, imageUrls);
        intent.putExtra(IMAGE_POS, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_views);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        mImageUrls = (List<String>) getIntent().getSerializableExtra(IMAGE_URL_LIST);
        if (mImageUrls.size() == 0) {
            finish();
        }
        mPosition = getIntent().getIntExtra(IMAGE_POS, 0);

    }

    private void initView() {
        mCurPosition.setText(String.valueOf(mPosition + 1));
        mPhotoCount.setText(String.valueOf(mImageUrls.size()));
        mPhotoViewPager.setAdapter(new PhotoViewPagerAdapter(this, mImageUrls));
    }

    private void initListener() {
        mPhotoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                mCurPosition.setText(String.valueOf(mPosition + 1));
                mPhotoViewPager.setTag(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPhotoViewPager.setCurrentItem(mPosition);
    }
}
