package com.hins.reader.ui.bigimage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.hins.reader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigImageActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "com.hins.reader.ui.bigimage.image_url";
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;

    public static void start(Context context, String imageUrl) {
        Intent intent = new Intent(context, BigImageActivity.class);
        intent.putExtra(IMAGE_URL, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra(IMAGE_URL);
        Glide.with(this).load(url).into(mPhotoView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
