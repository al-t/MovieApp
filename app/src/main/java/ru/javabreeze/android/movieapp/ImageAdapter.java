package ru.javabreeze.android.movieapp;

import android.content.Context;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Алексей on 16.06.2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] picsUrls;
    private final String imagesUrlBase = "https://image.tmdb.org/t/p/w185";

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public ImageAdapter(Context c, String[] picsUrls) {
        mContext = c;
        this.picsUrls = picsUrls;
    }

    public int getCount() {
        if (picsUrls == null) {
            return mThumbIds.length;
        } else {
            return picsUrls.length;
        }
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 277));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        if (picsUrls == null) {
            imageView.setImageResource(mThumbIds[position]);
        } else {
            Log.v(Constants.LOG_TAG, "Position: " + position + "; url: " + picsUrls[position]);
            Picasso.with(mContext).load(imagesUrlBase + picsUrls[position])
                    .placeholder(R.drawable.interstellar)
                    .error(R.drawable.interstellar)
                    .into(imageView);
        }

        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.interstellar
    };
}