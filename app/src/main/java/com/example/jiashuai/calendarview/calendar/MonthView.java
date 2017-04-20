package com.example.jiashuai.calendarview.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by JiaShuai on 2017/4/18.
 */

public class MonthView extends View {
    private static final int NUM_COLUMNS = 7;
    private static final String TAG = "CalendarView";
    private int NUM_ROWS = 6;//默认六行，需要计算几行
    private int mSelYear, mSelMonth, mSelDay = 10;//当前选中的年月天 month按照java从0月开始，实际+1
    private int mCurrYear, mCurrMonth, mCurrDay;//当年，当月，今天，
    private Paint mPaint;//画笔，
    private int background;//背景色
    private int mSelectCircleBGColor;//选中圆的背景；
    private int mSelectTodayCircleBGColor;//选中今天圆的背景；
    private int mDayTextColor;//文字颜色
    private int mDayTextSize;//文字大小
    private int mSelectTodayTextColor;//选中今天的字体颜色
    private int mSelectTextColor;//选中的字体颜色；
    private int mTheWeekendTextColor;//周六日字体颜色；
    private int mTodayTextColor;//今天的字体颜色；
    private DisplayMetrics mDisplayMetrics;
    private int mColumnSize, mRowSize, mSelectCircleSize;//文字宽、高、选中圆圈大小；
    private int[][] mDaysText;//存储日期的二位数组；
    private GestureDetector mGestureDetector;
    private int thisHight;
    private int weekNumber, monthDays;//本月一号是星期几，本月有多少天
    private int selectMonthRow;//计算当前月份是5行还是6行


    private void initGestureDetector() {
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                doOnclickAction((int) e.getX(), (int) e.getY());
                return true;
            }
        });
    }

    private void doOnclickAction(int x, int y) {
        if (y > getHeight())
            return;
        int row = y / mRowSize;
        int column = x / mColumnSize;
//        column = Math.min(column, 6);//防止越界
        if (mDaysText[row][column] != 0) {
            mSelDay = mDaysText[row][column];
        } else {
            return;
        }
        Log.i("CalendarView", "------------" + "Year:" + mSelYear + "Month:" + mSelMonth + "Day:" + mSelDay);
        invalidate();

    }


    public MonthView(Context context, int year, int month) {
        this(context, null, year, month);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int year, int month) {
        this(context, attrs, 0, year, month);
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int year, int month) {
        super(context, attrs, defStyleAttr);
        initAll(attrs,year, month);

    }

    private void initAll(AttributeSet attrs,int year, int month) {
        initPaint(attrs);
        initGestureDetector();
        initAttr(year, month);
    }

    public void initAttr(int year, int month) {
        mSelYear = year;
        mSelMonth = month;
        monthDays = CalendarUtils.getMonthDays(mSelYear, mSelMonth);//获取本月有多少天
        weekNumber = CalendarUtils.getFirstDayWeek(mSelYear, mSelMonth);//获取本月1号是星期几
        selectMonthRow = (monthDays - 1 + weekNumber - 1) / 7;
    }

    private void initPaint(AttributeSet attrs) {

        mSelectCircleBGColor = Color.parseColor("#f16868");
        mSelectTodayCircleBGColor = Color.parseColor("#f16868");
        mDayTextColor = Color.parseColor("#333333");
        mDayTextSize = 15;
        mSelectTodayTextColor = Color.parseColor("#ffffff");
        mSelectTextColor = Color.parseColor("#ffffff");
        mTheWeekendTextColor = Color.parseColor("#999999");
        mTodayTextColor = Color.parseColor("#f16868");
        background = Color.parseColor("#ffffff");


        mDisplayMetrics = getResources().getDisplayMetrics();//获取屏幕密度，不是分辨率，
        // 只有activity可以使用WindowManager，否则应该使用Context.getResources().getDisplayMetrics()来获取。
        //Context.getResources().getDisplayMetrics()依赖于手机系统，获取到的是系统的屏幕信息；
        //WindowManager.getDefaultDisplay().getMetrics(dm)是获取到Activity的实际屏幕信息。

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mDayTextSize * mDisplayMetrics.scaledDensity);//显示在显示器上的字体的缩放因子。这是相同的作为{@链接#密度}，除了它可以调整小根据用户对字体大小的偏好在运行时递增。

    }

    //配置当天的年月日
    private void initCurrMonth(int year, int month, int day) {
//        Calendar calendar = Calendar.getInstance();
        mCurrYear = year;
        mCurrMonth = month;
        mCurrDay = day;
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        //WRAP_CONTENT状态下view大小
//        if (heightMode == MeasureSpec.AT_MOST) {//MATCH_PARENT对应于EXACTLY，WRAP_CONTENT对应于AT_MOST，其他情况也对应于EXACTLY，它和MATCH_PARENT的区别在于size值不一样。
//            heightSize = heightSize / 5 * 2;//取屏幕高度的五分之二
//        }
//        if (widthMode == MeasureSpec.AT_MOST) {
//        }
        int heightSize = mDisplayMetrics.heightPixels / 5 * 2;//这里必须写固定大小，如果测量View的高度，View高度改变，文字高度也会跟着改变，
        initSize(widthSize, heightSize);
        setMeasuredDimension(widthSize, heightSize);//设置view大小

    }

    @Override
    protected void onDraw(Canvas canvas) {
        clearData();
        canvas.drawColor(background);
        drawMonth(canvas);

    }

    //测量
    private void initSize(int widthSize, int heightSize) {
        mColumnSize = widthSize / NUM_COLUMNS;//View宽度/7=文字宽度
        mRowSize = heightSize / NUM_ROWS;//View高度/6 = 文字高度
        mSelectCircleSize = (int) (mColumnSize / 3.2);//圆圈的大小;
        while (mSelectCircleSize > mRowSize / 2) {//如果圆圈大小超出view高度/1.3
            mSelectCircleSize = (int) (mSelectCircleSize / 1.3);
        }
        thisHight = mRowSize * (selectMonthRow + 1);

    }

    private void clearData() {
        mDaysText = new int[6][7];
    }

    private void drawMonth(Canvas canvas) {
        String dayString;
        for (int day = 0; day < monthDays; day++) {
            dayString = String.valueOf(day + 1);
            int column = (day + weekNumber - 1) % 7;
            int row = (day + weekNumber - 1) / 7;
            mDaysText[row][column] = day + 1;//当前日期添加到二位数组；

            int startX = (int) (mColumnSize * column + (mColumnSize / 2 - mPaint.measureText(dayString) / 2));//文字x起始坐标  （单个长度*横坐标+（单个长度-文字长度）/2）=x起始坐标
            int startY = (int) (mRowSize * row + (mRowSize / 2 - (mPaint.ascent() + mPaint.descent()) / 2));//文字y起始坐标， 根据绘制文字基线计算，
            if (dayString.equals(String.valueOf(mSelDay))) {//如果是选中的天
                float recX = mColumnSize * column + mColumnSize / 2;//圆心x
                float recY = mRowSize * row + mRowSize / 2;//圆心y
                if (mSelYear == mCurrYear && mSelMonth == mCurrMonth && day + 1 == mCurrDay) {//如果选中的是当天
                    mPaint.setColor(mSelectTodayCircleBGColor);
                } else {
                    mPaint.setColor(mSelectCircleBGColor);
                }
                canvas.drawCircle(recX, recY, mSelectCircleSize, mPaint);
            }
            mPaint.setColor(mDayTextColor);
            if (column == 0 || column == 6) {
                mPaint.setColor(mTheWeekendTextColor);
            }
            if (dayString.equals(String.valueOf(mCurrDay)) && mCurrDay != mSelDay && mCurrMonth == mSelMonth && mCurrYear == mSelYear) {
                mPaint.setColor(mTodayTextColor);
            }
            if (dayString.equals(String.valueOf(mCurrDay)) && mCurrDay == mSelDay && mCurrMonth == mSelMonth && mCurrYear == mSelYear) {
                mPaint.setColor(mSelectTodayTextColor);
            }
            if (dayString.equals(String.valueOf(mSelDay))) {
                mPaint.setColor(mSelectTextColor);
            }
            canvas.drawText(dayString, startX, startY, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public int getCalendarViewHight() {
        return thisHight;
    }
}
