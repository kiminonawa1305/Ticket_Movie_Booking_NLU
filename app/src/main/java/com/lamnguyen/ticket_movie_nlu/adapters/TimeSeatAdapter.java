package com.lamnguyen.ticket_movie_nlu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.List;
import java.util.zip.Inflater;

public class TimeSeatAdapter extends RecyclerView.Adapter<TimeSeatAdapter.TimeViewHolder> {
    List<TimeSeat> timeSeats;

    @NonNull
    @Override
    public TimeSeatAdapter.TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_seat, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSeatAdapter.TimeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return timeSeats.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public static class TimeSeat {
        private String start, end;
        private int totalSeat, availableSeat;

        public TimeSeat(String start, String end, int totalSeat, int availableSeat) {
            this.start = start;
            this.end = end;
            this.totalSeat = totalSeat;
            this.availableSeat = availableSeat;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public int getTotalSeat() {
            return totalSeat;
        }

        public void setTotalSeat(int totalSeat) {
            this.totalSeat = totalSeat;
        }

        public int getAvailableSeat() {
            return availableSeat;
        }

        public void setAvailableSeat(int availableSeat) {
            this.availableSeat = availableSeat;
        }
    }
}
