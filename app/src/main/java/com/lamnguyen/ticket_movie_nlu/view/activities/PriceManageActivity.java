package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.PriceManageAdapter;
import com.lamnguyen.ticket_movie_nlu.api.PriceManageApi;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceManageActivity extends AppCompatActivity {
    private RecyclerView rcvPriceManage;
    private PriceManageAdapter mpriceManage;
    private Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage);
        rcvPriceManage = findViewById(R.id.recycler_view_display_price_manage);

        mpriceManage = new PriceManageAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcvPriceManage.setLayoutManager(gridLayoutManager);
        rcvPriceManage.setAdapter(mpriceManage);

        dialogLoading = DialogLoading.newInstance(this);
        fetchPriceData();
    }

    private void fetchPriceData() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        PriceManageApi.getPriceManageList(this, new PriceManageApi.PriceManageApiListener() {
            @Override
            public void onSuccess(List<PriceManageDTO> priceManageDTOList) {
                dialogLoading.dismiss();
                mpriceManage.setData(priceManageDTOList);
            }

            @Override
            public void onError(String message) {
                dialogLoading.dismiss();
            }
        });
    }
}
