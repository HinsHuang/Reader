package com.hins.reader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hins.reader.R;

/**
 * Created by Hins on 2017/10/21.
 */

public class ErrorHandleAdapter extends RecyclerView.Adapter<ErrorHandleAdapter.ViewHolder>{

    private Context mContext;
    private String mErrorMsg;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View errorView;
        private TextView errorMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            errorView = itemView;
            errorMsg = (TextView) itemView.findViewById(R.id.error_msg);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.error_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
