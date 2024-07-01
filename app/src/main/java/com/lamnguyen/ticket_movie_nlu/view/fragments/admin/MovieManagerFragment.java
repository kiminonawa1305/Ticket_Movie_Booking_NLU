package com.lamnguyen.ticket_movie_nlu.view.fragments.admin;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDetailDTO;
import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieDetailService;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieManagerFragment extends Fragment {

    private Button cancelAddMovieButton, acceptAddMovieButton;
    private EditText inputIdMovieEditText;
    private ImageView clearInputIdMovieImageView, iv_add_movie;
    private Dialog addMovieDialog, dialogLoading;
    private MovieDetailService movieDetailService;
    private MovieService movieService;
    private TableLayout tlMovieManager;
    private List<MovieDetailDTO> movieDetailDTOS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init(view);

        getAllMovie();
    }


    private void init(View view) {
        movieDetailDTOS = new ArrayList<>();
        movieDetailService = MovieDetailService.getInstance();
        movieService = MovieService.getInstance();

        iv_add_movie = view.findViewById(R.id.image_view_add_movie);
        tlMovieManager = view.findViewById(R.id.table_layout_movie_manager);

        dialogLoading = DialogLoading.newInstance(getContext());

        addMovieDialog = new Dialog(getContext());
        addMovieDialog.setContentView(R.layout.dialog_add_movie);
        addMovieDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addMovieDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMovieDialog.setCancelable(false);

        Dialog addMovieSuccessDialog = new Dialog(getContext());
        addMovieSuccessDialog.setContentView(R.layout.dialog_add_movie_success);
        addMovieSuccessDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addMovieSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMovieSuccessDialog.setCancelable(false);

        iv_add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieDialog.show();
            }
        });

        cancelAddMovieButton = addMovieDialog.findViewById(R.id.button_cancel_add_movie);
        cancelAddMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieDialog.dismiss();
                inputIdMovieEditText.setText("");
            }
        });


        clearInputIdMovieImageView = addMovieDialog.findViewById(R.id.image_view_clear_input_id_movie);
        inputIdMovieEditText = addMovieDialog.findViewById(R.id.edit_text_input_id_movie);

        clearInputIdMovieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputIdMovieEditText.setText("");
            }
        });

        acceptAddMovieButton = addMovieDialog.findViewById(R.id.button_accept_add_movie);

        acceptAddMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idApi = inputIdMovieEditText.getText().toString();
                if (idApi.isEmpty()) {
                    Toast.makeText(getContext(), "Mã phim không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        movieService.addNewMovie(addMovieDialog.getContext(), idApi, new CallAPI.CallAPIListener<MovieDTO>() {
                            @Override
                            public void completed(MovieDTO movieDTO) {
                                addMovieDialog.dismiss();
                                inputIdMovieEditText.setText("");

                                TextView titleNewMovieTextView = addMovieSuccessDialog.findViewById(R.id.text_view_title_new_movie);
                                TextView genderNewMovieTextView = addMovieSuccessDialog.findViewById(R.id.text_view_gender_new_movie);
                                TextView durationNewMovieTextView = addMovieSuccessDialog.findViewById(R.id.text_view_duration_new_movie);
                                ImageView posterNewMovieImageView = addMovieSuccessDialog.findViewById(R.id.image_view_poster_new_movie);
                                titleNewMovieTextView.setText(movieDTO.getTitle());
                                genderNewMovieTextView.setText(movieDTO.getGenre());
                                durationNewMovieTextView.setText(movieDTO.getDuration());
                                Glide.with(addMovieSuccessDialog.getContext()).load(movieDTO.getPoster()).into(posterNewMovieImageView);
                                addMovieSuccessDialog.show();
                            }

                            @Override
                            public void error(Object error) {
                                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                                    Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                                else if (((VolleyError) error).networkResponse != null) {
                                    CallAPI.ErrorResponse errorResponse = new Gson().fromJson(new String(((VolleyError) error).networkResponse.data), CallAPI.ErrorResponse.class);
                                    Toast.makeText(getContext(), errorResponse.message(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Button closeAddMovieSuccessDialogButton = addMovieSuccessDialog.findViewById(R.id.button_close_dialog_add_movie_success);
        closeAddMovieSuccessDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieSuccessDialog.dismiss();
                getAllMovie();
            }
        });
    }

    private void getAllMovie() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        movieDetailService.loadAllMovieDetail(getContext(), new CallAPI.CallAPIListener<List<MovieDetailDTO>>() {
            @Override
            public void completed(List<MovieDetailDTO> movies) {
                dialogLoading.dismiss();
                MovieManagerFragment.this.movieDetailDTOS.clear();
                MovieManagerFragment.this.movieDetailDTOS.addAll(movies);
                tlMovieManager.removeAllViews();
                populateTable();
            }

            @Override
            public void error(Object error) {
                dialogLoading.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                    Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateTable() {
        TableRow headerRow = new TableRow(getContext());
        headerRow.addView(createTextView(getString(R.string.id), true));
        headerRow.addView(createTextView(getString(R.string.movie_name), true));
        headerRow.addView(createTextView(getString(R.string.film_duration), true));
        headerRow.addView(createTextView(getString(R.string.genre), true));
        headerRow.addView(createTextView(getString(R.string.director), true));
        headerRow.addView(createTextView(getString(R.string.actor), true));
        headerRow.addView(createTextView(getString(R.string.vote), true));
        headerRow.addView(createTextView(getString(R.string.movie_rate), true));
        headerRow.setBackgroundResource(R.color.gray);
        tlMovieManager.addView(headerRow);

        for (MovieDetailDTO movieDetailDTO : movieDetailDTOS) {
            int index = movieDetailDTOS.indexOf(movieDetailDTO);
            TableRow row = new TableRow(getContext());
            if (index % 2 != 0)
                row.setBackgroundResource(R.color.bg_input_info_sign);

            row.addView(createTextView(String.valueOf(movieDetailDTO.getId()), false));
            row.addView(createTextView(movieDetailDTO.getTitle(), false));
            row.addView(createTextView(movieDetailDTO.getDuration(), false));
            row.addView(createTextView(Arrays.stream(movieDetailDTO.getGenres()).collect(Collectors.joining(", ")), false));
            row.addView(createTextView(Arrays.stream(movieDetailDTO.getDirectors()).collect(Collectors.joining(", ")), false));
            row.addView(createTextView(Arrays.stream(movieDetailDTO.getActors()).collect(Collectors.joining(", ")), false));
            row.addView(createTextView(String.valueOf(movieDetailDTO.getVote()), false));
            row.addView(createTextView(String.valueOf(movieDetailDTO.getRate()), false));

            tlMovieManager.addView(row);
        }
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(20, 8, 8, 20);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        if (isHeader) {
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            textView.setTextSize(20);
        }
        return textView;
    }
}