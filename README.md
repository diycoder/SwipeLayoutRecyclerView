# SwipeLayoutRecyclerView
基于RecyclerView的封装支持下拉刷新，上拉加载更多，添加header，添加footer

基本组成

 ![image](https://github.com/diycoder/SwipeLayoutRecyclerView/tree/master/recyclerview/screenshot/screenshots.gif)
 
//添加Header

![image](https://github.com/diycoder/SwipeLayoutRecyclerView/tree/master/recyclerview/screenshot/screenshots1.gif)

//添加Header和Footer

![image](https://github.com/diycoder/SwipeLayoutRecyclerView/tree/master/recyclerview/screenshot/screenshots2.gif)

1、BaseAdapter        基本组成

2、HeaderAdapter     添加Header

3、HeaderBottomAdapter    添加Header和Footer
当没有更多数据的时候，可以通过adapter.setHasMoreData(false)；设置是否有更多数据

当需要隐藏Footer的时候，可以通过adapter.setHasFooter(false); 隐藏Footer

下拉刷新和SwipeRefreshLayout一样

加载更多,可以监听RecyclerView的滚动事件，通过实现ScrollListener即可

 recyclerView.addOnScrollListener(scrollListener);
 private ScrollListener scrollListener = new ScrollListener(mLayoutManager) {
        @Override
        public void onLoadMore() {
            loadMore();//加载更多
        }
 };
 
 加载成功后需要将加载更多开关打开
 
  ScrollListener.setLoadMore(!ScrollListener.loadMore);
  
