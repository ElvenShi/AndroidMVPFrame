Refreshlayout使用方式介绍

1、如何布局？

事例：

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_recycleview_test"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.mobcb.refreshlayout.BGARefreshLayout
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!--注意这里ScrollView的height必须设置成0dp,同时需要设置weight值为1。否则底部正在加载的view无法显示-->
        <android.support.v7.widget.RecyclerView
            android:id="@+id/recycler_view"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            android:overScrollMode="never"
            android:scrollbars="none">

        </android.support.v7.widget.RecyclerView>
    </com.mobcb.refreshlayout.BGARefreshLayout>

</RelativeLayout>



2、刷新布局BGARefreshLayout相关api

BGARefreshLayout.setDelegate(BGARefreshLayoutDelegate delegate); // 下拉或者上拉时的回调代理

/**
 * BGANormalRefreshViewHolder(Context,boolean)
 * 第二个参数表示上拉加载更多是否可用
 */
BGANormalRefreshViewHolder refreshViewHolder =  new BGANormalRefreshViewHolder(Context context, boolean isLoadingMoreEnabled);
BGARefreshLayout.setRefreshViewHolder(new BGANormalRefreshViewHolder(YHKTApplication.getContext(), true));


BGARefreshLayout.beginLoadingMore() // 通过点击方式下拉刷新
BGARefreshLayout.beginLoadingMore() //通过点击方式上拉刷新

BGARefreshLayout.endRefreshing();//结束下拉刷新
BGARefreshLayout.endLoadingMore();// 结束上拉刷新

//设置下拉刷新是否可用
BGARefreshLayout.setPullDownRefreshEnable(boolean pullDownRefreshEnable)


注：其他一些api可在使用过程中摸索，这里就不一一列举