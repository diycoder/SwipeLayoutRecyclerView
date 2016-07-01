package com.diy.swipelayoutrecyclerview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.diy.swipelayoutrecyclerview.R;
import com.diy.swipelayoutrecyclerview.adapter.TestBaseAdapter;
import com.diy.swipelayoutrecyclerview.api.Constant;
import com.diy.swipelayoutrecyclerview.entity.TestData;
import com.diycoder.library.listener.RecyclerTouchListener;
import com.diycoder.library.listener.ScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lq on 16/6/29.
 */
public class SimpleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private Activity mContext;
    private List<TestData> data = new ArrayList<>();
    private TestBaseAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private int currentPage = 0;
    private RecyclerTouchListener onTouchListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mContext = this;

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 15; i++) {
            TestData testData = new TestData();
            testData.icon = Constant.imags[i];
            testData.nick = Constant.nick[i];
            testData.msg = Constant.msg[i];
            testData.height = (int) (200 + Math.random() * 400);
            data.add(testData);
        }
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.RefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 12, false));
        mAdapter = new TestBaseAdapter(mContext);
        mAdapter.setDataList(data);
        mAdapter.setHasMoreData(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(scrollListener);
        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener
                .setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Toast.makeText(mContext, "Row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        Toast.makeText(getApplicationContext(), "Button in row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setSwipeOptionViews(R.id.start, R.id.thumb, R.id.favorite)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        String message = "";
                        if (viewID == R.id.start) {
                            message += "收 藏";
                        } else if (viewID == R.id.thumb) {
                            message += "点 赞";
                        } else if (viewID == R.id.favorite) {
                            message += "喜 欢";
                        }
                        message += " position-> " + (position + 1);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
        recyclerView.addOnItemTouchListener(onTouchListener);
    }

    private ScrollListener scrollListener = new ScrollListener(mLayoutManager) {
        @Override
        public void onLoadMore() {
            loadMore();
            currentPage++;
            Toast.makeText(mContext, "加载更多 page->" + currentPage, Toast.LENGTH_SHORT).show();
        }
    };

    private void loadMore() {
        //模拟加载更多
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage == 2) {
                    mAdapter.setHasMoreDataAndFooter(false, true);
                    return;
                }
                initData();
                mAdapter.setDataList(data);
                ScrollListener.setLoadMore(!ScrollListener.loadMore);
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        //避免刷新过快
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
