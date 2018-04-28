package com.arctouch.codechallenge.home.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.home.item.GenreItem;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<GenreItem> items;
    private final OnItemClickListener listener;

    public GenreAdapter(List<GenreItem> genres, OnItemClickListener listener) {
        this.items = genres;
        this.listener = listener;
    }

    public void setItems(List<GenreItem> genres) {
        this.items.clear();
        this.items.addAll(genres);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final ImageView removeImageView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.genreItem_nameTextView);
            removeImageView = itemView.findViewById(R.id.genreItem_removeImageView);
        }

        void bind(GenreItem genreItem, OnItemClickListener listener, int position) {
            nameTextView.setText(genreItem.getGenre().getName());
            removeImageView.setVisibility(genreItem.isSelect() ? View.VISIBLE : View.GONE);
            itemView.setOnClickListener(v -> listener.onItemClick(position));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_genre_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), listener, position);
    }

    public void setSelect(int position) {
        items.get(position).setSelect(!items.get(position).isSelect());
        notifyItemChanged(position);
    }
}
