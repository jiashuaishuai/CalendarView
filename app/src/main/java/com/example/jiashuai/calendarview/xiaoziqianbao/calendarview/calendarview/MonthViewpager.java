package com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionDateBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class MonthViewpager extends ViewPager {
    private MonthAdapter adapter;
    private MonthView calendarView, nextView, currView;
    private int selectPagerHeight, nextPagerHeigth;
    private int defaultHeight;
    private MonthView.OnClickMonthViewDayListener listener;
    //    public MonthPagerChangeListener monthPagerChangeListener;
    public int currPager;

    public MonthViewpager(Context context) {
        this(context, null);
    }

    public MonthViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new MonthAdapter(context);
        setAdapter(adapter);
//        setOffscreenPageLimit(2);
        defaultHeight = getResources().getDisplayMetrics().heightPixels / 3;//跟View一样
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
        this.listener = listener;
        adapter.setClickMonthViewDayListener(listener);
        setMonthPagerChangeListener();
    }

    public void setAdapterDate(List<CollectionDateBean.Data.YearData> yearDataList, String currdatemilliseconds) {
        List<CollectionDateBean.Data.YearData.MonthData> monthList = new ArrayList<>();
        //格式化当前时间
        Date date = new Date(Long.parseLong(currdatemilliseconds));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        String currDate = sdf.format(date);
        int pager = 0;
        for (CollectionDateBean.Data.YearData yearData : yearDataList) {
            for (CollectionDateBean.Data.YearData.MonthData monthData : yearData.getMonthList()) {
                monthList.add(monthData);
                if (monthData.getMonth().equals(currDate)) {
                    //记录当前时间为pager第几页
                    currPager = pager;
                }
                pager++;
            }
        }

        adapter.setCurrDate(date);
        adapter.setYearDataList(monthList);
    }

    //选中当前月
    public void selectPagerCurrMonth() {
        setCurrentItem(currPager);
    }

    //回到今天
    public void goToday() {
        setCurrentItem(currPager);
        adapter.selectToday(currPager);
    }

    public void setMonthPagerChangeListener() {
//        this.monthPagerChangeListener = monthPagerChangeListener;
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currView = adapter.getSonView(position);
                if (listener != null && currView != null)
                    listener.clickCallBacak(currView.getSelYear(), currView.getSelMonth(), currView.getSelDay());
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
