package com.arctouch.codechallenge.home.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMoviesAdapter extends RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Movie movie, View view);
    }

    private List<Movie> items;
    private List<Movie> itemsCopy;
    private Context mContext;
    private OnBottomReachedListener onBottomReachedListener;
    private final OnItemClickListener listener;

    public interface OnBottomReachedListener {
        void onBottomReached(int position);
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public UpcomingMoviesAdapter(List<Movie> movies, Context c, OnItemClickListener listener) {
        this.items = movies;
        this.mContext = c;
        this.listener = listener;
        this.itemsCopy = new ArrayList<>();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView genresTextView;
        private final TextView releaseDateTextView;
        private final ImageView posterImageView;
        private final ImageView adultImageView;

        ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            genresTextView = itemView.findViewById(R.id.genresTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            adultImageView = itemView.findViewById(R.id.adultImageView);
        }

        void bind(Movie movie, Context c, OnItemClickListener listener) {
            titleTextView.setText(movie.getTitle());
            genresTextView.setText(TextUtils.join(", ", movie.genres));
            releaseDateTextView.setText(movie.getReleaseDate());
            adultImageView.setVisibility(movie.adult ? View.VISIBLE : View.GONE);
            posterImageView.setImageResource(R.drawable.ic_launcher_foreground);

            String backdropPath = movie.getBackdropPath();
            if (!TextUtils.isEmpty(backdropPath)) {
                Glide.with(itemView)
                        .load(Constants.buildBackdropUrl(backdropPath))
                        .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                        .into(posterImageView);
            }
            itemView.setOnClickListener(v -> listener.onItemClick(movie, itemView));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == items.size() - 1){
            onBottomReachedListener.onBottomReached(position);
        }
        holder.bind(items.get(position), mContext, listener);
    }

    public void updateMainList(List<Movie> movies) {
        itemsCopy.clear();
        itemsCopy.addAll(movies);
    }


    public void filterByGenre(ArrayList<Genre> genres) {
        items.clear();

        if(genres.size() == 0){
            items.addAll(itemsCopy);
        } else{
            for (Movie movie : itemsCopy) {
                for (Genre genre : genres) {
                    if (movie.genreIds.contains(genre.getId())) {
                        if (!items.contains(movie))
                            items.add(movie);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    public void filterByName(String text) {
        items.clear();

        if(text.isEmpty()){
            items.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(Movie item: itemsCopy){
                if(item.getTitle().toLowerCase().contains(text)){
                    if (!items.contains(item))
                        items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
