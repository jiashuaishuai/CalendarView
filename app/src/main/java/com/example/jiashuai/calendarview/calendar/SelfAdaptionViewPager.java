package com.example.jiashuai.calendarview.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class SelfAdaptionViewPager extends ViewPager {
    private MonthAdapter adapter;
    private MonthView calendarView, nextView;
    private int selectPagerHeight, nextPagerHeigth;
    private int defaultHeight;

    public SelfAdaptionViewPager(Context context) {
        this(context,null);
    }

    public SelfAdaptionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdapter(new MonthAdapter(context));
        setOffscreenPageLimit(2);
        setCurrentItem(2);
        defaultHeight = getResources().getDisplayMetrics().heightPixels / 5 * 2;//跟View一样
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                nextView = adapter.getSonView(position + 1);
                calendarView = adapter.getSonView(position);

                if (calendarView != null && nextView != null) {
                    selectPagerHeight = calendarView.getCalendarViewHight() == 0 ? defaultHeight : calendarView.getCalendarViewHight();
                    nextPagerHeigth = nextView.getCalendarViewHight() == 0 ? defaultHeight : nextView.getCalendarViewHight();
                    setHeight((int) (selectPagerHeight * (1 - positionOffset) + nextPagerHeigth * positionOffset));
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = (MonthAdapter) adapter;
    }

    private void setHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.getMode(heightMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
