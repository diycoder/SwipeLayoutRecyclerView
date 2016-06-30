package com.diycoder.library.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by lq on 16/6/30.
 */
public abstract class ScrollListener extends HidingScrollListener {
    public ScrollListener(RecyclerView.LayoutManager layoutManager) {
        super(layoutManager);
        loadMore = true;
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onHide() {

    }

    //加载更多
    public abstract void onLoadMore();
}
