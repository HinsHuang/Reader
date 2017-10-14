package com.hins.reader.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hins.reader.R;

public class StoryDetailActivity extends AppCompatActivity {

    public static final String STORY_ID = "com.hins.reader.ui.detail.story_id";

    public static void start(Context context, int id) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(STORY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
    }
}
