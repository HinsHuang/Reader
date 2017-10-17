package com.hins.reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hins.reader.App;
import com.hins.reader.R;
import com.hins.reader.model.GankResult.GankResultBean;
import com.hins.reader.util.GlideUtils;

import java.util.List;

/**
 * Created by Hins on 2017/10/16.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private Context mContext;
    private List<GankResultBean> mGankResultBeans;

    public PhotoAdapter(List<GankResultBean> gankResultBean) {
        mGankResultBeans = gankResultBean;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView gankPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            gankPhoto = (ImageView) itemView.findViewById(R.id.gank_photo);
            gankPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.photo_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.gankPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Toast.makeText(mContext, ":" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GankResultBean result = mGankResultBeans.get(position);
//        Glide.with(mContext).asBitmap().load(result.getUrl()).into(holder.gankPhoto);

        Glide.with(App.application);
        GlideUtils.newInstance().loadAutoHeightNetImage(result.getUrl(), holder.gankPhoto);

    }

    @Override
    public int getItemCount() {
        return mGankResultBeans.size();
    }
}
