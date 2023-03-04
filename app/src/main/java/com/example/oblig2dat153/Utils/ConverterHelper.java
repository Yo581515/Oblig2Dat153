package com.example.oblig2dat153.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class ConverterHelper {

    /**
     * Used when storing an image in the database.
     *
     * @param bitmap
     * @return byte[]
     */
    @TypeConverter
    public static byte[] BitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap ByteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static byte[] fromDrawableToByteArray(Context context, int imageId) {
        Bitmap bitmapImage = BitmapFactory.decodeResource(context.getResources(), imageId);
        return BitmapToByteArray(bitmapImage);
    }

}