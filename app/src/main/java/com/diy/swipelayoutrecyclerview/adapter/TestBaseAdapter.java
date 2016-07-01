package com.diy.swipelayoutrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diy.swipelayoutrecyclerview.R;
import com.diy.swipelayoutrecyclerview.entity.TestData;
import com.diycoder.library.adapter.BaseAdapter;

/**
 * Created by lq on 16/6/29.
 */
public class TestBaseAdapter extends BaseAdapter<TestData, TestBaseAdapter.ItemViewHolder> {

    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public TestBaseAdapter(Context context) {
        super(context);
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.recycler_row, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        TestData data = getItemData(position);
        if (data != null) {
            String url = data.icon;
            Glide.with(mContext).load(url).centerCrop().into(holder.userIcon);
            holder.mainText.setText(data.nick);
            holder.subText.setText(data.msg);
        }
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mainText;
        private final TextView subText;
        private final ImageView userIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mainText = (TextView) itemView.findViewById(R.id.mainText);
            subText = (TextView) itemView.findViewById(R.id.subText);
            userIcon = (ImageView) itemView.findViewById(R.id.userIcon);
        }
    }

}
