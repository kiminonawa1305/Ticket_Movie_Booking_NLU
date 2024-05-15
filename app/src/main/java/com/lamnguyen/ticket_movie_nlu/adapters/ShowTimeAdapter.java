package com.lamnguyen.ticket_movie_nlu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.List;

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ShowTimeViewHolder> {
    List<TimeSeatAdapter.TimeSeat> showtimeList;

    @NonNull
    @Override
    public ShowTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return showtimeList.size();
    }

    public static class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvTime;

        public ShowTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            rvTime = itemView.findViewById(R.id.recycler_view_time_seat);
        }
    }

    public static class ShowTime {
        private String urlImageCinema;
        private String nameCinema;
        private String addressCinema;
        private String detailAddressCinema;
        private String distance;
        private List<TimeSeatAdapter.TimeSeat> timeSeatList;
    }
}
