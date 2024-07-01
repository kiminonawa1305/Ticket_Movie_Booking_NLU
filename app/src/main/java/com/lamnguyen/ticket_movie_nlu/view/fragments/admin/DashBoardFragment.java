package com.lamnguyen.ticket_movie_nlu.view.fragments.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.response.DashboardResponse;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DateTimeFormat;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {
    private static final String TAG = "DashboardActivity";
    private Button btnDay, btnWeek, btnMonth;
    private ImageView imgViewEditCalendar;
    private TextView tvCalendar;
    private Spinner spnSelectCinema;
    private final static String PATH = "/admin/dashboard/api/time?";
    private final static String START_TIME = "T00:00:00";
    private final static String END_TIME = "T23:59:59";
    private DashboardResponse dashboardResponse;
    private Dialog dialogLoading;
    private String selectedDatePicker;
    private int navTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        event();
        getDashboardData();
    }

    private void init(View view) {
        btnDay = view.findViewById(R.id.button_day);
        btnWeek = view.findViewById(R.id.button_week);
        btnMonth = view.findViewById(R.id.button_month);
        imgViewEditCalendar = view.findViewById(R.id.image_view_edit_calendar);
        spnSelectCinema = view.findViewById(R.id.spinner_name_cinema);
        tvCalendar = view.findViewById(R.id.text_view_calendar);
        dialogLoading = DialogLoading.newInstance(getContext());
        addItemToSpinner();
        setDateToTextView();

        replaceFragment(DayDashBoardFragment.class);
    }

    private void event() {
        btnDay.setOnClickListener(v -> {
            setBgButtonGradient(btnDay);
            setBgButtonNonGradient(btnWeek);
            setBgButtonNonGradient(btnMonth);
            navTime = 0;
            replaceFragment(DayDashBoardFragment.class);
            getDashboardData();
        });
        btnWeek.setOnClickListener(v -> {
            setBgButtonGradient(btnWeek);
            setBgButtonNonGradient(btnDay);
            setBgButtonNonGradient(btnMonth);
            navTime = 1;
            replaceFragment(WeekDashBoardFragment.class);
            getDashboardData();
        });
        btnMonth.setOnClickListener(v -> {
            setBgButtonGradient(btnMonth);
            setBgButtonNonGradient(btnDay);
            setBgButtonNonGradient(btnWeek);
            navTime = 2;
            replaceFragment(MonthDashboardFragment.class);
            getDashboardData();
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
    }


    private void setBgButtonGradient(Button btn) {
        btn.setBackground(getContext().getDrawable(R.drawable.bg_gradient_in_dashboard));
        btn.setTextColor(Color.WHITE);
    }

    private void setBgButtonNonGradient(Button btn) {
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setTextColor(Color.BLACK);
    }

    private void addItemToSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
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
                getContext(),
                (DatePicker view, int y, int m, int dayOfMonth) -> {
                    calendar.set(y, m, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    selectedDatePicker = dateFormat.format(calendar.getTime());
                    getDashboardData();
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void setDateToTextView() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        selectedDatePicker = formattedDate;
        tvCalendar.setText(formattedDate);
    }

    private void getDashboardData() {
        String query = createQuery();

        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        CallAPI.callJsonObjectRequest(getContext(), CallAPI.URL_WEB_SERVICE + PATH, query, Request.Method.GET,
                response -> {
                    dialogLoading.dismiss();
                    try {
                        dashboardResponse = new Gson().fromJson(response.getString("data"), DashboardResponse.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", dashboardResponse);
                        switch (navTime) {
                            case 0 -> {
                                getParentFragmentManager().setFragmentResult(DayDashBoardFragment.class.getSimpleName(), bundle);
                            }
                            case 1 ->
                                    getParentFragmentManager().setFragmentResult(WeekDashBoardFragment.class.getSimpleName(), bundle);
                            case 2 ->
                                    getParentFragmentManager().setFragmentResult(MonthDashboardFragment.class.getSimpleName(), bundle);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    dialogLoading.dismiss();
                    if (error instanceof TimeoutError || error instanceof NoConnectionError)
                        Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                });
    }

    private String createQuery() {
        String dateShow = selectedDatePicker;
        String selectedDate = dateShow.split("/")[2] + "-" + dateShow.split("/")[1] + "-" + dateShow.split("/")[0];
        String selectedCinema = spnSelectCinema.getSelectedItem().toString();
        if (navTime == 0) {
            tvCalendar.setText(dateShow);
            return "from=" + selectedDate + START_TIME + "&to=" + selectedDate + END_TIME + "&cinemaId=" + convertNameCinemaToId(selectedCinema);
        }
        String[] date = getStartAndEndOfWeekOrMonth(selectedDate, navTime);
        return "from=" + date[0] + "&to=" + date[1] + "&cinemaId=" + convertNameCinemaToId(selectedCinema);
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
        LocalDateTime givenDate = LocalDateTime.of(year, month, day, 0, 0, 0);
        if (navTime == 1) {
            // Xác định ngày đầu tuần (Thứ Hai)
            LocalDateTime startOfWeek = givenDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            result[0] = startOfWeek.toString();
            // Xác định ngày cuối tuần (Chủ Nhật)
            LocalDateTime endOfWeek = givenDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1);
            result[1] = endOfWeek.toString();

            tvCalendar.setText(DateTimeFormat.getDate(startOfWeek) + " - " + DateTimeFormat.getDate(endOfWeek.plusDays(-1)));
            return result;
        }
        // Xác định ngày đầu tháng
        LocalDateTime startOfMonth = givenDate.with(TemporalAdjusters.firstDayOfMonth());
        result[0] = startOfMonth.toString();
        // Xác định ngày cuối tháng
        LocalDateTime endOfMonth = givenDate.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        result[1] = endOfMonth.toString();
        tvCalendar.setText(DateTimeFormat.getDate(startOfMonth) + " - " + DateTimeFormat.getDate(endOfMonth.plusDays(-1)));
        return result;
    }

    private void replaceFragment(Class fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_show_statistical, fragment, null);
        fragmentTransaction.commit();
    }
}