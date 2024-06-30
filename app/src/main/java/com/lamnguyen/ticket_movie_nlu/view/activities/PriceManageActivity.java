package com.lamnguyen.ticket_movie_nlu.view.activities;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.PriceManageAdapter;
import com.lamnguyen.ticket_movie_nlu.api.PriceManageApi;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;

import java.util.List;

public class PriceManageActivity extends AppCompatActivity {
    private RecyclerView rcvPriceManage;
    private PriceManageAdapter priceManageAdapter;
    private Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage);
        rcvPriceManage = findViewById(R.id.recycler_view_display_price_manage);

        priceManageAdapter = new PriceManageAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcvPriceManage.setLayoutManager(gridLayoutManager);
        rcvPriceManage.setAdapter(priceManageAdapter);

        dialogLoading = DialogLoading.newInstance(this);
        fetchPriceData();
    }

    private void fetchPriceData() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        PriceManageApi.getPriceManageList(this, new CallAPI.CallAPIListener<>() {

            @Override
            public void completed(List<PriceManageDTO> priceManageDTOList) {
                dialogLoading.dismiss();
                priceManageAdapter.setData(priceManageDTOList);
            }

            @Override
            public void error(Object error) {
                dialogLoading.dismiss();
            }
        });
    }
}
