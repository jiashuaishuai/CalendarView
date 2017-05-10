package com.example.jiashuai.calendarview.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class MonthViewpager extends ViewPager {
    private MonthAdapter adapter;
    private MonthView calendarView, nextView, currView;
    private int selectPagerHeight, nextPagerHeigth;
    private int defaultHeight;
    private MonthView.OnClickMonthViewDayListener listener;
    public MonthPagerChangeListener monthPagerChangeListener;
    public int currPager;

    public MonthViewpager(Context context) {
        this(context, null);
    }

    public MonthViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new MonthAdapter(context);
        setAdapter(adapter);
//        setOffscreenPageLimit(2);
        defaultHeight = getResources().getDisplayMetrics().heightPixels / 5 * 2;//跟View一样
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(defaultHeight, MeasureSpec.getMode(heightMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        nextView = adapter.getSonView(position + 1);
        calendarView = adapter.getSonView(position);
        if (calendarView != null && nextView != null) {
            selectPagerHeight = calendarView.getCalendarViewHight() == 0 ? defaultHeight : calendarView.getCalendarViewHight();
            nextPagerHeigth = nextView.getCalendarViewHight() == 0 ? defaultHeight : nextView.getCalendarViewHight();
            setHeight((int) (selectPagerHeight * (1 - offset) + nextPagerHeigth * offset));
        }
    }

    private void setHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
    }

    public void setClickMonthViewDayListener(MonthView.OnClickMonthViewDayListener listener) {
        adapter.setClickMonthViewDayListener(listener);
    }

    public void setCurrPager(int pager) {
        currPager = pager;
        setCurrentItem(pager);
        adapter.setCurrPager(pager);
    }

    public void setMonthPagerChangeListener(final MonthPagerChangeListener monthPagerChangeListener) {
        this.monthPagerChangeListener = monthPagerChangeListener;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currView = adapter.getSonView(position);
                if (monthPagerChangeListener != null && currView != null)
                    monthPagerChangeListener.pagerChangeCallBack(position, currView.getSelYear(), currView.getSelMonth(), currView.getSelDay());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public interface MonthPagerChangeListener {
        void pagerChangeCallBack(int position, int selectYear, int selectMonth, int selectDay);
    }
}
