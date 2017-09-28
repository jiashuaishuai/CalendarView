package com.example.jiashuai.calendarview;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionDateBean;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.json.Data;
import com.example.jiashuai.calendarview.xiaoziqianbao3_4.calendar.calendar.OnClickMonthViewDayListener;
import com.example.jiashuai.calendarview.xiaoziqianbao3_4.calendar.calendar.ScheduleLayout;
import com.example.jiashuai.calendarview.xiaoziqianbao3_4.calendar.calendar.list.CalendarListViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by JiaShuai on 2017/9/28.
 */

public class Activity3 extends AppCompatActivity {
    private int mSelYear, mSelMonth, mSelDay;
    private ScheduleLayout calendarView;
    private TextView today_collection, today_wait_collection;
    private List<CollectionDateBean.Data.YearData> loopViewItemList;
    private LinearLayout click_today_lly;
    private CalendarListViewPager collection_view_pager;
    private SlidingTabLayout tabLayout;
    private LinearLayout today_wait_collection_lly, today_collection_lly;
    private CollectionDateBean bean = Data.getBean();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_layout);
        initView();
        initListener();
        requestData();
    }

    private void initView() {
        calendarView = (ScheduleLayout) findViewById(R.id.calendar_view);
        today_collection = (TextView) findViewById(R.id.today_collection);
        today_wait_collection = (TextView) findViewById(R.id.today_wait_collection);
        click_today_lly = (LinearLayout) findViewById(R.id.click_today_lly);
        collection_view_pager = (CalendarListViewPager) findViewById(R.id.collection_view_pager);
        tabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout);
        today_wait_collection_lly = (LinearLayout) findViewById(R.id.today_wait_collection_lly);
        today_collection_lly = (LinearLayout) findViewById(R.id.today_collection_lly);

    }

    private void initListener() {
        calendarView.setClickMonthViewDayListener(new OnClickMonthViewDayListener() {
            @Override
            public void clickCallBack(int selYear, int selMonth, int selDay, boolean isCashBackDay) {
                mSelYear = selYear;
                mSelMonth = selMonth;
                mSelDay = selDay;
                requestCollectionData(selYear, selMonth, selDay, isCashBackDay);
            }
        });
        click_today_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.goToday();
            }
        });
        tabLayout.setViewPager(collection_view_pager, new String[]{"今日待回款(元)", "今日待回款(元)"});
        today_wait_collection_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection_view_pager.setCurrentItem(0);
            }
        });
        today_collection_lly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collection_view_pager.setCurrentItem(1);
            }
        });
    }


    private void requestData() {
        calendarView.post(new Runnable() {
            @Override
            public void run() {
                loopViewItemList = bean.getData().getData();
                calendarView.setAdapterDate(loopViewItemList, bean.getData().getGmt());

            }
        });

    }

    private void requestCollectionData(int year, int month, int day, boolean isCashBackDay) {
        today_collection.setText("0.00");
        today_wait_collection.setText("0.00");
        collection_view_pager.setListData(null);
        if (!isCashBackDay)
            return;
//        Map<String, String> params = sharedPreferencesUtils.getRequestParams();
//        params.put("dueDate", year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day));
//        HttpManager manager = HttpManager.getInstance();
//        BaseApi<CollectionBean> beanBaseApi = new BaseApi<>(mContext, new HttpOnNextListener<CollectionBean>() {
//            @Override
//            public void getRequestDataSucceed(CollectionBean bean) {
//                today_collection.setText(formatAMT(MathUtils.formatData2(bean.getData().getToDayRepaidPhaseAmt())));
//                today_wait_collection.setText(formatAMT(MathUtils.formatData2(bean.getData().getToDayPhaseAmt())));
//                collection_view_pager.setListData(bean);
//            }
//
//            @Override
//            public void getRequestDataFailure(String msg, String code) {
//
//            }
//        });
    }

    public static final int REQUEST_CODE = 0;


    private String formatAMT(String amt) {
        return "0".equals(amt) ? "0.00" : amt;
    }

}
