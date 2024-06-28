package com.lamnguyen.ticket_movie_nlu.view.activities;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceManageActivity extends AppCompatActivity implements PriceManageAdapter.OnEditListener {
    private RecyclerView rcvPriceManage;
    private PriceManageAdapter mpriceManage;
    private Map<Integer, PriceManageDTO> originalPrices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manage);
        rcvPriceManage = findViewById(R.id.recycler_view_display_price_manage);

        mpriceManage = new PriceManageAdapter(this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        rcvPriceManage.setLayoutManager(gridLayoutManager);
        rcvPriceManage.setAdapter(mpriceManage);

        fetchPriceData();
    }

    private void fetchPriceData() {
        PriceManageApi.getPriceManageList(this, new PriceManageApi.PriceManageApiListener() {
            @Override
            public void onSuccess(List<PriceManageDTO> priceManageDTOList) {
                mpriceManage.setData(priceManageDTOList);
            }

            @Override
            public void onError(String message) {
                // Handle error here
            }
        });
    }

    @Override
    public void onEditClicked(int position) {
        View itemView = rcvPriceManage.getLayoutManager().findViewByPosition(position);

        if (itemView != null) {
            LinearLayout buttonContainer = itemView.findViewById(R.id.button_container);
            EditText singleChairPrice = itemView.findViewById(R.id.single_chair_price);
            EditText coupleChairPrice = itemView.findViewById(R.id.couple_chair_price);
            EditText vipChairPrice = itemView.findViewById(R.id.VIP_chair_price);

            originalPrices.put(position, new PriceManageDTO(
                    mpriceManage.getPriceList().get(position).getCinemaName(),
                    Integer.valueOf(singleChairPrice.getText().toString()),
                    Integer.valueOf(coupleChairPrice.getText().toString()),
                    Integer.valueOf(vipChairPrice.getText().toString()),
                    mpriceManage.getPriceList().get(position).getCinema_Id()
            ));

            buttonContainer.setVisibility(View.VISIBLE);

            enableEditTextEditing(singleChairPrice);
            enableEditTextEditing(coupleChairPrice);
            enableEditTextEditing(vipChairPrice);


            buttonContainer.findViewById(R.id.button1).setOnClickListener(v -> {
                rollbackToOriginalValues(position, singleChairPrice, coupleChairPrice, vipChairPrice);
                buttonContainer.setVisibility(View.GONE);
            });


            buttonContainer.findViewById(R.id.button2).setOnClickListener(v -> {
                saveUpdatedValues(position, singleChairPrice, coupleChairPrice, vipChairPrice);
                buttonContainer.setVisibility(View.GONE);
            });
        }
    }

    private void rollbackToOriginalValues(int position, EditText singleChairPrice, EditText coupleChairPrice, EditText vipChairPrice) {
        PriceManageDTO originalPrice = originalPrices.get(position);
        if (originalPrice != null) {
            singleChairPrice.setText(String.valueOf(originalPrice.getSingle()));
            coupleChairPrice.setText(String.valueOf(originalPrice.getCouple()));
            vipChairPrice.setText(String.valueOf(originalPrice.getVip()));
        }

        disableEditTextEditing(singleChairPrice);
        disableEditTextEditing(coupleChairPrice);
        disableEditTextEditing(vipChairPrice);
    }

    private void saveUpdatedValues(int position, EditText singleChairPrice, EditText coupleChairPrice, EditText vipChairPrice) {
        PriceManageDTO updatedPrice = new PriceManageDTO(
                mpriceManage.getPriceList().get(position).getCinemaName(),
                Integer.valueOf(singleChairPrice.getText().toString()),
                Integer.valueOf(coupleChairPrice.getText().toString()),
                Integer.valueOf(vipChairPrice.getText().toString()),
                originalPrices.get(position).getCinema_Id() // Lấy cinema_Id từ originalPrices
        );

        PriceManageApi.updatePrice(this, updatedPrice, new PriceManageApi.UpdatePriceListener() {
            @Override
            public void onUpdateSuccess() {
                mpriceManage.updatePrice(position, updatedPrice);
                originalPrices.remove(position);
                disableEditTextEditing(singleChairPrice);
                disableEditTextEditing(coupleChairPrice);
                disableEditTextEditing(vipChairPrice);
            }

            @Override
            public void onUpdateError(String message) {
                // Handle error here
            }
        });
        disableEditTextEditing(singleChairPrice);
        disableEditTextEditing(coupleChairPrice);
        disableEditTextEditing(vipChairPrice);
    }

    private void enableEditTextEditing(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
    }

    private void disableEditTextEditing(EditText editText) {
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
    }
}
