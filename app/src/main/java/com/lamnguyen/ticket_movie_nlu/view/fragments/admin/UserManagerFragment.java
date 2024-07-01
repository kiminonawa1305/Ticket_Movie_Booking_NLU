package com.lamnguyen.ticket_movie_nlu.view.fragments.admin;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.service.user.UserService;
import com.lamnguyen.ticket_movie_nlu.service.user.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserManagerFragment extends Fragment {
    private List<User> users;
    private TableLayout tbUserManager;
    private UserService userService;
    private Dialog dialogLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_manager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.init(view);
    }

    private void init(View view) {
        userService = UserServiceImpl.getInstance();
        dialogLoading = DialogLoading.newInstance(getContext());

        tbUserManager = view.findViewById(R.id.table_layout_user_manager);

        // Initialize accounts list
        users = new ArrayList<>();

        // Load accounts from API
        loadAccountsFromAPI();
    }


    private void loadAccountsFromAPI() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        userService.loadUsers(getContext(), new CallAPI.CallAPIListener<List<User>>() {
            @Override
            public void completed(List<User> users) {
                dialogLoading.dismiss();
                UserManagerFragment.this.users.addAll(users);
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
        tbUserManager.removeAllViews();

        TableRow headerRow = new TableRow(getContext());
        headerRow.addView(createTextView(getString(R.string.id), true));
        headerRow.addView(createTextView(getString(R.string.full_name), true));
        headerRow.addView(createTextView(getString(R.string.user_email), true));
        headerRow.addView(createTextView(getString(R.string.user_phone_number), true));
        headerRow.addView(createTextView(getString(R.string.user_role), true));
        headerRow.addView(createTextView(getString(R.string.user_lock), true));
        headerRow.setBackgroundResource(R.color.gray);
        tbUserManager.addView(headerRow);

        for (User user : users) {
            int index = users.indexOf(user);
            TableRow row = new TableRow(getContext());
            if (index % 2 != 0)
                row.setBackgroundResource(R.color.bg_input_info_sign);

            row.addView(createTextView(String.valueOf(user.getId()), false));
            row.addView(createTextView(user.getFullName(), false));
            row.addView(createTextView(user.getEmail(), false));
            row.addView(createTextView(user.getPhone(), false));
            row.addView(createTextView(user.getRole().toString(), false));
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setChecked(user.isLock());
            checkBox.setTag(user);
            row.addView(checkBox);

            checkBox.setOnClickListener(v -> {
                DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
                CheckBox cb = (CheckBox) v;
                User u = (User) cb.getTag();
                u.setLock(cb.isChecked());
                userService.lock(getContext(), u.getId(), new CallAPI.CallAPIListener<User>() {
                    @Override
                    public void completed(User user) {
                        dialogLoading.dismiss();
                        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error(Object error) {
                        dialogLoading.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError)
                            Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                    }
                });
            });

            tbUserManager.addView(row);
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