package com.hins.reader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hins.reader.R;
import com.hins.reader.model.TopStory.TopStoryBean;

import java.util.List;

/**
 * Created by Hins on 2017/10/10.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;

    private Context mContext;
    private List<TopStoryBean> mTopStories;

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView titleTextView;
        private ImageView titleImage;

        public ViewHolder(View itemView) {
            super(itemView);

            if (itemView == mHeaderView) return;

            cardView = (CardView) itemView;
            titleTextView = (TextView) itemView.findViewById(R.id.story_title);
            titleImage = (ImageView) itemView.findViewById(R.id.story_image);
        }
    }

    public HomeAdapter(List<TopStoryBean> topStories) {
        mTopStories = topStories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER) return new ViewHolder(mHeaderView);

        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.story_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getRealPosition(holder);
                TopStoryBean title = mTopStories.get(position);
//                ZhihuDetailStoryActivity.start(mContext, title.getId());
                Toast.makeText(mContext, "Clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(getItemViewType(position) == TYPE_HEADER) return;

        int pos = getRealPosition(holder);

        TopStoryBean title = mTopStories.get(pos);
        holder.titleTextView.setText(title.getTitle());
        Glide.with(mContext).load(title.getImage()).into(holder.titleImage);
    }

    private int getRealPosition(ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mTopStories.size() : mTopStories.size() + 1;
    }
}
