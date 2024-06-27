package com.lamnguyen.ticket_movie_nlu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.PriceManageDTO;

import java.util.List;

public class PriceManageAdapter extends RecyclerView.Adapter<PriceManageAdapter.PriceManageViewHolder> {

    private Context mContext;
    private List<PriceManageDTO> mListPriceManage;
    private OnEditListener mOnEditListener;

    public interface OnEditListener {
        void onEditClicked(int position);
    }

    public PriceManageAdapter(Context mContext, OnEditListener onEditListener) {
        this.mContext = mContext;
        this.mOnEditListener = onEditListener;
    }

    public void setData(List<PriceManageDTO> list) {
        this.mListPriceManage = list;
        notifyDataSetChanged();
    }

    public List<PriceManageDTO> getPriceList() {
        return mListPriceManage;
    }

    public void updatePrice(int position, PriceManageDTO updatedPrice) {
        if (mListPriceManage != null && position >= 0 && position < mListPriceManage.size()) {
            mListPriceManage.set(position, updatedPrice);
            notifyItemChanged(position);
        }
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
        if (priceManage == null) {
            return;
        }
        holder.tvCinemaName.setText(priceManage.getCinemaName());
        holder.tvCinemaId.setText(priceManage.getCinema_Id() != null ? String.valueOf(priceManage.getCinema_Id()) : "");
        holder.singleChairPPrice.setText(priceManage.getSingle() != null ? String.valueOf(priceManage.getSingle()) : "");
        holder.coupleChairPrice.setText(priceManage.getCouple() != null ? String.valueOf(priceManage.getCouple()) : "");
        holder.VIPChairPrice.setText(priceManage.getVip() != null ? String.valueOf(priceManage.getVip()) : "");


        holder.pencilImage.setOnClickListener(v -> mOnEditListener.onEditClicked(position));

        if (position % 2 == 0) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.bg_cinema_1)); // Màu 1
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.bg_cinema_2)); // Màu 2
        }
    }

    @Override
    public int getItemCount() {
        if (mListPriceManage != null) {
            return mListPriceManage.size();
        }
        return 0;
    }

    public class PriceManageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCinemaName, tvCinemaId;
        private EditText singleChairPPrice;
        private EditText coupleChairPrice;
        private EditText VIPChairPrice;
        private CardView cardView;
        private ImageView pencilImage;

        public PriceManageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCinemaName = itemView.findViewById(R.id.id_name_cinema);
            tvCinemaId = itemView.findViewById(R.id.cinema_id);
            singleChairPPrice = itemView.findViewById(R.id.single_chair_price);
            coupleChairPrice = itemView.findViewById(R.id.couple_chair_price);
            VIPChairPrice = itemView.findViewById(R.id.VIP_chair_price);
            cardView = itemView.findViewById(R.id.cv_price_manage);
            pencilImage = itemView.findViewById(R.id.pencil_image);
        }
    }
}
