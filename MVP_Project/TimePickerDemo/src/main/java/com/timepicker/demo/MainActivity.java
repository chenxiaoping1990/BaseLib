package com.timepicker.demo;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import com.lib.common.tool.ALog;
import com.lib.loopview.OnItemSelectedListener;
import com.lib.wheel.TimePickerDialog;
import com.lib.wheel.data.Type;
import com.lib.wheel.listener.OnDateSetListener;
import com.lib.widget.toast.UIToast;
import com.timepicker.demo.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initTimeDialog();
        initView();
    }

    TimePickerDialog timePickerDialog;
    long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;

    private void initTimeDialog() {
        timePickerDialog = new TimePickerDialog.Builder()
                .setCallBack(onDateSetListener)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("TimePicker")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(18)
                .build();
    }

    private void initView() {
        activityMainBinding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show(getSupportFragmentManager(), "");
            }
        });
        activityMainBinding.loopView.setItemsVisibleCount(4);
        activityMainBinding.loopView.setTextSize(18);
        activityMainBinding.loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                ALog.e("================" + index);
                UIToast.showToast(getApplicationContext(), "选择了" + index);
            }
        });
        activityMainBinding.loopView.setItems(getData());
    }

    OnDateSetListener onDateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

        }
    };

    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("一个非常长的小区名称，这是一个变态设置的配置大家要看好" + i);
        }
        return list;
    }

}
