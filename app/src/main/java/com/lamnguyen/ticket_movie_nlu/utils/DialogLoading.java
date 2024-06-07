package com.lamnguyen.ticket_movie_nlu.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.HashMap;
import java.util.Map;

public class DialogLoading {
    public static void showDialogLoading(Dialog dialog, String title) {
        TextView tvTitleDialogLoading = dialog.findViewById(R.id.text_view_title_progress_loading);
        tvTitleDialogLoading.setText(title);
        dialog.show();
    }

    public static Dialog newInstance(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        return dialog;
    }
}
