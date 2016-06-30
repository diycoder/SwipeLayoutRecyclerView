package com.diycoder.library.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/*
* This class is a ScrollListener for RecyclerView that allows to show/hide
* views when list is scrolled. It assumes that you have added a header
* to your list. @see pl.michalz.hideonscrollexample.adapter.partone.RecyclerAdapter
* */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_THRESHOLD = 10;

    private int mScrolledDistance = 0;
    private boolean mControlsVisible = true;
    private RecyclerView.LayoutManager mLayoutManager;
    public static boolean loadMore = true;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    public HidingScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = getFirstVisiblePosition(mLayoutManager);

        if (firstVisibleItem == 0) {
            if (!mControlsVisible) {
                onShow();
                mControlsVisible = true;
            }
        } else {
            if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                onHide();
                mControlsVisible = false;
                mScrolledDistance = 0;
            } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                onShow();
                mControlsVisible = true;
                mScrolledDistance = 0;
            }
        }
        if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
            mScrolledDistance += dy;
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if ((visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE &&
                (getLastVisiblePosition(layoutManager)) >= totalItemCount - 1)) {//1代表footer数，也可预加载，大于footer数即可
            if (loadMore) {
                onLoadMore();//加载page自己可以定义下
                loadMore = !loadMore;
            }
        }
    }


    /**
     * 获取第一条展示的位置
     *
     * @return
     */
    private int getFirstVisiblePosition(RecyclerView.LayoutManager mLayoutManager) {
        int position;
        if (mLayoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mLayoutManager;
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }


    /**
     * 获取最后一条展示索引
     *
     * @param layoutManager
     * @return
     */
    private int getLastVisiblePosition(RecyclerView.LayoutManager layoutManager) {
        int position;
        if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager mlayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = mlayoutManager.findLastVisibleItemPositions(new int[mlayoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = layoutManager.getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    public static void setLoadMore(boolean loadMore) {
        HidingScrollListener.loadMore = loadMore;
    }

    //隐藏某个控件
    public abstract void onShow();

    //显示某个控件
    public abstract void onHide();

    //加载更多
    public abstract void onLoadMore();
}
