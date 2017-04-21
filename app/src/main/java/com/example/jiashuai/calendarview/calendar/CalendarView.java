package com.example.jiashuai.calendarview.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by JiaShuai on 2017/4/20.
 */

public class CalendarView extends LinearLayout {
    private MonthViewpager viewPager;
    private WeekBarView weekBarView;
    private MonthView.OnClickMonthViewDayListener listener;
    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewPager = new MonthViewpager(context);
        weekBarView = new WeekBarView(context);
        addView(weekBarView);
        addView(viewPager);
    }

    public void setListener(MonthView.OnClickMonthViewDayListener listener) {
        viewPager.setListener(listener);
    }
}
