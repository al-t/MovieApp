package ru.javabreeze.android.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Алексей on 16.06.2016.
 * The class for fetching the movies using the api.themoviedb.org
 */
public class MoviesFetcher {

    private static MoviesFetcher fetcher;
    private static Context context;
    private static GridView gridview;

    static String getTypesOfMoviesFromPreferences(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(
                context.getString(R.string.pref_movies_sort_order_key),
                context.getString(R.string.pref_movies_sort_order_default));
    }


    public static MoviesFetcher getMoviesFetcher(Context context) {
        MoviesFetcher.context = context;
        if (fetcher != null) {
            return fetcher;
        } else {
            fetcher = new MoviesFetcher();
            return fetcher;
        }
    }

    private MoviesFetcher() {
    }

    public void getMovies(GridView gridview) {
        MoviesFetcher.gridview = gridview;
        if (isConnected(context)) {
            new FetchMoviesTask().execute(getMoviesUriBuilder().build().toString());
        } else {
            Messages.sendNetworkErrorMessage(context);
        }
    }

    // the method checks internet connection
    private boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private Uri.Builder getBaseUriBuilder() {
        return new Uri.Builder().scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY);
    }

    private Uri.Builder getMoviesUriBuilder() {
        return getBaseUriBuilder().appendPath(getTypesOfMoviesFromPreferences(context));
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, String> {

        String forecastJsonStr = null;

        @Override
        protected String doInBackground(String... urls) {
            //String url = getUriBuilder().appendQueryParameter("q", urls[0]).build().toString();

            Log.v("Url to download: ", urls[0]);
            downloadUrl(urls[0]);
            return forecastJsonStr;
        }

        private void downloadUrl(String stringUrl) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                URL url = new URL(stringUrl);

                //Log.v(Constants.LOG_TAG, "Weather Url: " +stringUrl);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    // Nothing to do.
                    return;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return;
                }
                forecastJsonStr = buffer.toString();
                //Log.v(LOG_TAG, forecastJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                //return;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        // onPostExecute displays the results of the AsyncTask.


        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                //Log.v(Constants.LOG_TAG, result);
                try {
                    int moviesNumber = MovieDataParser.getNumberofPopularMovies(result);
                    //String[] moviesList = new String[moviesNumber];/*= MovieDataParser.getPopularPic(result);*/
                    String[] moviesList = MovieDataParser.getPopularPics(result);

                    /*for (int i = 0; i < moviesNumber; i++) {
                        moviesList[i] = MovieDataParser.getPopularPic(result, i);
                        Log.v(Constants.LOG_TAG, "moviesList[" + i + "]: " + moviesList[i]);
                    }*/
                    gridview.setAdapter(new ImageAdapter(context, moviesList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
