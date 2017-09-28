package com.example.jiashuai.calendarview.xiaoziqianbao3_4.calendar.calendar.list;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jiashuai.calendarview.R;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionBean;


/**
 * Created by JiaShuai on 2017/9/22.
 */

public class CalendarListViewPager extends ViewPager {
    private ListViewPagerAdapter adapter;
    private View nullFootVuew;

    public CalendarListViewPager(Context context) {
        this(context, null);
    }

    public CalendarListViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ListViewPagerAdapter(context);
        setAdapter(adapter);
        nullFootVuew = new View(context);
    }

    public CollectionDayAdapter getListAdapter(int position) {
        return adapter.getAdapter(position);
    }

    public CalendarListView getCurrentList() {
        return adapter.getListView(getCurrentItem());
    }

    public LinearLayout getHintLayout(int position) {
        return adapter.getHintLayout(position);
    }

    public void setListData(CollectionBean bean) {
//        getListAdapter(0).upDateList(null);
//        getListAdapter(1).upDateList(null);
//
//        if (bean != null && !Utils.listIsNull(bean.getData().getPhaseList())) {
//            getHintLayout(0).setVisibility(GONE);
//            getListAdapter(0).upDateList(bean.getData().getPhaseList().get(0).getPhaseData());
//            adapter.getListView(0).addFooterView(footView);
//        } else {
//            getHintLayout(0).setVisibility(VISIBLE);
//            adapter.getListView(0).addFooterView(nullFootVuew);
//        }
//        if (bean != null && !Utils.listIsNull(bean.getData().getRepaidList())) {
//            getHintLayout(1).setVisibility(GONE);
//            getListAdapter(1).upDateList(bean.getData().getRepaidList().get(0).getPhaseData());
//            adapter.getListView(1).addFooterView(footView);
//        } else {
//            getHintLayout(1).setVisibility(VISIBLE);
//            adapter.getListView(1).addFooterView(nullFootVuew);
//        }
    }

}
