package com.hert.legacyofat.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Custom view pager that cant be touch controlled.
 */
public class NonSwipeableViewPager extends ViewPager {

    /**
     * Instantiates a new Non swipeable view pager.
     *
     * @param context the context
     */
    public NonSwipeableViewPager(Context context) {
        super(context);
        setMyScroller();
    }

    /**
     * Instantiates a new Non swipeable view pager.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The type My scroller.
     */
    public class MyScroller extends Scroller {
        /**
         * Instantiates a new My scroller.
         *
         * @param context the context
         */
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }
}