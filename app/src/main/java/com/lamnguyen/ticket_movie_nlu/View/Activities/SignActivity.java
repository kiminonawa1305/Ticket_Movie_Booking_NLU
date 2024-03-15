package com.lamnguyen.ticket_movie_nlu.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

public class SignActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    private Dialog dialog;

    private void initDialogLoading() {
        if (dialog == null) {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.item_loading);
            dialog.setCancelable(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        initDialogLoading();
    }

    public void showDialogLoading(String title) {
        TextView tvTitleDialogLoading = dialog.findViewById(R.id.text_view_title_progress_loading);
        tvTitleDialogLoading.setText(title);
        dialog.show();
    }

    public void dismissDialogLoading() {
        dialog.cancel();
    }
}