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
import com.lamnguyen.ticket_movie_nlu.dto.AccountDTO;
import com.lamnguyen.ticket_movie_nlu.service.user.AccountService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.ArrayList;
import java.util.List;

public class AccountManagementActivity extends AppCompatActivity {

    private List<Account> accounts;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        tableLayout = findViewById(R.id.table_account);

        // Initialize accounts list
        accounts = new ArrayList<>();

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
        AccountService.getInstance().loadAccounts(this, new CallAPI.CallAPIListener<List<AccountDTO>>() {
            @Override
            public void completed(List<AccountDTO> data) {
                // Convert AccountDTO to Account
                for (AccountDTO accountDTO : data) {
                    accounts.add(new Account(
                            accountDTO.getStt(),
                            accountDTO.getName(),
                            accountDTO.getPhone(),
                            accountDTO.getEmail(),
                            accountDTO.getPassword(),
                            accountDTO.isLocked()
                    ));
                }
                // Populate the table with the retrieved accounts
                populateTable();
            }

            @Override
            public void error(Object error) {

            }
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
