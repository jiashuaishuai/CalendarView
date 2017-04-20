package com.example.jiashuai.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jiashuai.calendarview.calendar.SelfAdaptionViewPager;

public class MainActivity extends AppCompatActivity {
    private SelfAdaptionViewPager calendar_viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        calendar_viewpager = (SelfAdaptionViewPager) findViewById(R.id.calendar_viewpager);
//        calendar_viewpager.setAdapter(new CalendarAdapter(this));
//        calendar_viewpager.setCurrentItem(2);
//        calendar_viewpager.setOffscreenPageLimit(2);

    }
}
