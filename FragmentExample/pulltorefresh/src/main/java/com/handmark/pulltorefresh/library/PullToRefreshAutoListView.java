package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;

/**
 * Created by wangxl02 on 2016/7/15.
 */
public class PullToRefreshAutoListView extends PullToRefreshListView {
    public PullToRefreshAutoListView(Context context) {
        super(context);
        init();
    }

    public PullToRefreshAutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshAutoListView(Context context, Mode mode) {
        super(context, mode);
        init();
    }

    public PullToRefreshAutoListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
        init();
    }

    private void init(){
        setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                {
                    if(totalItemCount > visibleItemCount){
                        if(firstVisibleItem + visibleItemCount == totalItemCount){
                            if(!isRefreshing()){
                                mCurrentMode = Mode.PULL_FROM_END;
                                mState = State.REFRESHING;
                                onRefreshing(true);
                            }
                        }
                    }
                }
            }
        });
    }
}
