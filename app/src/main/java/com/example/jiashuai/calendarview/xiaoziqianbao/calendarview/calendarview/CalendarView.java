package com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionDateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/20.
 */

public class CalendarView extends LinearLayout {
    private MonthViewpager viewPager;
    private WeekBarView weekBarView;
    private CalendarSlidingTabLayout slidingTabLayout;
    private MonthView.OnClickMonthViewDayListener clickMonthViewDayListener;//点击日历监听
    private MonthViewpager.MonthPagerChangeListener monthPagerChangeListener;//日历pager改变监听
    private int currPager;
    private DisplayMetrics mDisplayMetrics;

    private List<String> monthList;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mDisplayMetrics = getResources().getDisplayMetrics();
        slidingTabLayout = new CalendarSlidingTabLayout(context, attrs, defStyleAttr);
        weekBarView = new WeekBarView(context);
        viewPager = new MonthViewpager(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,0);
        viewPager.setLayoutParams(params);
        addView(slidingTabLayout);
        addView(weekBarView);
        addView(viewPager);
    }

//    public void setMonthPagerChangeListener(MonthViewpager.MonthPagerChangeListener monthPagerChangeListener) {
//        this.monthPagerChangeListener = monthPagerChangeListener;
//        viewPager.setMonthPagerChangeListener(monthPagerChangeListener);
//    }

    public void setClickMonthViewDayListener(MonthView.OnClickMonthViewDayListener listener) {
        viewPager.setClickMonthViewDayListener(listener);
    }

    public void setAdapterDate(List<CollectionDateBean.Data.YearData> yearDataList, String currdatemilliseconds) {
        viewPager.setAdapterDate(yearDataList, currdatemilliseconds);
        monthList = new ArrayList<>();
        for (CollectionDateBean.Data.YearData yearData : yearDataList) {
            for (CollectionDateBean.Data.YearData.MonthData monthData : yearData.getMonthList()) {
                monthList.add(monthData.getMonth());
            }
        }
        setMonthTablArray(monthList);
        viewPager.selectPagerCurrMonth();
    }

    public void setMonthTablArray(List<String> list) {
        if (Utils.listIsNull(list))
            return;
        String[] tablArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            tablArray[i] = Integer.parseInt(s.split("-")[1]) + "月";
        }
        if (slidingTabLayout != null && viewPager != null && tablArray.length > 0) {
            slidingTabLayout.setViewPager(viewPager, tablArray);
        }
    }

    public void goToday() {
        viewPager.goToday();
    }

    public void jumpToMonth(String month) {
        int index = monthList.indexOf(month);
        viewPager.setCurrentItem(index);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
