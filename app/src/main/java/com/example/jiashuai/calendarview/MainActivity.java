package com.example.jiashuai.calendarview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionDateBean;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview.CalendarView;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview.MonthView;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.json.Data;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private CollectionDateBean bean = Data.getBean();
    private int mSelYear;
    private int mSelMonth;
    private List<CollectionDateBean.Data.YearData> loopViewItemList = bean.getData().getData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        findViewById(R.id.next_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity2.gouNext(MainActivity.this,mSelYear, mSelMonth, loopViewItemList);
            }
        });
        calendarView.setClickMonthViewDayListener(new MonthView.OnClickMonthViewDayListener() {
            @Override
            public void clickCallBacak(int selYear, int selMonth, int selDay) {
                mSelYear = selYear;
                mSelMonth = selMonth;
//                mSelDay = selDay;
            }
        });

        calendarView.setAdapterDate(bean.getData().getData(), bean.getData().getGmt());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Activity2.REQUEST_CODE && resultCode == Activity2.RESULT_CODE) {
            String month = data.getStringExtra("collectionUpdata");//获取列表模式选择的时间
            calendarView.jumpToMonth(month);
        }
    }
}
