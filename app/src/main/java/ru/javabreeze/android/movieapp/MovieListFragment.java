package ru.javabreeze.android.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Алексей on 16.06.2016.
 */
public class MovieListFragment extends Fragment {

    private View view;
    private MoviesFetcher fetcher;
    private GridView gridview;
    private final String TAG = "MovieListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        initViews(view);

        return view;
    }

    private void setListTitle() {
        String moviesType = MoviesFetcher.getTypesOfMoviesFromPreferences(getContext());
        //if (Constants.DEBUG_IS_ON) Log.v(TAG, "moviesType: " + moviesType);
        String[] movies_sort_order_names = getResources().getStringArray(R.array.movies_sort_order_names);
        if (moviesType.equals(movies_sort_order_names[0])) {
            getActivity().setTitle(getString(R.string.popular_movies_list_title));
        } else if (moviesType.equals(movies_sort_order_names[1])) {
            getActivity().setTitle(getString(R.string.top_rated_movies_list_title));
        }
    }

    private void initViews(View view) {
        setListTitle();

        fetcher = MoviesFetcher.getMoviesFetcher(view.getContext());

        gridview = (GridView) view.findViewById(R.id.gridview_movies);
        fetcher.getMovies(gridview);

        gridview.setAdapter(new ImageAdapter(getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateList(View view, String[] moviesPics) {
        //gridview.setAdapter(new ImageAdapter(getContext(), moviesPics));
    }
}
