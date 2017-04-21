package com.example.jiashuai.calendarview.calendar;

import java.util.Calendar;

/**
 * Created by JiaShuai on 2017/4/19.
 */

public class CalendarUtils {
    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取月+1返回年月月按照java规则从0月开始到11月
    public static int[] plusMonths(int year, int month) {
        int date[] = new int[2];
        int nextYear;
        if (month > 11) {//月大于11加一年，
            nextYear = month / 12;
            year += nextYear;
            month = month - 12 * nextYear;
        }
        if (month < 0) {//月小于0减一年
            nextYear=(11-month)/12;
            year -= nextYear;
            month = 12*nextYear+ month;//
        }
        date[0] = year;
        date[1] = month;
        return date;
    }
}
