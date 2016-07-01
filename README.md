# SwipeLayoutRecyclerView
基于RecyclerView的封装支持下拉刷新，上拉加载更多，添加header，添加footer

仿qq侧拉删除

 ![image](https://github.com/diycoder/SwipeLayoutRecyclerView/blob/master/recyclerview/screenshot/screenshots3.gif)   
 给RecyclerView添加触摸事件监听
 
 //添加触摸监听
 
        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener
                .setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {//item点击监听
                        Toast.makeText(mContext, "Row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {//button点击监听
                        Toast.makeText(getApplicationContext(), "Button in row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setSwipeOptionViews(R.id.start, R.id.thumb, R.id.favorite)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {//侧拉出现的三个按钮监听事件
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
 
 
 
//添加Header

![image](https://github.com/diycoder/SwipeLayoutRecyclerView/blob/master/recyclerview/screenshot/screenshots1.gif)

//添加Header和Footer

![image](https://github.com/diycoder/SwipeLayoutRecyclerView/blob/master/recyclerview/screenshot/screenshots2.gif)

1、BaseAdapter        基本组成

2、HeaderAdapter     添加Header

3、HeaderBottomAdapter    添加Header和Footer
当没有更多数据的时候
可以通过adapter.setHasMoreData(false)；设置是否有更多数据

当需要隐藏Footer的时候
可以通过adapter.setHasFooter(false); 隐藏Footer

下拉刷新和SwipeRefreshLayout一样

加载更多,可以监听RecyclerView的滚动事件，通过实现ScrollListener即可

    recyclerView.addOnScrollListener(scrollListener);
    private ScrollListener scrollListener = new ScrollListener(mLayoutManager) {
        @Override
        public void onLoadMore() {
            loadMore();
            currentPage++;
            Toast.makeText(mContext, "加载更多" + currentPage, Toast.LENGTH_SHORT).show();
        }
    };
 
 
 
 
 
 
 加载成功后需要将加载更多开关打开
   ScrollListener.setLoadMore(!ScrollListener.loadMore);
  
