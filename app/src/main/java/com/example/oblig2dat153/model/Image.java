package com.example.oblig2dat153.model;


import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.oblig2dat153.Utils.GliderUtils;

import java.util.Objects;


@Entity(tableName = "image")
public class Image extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "image_data")
    private byte[] imageData;

    @ColumnInfo(name = "image_name")
    private String imageName;

    @BindingAdapter("imageBinde")
    public static void loadImage(ImageView view, byte[] imageData) {
        GliderUtils.insertByteArrayMapToImageView(view, imageData);
    }



    @Ignore
    public Image() {
    }

    public Image(long id, byte[] imageData, String imageName) {
        this.id = id;
        this.imageData = imageData;
        this.imageName = imageName;
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
        notifyPropertyChanged(BR.imageData);
    }

    @Bindable
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
        notifyPropertyChanged(BR.imageName);

    }

    @Override
    public String toString() {
        return this.imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.getId()
                && imageName == image.getImageName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageName);
    }
}
