package com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.loopView;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jiashuai.calendarview.R;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionDateBean;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiaShuai on 2017/4/26.
 */

public class SelectDatePopupWindow extends PopupWindow {
    private TextView select_date_ok;
    private LoopView year_loop_view, month_loop_view;
    private List<String> yearLoopList = new ArrayList<>();
    private List<String> yearLoopShowList = new ArrayList<>();
    private List<String> monthLoopList = new ArrayList<>();
    private List<String> monthLoopShowList = new ArrayList<>();
    private SelectDateOkLinsenter selectDateOkLinsenter;
    private List<CollectionDateBean.Data.YearData> selectData;

    private int selYearIndex, selMonthIndex;

    private int selYear, selMonth;

    public SelectDatePopupWindow(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_date_popupwindow_layout, null);
        select_date_ok = (TextView) view.findViewById(R.id.select_date_ok);
        year_loop_view = (LoopView) view.findViewById(R.id.year_loop_view);
        month_loop_view = (LoopView) view.findViewById(R.id.month_loop_view);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(view);

        year_loop_view.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selYearIndex = index;
                if (!Utils.listIsNull(selectData.get(selYearIndex).getMonthList()))
                    selMonth = Integer.parseInt(selectData.get(selYearIndex).getMonthList().get(0).getMonth().split("-")[1]);//滚动年时，选中月为第数组第一个；
                addMonthListItem(selYearIndex);
            }
        });
        month_loop_view.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                selMonthIndex = index;
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        select_date_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Utils.listIsNull(yearLoopList))
                    selYear = Integer.parseInt(yearLoopList.get(selYearIndex));
                if (!Utils.listIsNull(monthLoopList)) {
                    selMonth = Integer.parseInt(monthLoopList.get(selMonthIndex));
                }
                if (selectDateOkLinsenter != null)
                    selectDateOkLinsenter.click(selYear, selMonth);
                dismiss();
            }
        });

    }
//
//    public void setYearLoopList(List<String> yearLoopList) {
//        this.yearLoopList = yearLoopList;
//    }
//
//    public void setMonthLoopList(List<String> monthLoopList) {
//        this.monthLoopList = monthLoopList;
//    }

    public void showSelectDate(View mView) {
        showAtLocation(mView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void setYearLoopCurrItem(int index) {
        selYearIndex = index;

    }

    public void setMonthLoopCurrItem(int index) {
        selMonthIndex = index;
        month_loop_view.setCurrentPosition(index);
    }

    public void setSelectDateOkLinsenter(SelectDateOkLinsenter selectDateOkLinsenter) {
        this.selectDateOkLinsenter = selectDateOkLinsenter;
    }

    public void setSelectData(List<CollectionDateBean.Data.YearData> selectData) {
        yearLoopList.clear();
        yearLoopShowList.clear();
        this.selectData = selectData;
        if (!Utils.listIsNull(selectData)) {
            for (int i = 0; i < selectData.size(); i++) {
                yearLoopList.add(selectData.get(i).getYear());
                yearLoopShowList.add(selectData.get(i).getYear() + "年");
                if (selYear == Integer.parseInt(yearLoopList.get(i)))
                    selYearIndex = i;
            }
            year_loop_view.setItems(yearLoopShowList);
            year_loop_view.setCurrentPosition(selYearIndex);
            addMonthListItem(selYearIndex);
        }

    }

    private void addMonthListItem(int yearindex) {
        monthLoopList.clear();
        monthLoopShowList.clear();
        List<CollectionDateBean.Data.YearData.MonthData> monthList = selectData.get(yearindex).getMonthList();
        if (!Utils.listIsNull(monthList)) {
            for (int i = 0; i < monthList.size(); i++) {
                monthLoopList.add(monthList.get(i).getMonth().split("-")[1]);//截取服务器传回的时间"-"分割第二位是月
                monthLoopShowList.add(Integer.parseInt(monthList.get(i).getMonth().split("-")[1]) + "月");
                if (selMonth == Integer.parseInt(monthLoopList.get(i))) {
                    selMonthIndex = i;
                }
            }
        } else {
            selYearIndex = 0;
            selMonthIndex = 0;
        }
        month_loop_view.setItems(monthLoopShowList);
        month_loop_view.setCurrentPosition(selMonthIndex);
    }

    public void setSelYear(int selYear) {
        this.selYear = selYear;
    }

    public void setSelMonth(int selMonth) {
        this.selMonth = selMonth;
    }

    public interface SelectDateOkLinsenter {
        void click(int year, int month);
    }
}
