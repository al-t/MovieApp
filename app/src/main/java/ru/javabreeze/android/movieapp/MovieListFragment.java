package ru.javabreeze.android.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {

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
