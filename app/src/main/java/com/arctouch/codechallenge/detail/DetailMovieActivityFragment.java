package com.arctouch.codechallenge.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.api.APIHandler;
import com.arctouch.codechallenge.api.APIServiceResultFailed;
import com.arctouch.codechallenge.services.MoviesServices;
import com.arctouch.codechallenge.detail.adapter.MovieReviewAdapter;
import com.arctouch.codechallenge.model.MovieDetail;
import com.arctouch.codechallenge.model.MovieReview;
import com.arctouch.codechallenge.util.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailMovieActivityFragment extends Fragment {

    @BindView(R.id.movieDetail_bgImageView)
    ImageView bgImageView;
    @BindView(R.id.movieDetail_posterImageView)
    ImageView posterImageView;
    @BindView(R.id.movieDetail_titleTextView)
    TextView titleTextView;
    @BindView(R.id.movieDetail_releaseDateTextView)
    TextView releaseDateTextView;
    @BindView(R.id.movieDetail_genresTextView)
    TextView genresTextView;
    @BindView(R.id.movieDetail_descTextView)
    TextView descTextView;
    @BindView(R.id.movieDetail_taglineTextView)
    TextView taglineTextView;
    @BindView(R.id.movieDetail_runtimeTextView)
    TextView runtimeTextView;
    @BindView(R.id.movieDetail_ReviewListView)
    ListView reviewListView;
    @BindView(R.id.movieDetail_adultImageView)
    ImageView adultImageView;

    private View mView;
    private MovieReview mMovieReview;
    private MovieReviewAdapter mMovieReviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        mMovieReview = new MovieReview();

        mMovieReviewAdapter = new MovieReviewAdapter(mMovieReview.getReviews(), DetailMovieActivityFragment.this.getContext());
        reviewListView.setAdapter(mMovieReviewAdapter);

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            getDetail(extras.getInt("movieId"));
            getReviews(extras.getInt("movieId"));
        }
    }

    private void getDetail(int id) {
        MoviesServices.getDetailMovie(id, new APIHandler.Controller() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object obj) {
                setValues((MovieDetail)obj);
            }

            @Override
            public void onError(APIServiceResultFailed error) { }

            @Override
            public void onFailure(APIServiceResultFailed failure) { }

            @Override
            public void onFinish() { }
        });
    }

    private void getReviews(int id) {
        MoviesServices.getReview(id, new APIHandler.Controller() {
            @Override
            public void onStart() { }

            @Override
            public void onSuccess(Object obj) {
                mMovieReview.setReviews(((MovieReview)obj).getReviews());
                mMovieReviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(APIServiceResultFailed error) { }

            @Override
            public void onFailure(APIServiceResultFailed failure) { }

            @Override
            public void onFinish() { }
        });
    }

    private void setValues(MovieDetail movie) {
        titleTextView.setText(movie.getTitle());
        releaseDateTextView.setText(movie.getReleaseDate());
        genresTextView.setText(TextUtils.join(" • ", movie.genres));
        taglineTextView.setText(movie.getTagline());
        descTextView.setText(movie.getOverview());
        runtimeTextView.setText(movie.getRuntime());

        if (movie.adult)
            adultImageView.setVisibility(View.VISIBLE);

        String posterPath = movie.getPosterPath();
        if (!TextUtils.isEmpty(posterPath)) {
            Glide.with(mView)
                    .load(Constants.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                    .into(posterImageView);
        }

        String backdropPath = movie.getBackdropPath();
        if (!TextUtils.isEmpty(posterPath)) {
            Glide.with(mView)
                    .load(Constants.buildBackdropUrl(backdropPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_launcher_foreground))
                    .into(bgImageView);
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.movieDetail_closeImageButton)
    public void onCloseButtonClick() {
        DetailMovieActivityFragment.this.getActivity().onBackPressed();
    }
}
