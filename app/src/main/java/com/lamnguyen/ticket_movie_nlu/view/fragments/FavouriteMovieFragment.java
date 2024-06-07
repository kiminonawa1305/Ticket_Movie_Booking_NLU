package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.adapters.MovieAdapter;
import com.lamnguyen.ticket_movie_nlu.dto.MovieDTO;
import com.lamnguyen.ticket_movie_nlu.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteMovieFragment extends Fragment {
    private RecyclerView rvDisplayFavoriteMovie;
    private Map<Integer, UserDTO> users;
    private UserDTO currentUser;
    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDisplayFavoriteMovie = view.findViewById(R.id.recycler_view_display_favorite_movie);

        // Tạo dữ liệu mẫu cho nhiều account và phim yêu thích của account ddos
        users = createSampleUsersData();

        // Đổi account
        currentUser = users.get(4);

        movieAdapter = new MovieAdapter(currentUser.getFavoriteMovies(), this.getActivity());
        rvDisplayFavoriteMovie.setAdapter(movieAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 2);
        rvDisplayFavoriteMovie.setLayoutManager(layoutManager);
    }

    private Map<Integer, UserDTO> createSampleUsersData() {
        Map<Integer, UserDTO> userMap = new HashMap<>();

        // data danh sach phim mãu cho account 1
        List<MovieDTO> favoriteMoviesUser1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MovieDTO movie = new MovieDTO();
            movie.setTitle("KXG" + (i + 1));
            movie.setPoster("https://upload.wikimedia.org/wikipedia/en/b/be/Godzilla_x_kong_the_new_empire_poster.jpg");
            movie.setGenre("Genre " + (i + 1));
            movie.setDuration("120 mins");
            movie.setRate(4.5);
            movie.setVote(1000);
            favoriteMoviesUser1.add(movie);
        }
        userMap.put(1, new UserDTO(1, "User 1", favoriteMoviesUser1));

        // data danh sach phim mãu cho account 2
        List<MovieDTO> favoriteMoviesUser2 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MovieDTO movie = new MovieDTO();
            movie.setTitle("The dark knight" + (i + 1));
            movie.setPoster("https://m.media-amazon.com/images/I/91KkWf50SoL._AC_UF1000,1000_QL80_.jpg");
            movie.setGenre("Genre " + (i + 1));
            movie.setDuration("100 mins");
            movie.setRate(5.0);
            movie.setVote(750);
            favoriteMoviesUser2.add(movie);
        }
        userMap.put(2, new UserDTO(2, "User 2", favoriteMoviesUser2));

        // data danh sach phim mãu cho account 3
        List<MovieDTO> favoriteMoviesUser3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MovieDTO movie = new MovieDTO();
            movie.setTitle("Avata" + (i + 1));
            movie.setPoster("https://m.media-amazon.com/images/I/8124Pstj51L._AC_SY679_.jpg");
            movie.setGenre("Genre " + (i + 1));
            movie.setDuration("130 mins");
            movie.setRate(4.0);
            movie.setVote(800);
            favoriteMoviesUser3.add(movie);
        }
        userMap.put(3, new UserDTO(3, "User 3", favoriteMoviesUser3));

        // data mãu cho account 4
        List<MovieDTO> favoriteMoviesUser4 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MovieDTO movie = new MovieDTO();
            movie.setTitle("Doraemon " + (i + 1));
            movie.setPoster("https://iguov8nhvyobj.vcdn.cloud/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/d/r/drm24_-_poster.jpg");
            movie.setGenre("Genre " + (i + 1));
            movie.setDuration("130 mins");
            movie.setRate(4.0);
            movie.setVote(800);
            favoriteMoviesUser4.add(movie);
        }
        userMap.put(4, new UserDTO(4, "User 4", favoriteMoviesUser4));

        // data mãu cho account 5
        List<MovieDTO> favoriteMoviesUser5 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MovieDTO movie = new MovieDTO();
            movie.setTitle("Join wick " + (i + 1));
            movie.setPoster("https://m.media-amazon.com/images/S/pv-target-images/6c2c7ace999b2efa7d6d113f7f3ec49f83722dbd2a22b202ef8028f26a1d0b69.jpg");
            movie.setGenre("Genre " + (i + 1));
            movie.setDuration("130 mins");
            movie.setRate(4.0);
            movie.setVote(800);
            favoriteMoviesUser5.add(movie);
        }
        userMap.put(5, new UserDTO(5, "User 5", favoriteMoviesUser5));
        return userMap;
    }
}