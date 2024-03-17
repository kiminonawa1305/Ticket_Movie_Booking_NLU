package com.lamnguyen.ticket_movie_nlu.model.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.HashMap;
import java.util.Map;

public class DialogLoading {
    private Dialog dialog;
    private Context context;

    private final static Map<String, DialogLoading> mapDialogLoading = new HashMap<>();

    public static DialogLoading getInstance(Context context) {
        DialogLoading dialogLoading = mapDialogLoading.get(context.getClass().getName());
        if (dialogLoading == null) {
            dialogLoading = new DialogLoading(context);
            mapDialogLoading.put(context.getClass().getName(), dialogLoading);
        }

        return dialogLoading;
    }

    private DialogLoading(Context context) {
        this.context = context;
        initDialog();
    }

    private Dialog initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        return dialog;
    }

    public void showDialogLoading(String title) {
        TextView tvTitleDialogLoading = dialog.findViewById(R.id.text_view_title_progress_loading);
        tvTitleDialogLoading.setText(title);
        dialog.show();
    }

    public void dismissDialogLoading() {
        dialog.cancel();
    }

    public void destroy(Context context) {
        mapDialogLoading.remove(context.getClass().getName());
    }
}
