package com.lamnguyen.ticket_movie_nlu.adapters;

import android.annotation.SuppressLint;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketItemViewHolder> {
    List<TicketDTO> tickets;
    static boolean avail;
    public TicketAdapter(List<TicketDTO> tickets, boolean avail) {
        this.tickets = tickets;
        TicketAdapter.avail = avail;
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
        LocalDateTime startShowtime = LocalDateTime.parse(ticket.getStartShowtime());
        Log.i("TicketAdapter", "onBindViewHolder: " + ticket.toString());
        holder.setMovieName(ticket.getNameMovie());
        holder.setCinemaName(ticket.getNameCinema());
        holder.setRoomName(ticket.getNameRoom());
        holder.setNameChair(ticket.getNameChair());
        holder.setTime(startShowtime.toLocalTime().toString());
        holder.setDate(startShowtime.toLocalDate().toString());
        holder.setShibMovieImage(ticket.getPoster());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class TicketItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieName, tvCinemaName, tvRoomNumber, tvChairNumber, tvTime, tvDate;
        private ShapeableImageView shibMovieImage;
        private FrameLayout leftTicketFrameLayout;
        private LinearLayout rightTicketLinearLayout;
        private RelativeLayout cardTicketRelativeLayout;

        @SuppressLint("ResourceAsColor")
        public TicketItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.text_view_movie_name);
            tvCinemaName = itemView.findViewById(R.id.text_view_cinema_name);
            tvRoomNumber = itemView.findViewById(R.id.text_view_room_name);
            tvChairNumber = itemView.findViewById(R.id.text_view_chair_number);
            tvTime = itemView.findViewById(R.id.text_view_ticket_time);
            tvDate = itemView.findViewById(R.id.text_view_ticket_date);
            shibMovieImage = itemView.findViewById(R.id.shapeable_image_view_movie_ticket_item);
            leftTicketFrameLayout = itemView.findViewById(R.id.frame_layout_left_ticket);
            rightTicketLinearLayout = itemView.findViewById(R.id.linear_layout_right_ticket);
            cardTicketRelativeLayout = itemView.findViewById(R.id.relative_layout_card_ticket);

            if(!avail){
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
                leftTicketFrameLayout.getBackground().setColorFilter(filter);
                rightTicketLinearLayout.getBackground().setColorFilter(filter);
                shibMovieImage.setColorFilter(filter);
                cardTicketRelativeLayout.setEnabled(false);
                cardTicketRelativeLayout.setClickable(false);
                cardTicketRelativeLayout.setFocusable(false);
            }
        }

        public void setMovieName(String movieName) {
            this.tvMovieName.setText(movieName);
        }

        public void setCinemaName(String cinemaName) {
            this.tvCinemaName.setText(cinemaName);
        }

        public void setRoomName(String roomNumber) {
            this.tvRoomNumber.setText(roomNumber);
        }

        public void setNameChair(String nameChair) {
            this.tvChairNumber.setText("Ghế " + nameChair);
        }

        public void setTime(String time) {
            this.tvTime.setText(time);
        }

        public void setDate(String date) {
            tvDate.setText(date);
        }

        public void setShibMovieImage(String url) {
            Glide.with(shibMovieImage).load(url).into(shibMovieImage);
        }
    }
}
