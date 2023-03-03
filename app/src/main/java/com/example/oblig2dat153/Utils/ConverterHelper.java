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
    public byte[] BitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 100, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap ByteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

//    Bitmap catImage = createBitmapFromDrawable(R.drawable.cat1);

    public byte[] fromDrawableToByteArray(Context context, int imageId) {
        Bitmap bitmapImage = BitmapFactory.decodeResource(context.getResources(), imageId);
            return BitmapToByteArray(bitmapImage);
    }

}