package com.lamnguyen.ticket_movie_nlu.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.view.activities.MovieDetailActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MovieDTO> movies;
    private Activity activity;

    public MovieAdapter(@NonNull List<MovieDTO> movies, Activity activity) {
        this.movies = movies;
        this.activity = activity;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
        notifyDataSetChanged(); // Cập nhật lại RecyclerView khi dữ liệu thay đổi
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(), R.layout.item_movie, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieDTO movie = movies.get(position);
        holder.setImage(movie.getPoster());
        holder.setGenre(movie.getGenre());
        holder.setRating(movie.getRate());
        holder.setVote(movie.getVote());
        holder.setTitle(movie.getTitle());
        holder.setTime(movie.getDuration());

        holder.onClick(view -> {
            Intent intent = new Intent(this.activity, MovieDetailActivity.class);
            intent.putExtra("id", movie.getId());
            this.activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgvMovieImage;
        private RatingBar rtbMovieRate;
        private TextView tvMovieTitle, tvMovieTime, tvMovieVote, tvMovieType;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvMovieImage = itemView.findViewById(R.id.image_view_movie_image);
            tvMovieType = itemView.findViewById(R.id.text_view_movie_type);
            tvMovieVote = itemView.findViewById(R.id.text_view_movie_vote);
            tvMovieTitle = itemView.findViewById(R.id.text_view_movie_title);
            tvMovieTime = itemView.findViewById(R.id.text_view_movie_time);
            rtbMovieRate = itemView.findViewById(R.id.rating_bar_movie_rate);
        }

        public void setImage(String url) {
            Glide.with(imgvMovieImage).load(url).into(imgvMovieImage);
        }

        public void setGenre(String type) {
            tvMovieType.setText(type);
        }

        public void setRating(double rating) {
            rtbMovieRate.setRating((float) rating);
        }

        public void setVote(Integer vote) {
            tvMovieVote.setText(vote + " Reviews");
        }

        public void setTitle(String title) {
            tvMovieTitle.setText(title);
        }

        public void setTime(String time) {
            tvMovieTime.setText(time);
        }

        public void setTag(String tag) {
            this.itemView.setTag(tag);
        }

        public void onClick(View.OnClickListener listener) {
            this.itemView.setOnClickListener(listener);
        }

    }
}
