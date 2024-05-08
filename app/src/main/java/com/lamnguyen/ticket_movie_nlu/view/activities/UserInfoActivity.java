package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.lamnguyen.ticket_movie_nlu.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity {

    private EditText currentEditText;
    private EditText birthdayInput;
    private Calendar calendar;
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final EditText userNameInput = findViewById(R.id.UserNameInput);
        final EditText emailInput = findViewById(R.id.EmailInput);
        final EditText phoneInput = findViewById(R.id.PhoneInput);
        final EditText addressInput = findViewById(R.id.AddressInput);

        userNameInput.setFocusable(false);
        emailInput.setFocusable(false);
        phoneInput.setFocusable(false);
        addressInput.setFocusable(false);


        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (v.getRight() - ((EditText) v).getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        setEditable((EditText) v);
                        return true;
                    }
                }
                return false;
            }
        };

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

        //changeAvata
        Button changeAvatarButton = findViewById(R.id.btnChangeAvatar);
        changeAvatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
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
    private void setEditable(EditText editText) {
        if (currentEditText != null) {
            currentEditText.setFocusable(false);
        }
        editText.setFocusableInTouchMode(true);
        currentEditText = editText;
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            ImageView imageView = findViewById(R.id.avatar);
            Glide.with(imageView).load(imageUri).into(imageView);
        }
    }
}
