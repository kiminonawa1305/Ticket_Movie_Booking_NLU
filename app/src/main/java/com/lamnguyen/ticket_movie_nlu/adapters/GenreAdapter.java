package com.lamnguyen.ticket_movie_nlu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamnguyen.ticket_movie_nlu.R;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    List<String> listGenre;

    public void setData(List<String> listGenre){
        this.listGenre = listGenre;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_genre, parent, false);
        GenreViewHolder viewHolder = new GenreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        String genre = listGenre.get(position);
        if(genre == null || genre.isEmpty()){
            return;
        }
        holder.genreMovieTv.setText(genre);
    }

    @Override
    public int getItemCount() {
        if(listGenre != null){
            return listGenre.size();
        }
        return 0;
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder{

        private TextView genreMovieTv;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreMovieTv = itemView.findViewById(R.id.genre_movie);
        }
    }
}
