package com.example.icecream_renual;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class AddDialog extends Dialog{
    public AddDialog(@NonNull Context context) {
        super(context);
    }

    public EditText et_name;

    public TextView et_date;

    public EditText et_quantity;
    public EditText et_memo;

    public String category;
    public Spinner s_category;

    public ImageView btn_save;
    public ImageView btn_cancel;
    private ImageView btn_calendar;
    public TextView tv_delete;

    boolean buttonState = false;

    Func fun = new Func();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add);

        buttonState = false;

        tv_delete = (TextView) findViewById(R.id.tv_delete);
        et_name = (EditText) findViewById(R.id.et_foodname);


        et_date = (TextView) findViewById(R.id.et_date);

//        et_quantity = (EditText) findViewById(R.id.et_quantity);
        et_memo = (EditText) findViewById(R.id.et_memo);


        s_category = (Spinner)findViewById(R.id.s_category);
        s_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_save = (ImageView) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //필수정보가 하나라도 없는 경우
                if((et_name.getText().toString().equals("") || et_name.getText().toString() == null) ||
                        (et_date.getText().toString().equals("") || et_date.getText().toString() == null) ||
                        (category.equals("") || category == null)){
                    Toast.makeText(getContext(),"Fail", Toast.LENGTH_LONG).show();
                }
                // 모든 필수 정보가 다 입력된 경우 Edit Text로 받은 정보 각 형식에 맞게 변환
                else{
                    buttonState = true;

                    dismiss();
                }
            }
        });

        btn_cancel = (ImageView) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonState = false;
                dismiss();
            }
        });

        btn_calendar = (ImageView) findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.Datepicker, datepicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#326199"));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#326199"));

            }
        });

    }
    public boolean getButtonStates(){return buttonState;}
    public String[] getelements(){
        String name = et_name.getText().toString();
        //유통기한 (년 | 월 | 일 로 나눠서 저장)
        String date = et_date.getText().toString();
//        String[] date_split = date.split("\\."); //YYYY.MM.DD 형식을 YYYY MM DD 로 나누기
//        int year = Integer.parseInt(date_split[0]);
//        int month = Integer.parseInt(date_split[1]);
//        int day = Integer.parseInt(date_split[2]);
//        //메모 (선택사항)
        String memo = et_memo.getText().toString();

        return new String[] {name,date,category,memo};
    }
    //달력 표시를 위한 함수 1 https://stickode.tistory.com/224
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    //달력 표시를 위한 함수 2
    private void updateLabel() {
        et_date.setText(calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.DAY_OF_MONTH));
    }
}

