package com.second.project.heysched.plan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.second.project.heysched.R;

import java.util.ArrayList;
import java.util.Calendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddPlanActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText plan_title;
    ImageView color_picker;
    EditText plan_start_date;
    EditText plan_start_time;
    EditText plan_end_date;
    EditText plan_end_time;
    TextView plan_location;
    Button find_location;
    InputMethodManager imm;
    Button find_friend;
    TextView plan_friends;
    EditText memo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plan);

        plan_title = findViewById(R.id.plan_title);
        color_picker = findViewById(R.id.color_picker);
        plan_start_date = findViewById(R.id.plan_start_date);
        plan_start_time = findViewById(R.id.plan_start_time);
        plan_end_date = findViewById(R.id.plan_end_date);
        plan_end_time = findViewById(R.id.plan_end_time);
        plan_location = findViewById(R.id.plan_location);
        find_location = findViewById(R.id.find_location);
        plan_friends = findViewById(R.id.plan_friends);
        find_friend = findViewById(R.id.find_friends);
        memo = findViewById(R.id.memo);
        plan_start_date.setShowSoftInputOnFocus(false);
        plan_end_date.setShowSoftInputOnFocus(false);
        plan_start_time.setShowSoftInputOnFocus(false);
        plan_end_time.setShowSoftInputOnFocus(false);

        // 색
        color_picker.setOnClickListener(this);

        // 날짜 선택시 키보드 없어지게
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        // 날짜 다이얼로그 띄우기
        plan_start_date.setOnFocusChangeListener(this);
        plan_start_time.setOnFocusChangeListener(this);
        plan_end_date.setOnFocusChangeListener(this);
        plan_end_time.setOnFocusChangeListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_picker:
                openColorPicker();
                break;
           /* case R.id.plan_start_date:
            case R.id.plan_end_date:
                hideKeyboard();
                showDateDialog((EditText) v);
                v.clearFocus();
                break;
            case R.id.plan_start_time:
            case R.id.plan_end_time:
                hideKeyboard();
                showTimeDialog((EditText) v);
                v.clearFocus();
                break;*/
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.plan_start_date:
            case R.id.plan_end_date:
                if (hasFocus) {
                    hideKeyboard();
                    showDateDialog((EditText) v);
                    v.clearFocus();
                }
                break;
            case R.id.plan_start_time:
            case R.id.plan_end_time:
                if (hasFocus) {
                    hideKeyboard();
                    showTimeDialog((EditText) v);
                    v.clearFocus();
                }
                break;
        }
    }

    // color picker
    private void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        colors.add("#ffab91");
        colors.add("#F48FB1");
        colors.add("#ce93d8");
        colors.add("#b39ddb");
        colors.add("#9fa8da");
        colors.add("#90caf9");
        colors.add("#81d4fa");
        colors.add("#80deea");
        colors.add("#80cbc4");
        colors.add("#c5e1a5");
        colors.add("#e6ee9c");
        colors.add("#fff59d");
        colors.add("#ffe082");
        colors.add("#ffcc80");
        colors.add("#bcaaa4");

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        color_picker.setColorFilter(color, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
    }

    // date dialog
    private void showDateDialog(final EditText v) {
        Calendar c = Calendar.getInstance();
        int nYear = c.get(Calendar.YEAR);
        int nMon = c.get(Calendar.MONTH);
        int nDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener mDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        String strDate = String.valueOf(year) + "/";
                        strDate += String.valueOf(monthOfYear + 1) + "/";
                        strDate += String.valueOf(dayOfMonth);

                        v.setText(strDate);


                        switch (v.getId()) {
                            case R.id.plan_start_date:
                                plan_start_time.requestFocus();
                                break;
                            case R.id.plan_end_date:
                                plan_end_time.requestFocus();
                                break;
                        }

                    }

                };

        if ((!v.getText().equals("")) && v.getText().length() > 0) {
            String[] selected_date = v.getText().toString().split("/");

            nYear = Integer.parseInt(selected_date[0]);
            nMon = Integer.parseInt(selected_date[1]);
            nDay = Integer.parseInt(selected_date[2]);

        }
        DatePickerDialog oDialog = new DatePickerDialog(this,
                android.R.style.Theme_DeviceDefault_Light_Dialog,
                mDateSetListener, nYear, nMon, nDay);
        oDialog.show();


    }

    // time dialog
    private void showTimeDialog(final EditText v) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String strTime = String.valueOf(hourOfDay) + ":";
                String min = "";
                if (minute < 10) {
                    min = "0" + String.valueOf(minute);
                } else {
                    min = String.valueOf(minute);
                }
                strTime += min;
                v.setText(strTime);
            }
        };

        if ((!v.getText().equals("")) && v.getText().length() > 0) {
            String[] selected_time = v.getText().toString().split(":");

            int hour = Integer.parseInt(selected_time[0]);
            int min = Integer.parseInt(selected_time[1]);

            TimePickerDialog tPd = new TimePickerDialog(
                    this,
                    android.R.style.Theme_DeviceDefault_DayNight,
                    onTimeSetListener,
                    hour,
                    min,
                    true);
            tPd.show();
        } else {
            TimePickerDialog tPd = new TimePickerDialog(
                    this,
                    android.R.style.Theme_DeviceDefault_DayNight,
                    onTimeSetListener,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true);
            tPd.show();
        }
        if (v.getId() == R.id.plan_end_time) {
            //timeCheck();
        }

    }

    private void timeCheck() {
        if ((!plan_start_time.getText().equals("")) && plan_start_time.getText().length() > 0) {
            String[] start = plan_start_time.getText().toString().split(":");
            String[] end = plan_end_time.getText().toString().split(":");

            if (Integer.parseInt(start[0]) > Integer.parseInt(end[0])) {
                int time = Integer.parseInt(end[0]) - 1;
                plan_start_time.setText(time + ":" + start[1]);
            }
        }
    }

    private void hideKeyboard() {
        imm.hideSoftInputFromWindow(plan_start_date.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(plan_end_date.getWindowToken(), 0);
    }


}
