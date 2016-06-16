package ru.javabreeze.android.movieapp;

import android.net.Uri;

/**
 * Created by Алексей on 16.06.2016.
 */
public class MoviesFetcher {

    private Uri.Builder getBaseUriBuilder() {
        return new Uri.Builder().scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY);
    }
    private Uri.Builder getPopularMoviewUriBuilder() {
        return getBaseUriBuilder().appendPath("popular");
    }
}
