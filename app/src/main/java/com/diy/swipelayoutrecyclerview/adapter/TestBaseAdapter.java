package com.diy.swipelayoutrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        return new ItemViewHolder(mLayoutInflater.inflate(R.layout.item_test_item, parent, false));
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int position) {
        TestData data = getItemData(position);
        if (data != null) {
            String title = data.title;
            Glide.with(mContext).load(title).centerCrop().into(holder.textView);
        }
    }


    //内容 ViewHolder
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (ImageView) itemView.findViewById(R.id.tv_item_text);
        }
    }

}
