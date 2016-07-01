package com.diy.swipelayoutrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diy.swipelayoutrecyclerview.R;
import com.diy.swipelayoutrecyclerview.entity.TestData;
import com.diycoder.library.adapter.BaseAdapter;

import java.util.Date;
import java.util.Random;

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
            Date date = new Date();
            int i = new Random().nextInt(2);
            long hours = date.getHours() + i;
            int minutes = date.getMinutes() + i;

            holder.rowButton.setText(hours + ":" + minutes);
        }
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mainText;
        private final TextView subText;
        private final ImageView userIcon;
        private final Button rowButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mainText = (TextView) itemView.findViewById(R.id.mainText);
            subText = (TextView) itemView.findViewById(R.id.subText);
            userIcon = (ImageView) itemView.findViewById(R.id.userIcon);
            rowButton = (Button) itemView.findViewById(R.id.rowButton);
        }
    }

}
