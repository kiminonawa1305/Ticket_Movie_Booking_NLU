package com.lamnguyen.ticket_movie_nlu.adapters;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketItemViewHolder> {
    List<TicketDTO> tickets;

    public TicketAdapter(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(), R.layout.item_ticket, null);
        return new TicketItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketItemViewHolder holder, int position) {
        TicketDTO ticket = tickets.get(position);
        Log.i("TicketAdapter", "onBindViewHolder: " + ticket.toString());
        holder.setMovieName(ticket.getMovieName());
        holder.setCinemaName(ticket.getCinemaName());
        holder.setRoomNumber(ticket.getRoomNumber());
        holder.setChairNumber(ticket.getChairNumber());
        holder.setTime(ticket.getTime().toString());
//        holder.setDate(ticket.getDate().toString());
//        holder.setShibMovieImage(ticket.setMovieImage());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieName, tvCinemaName, tvRoomNumber, tvChairNumber, tvTime, tvDate;
        private ShapeableImageView shibMovieImage;

        public TicketItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.text_view_movie_name);
            tvCinemaName = itemView.findViewById(R.id.text_view_cinema_name);
            tvRoomNumber = itemView.findViewById(R.id.text_view_room_number);
            tvChairNumber = itemView.findViewById(R.id.text_view_chair_number);
            tvTime = itemView.findViewById(R.id.text_view_ticket_time);
            tvDate = itemView.findViewById(R.id.text_view_ticket_date);
//            shibMovieImage = itemView.findViewById(R.id.shapeable_image_view_ticket_image);
        }


        public void setMovieName(String movieName) {
            this.tvMovieName.setText(movieName);
        }

        public void setCinemaName(String cinemaName) {
            this.tvCinemaName.setText(cinemaName);
        }

        public void setRoomNumber(int roomNumber) {
            this.tvRoomNumber.setText("Phòng " + roomNumber);
        }

        public void setChairNumber(String chairNumber) {
            this.tvChairNumber.setText("Ghế " + chairNumber);
        }

        public void setTime(String time) {
            this.tvTime.setText(time);
        }

        public void setDate(String date) {
            this.setDate(date);
        }

        public void setShibMovieImage(String url) {
            Glide.with(shibMovieImage).load(url).into(shibMovieImage);
        }
    }
}
