package com.lamnguyen.ticket_movie_nlu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.api.PriceManageApi;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PriceManageAdapter extends RecyclerView.Adapter<PriceManageAdapter.PriceManageViewHolder> {
    private List<PriceManageDTO> mListPriceManage;
    private static NumberFormat numberFormat;

    static {
        numberFormat = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    }

    public void setData(List<PriceManageDTO> list) {
        this.mListPriceManage = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PriceManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_price_manage, parent, false);
        return new PriceManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceManageViewHolder holder, int position) {
        PriceManageDTO priceManage = mListPriceManage.get(position);
        if (priceManage == null)
            return;

        holder.setPriceManageDTO(priceManage);
        holder.tvCinemaName.setText(priceManage.getCinemaName());
        holder.tvCinemaId.setText(priceManage.getCinemaId() != null ? String.valueOf(priceManage.getCinemaId()) : "");

        if (position % 2 == 0) {
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.bg_cinema_1)); // Màu 1
        } else {
            holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(R.color.bg_cinema_2)); // Màu 2
        }
    }

    @Override
    public int getItemCount() {
        return mListPriceManage != null ? mListPriceManage.size() : 0;
    }

    public static class PriceManageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCinemaName, tvCinemaId;
        private EditText etSingleChairPrice, etCoupleChairPrice, etVIPChairPrice;
        private CardView cardView;
        private ImageView pencilImage;
        private LinearLayout llButtonContainer;
        private Button btnSaveUpdate, btnCancelUpdate;
        private PriceManageDTO priceManageDTO;
        private InputMethodManager imm;

        public PriceManageViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
            event();
            imm = (InputMethodManager) itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        private void init() {
            tvCinemaName = itemView.findViewById(R.id.text_view_name_cinema);
            tvCinemaId = itemView.findViewById(R.id.cinema_id);
            etSingleChairPrice = itemView.findViewById(R.id.edit_text_single_chair_price);
            etCoupleChairPrice = itemView.findViewById(R.id.edit_text_couple_chair_price);
            etVIPChairPrice = itemView.findViewById(R.id.edit_text_VIP_chair_price);
            cardView = itemView.findViewById(R.id.cv_price_manage);
            pencilImage = itemView.findViewById(R.id.pencil_image);
            llButtonContainer = itemView.findViewById(R.id.linear_layout_button_container);
            btnSaveUpdate = itemView.findViewById(R.id.button_save_update_price);
            btnCancelUpdate = itemView.findViewById(R.id.button_cancel_update_price);
        }

        private void event() {
            pencilImage.setOnClickListener(v -> {
                if (llButtonContainer.getVisibility() == View.VISIBLE) return;
                llButtonContainer.setVisibility(View.VISIBLE);
                setFocusableEditView(true);
            });

            btnCancelUpdate.setOnClickListener(v -> {
                llButtonContainer.setVisibility(View.GONE);
                setFocusableEditView(false);
                bindPrice();
            });

            btnSaveUpdate.setOnClickListener(v -> {
                PriceManageDTO newPrice = getPrice();
                updatePrice(newPrice);
            });
        }

        private void setFocusableEditView(boolean focus) {
            etSingleChairPrice.setFocusableInTouchMode(focus);
            etCoupleChairPrice.setFocusableInTouchMode(focus);
            etVIPChairPrice.setFocusableInTouchMode(focus);

            etSingleChairPrice.setFocusable(focus);
            etCoupleChairPrice.setFocusable(focus);
            etVIPChairPrice.setFocusable(focus);
        }

        private void setPriceManageDTO(PriceManageDTO priceManageDTO) {
            this.priceManageDTO = priceManageDTO;
            bindPrice();
        }

        private PriceManageDTO getPrice() {
            return PriceManageDTO.builder()
                    .cinemaId(priceManageDTO.getCinemaId())
                    .cinemaName(priceManageDTO.getCinemaName())
                    .single(Integer.valueOf(etSingleChairPrice.getText().toString().replace(".", "")))
                    .couple(Integer.valueOf(etCoupleChairPrice.getText().toString().replace(".", "")))
                    .vip(Integer.valueOf(etVIPChairPrice.getText().toString().replace(".", "")))
                    .build();
        }

        private void bindPrice() {
            etSingleChairPrice.setText(numberFormat.format(priceManageDTO.getSingle()));
            etCoupleChairPrice.setText(numberFormat.format(priceManageDTO.getCouple()));
            etVIPChairPrice.setText(numberFormat.format(priceManageDTO.getVip()));
        }

        private void updatePrice(PriceManageDTO updatedPrice) {
            PriceManageApi.updatePrice(this.itemView.getContext(), updatedPrice, new PriceManageApi.UpdatePriceListener() {
                @Override
                public void onUpdateSuccess() {
                    setFocusableEditView(false);
                    llButtonContainer.setVisibility(View.GONE);
                    priceManageDTO = updatedPrice;
                    bindPrice();
                    imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
                }

                @Override
                public void onUpdateError(String message) {
                    Toast.makeText(itemView.getContext(), message, Toast.LENGTH_SHORT).show();
                    bindPrice();
                }
            });
        }
    }
}
