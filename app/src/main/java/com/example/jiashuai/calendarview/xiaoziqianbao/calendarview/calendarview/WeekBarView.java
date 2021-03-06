package com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.jiashuai.calendarview.R;


/**
 * Created by Jimmy on 2016/10/6 0006.
 */
public class WeekBarView extends View {

    private int mWeekTextColor;
    private int theWeekendTextColor;
    private int linColor;
    private int linHeight;
    private int backgroundColor;
    private int mWeekSize;
    private Paint mPaint;
    private Paint mLinPaint;
    private DisplayMetrics mDisplayMetrics;
    private String[] mWeekString;

    public WeekBarView(Context context) {
        this(context, null);
    }

    public WeekBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WeekBarView);
        mWeekTextColor = array.getColor(R.styleable.WeekBarView_week_text_color, Color.parseColor("#999999"));
        theWeekendTextColor = array.getColor(R.styleable.WeekBarView_the_weekend_color, Color.parseColor("#dcdcdc"));
        mWeekSize = array.getInteger(R.styleable.WeekBarView_week_text_size, 14);
        linColor = array.getInteger(R.styleable.WeekBarView_linColor, Color.parseColor("#efefef"));
        linHeight = array.getInteger(R.styleable.WeekBarView_linHeight, 1);
        backgroundColor = array.getInteger(R.styleable.WeekBarView_backgroundColor, Color.parseColor("#fafafa"));
        array.recycle();
        mWeekString = context.getResources().getStringArray(R.array.calendar_week);
    }

    private void initPaint() {
        mDisplayMetrics = getResources().getDisplayMetrics();
        mLinPaint = new Paint();
        mLinPaint.setColor(linColor);
        mLinPaint.setStrokeWidth((float) linHeight * mDisplayMetrics.scaledDensity);


        //实例化文字画笔
        mPaint = new Paint();
        mPaint.setColor(mWeekTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mWeekSize * mDisplayMetrics.scaledDensity);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) (mDisplayMetrics.scaledDensity * 40);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(backgroundColor);
        //绘制线条
        canvas.drawLine(0, 0, width, 0, mLinPaint);
        canvas.drawLine(0, height, width, height, mLinPaint);

        //绘制文字
        int columnWidth = width / 7;
        for (int i = 0; i < mWeekString.length; i++) {
            String text = mWeekString[i];
            int fontWidth = (int) mPaint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
            int startY = (int) (height / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            if (i == 0 || i == mWeekString.length - 1) {
                mPaint.setColor(theWeekendTextColor);
            } else {
                mPaint.setColor(mWeekTextColor);
            }
            canvas.drawText(text, startX, startY, mPaint);
        }
    }

}
