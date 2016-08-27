package ru.javabreeze.android.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
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
 *
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] picsUrls;
    private final String imagesUrlBase = "https://image.tmdb.org/t/p/";
    private final String TAG = "ImageAdapter";

    // Width and height of the pictures in the grid
    private int picWidth;
    private int picHeight;
    private String imageWidthUrlAddition;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public ImageAdapter(Context c, String[] picsUrls) {
        mContext = c;
        this.picsUrls = picsUrls;

        /* Calculating width and height for pictures in the grid */
        View gridView = ((Activity) c).findViewById(R.id.gridview_movies);
        picWidth = gridView.getWidth()/2;
        int orientation = c.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            picHeight = gridView.getHeight()/2;
        } else {
            picHeight = gridView.getHeight();
        }
        setImageWidthUrlAddition();
    }

    private void setImageWidthUrlAddition() {
        if (picWidth <= 92) {
            imageWidthUrlAddition = "w92";
        } else if (picWidth <= 154) {
            imageWidthUrlAddition = "w154";
        } else if (picWidth <= 185) {
            imageWidthUrlAddition = "w185";
        } else if (picWidth <= 342) {
            imageWidthUrlAddition = "w342";
        } else if (picWidth <= 500) {
            imageWidthUrlAddition = "w500";
        } else if (picWidth <= 780) {
            imageWidthUrlAddition = "w780";
        } else {
            imageWidthUrlAddition = "original";
        }
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
            imageView.setLayoutParams(new GridView.LayoutParams(picWidth, picHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        if (picsUrls == null) {
            imageView.setImageResource(mThumbIds[position]);
        } else {
            String picUrl = imagesUrlBase + imageWidthUrlAddition + picsUrls[position];
            if (Constants.DEBUG_IS_ON) {
                Log.v(TAG, "Position: " + position + "; url: " + picUrl);
            }
            Picasso.with(mContext).load(picUrl)
                    .placeholder(R.drawable.default_placeholder)
                    .error(R.drawable.default_placeholder)
                    .into(imageView);
        }

        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.default_placeholder, R.drawable.default_placeholder
    };
}