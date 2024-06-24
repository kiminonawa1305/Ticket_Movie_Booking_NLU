package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieSpinnerAdapter;
import com.lamnguyen.ticket_movie_nlu.adapters.RoomSpinnerAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddScheduleActivity extends AppCompatActivity {

    private Dialog addScheduleDialog;
    private RoomSpinnerAdapter roomSpinnerAdapter;
    private MovieSpinnerAdapter movieSpinnerAdapter;
    private Button openAddScheduleDialogButton;
    private List<MovieDTO> movieItems;
    private List<RoomDTO> roomItems;
    private int showtimeGroupsInCurrentRow = 0;
    private final int MAX_SHOWTIME_GROUPS_PER_ROW = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        openAddScheduleDialogButton = findViewById(R.id.button_open_dialog_add_schedule);
        openAddScheduleDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScheduleDialog();
                Button cancelAddScheduleDialog = addScheduleDialog.findViewById(R.id.button_cancel_add_schedule);
                Spinner movieSpinner = addScheduleDialog.findViewById(R.id.spinner_movie);
                Spinner roomSpinner = addScheduleDialog.findViewById(R.id.spinner_room);
                EditText dateEditText = addScheduleDialog.findViewById(R.id.edit_text_date);
                ImageView openDatePickerImageView = addScheduleDialog.findViewById(R.id.image_view_open_date_picker);
                movieItems = setupMovieItemsData();
                roomItems = setupRoomItemsData();
                populateMovieSpinner(movieSpinner, movieItems);
                populateRoomSpinner(roomSpinner, roomItems);

                cancelAddScheduleDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addScheduleDialog.dismiss();
                        showtimeGroupsInCurrentRow = 0;
                    }
                });

                openDatePickerImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDatePickerDialog(dateEditText);
                    }
                });

                LinearLayout showtimeGroupsContainerLayout = addScheduleDialog.findViewById(R.id.layout_showtime_groups_container);
                TextView addShowtimeGroupTextView = addScheduleDialog.findViewById(R.id.text_view_add_showtime_group);
                TextView noShowtimeGroup = addScheduleDialog.findViewById(R.id.text_view_no_showtime_group);

                addShowtimeGroupTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showtimeGroupsContainerLayout.removeView(noShowtimeGroup);
                        handleAddShowtimeGroup(showtimeGroupsContainerLayout, noShowtimeGroup);
                    }
                });
            }
        });
    }

    private void handleAddShowtimeGroup(LinearLayout showtimeGroupsContainerLayout, TextView noShowtimeGroup){
        int spacing = 18;
        if(showtimeGroupsInCurrentRow == 0 || showtimeGroupsInCurrentRow == MAX_SHOWTIME_GROUPS_PER_ROW){
            if(showtimeGroupsInCurrentRow == MAX_SHOWTIME_GROUPS_PER_ROW){
                showtimeGroupsInCurrentRow = 0;
            }
            LinearLayout newShowtimeGroupsRow = new LinearLayout(showtimeGroupsContainerLayout.getContext());
            newShowtimeGroupsRow.setOrientation(LinearLayout.HORIZONTAL);

            showtimeGroupsContainerLayout.addView(newShowtimeGroupsRow);

            LinearLayout.LayoutParams showtimeGroupsRowParams = (LinearLayout.LayoutParams) newShowtimeGroupsRow.getLayoutParams();

            if(showtimeGroupsContainerLayout.getChildCount() == 1){
                showtimeGroupsRowParams.setMargins(0,0,0,0);
            }else{
                showtimeGroupsRowParams.setMargins(0,spacing,0,0);
            }
            newShowtimeGroupsRow.setLayoutParams(showtimeGroupsRowParams);
        }

        View showtimeGroup = LayoutInflater.from(showtimeGroupsContainerLayout.getContext())
                .inflate(R.layout.item_showtime_group, null);

        LinearLayout currentShowtimeGroupsRow = (LinearLayout) showtimeGroupsContainerLayout
                .getChildAt(showtimeGroupsContainerLayout.getChildCount() - 1);

        int showtimeGroupsRowWidth = showtimeGroupsContainerLayout.getWidth();
        int showtimeGroupWidth = (showtimeGroupsRowWidth - 2 * spacing) / 3;

        currentShowtimeGroupsRow.addView(showtimeGroup);
        showtimeGroupsInCurrentRow++;

        LinearLayout.LayoutParams showtimeGrouplayoutParams = new LinearLayout.LayoutParams(
                showtimeGroupWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        showtimeGrouplayoutParams.setMargins(0, 0, spacing, 0);
        showtimeGroup.setLayoutParams(showtimeGrouplayoutParams);

        LinearLayout deleteShowtimeGroupTextView = showtimeGroup.findViewById(R.id.delete_showtime_group);
        ImageView openTimePickerImageView = showtimeGroup.findViewById(R.id.image_view_open_time_picker);
        EditText showtimeEditText = showtimeGroup.findViewById(R.id.edit_text_showtime);

        deleteShowtimeGroupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout showtimeGroupsRow = (LinearLayout) showtimeGroup.getParent();
                showtimeGroupsRow.removeView(showtimeGroup);
                showtimeGroupsInCurrentRow--;
                adjustShowtimeGroupsRows(showtimeGroupsContainerLayout, showtimeGroupsRow, noShowtimeGroup);
            }
        });

        openTimePickerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(showtimeEditText);
            }
        });
    }

    private void adjustShowtimeGroupsRows(LinearLayout showtimeGroupsContainerLayout, LinearLayout currentShowtimeGroupsRow, TextView noShowtimeGroup){
        for(int i = 0; i < showtimeGroupsContainerLayout.getChildCount(); i++){
            LinearLayout showtimeGroupsRow = (LinearLayout) showtimeGroupsContainerLayout.getChildAt(i);
            while (showtimeGroupsRow.getChildCount() < MAX_SHOWTIME_GROUPS_PER_ROW
                    && i < showtimeGroupsContainerLayout.getChildCount() - 1){
                LinearLayout nextShowtimeGroupsRow = (LinearLayout) showtimeGroupsContainerLayout.getChildAt(i + 1);
                if(nextShowtimeGroupsRow.getChildCount() > 0){
                    View showtimeGroupFirst = nextShowtimeGroupsRow.getChildAt(0);
                    nextShowtimeGroupsRow.removeView(showtimeGroupFirst);
                    showtimeGroupsRow.addView(showtimeGroupFirst);
                }

                if(nextShowtimeGroupsRow.getChildCount() == 0){
                    showtimeGroupsContainerLayout.removeView(nextShowtimeGroupsRow);
                }
            }
        }

        int currentShowtimeGroupsRowIndex = showtimeGroupsContainerLayout.indexOfChild(currentShowtimeGroupsRow);
        if (currentShowtimeGroupsRowIndex == showtimeGroupsContainerLayout.getChildCount() - 1
                && currentShowtimeGroupsRow.getChildCount() == 0){
            showtimeGroupsContainerLayout.removeView(currentShowtimeGroupsRow);
        }

        if (showtimeGroupsContainerLayout.getChildCount() > 0){
            LinearLayout lastShowtimeGroupsRow = (LinearLayout) showtimeGroupsContainerLayout
                    .getChildAt(showtimeGroupsContainerLayout.getChildCount() - 1);
            showtimeGroupsInCurrentRow = lastShowtimeGroupsRow.getChildCount();
        }else{
            showtimeGroupsContainerLayout.addView(noShowtimeGroup);
            showtimeGroupsInCurrentRow = 0;
        }
    }

    private List<MovieDTO> setupMovieItemsData(){
        List<MovieDTO> movieItems = new ArrayList<>();
        movieItems.add(MovieDTO.builder().title("Phim 1").build());
        movieItems.add(MovieDTO.builder().title("Phim 2").build());
        movieItems.add(MovieDTO.builder().title("Phim 3").build());
        movieItems.add(MovieDTO.builder().title("Phim 4").build());
        movieItems.add(MovieDTO.builder().title("Phim 5").build());
        return movieItems;
    }

    private List<RoomDTO> setupRoomItemsData(){
        List<RoomDTO> roomItems = new ArrayList<>();
        roomItems.add(RoomDTO.builder().name("Phòng 1").build());
        roomItems.add(RoomDTO.builder().name("Phòng 2").build());
        roomItems.add(RoomDTO.builder().name("Phòng 3").build());
        return roomItems;
    }

    private void showAddScheduleDialog(){
        addScheduleDialog = new Dialog(this);
        addScheduleDialog.setContentView(R.layout.dialog_add_schedule);
        addScheduleDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addScheduleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addScheduleDialog.setCancelable(false);
        addScheduleDialog.show();
    }

    private void populateMovieSpinner(Spinner movieSpinner, List<MovieDTO> movieItems){
        movieSpinnerAdapter = new MovieSpinnerAdapter(this, R.layout.item_spinner, movieItems);
        movieSpinner.setAdapter(movieSpinnerAdapter);
    }

    private void populateRoomSpinner(Spinner roomSpinner, List<RoomDTO> roomItems){
        roomSpinnerAdapter = new RoomSpinnerAdapter(this, R.layout.item_spinner, roomItems);
        roomSpinner.setAdapter(roomSpinnerAdapter);
    }

    private void openDatePickerDialog(EditText dateEditText){
        LocalDate currentDate = LocalDate.now();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate selectedDate = LocalDate.of(year, month, dayOfMonth);
                dateEditText.setText(selectedDate.toString());
            }
        }, currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        datePickerDialog.show();
    }

    private void openTimePickerDialog(EditText showTimeEditText){
        LocalTime currentTime = LocalTime.now();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                LocalTime selectedTime = LocalTime.of(hourOfDay, minute);
                showTimeEditText.setText(selectedTime.toString());
            }
        }, currentTime.getHour(), currentTime.getMinute(), true);
        timePickerDialog.show();
    }
}