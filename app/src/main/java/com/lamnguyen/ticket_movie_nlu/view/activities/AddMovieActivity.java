package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lamnguyen.ticket_movie_nlu.R;

public class AddMovieActivity extends AppCompatActivity {

    Button openDialogAddMovieButton, cancelAddMovieButton, acceptAddMovieButton;
    EditText inputIdMovieEditText;
    ImageView clearInputIdMovieImageView;
    Dialog addMovieDialog;

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
    }
}