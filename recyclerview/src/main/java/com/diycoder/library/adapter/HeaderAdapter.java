package com.diycoder.library.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.diycoder.library.R;
import com.diycoder.library.widget.ProgressWheel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lq on 16/6/29.
 */
public abstract class HeaderAdapter<M, HVH extends RecyclerView.ViewHolder, IVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    /**
     * M  item  数据类型
     * HVH  header  viewHolder
     * IVH  Item    viewHolder
     * BVH  bottom  viewHolder
     */

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 3;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private boolean hasMoreData = true;
    private boolean hasFooter = true;
    private List<M> dataList = new ArrayList<>();
    private String hint;

    public HeaderAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        hint = context.getString(R.string.no_more_data);
    }

    //内容长度
    public int getContentItemCount() {
        return dataList.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }


    //判断是否是Footer
    public boolean isFooter(int position) {
        return position == mHeaderCount + getContentItemCount();
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (isFooter(position)) {
            //Footer
            return ITEM_TYPE_FOOTER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //Footer ViewHolder
    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public final ProgressWheel mProgressView;
        private final TextView loadMore;

        public FooterViewHolder(View view) {
            super(view);
            mProgressView = (ProgressWheel) itemView.findViewById(R.id.progress_view);
            loadMore = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (isFullSpanType(adapter.getItemViewType(position))) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        int type = getItemViewType(position);
        if (isFullSpanType(type)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                lp.setFullSpan(true);//瀑布流单独占一行设置方式
            }
        }
    }

    private boolean isFullSpanType(int type) {
        return type == ITEM_TYPE_FOOTER || type == ITEM_TYPE_HEADER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return onCreateItemViewHolder(parent, viewType);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            return new FooterViewHolder(mLayoutInflater.inflate(R.layout.item_view_load_more, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderView(position)) {
            onBindHeaderViewHolder(((HVH) holder), position);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            //没有更多数据
            if (!hasMoreData) {
                footerViewHolder.mProgressView.setVisibility(View.GONE);
                footerViewHolder.loadMore.setText(hint);
            }
        } else {
            onBindItemViewHolder(((IVH) holder), position);
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + (hasFooter ? 1 : 0);
    }


    public void setHasMoreData(boolean isMoreData) {
        if (this.hasMoreData != isMoreData) {
            this.hasMoreData = isMoreData;
            notifyDataSetChanged();
        }
    }

    //设置底部加载提示信息
    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setHint(int resId) {
        this.hint = mContext.getResources().getString(resId);
    }

    public void setDataList(List<M> data) {
        if (data == null || data.size() == 0)
            return;
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public M getItemData(int positon) {
        return dataList.get(positon - mHeaderCount);
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        if (this.hasFooter != hasFooter) {
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreDataAndFooter(boolean hasMoreData, boolean hasFooter) {
        if (this.hasMoreData != hasMoreData || this.hasFooter != hasFooter) {
            this.hasMoreData = hasMoreData;
            this.hasFooter = hasFooter;
            notifyDataSetChanged();
        }
    }

    //item   ViewHolder 实现
    public abstract IVH onCreateItemViewHolder(ViewGroup parent, int viewType);

    //item   ViewHolder data实现
    public abstract void onBindItemViewHolder(final IVH holder, int position);

    //header  ViewHolder 实现
    public abstract HVH onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    //header  ViewHolder  data实现
    public abstract void onBindHeaderViewHolder(final HVH holder, int position);

}
