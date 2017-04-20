package com.example.jiashuai.calendarview.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class MonthAdapter extends PagerAdapter {
    private int mCount = 15;
    private SparseArray<MonthView> mViews;
    private Context mContext;
    private TypedArray mArray;
    private int year = 2017, month, day = 19;
//    private CalendarView calendarView;

    public MonthAdapter(Context context) {
        mContext = context;
        mViews = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mViews.get(position) == null) {
            int date[] = getYearAndMonth(position);
            MonthView calendarView = new MonthView(mContext, date[0], date[1]);
            calendarView.setId(position);
            calendarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            calendarView.invalidate();
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
        if (mCount < 7) {
            mCount = 7;
        }
        if (year > 2017) {
            month++;
        } else if (year < 2017) {
            month--;
        } else {
            month = 3 + position - 2;
        }
        //月按照java规则从0月开始到11月
        if (month > 11) {//月大于11加一年，
            year++;
            month = month - 12;
        }
        if (month < 0) {//月小于0减一年
            year--;
            month = 12 + month;//
        }
        date[0] = year;
        date[1] = month;
        return date;
    }

    public MonthView getSonView(int position) {
        return mViews.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }
}
