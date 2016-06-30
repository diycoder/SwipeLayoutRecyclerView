package com.diycoder.library.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by lq on 16/4/22.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private StaggeredGridLayoutManager.LayoutParams lp;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);//根据适配器获取条目的索引

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if (position == 0) {
            outRect.top = space;//header  top间距
        } else {
            lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            if (lp.getSpanIndex() == 0) {
                outRect.right = 0;
            }
        }
    }
}
