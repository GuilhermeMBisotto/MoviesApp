package com.arctouch.codechallenge.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.arctouch.codechallenge.api.APIHandler;
import com.arctouch.codechallenge.api.APIServiceResultFailed;
import com.arctouch.codechallenge.model.GenresResponse;
import com.arctouch.codechallenge.services.MoviesServices;
import com.arctouch.codechallenge.detail.DetailMovieActivity;
import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.home.adapters.GenreAdapter;
import com.arctouch.codechallenge.home.adapters.UpcomingMoviesAdapter;
import com.arctouch.codechallenge.home.item.GenreItem;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.model.UpcomingModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingActivityFragment extends Fragment {

    @BindView(R.id.homeFragment_recyclerView)
    RecyclerView moviesRecycleView;
    @BindView(R.id.homeFragment_genreFilterRecyclerView)
    RecyclerView genreFilterRecyclerView;
    @BindView(R.id.homeFragment_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.homeFragment_genreFilterSearchView)
    SearchView searchView;

    private View mView;
    private UpcomingMoviesAdapter mMoviesAdapter;
    private GenreAdapter mGenreAdapter;
    private ArrayList<Movie> mMovies;
    private ArrayList<Movie> mMoviesCopy;
    private int mActualPage = 1;
    private int mMaxPage = 1;
    private ArrayList<Genre> mGenreFilter;
    private String mNameFilter = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        mGenreFilter = new ArrayList<>();

        mMovies = new ArrayList<>();
        mMoviesCopy = new ArrayList<>();
        mMoviesAdapter = new UpcomingMoviesAdapter(mMovies, UpcomingActivityFragment.this.getContext(), (movie, view1) -> {
            performDetail(movie.id, view1);
        });

        mMoviesAdapter.setOnBottomReachedListener(position -> getMovies());
        moviesRecycleView.setAdapter(mMoviesAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mNameFilter = query;
                filterByName();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mNameFilter = newText;
                filterByName();
                return false;
            }
        });
        searchView.clearFocus();
        genreFilterRecyclerView.requestFocus();
        getGenres();
    }

    private void getGenres(){
        MoviesServices.getGenres(new APIHandler.Controller() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(Object obj) {
                Cache.setGenres(((GenresResponse)obj).genres);
                mGenreAdapter = new GenreAdapter(createGenreList(), position -> {
                    mGenreAdapter.setSelect(position);

                    if (mGenreFilter.contains(Cache.getGenres().get(position))) {
                        mGenreFilter.remove(Cache.getGenres().get(position));
                    } else {
                        mGenreFilter.add(Cache.getGenres().get(position));
                    }
                    filterByGenre();
                });
                genreFilterRecyclerView.setAdapter(mGenreAdapter);
                mGenreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(APIServiceResultFailed error) { }

            @Override
            public void onFailure(APIServiceResultFailed failure) { }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
                getMovies();
            }
        });
    }

    private void getMovies() {
        if (mActualPage > mMaxPage) return;

        MoviesServices.getUpcomingMovies(mActualPage, new APIHandler.Controller() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(Object obj) {
                mMaxPage = ((UpcomingModel)obj).totalPages;
                mActualPage++;
                updateList(((UpcomingModel)obj).getMovieList());
            }

            @Override
            public void onError(APIServiceResultFailed error) { }

            @Override
            public void onFailure(APIServiceResultFailed failure) { }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void updateList(List<Movie> movies) {
        for (Movie movie : movies) {
            movie.genres = new ArrayList<>();
            for (Genre genre : Cache.getGenres()) {
                if (movie.genreIds.contains(genre.getId())) {
                    movie.genres.add(genre);
                }
            }
        }

        mMovies.addAll(Movie.filterList(mMovies, movies));
        mMoviesCopy.addAll(Movie.filterList(mMoviesCopy, movies));
        mMoviesAdapter.updateMainList(mMoviesCopy);
        checkFilter();
    }

    private List<GenreItem> createGenreList() {
        List<GenreItem> items = new ArrayList<>();

        for (Genre g : Cache.getGenres()) {
            GenreItem item = new GenreItem(g, false);
            items.add(item);
        }

        return items;
    }

    private void checkFilter() {
        if (mNameFilter.isEmpty() && !mGenreFilter.isEmpty()) {
            filterByGenre();
        } else if (!mNameFilter.isEmpty() && mGenreFilter.isEmpty()) {
            filterByName();
        }
    }

    private void filterByGenre() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        mNameFilter = "";
        mMoviesAdapter.filterByGenre(mGenreFilter);
    }

    private void filterByName() {
        mGenreFilter.clear();
        mMoviesAdapter.filterByName(mNameFilter);
        mGenreAdapter.setItems(createGenreList());
    }

    private void performDetail(int id, View view) {
        Intent intent = new Intent(UpcomingActivityFragment.this.getActivity(), DetailMovieActivity.class);
        intent.putExtra("movieId", id);

        Pair<View, String> p1 = Pair.create(view.findViewById(R.id.posterImageView), "transitionMovieBG");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(UpcomingActivityFragment.this.getActivity(), p1);
        startActivity(intent, options.toBundle());
    }
}
