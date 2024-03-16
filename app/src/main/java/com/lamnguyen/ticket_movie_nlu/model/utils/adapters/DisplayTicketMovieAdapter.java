package com.lamnguyen.ticket_movie_nlu.model.utils.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lamnguyen.ticket_movie_nlu.model.bean.Movie;
import com.lamnguyen.ticket_movie_nlu.R;

import java.util.List;

public class DisplayTicketMovieAdapter extends RecyclerView.Adapter<DisplayTicketMovieAdapter.MovieTicketViewHolder> {
    private List<Movie> movies;

    public DisplayTicketMovieAdapter(@NonNull List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = View.inflate(parent.getContext(), R.layout.item_ticket_movie, null);
        return new MovieTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTicketViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.setImage(movie.getPoster());
        holder.setGenre(movie.getGenre());
        holder.setRating(movie.getImdbRating());
        holder.setVote(String.valueOf(movie.getImdbVotes()));
        holder.setTitle(movie.getTitle());
        holder.setTime(movie.getRunTime());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieTicketViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgvMovieImage;
        private RatingBar rtbMovieRate;
        private TextView tvMovieTitle, tvMovieTime, tvMovieVote, tvMovieType;

        public MovieTicketViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvMovieImage = itemView.findViewById(R.id.image_view_movie_image);
            tvMovieType = itemView.findViewById(R.id.text_view_movie_type);
            tvMovieVote = itemView.findViewById(R.id.text_view_movie_vote);
            tvMovieTitle = itemView.findViewById(R.id.text_view_movie_Title);
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
            rtbMovieRate.setRating((float) rating/2);
        }

        public void setVote(String vote) {
            tvMovieVote.setText(vote + " Reviews");
        }

        public void setTitle(String title) {
            tvMovieTitle.setText(title);
        }

        public void setTime(String time) {
            tvMovieTime.setText(time);
        }
    }
}
