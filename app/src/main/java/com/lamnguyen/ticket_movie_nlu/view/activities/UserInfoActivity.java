package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;
//import com.lamnguyen.ticket_movie_nlu.adapters.CustomSpinnerAdapter;
//import com.lamnguyen.ticket_movie_nlu.adapters.GenderDropdownAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity {



    private EditText birthdayInput;
    private Calendar calendar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final EditText userNameInput = findViewById(R.id.UserNameInput);
        final EditText emailInput = findViewById(R.id.EmailInput);
        final EditText phoneInput = findViewById(R.id.PhoneInput);
        final EditText addressInput = findViewById(R.id.AddressInput);

        // Thiết lập trạng thái ban đầu của EditText là không thể chỉnh sửa
        userNameInput.setFocusable(false);
        emailInput.setFocusable(false);
        phoneInput.setFocusable(false);
        addressInput.setFocusable(false);

        // Thêm sự kiện click cho drawableRight của EditText
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (v.getRight() - ((EditText) v).getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Người dùng nhấn vào drawableRight (nút bút chì)
                        // Chuyển trạng thái EditText thành có thể chỉnh sửa
                        v.setFocusableInTouchMode(true);
                        return true;
                    }
                }
                return false;
            }
        };

        // Ánh xạ sự kiện cho từng EditText
        userNameInput.setOnTouchListener(onTouchListener);
        emailInput.setOnTouchListener(onTouchListener);
        phoneInput.setOnTouchListener(onTouchListener);
        addressInput.setOnTouchListener(onTouchListener);

        birthdayInput = findViewById(R.id.BirthdayInput);
        calendar = Calendar.getInstance();

        birthdayInput.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (birthdayInput.getRight() - birthdayInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showDatePickerDialog();
                        return true;
                    }
                }
                return false;
            }
        });

        updateDate();
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDate();
            }
        };

        new DatePickerDialog(UserInfoActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        birthdayInput.setText(sdf.format(calendar.getTime()));
    }


}
