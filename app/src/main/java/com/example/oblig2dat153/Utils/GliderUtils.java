package com.example.oblig2dat153.Utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GliderUtils {
    public static void insertBitMapToImageView(ImageView imageView, Bitmap image) {
        Glide
                .with(imageView.getContext())
                .load(image)
                .into(imageView);
    }

    public static void insertByteArrayMapToImageView(ImageView imageView, byte[] image) {
        Glide
                .with(imageView.getContext())
                .load(image)
                .into(imageView);
    }
}
