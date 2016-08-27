package ru.javabreeze.android.movieapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Алексей on 17.06.2016.
 */
public class MovieDataParser {
    public static int getNumberofPopularMovies (String movieJsonStr) throws JSONException {
        return new JSONObject(movieJsonStr).getJSONArray("results").length();
    }

    public static String getPopularPic (String movieJsonStr, int position) throws JSONException  {
        return new JSONObject(movieJsonStr).getJSONArray("results").getJSONObject(position)
                .getString("poster_path");
    }

    public static String[] getPopularPics (String movieJsonStr) throws JSONException {
        JSONArray results = new JSONObject(movieJsonStr).getJSONArray("results");
        String[] pics = new String[results.length()];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = results.getJSONObject(i).getString("poster_path");
        }
        return pics;
    }

}
