package com.lamnguyen.ticket_movie_nlu.service.user;

import android.content.Context;
import com.lamnguyen.ticket_movie_nlu.api.AccountApi;
import com.lamnguyen.ticket_movie_nlu.dto.AccountDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.List;

public class AccountService {
    private AccountApi accountApi;
    private static AccountService instance;

    public static AccountService getInstance() {
        if (instance == null) instance = new AccountService();
        return instance;
    }

    private AccountService() {
        accountApi = AccountApi.getInstance();
    }

    public void loadAccounts(Context context, CallAPI.CallAPIListener<List<AccountDTO>> listener) {
        accountApi.loadUsers(context, listener);
    }
}
