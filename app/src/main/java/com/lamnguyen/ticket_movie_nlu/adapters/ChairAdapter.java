package com.lamnguyen.ticket_movie_nlu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.api.ChairApi;
import com.lamnguyen.ticket_movie_nlu.dto.ChairDTO;
import com.lamnguyen.ticket_movie_nlu.enums.ChairStatus;
import com.lamnguyen.ticket_movie_nlu.service.chair.ChairService;
import com.lamnguyen.ticket_movie_nlu.utils.CallAPI;

import java.util.ArrayList;
import java.util.List;

public class ChairAdapter extends RecyclerView.Adapter<ChairAdapter.ChairHolder> {
    private List<ChairDTO> dtos;
    private ChairService chairService;
    private Context context;
    private String uuid;
    private int userId;

    public ChairAdapter(Context context, List<ChairDTO> dtos, String uuid) {
        this.dtos = dtos;
        chairService = ChairService.getInstance();
        this.context = context;
        this.uuid = uuid;
        userId = context.getSharedPreferences("sign", Context.MODE_PRIVATE).getInt("userId", 0);
    }

    @NonNull
    @Override
    public ChairHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, parent, false);
        return new ChairHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChairHolder holder, int position) {
        ChairDTO chairDTO = dtos.get(position);
        ((TextView) holder.itemView).setText(chairDTO.getName());
        holder.setStatus(chairDTO.getStatus().toString());

        ChairStatus status = chairDTO.getStatus();
        if (chairDTO.getUserId() == null ||
                (!chairDTO.getStatus().equals(ChairStatus.SOLD) && (chairDTO.getUserId().equals(userId) || chairDTO.getStatus().equals(ChairStatus.AVAILABLE)))
        ) {
            ChairStatus statusOnClick = status.equals(ChairStatus.SELECTED) ? ChairStatus.AVAILABLE : ChairStatus.SELECTED;
            holder.itemView.setOnClickListener(v -> {
                chairService.updateChair(uuid, chairDTO.getId(), statusOnClick, context, new CallAPI.CallAPIListener<ChairDTO>() {
                    @Override
                    public void completed(ChairDTO chairDTO) {

                    }

                    @Override
                    public void error(Object error) {

                    }
                });
            });
        }

    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    public static class ChairHolder extends RecyclerView.ViewHolder {

        public ChairHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setStatus(String status) {
            switch (status) {
                case "AVAILABLE" -> {
                    this.itemView.setBackgroundResource(R.drawable.bg_chair_available);
                }
                case "SELECTED" -> {
                    this.itemView.setBackgroundResource(R.drawable.bg_chair_select);
                }
                case "SOLD" -> {
                    this.itemView.setBackgroundResource(R.drawable.bg_chair_sold);
                }
                case "OTHER_SELECTED" -> {
                    this.itemView.setBackgroundResource(R.drawable.bg_chair_other_select);
                }
            }
        }
    }
}
