package com.lamnguyen.ticket_movie_nlu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.TimeSeat;

import java.util.List;
import java.util.zip.Inflater;

public class TimeSeatAdapter extends RecyclerView.Adapter<TimeSeatAdapter.TimeViewHolder> {
    List<TimeSeat> timeSeats;

    public TimeSeatAdapter(List<TimeSeat> timeSeats) {
        this.timeSeats = timeSeats;
    }

    @NonNull
    @Override
    public TimeSeatAdapter.TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_seat, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSeatAdapter.TimeViewHolder holder, int position) {
        TimeSeat timeSeat = timeSeats.get(position);
        holder.setTimeStart(timeSeat.getStart());
        holder.setTimeEnd(timeSeat.getEnd());
        holder.setTotalSeat(timeSeat.getTotalSeat());
        holder.setAvailableSeat(timeSeat.getAvailableSeat());
    }

    @Override
    public int getItemCount() {
        return timeSeats.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTimeStart, tvTimeEnd, tvTotalSeat, tvAvailableSeat;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeStart = itemView.findViewById(R.id.text_view_time_start);
            tvTimeEnd = itemView.findViewById(R.id.text_view_time_end);
            tvTotalSeat = itemView.findViewById(R.id.text_view_total_seat);
            tvAvailableSeat = itemView.findViewById(R.id.text_view_available_seat);
        }

        public void setTimeStart(String timeStart) {
            tvTimeStart.setText(timeStart);
        }

        public void setTimeEnd(String timeEnd) {
            tvTimeEnd.setText(timeEnd);
        }

        public void setTotalSeat(int totalSeat) {
            tvTotalSeat.setText(String.valueOf(totalSeat));
        }

        public void setAvailableSeat(int availableSeat) {
            tvAvailableSeat.setText(String.valueOf(availableSeat));
        }
    }
}
