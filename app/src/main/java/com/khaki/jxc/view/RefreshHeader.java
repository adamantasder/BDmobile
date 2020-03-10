package com.khaki.jxc.view;

import android.view.View;


public interface RefreshHeader {
    /**
     * 下拉状态中
     *
     * @param deltaY 偏移量
     */
    void onMove(float deltaY);

    View getContainer();

    void refreshComplete();

    boolean isReleaseToRefresh();

    boolean isRefreshing();

    void onRefresh();

    void smoothScrollTo(int dest);

    int getMeasuredHeight();
}
