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


    public PriceManageAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setData(List<PriceManageDTO> list) {
        this.mListPriceManage = list;
        notifyDataSetChanged();
    }

    public List<PriceManageDTO> getPriceList() {
        return mListPriceManage;
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
        holder.singleChairPPrice.setText(priceManage.getSingle());
        holder.coupleChairPrice.setText(priceManage.getCouple());
        holder.VIPChairPrice.setText(priceManage.getVip());


    }

    @Override
    public int getItemCount() {
        if (mListPriceManage != null) {
            return mListPriceManage.size();
        }
        return 0;
    }

    public class PriceManageViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCinemaName;
        private EditText singleChairPPrice;
        private EditText coupleChairPrice;
        private EditText VIPChairPrice;

        public PriceManageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCinemaName = itemView.findViewById(R.id.id_name_cinema);
            singleChairPPrice = itemView.findViewById(R.id.single_chair_price);
            coupleChairPrice = itemView.findViewById(R.id.couple_chair_price);
            VIPChairPrice = itemView.findViewById(R.id.VIP_chair_price);

        }
    }
}
