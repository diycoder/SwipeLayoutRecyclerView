package com.diy.swipelayoutrecyclerview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.diy.swipelayoutrecyclerview.api.Constant;
import com.diy.swipelayoutrecyclerview.R;
import com.diy.swipelayoutrecyclerview.entity.TestData;
import com.diy.swipelayoutrecyclerview.adapter.TestHeaderBottomAdapter;
import com.diycoder.library.decoration.SpacesItemDecoration;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lq on 16/6/29.
 */
public class HeaderAndBottomActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private Activity mContext;
    private List<TestData> data = new ArrayList<>();
    private TestHeaderBottomAdapter mAdapter;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mContext = this;

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 19; i++) {
            TestData testData = new TestData();
            testData.title = Constant.imags[i];
            testData.height = (int) (200 + Math.random() * 400);
            data.add(testData);
        }
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.RefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRefreshLayout.setOnRefreshListener(this);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(this.mLayoutManager);
        mAdapter = new TestHeaderBottomAdapter(mContext);
        mAdapter.setDataList(data);
        mAdapter.setHasMoreData(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(12));
        recyclerView.setAdapter(mAdapter);

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
