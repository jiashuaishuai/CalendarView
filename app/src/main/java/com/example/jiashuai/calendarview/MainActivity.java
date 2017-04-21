package com.example.jiashuai.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jiashuai.calendarview.calendar.CalendarView;
import com.example.jiashuai.calendarview.calendar.MonthView;

public class MainActivity extends AppCompatActivity  {
private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        calendarView.setListener(new MonthView.OnClickMonthViewDayListener() {
            @Override
            public void clickCallBacak(int selYear, int selMonth, int selDay) {
                Toast.makeText(MainActivity.this,"Year"+selYear+"Month"+selMonth+"Day"+selDay,Toast.LENGTH_SHORT).show();
            }
        });
//        calendar_viewpager = (SelfAdaptionViewPager) findViewById(R.id.calendar_viewpager);
//        calendar_viewpager.setAdapter(new CalendarAdapter(this));
//        calendar_viewpager.setCurrentItem(2);
//        calendar_viewpager.setOffscreenPageLimit(2);

    }
}
