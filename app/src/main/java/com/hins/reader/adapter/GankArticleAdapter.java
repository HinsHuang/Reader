package com.hins.reader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hins.reader.App;
import com.hins.reader.R;
import com.hins.reader.model.GankResult.GankResultBean;
import com.hins.reader.ui.detail.GankArticleDetailActivity;
import com.hins.reader.util.DensityUtil;
import com.hins.reader.util.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hins on 2017/10/19.
 */

public class GankArticleAdapter extends RecyclerView.Adapter<GankArticleAdapter.ViewHolder> {


    private Context mContext;
    private List<GankResultBean> mGankResultBeans;

    public GankArticleAdapter(List<GankResultBean> gankResultBean) {
        mGankResultBeans = gankResultBean;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        @BindView(R.id.article_image)
        ImageView mArticleImage;
        @BindView(R.id.article_title)
        TextView mArticleTitle;
        @BindView(R.id.article_author)
        TextView mArticleAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GankResultBean result = mGankResultBeans.get(position);
        // 设置16:9图片
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mArticleImage.getLayoutParams();
        layoutParams.height = (DensityUtil.getScreenWidth(App.application) - DensityUtil.dip2px(App.application, 30)) / 16 * 9;
        holder.mArticleImage.setLayoutParams(layoutParams);
        holder.mArticleImage.setVisibility(View.GONE);

        if (result.getImages() != null && result.getImages().size() > 0) {
            GlideUtils.newInstance().loadNetImage(result.getImages().get(0), holder.mArticleImage);
            holder.mArticleImage.setVisibility(View.VISIBLE);
        }

        holder.mArticleTitle.setText(result.getDesc());

        String author = TextUtils.isEmpty(result.getWho()) ? "佚名" : result.getWho();
        holder.mArticleAuthor.setText(author);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GankArticleDetailActivity.start(mContext, result.getDesc(), result.getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGankResultBeans.size();
    }
}
