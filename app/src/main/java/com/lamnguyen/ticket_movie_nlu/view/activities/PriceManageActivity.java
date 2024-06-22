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
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;

import java.util.ArrayList;
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

        GridLayoutManager gridlayoutmanager = new GridLayoutManager(this, 1);
        rcvPriceManage.setLayoutManager(gridlayoutmanager);

        mpriceManage.setData(getListData());
        rcvPriceManage.setAdapter(mpriceManage);
    }

    private List<PriceManageDTO> getListData() {
        List<PriceManageDTO> list = new ArrayList<>();
        list.add(new PriceManageDTO("Cinema 1", "40000", "90000", "60000"));
        list.add(new PriceManageDTO("Cinema 2", "40000", "90000", "60000"));
        list.add(new PriceManageDTO("Cinema 3", "40000", "90000", "60000"));
        list.add(new PriceManageDTO("Cinema 4", "40000", "90000", "60000"));

        return list;
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
                    singleChairPrice.getText().toString(),
                    coupleChairPrice.getText().toString(),
                    vipChairPrice.getText().toString()
            ));

            buttonContainer.setVisibility(View.VISIBLE);
            singleChairPrice.setFocusableInTouchMode(true);
            singleChairPrice.setFocusable(true);
            coupleChairPrice.setFocusableInTouchMode(true);
            coupleChairPrice.setFocusable(true);
            vipChairPrice.setFocusableInTouchMode(true);
            vipChairPrice.setFocusable(true);

            buttonContainer.findViewById(R.id.button1).setOnClickListener(v -> {

                PriceManageDTO originalPrice = originalPrices.get(position);
                if (originalPrice != null) {
                    singleChairPrice.setText(originalPrice.getSingle());
                    coupleChairPrice.setText(originalPrice.getCouple());
                    vipChairPrice.setText(originalPrice.getVip());
                }

                buttonContainer.setVisibility(View.GONE);
                singleChairPrice.setFocusableInTouchMode(false);
                singleChairPrice.setFocusable(false);
                coupleChairPrice.setFocusableInTouchMode(false);
                coupleChairPrice.setFocusable(false);
                vipChairPrice.setFocusableInTouchMode(false);
                vipChairPrice.setFocusable(false);
            });

            buttonContainer.findViewById(R.id.button2).setOnClickListener(v -> {
                buttonContainer.setVisibility(View.GONE);
                singleChairPrice.setFocusableInTouchMode(false);
                singleChairPrice.setFocusable(false);
                coupleChairPrice.setFocusableInTouchMode(false);
                coupleChairPrice.setFocusable(false);
                vipChairPrice.setFocusableInTouchMode(false);
                vipChairPrice.setFocusable(false);

                PriceManageDTO updatedPriceManageDTO = new PriceManageDTO(
                        mpriceManage.getPriceList().get(position).getCinemaName(),
                        singleChairPrice.getText().toString(),
                        coupleChairPrice.getText().toString(),
                        vipChairPrice.getText().toString()
                );

                mpriceManage.updatePrice(position, updatedPriceManageDTO);
                originalPrices.remove(position);
            });
        }
    }
}
