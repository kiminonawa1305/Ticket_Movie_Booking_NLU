package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.service.movie.MovieService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import org.json.JSONException;

public class AddMovieActivity extends AppCompatActivity {

    Button openDialogAddMovieButton, cancelAddMovieButton, acceptAddMovieButton;
    EditText inputIdMovieEditText;
    ImageView clearInputIdMovieImageView;
    Dialog addMovieDialog;
    MovieService movieService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie);
        openDialogAddMovieButton = findViewById(R.id.button_open_dialog_add_movie);

        addMovieDialog = new Dialog(this);
        addMovieDialog.setContentView(R.layout.dialog_add_movie);
        addMovieDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addMovieDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMovieDialog.setCancelable(false);

        Dialog addMovieSuccessDialog = new Dialog(this);
        addMovieSuccessDialog.setContentView(R.layout.dialog_add_movie_success);
        addMovieSuccessDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addMovieSuccessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addMovieSuccessDialog.setCancelable(false);

        openDialogAddMovieButton.setOnClickListener(new View.OnClickListener() {
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
                movieService = MovieService.getInstance();
                String idApi = inputIdMovieEditText.getText().toString();
                if (idApi.isEmpty()) {
                    Toast.makeText(AddMovieActivity.this, "Mã phim không được bỏ trống", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AddMovieActivity.this, "Phim không tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        Button closeAddMovieSuccessDialogButton = addMovieSuccessDialog.findViewById(R.id.button_close_dialog_add_movie_success);
        closeAddMovieSuccessDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieSuccessDialog.dismiss();
            }
        });
    }
}