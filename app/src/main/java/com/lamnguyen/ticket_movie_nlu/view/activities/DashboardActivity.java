package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.view.fragments.DayDashBoardFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.MonthDashboardFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.WeekDashBoardFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private Button btnDay, btnWeek, btnMonth;
    private ImageView imgViewEditCalendarFrom, imgViewEditCalendarTo;
    private TextView txtDateFrom, txtDateTo;
    private Spinner spnSelectCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnDay = findViewById(R.id.button_day);
        btnWeek = findViewById(R.id.button_week);
        btnMonth = findViewById(R.id.button_month);
        imgViewEditCalendarFrom = findViewById(R.id.image_view_edit_calendar_from);
        imgViewEditCalendarTo = findViewById(R.id.image_view_edit_calendar_to);
        spnSelectCinema = findViewById(R.id.spinner_name_cinema);
        txtDateFrom = findViewById(R.id.text_view_date_from);
        txtDateTo = findViewById(R.id.text_view_date_to);

        // Set initial fragment
        if (savedInstanceState == null) {
            replaceFragment (new DayDashBoardFragment());
        }
        addItemToSpinner();

        btnDay.setOnClickListener(v -> {
            setBgButtonGradient(btnDay);
            setBgButtonNonGradient(btnWeek);
            setBgButtonNonGradient(btnMonth);
            replaceFragment(new DayDashBoardFragment());
        });
        btnWeek.setOnClickListener(v -> {
            setBgButtonGradient(btnWeek);
            setBgButtonNonGradient(btnDay);
            setBgButtonNonGradient(btnMonth);
            replaceFragment(new WeekDashBoardFragment());
        });
        btnMonth.setOnClickListener(v -> {
            setBgButtonGradient(btnMonth);
            setBgButtonNonGradient(btnDay);
            setBgButtonNonGradient(btnWeek);
            replaceFragment(new MonthDashboardFragment());
        });
        imgViewEditCalendarFrom.setOnClickListener(v -> {
            showDatePicker(txtDateFrom);
        });

        imgViewEditCalendarTo.setOnClickListener(v -> {
            showDatePicker(txtDateTo);
        });
    }

    private void setBgButtonGradient(Button btn) {
        btn.setBackground(getDrawable(R.drawable.bg_gradient_in_dashboard));
        btn.setTextColor(Color.WHITE);
    }
    private void setBgButtonNonGradient(Button btn) {
        btn.setBackground(getDrawable(R.drawable.bg_nav_dashboard));
        btn.setTextColor(Color.BLACK);
    }
    private void addItemToSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items_name_cinema, R.layout.items_spinner_name_cinema);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSelectCinema.setAdapter(adapter);
    }

    private void showDatePicker(TextView txtView) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                DashboardActivity.this,
                new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String selectedDate = dateFormat.format(calendar.getTime());
                        txtView.setText(selectedDate);
                    }

                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void replaceFragment(Fragment fragment) {
        // Lấy FragmentManager và bắt đầu giao dịch
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.linear_layout_show_statistical, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();

    }
}