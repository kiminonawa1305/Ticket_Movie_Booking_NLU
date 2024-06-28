package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.response.DashboardResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.view.fragments.DayDashBoardFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.MonthDashboardFragment;
import com.lamnguyen.ticket_movie_nlu.view.fragments.WeekDashBoardFragment;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    private Button btnDay, btnWeek, btnMonth;
    private ImageView imgViewEditCalendar;
    private TextView tvCalendar;
    private Spinner spnSelectCinema;
    private String url = "/dashboard/api/time?";
    private final static String START_TIME = "T00:00:00";
    private final static String END_TIME = "T23:59:59";
    private DashboardResponse dashboardResponse;
    private Dialog dialogLoading;

    private int navTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnDay = findViewById(R.id.button_day);
        btnWeek = findViewById(R.id.button_week);
        btnMonth = findViewById(R.id.button_month);
        imgViewEditCalendar = findViewById(R.id.image_view_edit_calendar);
        spnSelectCinema = findViewById(R.id.spinner_name_cinema);
        tvCalendar = findViewById(R.id.text_view_calendar);

        addItemToSpinner();

        setDateToTextView();

        btnDay.setOnClickListener(v -> {
            setBgButtonGradient(btnDay);
            setBgButtonNonGradient(btnWeek);
            setBgButtonNonGradient(btnMonth);
            navTime = 0;
            getDashboardData();
            replaceFragment(new DayDashBoardFragment());
        });
        btnWeek.setOnClickListener(v -> {
            setBgButtonGradient(btnWeek);
            setBgButtonNonGradient(btnDay);
            setBgButtonNonGradient(btnMonth);
            navTime = 1;
            getDashboardData();
            replaceFragment(new WeekDashBoardFragment());
        });
        btnMonth.setOnClickListener(v -> {
            setBgButtonGradient(btnMonth);
            setBgButtonNonGradient(btnDay);
            setBgButtonNonGradient(btnWeek);
            navTime = 2;
            getDashboardData();
            replaceFragment(new MonthDashboardFragment());
        });


        imgViewEditCalendar.setOnClickListener(v -> {
            showDatePicker(tvCalendar);
        });

        spnSelectCinema.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getDashboardData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialogLoading = DialogLoading.newInstance(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        getDashboardData();
    }

    private void setBgButtonGradient(Button btn) {
        btn.setBackground(getDrawable(R.drawable.bg_gradient_in_dashboard));
        btn.setTextColor(Color.WHITE);
    }

    private void setBgButtonNonGradient(Button btn) {
        btn.setBackgroundColor(Color.TRANSPARENT);
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
                (DatePicker view, int y, int m, int dayOfMonth) -> {
                    calendar.set(y, m, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String selectedDate = dateFormat.format(calendar.getTime());
                    txtView.setText(selectedDate);
                    getDashboardData();
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void setDateToTextView() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        tvCalendar.setText(formattedDate);
    }

    private void getDashboardData() {
        String query = createQuery();

        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        CallAPI.callJsonObjectRequest(this, CallAPI.URL_WEB_SERVICE + url, query, Request.Method.GET,
                response -> {
                    dialogLoading.dismiss();
                    try {
                        dashboardResponse = new Gson().fromJson(response.getString("data"), DashboardResponse.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", dashboardResponse);
                        switch (navTime) {
                            case 0:
                                getSupportFragmentManager().setFragmentResult(DayDashBoardFragment.class.getSimpleName(), bundle);
                               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_show_statistical, new DayDashBoardFragment()).commit();
                                break;
                            case 1:
                                getSupportFragmentManager().setFragmentResult(WeekDashBoardFragment.class.getSimpleName(), bundle);
                                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_show_statistical, new WeekDashBoardFragment()).commit();
                                break;
                            case 2:
                                getSupportFragmentManager().setFragmentResult(MonthDashboardFragment.class.getSimpleName(), bundle);
                                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_show_statistical, new MonthDashboardFragment()).commit();
                                break;
                        }
//                        getSupportFragmentManager().setFragmentResult(DayDashBoardFragment.class.getSimpleName(), bundle);
                    } catch (JSONException e) {
                        Log.e(TAG, "getDashboardData: ", e);
                    }
                }, error -> {
                    dialogLoading.dismiss();
                    Log.e(TAG, "getDashboardData: ", error);
                });
    }

    private String createQuery() {
        String selectedDate = tvCalendar.getText().toString();
        String selectedCinema = spnSelectCinema.getSelectedItem().toString();
        if (navTime == 0) {
            return "from=" + selectedDate + START_TIME + "&to=" + selectedDate + END_TIME + "&cinemaId=" + convertNameCinemaToId(selectedCinema);
        }
        String[] date = getStartAndEndOfWeekOrMonth(selectedDate, navTime);
        return "from=" + date[0] + START_TIME + "&to=" + date[1] + START_TIME + "&cinemaId=" + convertNameCinemaToId(selectedCinema);
    }

    private int convertNameCinemaToId(String nameCinema) {
        return switch (nameCinema.toUpperCase()) {
            case "CINESTAR" -> 1;
            case "GIGALMALL" -> 2;
            case "GALAXY" -> 3;
            case "COOP.MART" -> 4;
            default -> 0;
        };
    }

    private String[] getStartAndEndOfWeekOrMonth(String selectedDate, int navTime) {
        int year = Integer.parseInt(selectedDate.split("-")[0]);
        int month = Integer.parseInt(selectedDate.split("-")[1]);
        int day = Integer.parseInt(selectedDate.split("-")[2]);
        String[] result = new String[2];
        LocalDate givenDate = LocalDate.of(year, month, day);
        if (navTime == 1) {
            // Xác định ngày đầu tuần (Thứ Hai)
            LocalDate startOfWeek = givenDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            result[0] = startOfWeek.toString();
            // Xác định ngày cuối tuần (Chủ Nhật)
            LocalDate endOfWeek = givenDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1);
            result[1] = endOfWeek.toString();
            return result;
        }
        // Xác định ngày đầu tháng
        LocalDate startOfMonth = givenDate.with(TemporalAdjusters.firstDayOfMonth());
        result[0] = startOfMonth.toString();
        // Xác định ngày cuối tháng
        LocalDate endOfMonth = givenDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        result[1] = endOfMonth.toString();
        return result;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_show_statistical, fragment);
        fragmentTransaction.commit();
    }
}