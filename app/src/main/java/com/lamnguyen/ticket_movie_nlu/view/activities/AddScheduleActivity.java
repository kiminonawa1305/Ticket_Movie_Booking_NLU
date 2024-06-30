package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddScheduleActivity extends AppCompatActivity {

    private Dialog addScheduleDialog, chooseRoomDialog, addScheduleSuccessDialog;
    private Spinner movieSpinner, cinemaSpinner, roomSpinner;
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
    private LinearLayout roomGroupsContainerLayout;
    private TextView noRoomGroup;
    private List<RoomDTO> selectedRoomDTOs = new ArrayList<>();
    private List<RoomDTO> roomItems;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
    private EditText showDateEditText, showtimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        openAddScheduleDialogButton = findViewById(R.id.button_open_dialog_add_schedule);

        selectedDate = LocalDate.now();
        selectedTime = LocalTime.now();
        cinemaService = CinemaService.getInstance();
        movieService = MovieService.getInstance();
        roomService = RoomService.getInstance();
        showtimeService = ShowtimeService.getInstance();

        addScheduleDialog = new Dialog(this);
        addScheduleDialog.setContentView(R.layout.dialog_add_schedule);
        addScheduleDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addScheduleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addScheduleDialog.setCancelable(false);

        chooseRoomDialog = new Dialog(this);
        chooseRoomDialog.setContentView(R.layout.dialog_choose_room);
        chooseRoomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chooseRoomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooseRoomDialog.setCancelable(false);

        addScheduleSuccessDialog = new Dialog(this);
        addScheduleSuccessDialog.setContentView(R.layout.dialog_add_schedule_success);
        addScheduleSuccessDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addScheduleSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addScheduleSuccessDialog.setCancelable(false);

        movieSpinner = addScheduleDialog.findViewById(R.id.spinner_movie);
        roomSpinner = chooseRoomDialog.findViewById(R.id.spinner_room);
        cinemaSpinner = addScheduleDialog.findViewById(R.id.spinner_cinema);

        loadMovieData();
        loadCinemaData();

        openAddScheduleDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScheduleDialog.show();

                Button cancelAddScheduleDialog = addScheduleDialog.findViewById(R.id.button_cancel_add_schedule);
                Button acceptAddSchedule = addScheduleDialog.findViewById(R.id.button_accept_add_schedule);

                showDateEditText = addScheduleDialog.findViewById(R.id.edit_text_date);
                showDateEditText.setText(formatLocalDate(selectedDate));
                showtimeEditText = addScheduleDialog.findViewById(R.id.edit_text_showtime);
                showtimeEditText.setText(formatLocalTime(selectedTime));

                ImageView openDatePickerImageView = addScheduleDialog.findViewById(R.id.image_view_open_date_picker);
                ImageView openTimePickerImageView = addScheduleDialog.findViewById(R.id.image_view_open_time_picker);

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
                        CinemaDTO newSelectedCinemaDTO = (CinemaDTO) parent.getItemAtPosition(position);
                        if (!newSelectedCinemaDTO.equals(selectedCinemaDTO)){
                            roomGroupsInCurrentRow = 0;
                            selectedRoomDTOs = new ArrayList<>();
                            roomGroupsContainerLayout.removeAllViews();
                            roomGroupsContainerLayout.addView(noRoomGroup);
                        }
                        selectedCinemaDTO = newSelectedCinemaDTO;
                        loadRoomData(selectedCinemaDTO.getId());
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
                        reset();
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

                roomGroupsContainerLayout = addScheduleDialog.findViewById(R.id.layout_room_groups_container);
                TextView openChooseRoomDialogTextView = addScheduleDialog.findViewById(R.id.text_view_open_choose_room_dialog);
                noRoomGroup = addScheduleDialog.findViewById(R.id.text_view_no_room_group);

                openChooseRoomDialogTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(roomItems == null || roomItems.isEmpty() ){
                            Toast.makeText(AddScheduleActivity.this, "Phòng chiếu đã hết", Toast.LENGTH_SHORT).show();
                        }else {
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
                                            handleAddRoomGroup();
                                        }
                                    }
                                }
                            });

                            Button closeChooseRoomDialogButton = chooseRoomDialog.findViewById(R.id.button_close_dialog_choose_room);
                            closeChooseRoomDialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    chooseRoomDialog.dismiss();
                                }
                            });
                        }
                    }
                });

                acceptAddSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedRoomDTOs.isEmpty()){
                            Toast.makeText(AddScheduleActivity.this, "Vui lòng thêm phòng chiếu", Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                showtimeService.addShowtime(addScheduleDialog.getContext(), selectedRoomDTOs, selectedDate.atTime(selectedTime), selectedMovieDTO.getId(), selectedRoomDTO.getId(), new CallAPI.CallAPIListener<ShowtimeDTO>() {
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
                                        showDateNewScheduleTextView.setText(formatLocalDate(selectedDate));
                                        showtimeNewScheduleTextView.setText(formatLocalTime(selectedTime));

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
                reset();
                addScheduleSuccessDialog.dismiss();
            }
        });
    }

    private void loadMovieData(){
        movieService.loadMovie(null, this, new CallAPI.CallAPIListener<List<MovieDTO>>() {
            @Override
            public void completed(List<MovieDTO> movieDTOS) {
                populateMovieSpinner(movieSpinner, movieDTOS);
                if(!movieDTOS.isEmpty()){
                    selectedMovieDTO = movieDTOS.get(0);
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
                populateCinemaSpinner(cinemaSpinner, cinemaDTOS);
                if(!cinemaDTOS.isEmpty()) {
                    selectedCinemaDTO = cinemaDTOS.get(0);
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
                populateRoomSpinner(roomSpinner, roomDTOS);
                if(!roomDTOS.isEmpty()) {
                    selectedRoomDTO = roomDTOS.get(0);
                }
            }

            @Override
            public void error(Object error) {
                // Handle error
            }
        });
    }

    private void handleAddRoomGroup(){
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
                .inflate(R.layout.item_room_group, null);

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
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                if (selectedDate.isBefore(currentDate)){
                    Toast.makeText(AddScheduleActivity.this, "Vui lòng chọn ngày lớn hơn hoặc bằng hiện tại", Toast.LENGTH_SHORT).show();
                }else {
                    showDateEditText.setText(formatLocalDate(selectedDate));
                }
            }
        }, currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
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
                    showTimeEditText.setText(formatLocalTime(selectedTime));
                }
            }
        }, currentTime.getHour(), currentTime.getMinute(), true);
        timePickerDialog.show();
    }

    private String formatLocalDate(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String formatLocalTime(LocalTime localTime){
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    private void reset(){
        roomGroupsInCurrentRow = 0;
        selectedTime = LocalTime.now();
        selectedDate = LocalDate.now();
        selectedRoomDTOs = new ArrayList<>();
        roomItems = new ArrayList<>();
        roomGroupsContainerLayout.removeAllViews();
        roomGroupsContainerLayout.addView(noRoomGroup);
        loadMovieData();
        loadCinemaData();
    }
}


