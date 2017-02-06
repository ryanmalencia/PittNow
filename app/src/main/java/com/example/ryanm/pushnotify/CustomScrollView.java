package com.example.ryanm.pushnotify;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    private ScrollListener scrollViewListener = null;

    public CustomScrollView(Context context, AttributeSet attrs,
                            int defStyle) {
        super(context, attrs, defStyle);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public void setScrollViewListener(ScrollListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        View view = getChildAt(getChildCount() - 1);

        int diff = (view.getBottom() - (getHeight() + getScrollY()));

        if (diff <= 600 && scrollViewListener != null) {
            scrollViewListener.onScrollBottomedOut();
        }
    }
}