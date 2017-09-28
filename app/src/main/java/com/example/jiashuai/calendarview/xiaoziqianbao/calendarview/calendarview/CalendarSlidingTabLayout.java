package com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;

/**
 * Created by JiaShuai on 2017/4/25.
 */

public class CalendarSlidingTabLayout extends SlidingTabLayout {
    private int mHeight;

    public CalendarSlidingTabLayout(Context context) {
        this(context, null);
    }

    public CalendarSlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarSlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
        int mDpi = (int) mDisplayMetrics.density;
        mHeight = 48 * mDpi;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 48 * mDpi));
        setBackgroundColor(Color.parseColor("#ffffff"));
        setTextsize(15);
        setTabPadding(1);
        setDividerPadding(1);
        setDividerColor(Color.parseColor("#ffffff"));
        setIndicatorColor(Color.parseColor("#ffffff"));
        setTextSelectColor(Color.parseColor("#f05858"));
        setTextUnselectColor(Color.parseColor("#333333"));
        setIndicatorWidthEqualTitle(true);
        setTabSpaceEqual(false);
        setTabWidth(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        setTabWidthEqualSize(7);//tabwidth屏幕7分之一

        super.onDraw(canvas);
    }
    public int getmHeight() {
        return mHeight;
    }
}
