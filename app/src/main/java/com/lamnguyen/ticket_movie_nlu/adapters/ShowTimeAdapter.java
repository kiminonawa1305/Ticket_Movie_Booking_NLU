package com.lamnguyen.ticket_movie_nlu.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.ShowtimeByCinema;
import com.lamnguyen.ticket_movie_nlu.dto.TimeSeat;
import com.lamnguyen.ticket_movie_nlu.view.activities.BookingChairActivity;

import java.util.List;

public class ShowTimeAdapter extends RecyclerView.Adapter<ShowTimeAdapter.ShowTimeViewHolder> {
    List<ShowtimeByCinema> showtimeList;
    public static FirebaseStorage firebaseStorage;
    public static int movieId;

    public ShowTimeAdapter(List<ShowtimeByCinema> showtimeList, int movieId) {
        this.showtimeList = showtimeList;
        firebaseStorage = FirebaseStorage.getInstance();
        ShowTimeAdapter.movieId = movieId;
    }

    @NonNull
    @Override
    public ShowTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowTimeViewHolder holder, int position) {
        ShowtimeByCinema showtime = this.showtimeList.get(position);
        holder.setTimeSeat(showtime.getTimeSeats());
        holder.setCinemaName(showtime.getNameCinema());
        holder.setCinemaAddress(showtime.getAddressCinema());
        holder.setCinemaDetailAddress(showtime.getDetailAddressCinema());
        holder.setImageIcon(showtime.getUrlImageCinema());
        if (showtime.getDistance() != null)
            holder.setCinemaDistance(String.valueOf(
                    ((int) ((Integer.valueOf(showtime.getDistance()) / 1000) * 100)) / 100.0 + " km"
            ));
    }

    @Override
    public int getItemCount() {
        return showtimeList.size();
    }

    public static class ShowTimeViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvTimeSeat;
        private ImageView ivCinema;
        private TextView tvCinemaName, tvCinemaAddress, tvCinemaDetailAddress, tvCinemaDistance;

        public ShowTimeViewHolder(@NonNull View itemView) {
            super(itemView);
            rvTimeSeat = itemView.findViewById(R.id.recycler_view_time_seat);
            rvTimeSeat.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            tvCinemaName = itemView.findViewById(R.id.text_view_cinema_name);
            tvCinemaAddress = itemView.findViewById(R.id.text_view_cinema_address);
            tvCinemaDetailAddress = itemView.findViewById(R.id.text_view_cinema_detail_address);
            tvCinemaDistance = itemView.findViewById(R.id.text_view_cinema_distance);
            ivCinema = itemView.findViewById(R.id.image_view_cinema_image);
        }

        public void setTimeSeat(List<TimeSeat> timeSeats) {
            TimeSeatAdapter timeSeatAdapter = new TimeSeatAdapter(timeSeats, movieId);
            rvTimeSeat.setAdapter(timeSeatAdapter);
        }

        public void setCinemaName(String cinemaName) {
            tvCinemaName.setText(cinemaName);
        }

        public void setCinemaAddress(String cinemaAddress) {
            tvCinemaAddress.setText(cinemaAddress);
        }

        public void setCinemaDetailAddress(String cinemaDetailAddress) {
            tvCinemaDetailAddress.setText(cinemaDetailAddress);
        }

        public void setCinemaDistance(String cinemaDistance) {
            tvCinemaDistance.setText(cinemaDistance);
        }

        public void setImageIcon(String url) {
            StorageReference cimeneIcon = firebaseStorage.getReferenceFromUrl(url);
            cimeneIcon.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri uri = task.getResult();
                    Glide.with(itemView.getContext()).load(uri).into(ivCinema);
                }
            });
        }
    }
}
