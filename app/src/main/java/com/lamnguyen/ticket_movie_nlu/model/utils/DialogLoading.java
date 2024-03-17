package com.lamnguyen.ticket_movie_nlu.model.utils;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.Map;

public class DialogLoading {
    private Dialog dialog;

    private static Map<String, DialogLoading> mapDialogLoading;

    public static DialogLoading getInstance(Activity activity) {
        DialogLoading dialogLoading = mapDialogLoading.get(activity.getClass().getName());
        if (dialogLoading == null) {
            dialogLoading = new DialogLoading(activity);
            mapDialogLoading.put(activity.getClass().getName(), dialogLoading);
        }

        return dialogLoading;
    }

    private DialogLoading(Activity activity) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
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
