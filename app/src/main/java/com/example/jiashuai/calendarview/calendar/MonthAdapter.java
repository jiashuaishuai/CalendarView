package com.example.jiashuai.calendarview.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class MonthAdapter extends PagerAdapter {
    private int mCount = 100;
    private SparseArray<MonthView> mViews;
    private Context mContext;
    private int currYear = 2017, currMonth = 4, currDay = 16;
    private MonthView.OnClickMonthViewDayListener listener;
    private List<Integer> hintList;

    public MonthAdapter(Context context) {
        mContext = context;
        mViews = new SparseArray<>();
        hintList = new ArrayList<>();
        hintList.add(2);
        hintList.add(14);
        hintList.add(19);
        hintList.add(24);
    }

    @Override
    public int getCount() {
        if (mCount < 7) {
            mCount = 7;//最少7页
        }
        return mCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews.get(position) == null) {
            int date[] = getYearAndMonth(position);
            MonthView calendarView = new MonthView(mContext, date[0], date[1]);
            calendarView.initCurrMonth(currYear, currMonth, currDay);
            calendarView.setmHintList(hintList);
            calendarView.setId(position);
            calendarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            calendarView.invalidate();
            calendarView.setOnClickMonthViewDayListener(listener);
            mViews.put(position, calendarView);
        }
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public int[] getYearAndMonth(int position) {
        int date[] = new int[2];
        int selectMonth = (currMonth - 1) + position - mCount/2;
        date[0] = CalendarUtils.plusMonths(currYear, selectMonth)[0];
        date[1] = CalendarUtils.plusMonths(currYear, selectMonth)[1];
        return date;
    }

    public MonthView getSonView(int position) {
        return mViews.get(position);
    }

    public void setListener(MonthView.OnClickMonthViewDayListener listener) {
        this.listener = listener;
    }
}
