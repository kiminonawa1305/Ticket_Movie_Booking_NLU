package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.CinemaSpinnerAdapter;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieSpinnerAdapter;
import com.lamnguyen.ticket_movie_nlu.adapters.RoomSpinnerAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.CinemaDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.RoomDTO;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeDTO;
import com.lamnguyen.ticket_movie_nlu.service.cinema.CinemaService;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieService;
import com.lamnguyen.ticket_movie_nlu.service.room.RoomService;
import com.lamnguyen.ticket_movie_nlu.service.showtime.ShowtimeService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddScheduleActivity extends AppCompatActivity {

    private Dialog addScheduleDialog;
    private RoomSpinnerAdapter roomSpinnerAdapter;
    private MovieSpinnerAdapter movieSpinnerAdapter;
    private CinemaSpinnerAdapter cinemaSpinnerAdapter;
    private Button openAddScheduleDialogButton;
    private int roomGroupsInCurrentRow = 0;
    private final int MAX_ROOM_GROUPS_PER_ROW = 3;
    private CinemaService cinemaService;
    private MovieService movieService;
    private RoomService roomService;
    private ShowtimeService showtimeService;
    private MovieDTO selectedMovieDTO;
    private CinemaDTO selectedCinemaDTO;
    private RoomDTO selectedRoomDTO;

    private List<MovieDTO> movieItems = new ArrayList<>();
    private List<CinemaDTO> cinemaItems = new ArrayList<>();
    private List<RoomDTO> roomItems = new ArrayList<>();
    private List<RoomDTO> selectedRoomDTOs = new ArrayList<>();
    private LocalDateTime schedule;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    private EditText showDateEditText, showtimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        schedule = LocalDateTime.now();
        openAddScheduleDialogButton = findViewById(R.id.button_open_dialog_add_schedule);

        cinemaService = CinemaService.getInstance();
        movieService = MovieService.getInstance();
        roomService = RoomService.getInstance();
        showtimeService = ShowtimeService.getInstance();

        loadMovieData();
        loadCinemaData();

        Dialog addScheduleSuccessDialog = new Dialog(this);
        addScheduleSuccessDialog.setContentView(R.layout.dialog_add_schedule_success);
        addScheduleSuccessDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addScheduleSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addScheduleSuccessDialog.setCancelable(false);

        Dialog chooseRoomDialog = new Dialog(this);
        chooseRoomDialog.setContentView(R.layout.dialog_choose_room);
        chooseRoomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chooseRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseRoomDialog.setCancelable(false);


        openAddScheduleDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScheduleDialog();
                Button cancelAddScheduleDialog = addScheduleDialog.findViewById(R.id.button_cancel_add_schedule);
                Button acceptAddSchedule = addScheduleDialog.findViewById(R.id.button_accept_add_schedule);
                Spinner movieSpinner = addScheduleDialog.findViewById(R.id.spinner_movie);
                Spinner roomSpinner = chooseRoomDialog.findViewById(R.id.spinner_room);
                Spinner cinemaSpinner = addScheduleDialog.findViewById(R.id.spinner_cinema);

                showDateEditText = addScheduleDialog.findViewById(R.id.edit_text_date);
                showDateEditText.setText(schedule.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
                showtimeEditText = addScheduleDialog.findViewById(R.id.edit_text_showtime);
                showtimeEditText.setText(schedule.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm")).toString());

                ImageView openDatePickerImageView = addScheduleDialog.findViewById(R.id.image_view_open_date_picker);
                ImageView openTimePickerImageView = addScheduleDialog.findViewById(R.id.image_view_open_time_picker);

                populateMovieSpinner(movieSpinner, movieItems);
                populateCinemaSpinner(cinemaSpinner, cinemaItems);

                movieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedMovieDTO = (MovieDTO) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                cinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedCinemaDTO = (CinemaDTO) parent.getItemAtPosition(position);
                        loadRoomData(selectedCinemaDTO.getId());
                        populateRoomSpinner(roomSpinner, roomItems);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedRoomDTO = (RoomDTO) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Do nothing
                    }
                });

                cancelAddScheduleDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addScheduleDialog.dismiss();
                        roomGroupsInCurrentRow = 0;
                        selectedCinemaDTO = cinemaItems.get(0);
                        selectedMovieDTO = movieItems.get(0);
                        selectedRoomDTO = roomItems.get(0);
                        selectedDate = null;
                        selectedRoomDTOs = new ArrayList<>();
                    }
                });

                openDatePickerImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDatePickerDialog(showDateEditText);
                    }
                });

                openTimePickerImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTimePickerDialog(showtimeEditText);
                    }
                });

                LinearLayout roomGroupsContainerLayout = addScheduleDialog.findViewById(R.id.layout_room_groups_container);
                TextView openChooseRoomDialogTextView = addScheduleDialog.findViewById(R.id.text_view_open_choose_room_dialog);
                TextView noRoomGroup = addScheduleDialog.findViewById(R.id.text_view_no_room_group);

                openChooseRoomDialogTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadRoomData(selectedCinemaDTO.getId());
                        populateRoomSpinner(roomSpinner, roomItems);
                        chooseRoomDialog.show();
                        Button acceptChooseRoomButton = chooseRoomDialog.findViewById(R.id.button_accept_choose_room);
                        acceptChooseRoomButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(selectedRoomDTO != null){
                                    if(selectedRoomDTOs.contains(selectedRoomDTO)){
                                        Toast.makeText(AddScheduleActivity.this, "Phòng này đã được thêm", Toast.LENGTH_SHORT).show();
                                    }else {
                                        selectedRoomDTOs.add(selectedRoomDTO);
                                        chooseRoomDialog.dismiss();
                                        roomGroupsContainerLayout.removeView(noRoomGroup);
                                        handleAddRoomGroup(roomGroupsContainerLayout, noRoomGroup);
                                    }
                                }
                            }
                        });

                        Button closeChooseRoomDialogButton = chooseRoomDialog.findViewById(R.id.button_close_dialog_choose_room);
                        closeChooseRoomDialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                chooseRoomDialog.dismiss();
                                selectedRoomDTO = roomItems.get(0);
                                selectedRoomDTOs = new ArrayList<>();
                            }
                        });
                    }
                });

                acceptAddSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedRoomDTOs.isEmpty()){
                            Toast.makeText(AddScheduleActivity.this, "Vui lòng thêm phòng chiếu", Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                showtimeService.addShowtime(addScheduleDialog.getContext(), selectedRoomDTOs, schedule, selectedMovieDTO.getId(), selectedRoomDTO.getId(), new CallAPI.CallAPIListener<ShowtimeDTO>() {
                                    @Override
                                    public void completed(ShowtimeDTO showtimeDTO) {
                                        addScheduleDialog.dismiss();
                                        addScheduleSuccessDialog.show();

                                        TextView movieNewScheduleTextView = addScheduleSuccessDialog.findViewById(R.id.text_view_movie_new_schedule);
                                        TextView cinemaNewScheduleTextView = addScheduleSuccessDialog.findViewById(R.id.text_view_cinema_new_schedule);
                                        TextView roomNewScheduleTextView = addScheduleSuccessDialog.findViewById(R.id.text_view_room_new_schedule);
                                        TextView showDateNewScheduleTextView = addScheduleSuccessDialog.findViewById(R.id.text_view_showDate_new_schedule);
                                        TextView showtimeNewScheduleTextView = addScheduleSuccessDialog.findViewById(R.id.text_view_showtime_new_schedule);

                                        movieNewScheduleTextView.setText(selectedMovieDTO.getTitle());
                                        cinemaNewScheduleTextView.setText(selectedCinemaDTO.getName());
                                        roomNewScheduleTextView.setText(selectedRoomDTOs.stream().map(selectedDTO -> selectedDTO.getName()).collect(Collectors.joining(", ")));
                                        showDateNewScheduleTextView.setText(schedule.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                        showtimeNewScheduleTextView.setText(schedule.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm")));
                                    }

                                    @Override
                                    public void error(Object error) {

                                    }
                                });
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
            }
        });

        Button closeAddScheduleSuccessDialogButton = addScheduleSuccessDialog.findViewById(R.id.button_close_dialog_add_schedule_success);
        closeAddScheduleSuccessDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScheduleSuccessDialog.dismiss();
                roomGroupsInCurrentRow = 0;
                selectedCinemaDTO = cinemaItems.get(0);
                selectedMovieDTO = movieItems.get(0);
                selectedRoomDTO = roomItems.get(0);
                selectedDate = null;
                selectedRoomDTOs = new ArrayList<>();
            }
        });
    }

    private void loadMovieData(){
        movieService.loadMovie(null, this, new CallAPI.CallAPIListener<List<MovieDTO>>() {
            @Override
            public void completed(List<MovieDTO> movieDTOS) {
                movieItems = movieDTOS;
                if(!movieItems.isEmpty()){
                    selectedMovieDTO = movieItems.get(0);
                }
            }

            @Override
            public void error(Object error) {
                // Handle error
            }
        });
    }

    private void loadCinemaData(){
        cinemaService.loadCinemas(this, new CallAPI.CallAPIListener<List<CinemaDTO>>() {
            @Override
            public void completed(List<CinemaDTO> cinemaDTOS) {
                cinemaItems = cinemaDTOS;
                if(!cinemaItems.isEmpty()){
                    selectedCinemaDTO = cinemaItems.get(0);
                    loadRoomData(selectedCinemaDTO.getId());
                }
            }

            @Override
            public void error(Object error) {
                // Handle error
            }
        });
    }

    private void loadRoomData(Integer cinemaId){
        roomService.loadRoomsOfCinema(this, cinemaId, new CallAPI.CallAPIListener<List<RoomDTO>>() {
            @Override
            public void completed(List<RoomDTO> roomDTOS) {
                roomItems = roomDTOS;
                if(!roomItems.isEmpty()){
                    selectedRoomDTO = roomItems.get(0);
                }
            }

            @Override
            public void error(Object error) {
                // Handle error
            }
        });
    }

    private void handleAddRoomGroup(LinearLayout roomGroupsContainerLayout, TextView noRoomGroup){
        int spacing = 18;
        if(roomGroupsInCurrentRow == 0 || roomGroupsInCurrentRow == MAX_ROOM_GROUPS_PER_ROW){
            if(roomGroupsInCurrentRow == MAX_ROOM_GROUPS_PER_ROW){
                roomGroupsInCurrentRow = 0;
            }
            LinearLayout newRoomGroupsRow = new LinearLayout(roomGroupsContainerLayout.getContext());
            newRoomGroupsRow.setOrientation(LinearLayout.HORIZONTAL);

            roomGroupsContainerLayout.addView(newRoomGroupsRow);

            LinearLayout.LayoutParams roomGroupsRowLayoutParams = (LinearLayout.LayoutParams) newRoomGroupsRow.getLayoutParams();

            if(roomGroupsContainerLayout.getChildCount() == 1){
                roomGroupsRowLayoutParams.setMargins(0,0,0,0);
            }else{
                roomGroupsRowLayoutParams.setMargins(0,spacing,0,0);
            }
            newRoomGroupsRow.setLayoutParams(roomGroupsRowLayoutParams);
        }

        View roomGroup = LayoutInflater.from(roomGroupsContainerLayout.getContext())
                .inflate(R.layout.item_showtime_group, null);

        LinearLayout currentRoomGroupsRow = (LinearLayout) roomGroupsContainerLayout
                .getChildAt(roomGroupsContainerLayout.getChildCount() - 1);

        int roomGroupsRowWidth = roomGroupsContainerLayout.getWidth();
        int roomGroupWidth = (roomGroupsRowWidth - 2 * spacing) / 3;

        currentRoomGroupsRow.addView(roomGroup);
        roomGroupsInCurrentRow++;

        LinearLayout.LayoutParams roomGrouplayoutParams = new LinearLayout.LayoutParams(
                roomGroupWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        roomGrouplayoutParams.setMargins(0, 0, spacing, 0);
        roomGroup.setLayoutParams(roomGrouplayoutParams);

        ImageView deleteRoomGroupImageView = roomGroup.findViewById(R.id.image_view_delete_room_group);
        TextView roomTextView = roomGroup.findViewById(R.id.text_view_room);

        if(selectedRoomDTO != null){
            roomTextView.setText(selectedRoomDTO.getName());
        }

        deleteRoomGroupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout roomGroupsRow = (LinearLayout) roomGroup.getParent();
                roomGroupsRow.removeView(roomGroup);
                roomGroupsInCurrentRow--;
                adjustRoomGroupsRows(roomGroupsContainerLayout, roomGroupsRow, noRoomGroup);

                TextView roomTextView = roomGroup.findViewById(R.id.text_view_room);

                selectedRoomDTOs.removeIf(selectedDTO -> selectedDTO.getName() == roomTextView.getText());
            }
        });
    }



    private void adjustRoomGroupsRows(LinearLayout roomGroupsContainerLayout, LinearLayout currentRoomGroupsRow, TextView noRoomGroup){
        for(int i = 0; i < roomGroupsContainerLayout.getChildCount(); i++){
            LinearLayout showtimeGroupsRow = (LinearLayout) roomGroupsContainerLayout.getChildAt(i);
            while (showtimeGroupsRow.getChildCount() < MAX_ROOM_GROUPS_PER_ROW
                    && i < roomGroupsContainerLayout.getChildCount() - 1){
                LinearLayout nextShowtimeGroupsRow = (LinearLayout) roomGroupsContainerLayout.getChildAt(i + 1);
                if(nextShowtimeGroupsRow.getChildCount() > 0){
                    View showtimeGroupFirst = nextShowtimeGroupsRow.getChildAt(0);
                    nextShowtimeGroupsRow.removeView(showtimeGroupFirst);
                    showtimeGroupsRow.addView(showtimeGroupFirst);
                }

                if(nextShowtimeGroupsRow.getChildCount() == 0){
                    roomGroupsContainerLayout.removeView(nextShowtimeGroupsRow);
                }
            }
        }

        int currentShowtimeGroupsRowIndex = roomGroupsContainerLayout.indexOfChild(currentRoomGroupsRow);
        if (currentShowtimeGroupsRowIndex == roomGroupsContainerLayout.getChildCount() - 1
                && currentRoomGroupsRow.getChildCount() == 0){
            roomGroupsContainerLayout.removeView(currentRoomGroupsRow);
        }

        if (roomGroupsContainerLayout.getChildCount() > 0){
            LinearLayout lastShowtimeGroupsRow = (LinearLayout) roomGroupsContainerLayout
                    .getChildAt(roomGroupsContainerLayout.getChildCount() - 1);
            roomGroupsInCurrentRow = lastShowtimeGroupsRow.getChildCount();
        }else{
            roomGroupsContainerLayout.addView(noRoomGroup);
            roomGroupsInCurrentRow = 0;
        }
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

    private void populateCinemaSpinner(Spinner cinemaSpinner, List<CinemaDTO> cinemaItems){
        cinemaSpinnerAdapter = new CinemaSpinnerAdapter(this, R.layout.item_spinner, cinemaItems);
        cinemaSpinner.setAdapter(cinemaSpinnerAdapter);
    }

    private void openDatePickerDialog(EditText showDateEditText){
        LocalDate currentDate = LocalDate.now();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDate = LocalDate.of(year, month, dayOfMonth);
                if (selectedDate.isBefore(currentDate)){
                    Toast.makeText(AddScheduleActivity.this, "Vui lòng chọn ngày lớn hơn hoặc bằng hiện tại", Toast.LENGTH_SHORT).show();
                }else {
                    showDateEditText.setText(selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
                    if(selectedTime == null){
                        selectedTime = LocalTime.parse(showtimeEditText.getText());
                    }
                    schedule = selectedDate.atTime(selectedTime);
                }
            }
        }, currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
        datePickerDialog.show();
    }

    private void openTimePickerDialog(EditText showTimeEditText){
        LocalTime currentTime = LocalTime.now();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = LocalTime.of(hourOfDay, minute);
                if(selectedTime.isBefore(currentTime)){
                    Toast.makeText(AddScheduleActivity.this, "Vui lòng chọn thời gian lớn hơn hoặc bằng hiện tại", Toast.LENGTH_SHORT).show();
                }else{
                    showTimeEditText.setText(selectedTime.toString());
                    if(selectedDate == null){
                        selectedDate = LocalDate.parse(showDateEditText.getText());
                    }
                    schedule = selectedDate.atTime(selectedTime);
                }
            }
        }, currentTime.getHour(), currentTime.getMinute(), true);
        timePickerDialog.show();
    }
}


