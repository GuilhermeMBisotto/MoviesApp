package com.arctouch.codechallenge.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Review;

import java.util.ArrayList;

public class MovieReviewAdapter extends BaseAdapter {

    private ArrayList<Review> items;
    private Context mContext;


    public MovieReviewAdapter(ArrayList<Review> reviews, Context context) {
        this.items = reviews;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Review getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Review review = items.get(position);

        if (convertView == null){
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.movie_detail_review_item, parent);
        }

        ((TextView) convertView.findViewById(R.id.movieReviewItem_authorTitleTextView)).setText(review.getAuthor());
        ((TextView) convertView.findViewById(R.id.movieReviewItem_contentTextView)).setText(review.getContent());

        return convertView;
    }
}
