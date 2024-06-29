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
import com.lamnguyen.ticket_movie_nlu.bean.Account;

import java.util.ArrayList;
import java.util.List;

public class UserManagementActivity extends AppCompatActivity {

    private List<Account> accounts;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        tableLayout = findViewById(R.id.table_account);


        accounts = new ArrayList<>();
        accounts.add(new Account(1, "Nguyễn Văn A", "0333840481", "anhduong@gmail.com", "abc123", false));
        accounts.add(new Account(2, "Nguyễn Văn B", "0333840481", "anhduong@gmail.com", "xyz789", true));


        populateTable();

        Button btnCreateAccount = findViewById(R.id.btnCreateAccount);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        btnCreateAccount.setOnClickListener(v -> {
            // Handle create account button click
        });

        btnConfirm.setOnClickListener(v -> {
            confirmAccountLock();
        });
    }

    private void populateTable() {
        tableLayout.removeAllViews();

        // Add header row
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("STT", true));
        headerRow.addView(createTextView("Tên người dùng", true));
        headerRow.addView(createTextView("Số điện thoại", true));
        headerRow.addView(createTextView("Email", true));
        headerRow.addView(createTextView("Mật khẩu", true));
        headerRow.addView(createTextView("Khóa", true));
        tableLayout.addView(headerRow);

        // Add account rows
        for (Account account : accounts) {
            TableRow row = new TableRow(this);
            row.addView(createTextView(String.valueOf(account.getStt()), false));
            row.addView(createTextView(account.getName(), false));
            row.addView(createTextView(account.getPhone(), false));
            row.addView(createTextView(account.getEmail(), false));
            row.addView(createTextView(hashPassword(account.getPassword()), false));
            CheckBox checkBox = new CheckBox(this);
            checkBox.setChecked(account.isLocked());
            checkBox.setTag(account);
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
            textView.setBackgroundColor(0xFFCCCCCC); // Light gray background for headers
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        return textView;
    }

    private String hashPassword(String password) {
        // For simplicity, just masking the password with asterisks
        return password.substring(0, 3) + "*****";
    }

    private void confirmAccountLock() {
        int childCount = tableLayout.getChildCount();

        // Skip the header row (index 0)
        for (int i = 1; i < childCount; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox) row.getChildAt(6);
            Account account = (Account) checkBox.getTag();

            if (checkBox.isChecked()) {
                account.setLocked(true);
            } else {
                account.setLocked(false);
            }
        }

    }
}
