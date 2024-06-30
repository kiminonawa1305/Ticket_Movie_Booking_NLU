package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.User;
import com.lamnguyen.ticket_movie_nlu.service.user.UserService;
import com.lamnguyen.ticket_movie_nlu.service.user.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private List<User> users;
    private TableLayout tableLayout;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        userService = UserServiceImpl.getInstance();

        tableLayout = findViewById(R.id.table_account);

        // Initialize accounts list
        users = new ArrayList<>();

        // Load accounts from API
        loadAccountsFromAPI();

        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        btnCreateAccount.setOnClickListener(v -> {
            // Handle create account button click
        });

        btnConfirm.setOnClickListener(v -> {
            confirmAccountLock();
        });
    }

    private void loadAccountsFromAPI() {
        userService.loadUsers(this, new CallAPI.CallAPIListener<List<User>>() {
            @Override
            public void completed(List<User> users) {
                UserManagementActivity.this.users.addAll(users);
                populateTable();
            }

            @Override
            public void error(Object error) {

            }
        });
    }

    private void populateTable() {
        tableLayout.removeAllViews();

        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("STT", true));
        headerRow.addView(createTextView("Tên người dùng", true));
        headerRow.addView(createTextView("Số điện thoại", true));
        headerRow.addView(createTextView("Email", true));
        headerRow.addView(createTextView("Khóa", true));
        tableLayout.addView(headerRow);

        for (User user : users) {
            TableRow row = new TableRow(this);
            row.addView(createTextView(String.valueOf(users.indexOf(user)), false));
            row.addView(createTextView(user.getFullName(), false));
            row.addView(createTextView(user.getPhone(), false));
            row.addView(createTextView(user.getEmail(), false));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setChecked(user.isLock());
            checkBox.setTag(user);
            row.addView(checkBox);

            tableLayout.addView(row);
        }
    }

    private TextView createTextView(String text, boolean isHeader) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(18, 8, 8, 18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        if (isHeader) {
            textView.setBackgroundColor(0xFFCCCCCC);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        return textView;
    }

    private void confirmAccountLock() {
        int childCount = tableLayout.getChildCount();

        // Skip the header row (index 0)
        for (int i = 1; i < childCount; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox) row.getChildAt(6);
            User account = (User) checkBox.getTag();
            account.setLock(checkBox.isChecked());
        }
    }
}
