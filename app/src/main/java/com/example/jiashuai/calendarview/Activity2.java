package com.example.jiashuai.calendarview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.bean.CollectionDateBean;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.calendarview.Utils;
import com.example.jiashuai.calendarview.xiaoziqianbao.calendarview.loopView.SelectDatePopupWindow;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JiaShuai on 2017/5/10.
 */

public class Activity2 extends AppCompatActivity {
    public static final int REQUEST_CODE = 0;
    public static final int RESULT_CODE = 1;
    private View mView;
    private Button button;
    private int currYear;
    private int currMonth;
    private List<CollectionDateBean.Data.YearData> yearDataList;

    public static void gouNext(Activity context, int year, int month, List<CollectionDateBean.Data.YearData> loopViewItemList) {
        if (!Utils.listIsNull(loopViewItemList) && year != 0 && month != 0) {
            Intent intent = new Intent(context, Activity2.class);
            intent.putExtra("currYear", year);
            intent.putExtra("currMonth", month);
            Bundle bundle = new Bundle();
            bundle.putSerializable("loopViewItem", (Serializable) loopViewItemList);
            intent.putExtras(bundle);
            context.startActivityForResult(intent, Activity2.REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutInflater.from(this).inflate(R.layout.activity2, null);
        setContentView(mView);
        button = (Button) findViewById(R.id.showpop);
        Intent intent = getIntent();
        currYear = intent.getIntExtra("currYear", 0);
        currMonth = intent.getIntExtra("currMonth", 0);
        Bundle bundle = intent.getExtras();
        yearDataList = (List<CollectionDateBean.Data.YearData>) bundle.getSerializable("loopViewItem");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectDate();
            }
        });
    }

    private void showSelectDate() {

        SelectDatePopupWindow popupWindow = new SelectDatePopupWindow(this);
        popupWindow.setSelYear(currYear);
        popupWindow.setSelMonth(currMonth);
        popupWindow.setSelectData(yearDataList);
        popupWindow.setSelectDateOkLinsenter(new SelectDatePopupWindow.SelectDateOkLinsenter() {
            @Override
            public void click(int year, int month) {
                currYear = year;
                currMonth = month;
                button.setText(currYear + "年" + currMonth + "月");
//                RxBus.get().post("collectionUpdata", );
            }
        });
        popupWindow.showSelectDate(mView);
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("collectionUpdata", currYear + "-" + String.format("%02d", currMonth));//月份不足两位前边补0，回调CollectionCalendar.notifyCalendar
        setResult(RESULT_CODE, intent);
        super.finish();
    }
}
