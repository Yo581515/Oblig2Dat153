package com.example.oblig2dat153.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.oblig2dat153.model.Image;
import com.example.oblig2dat153.repository.ImageRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageListActivityViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;

    private LiveData<List<Image>> images;

    MutableLiveData<Boolean> sorted = new MutableLiveData<>(false);

    public ImageListActivityViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new ImageRepository(application);
    }

    public LiveData<List<Image>> getAllImages() {
        images = imageRepository.getAllImages();
        return images;
    }

    public void setSorted(boolean sorted) {
        this.sorted.setValue(sorted);
    }

    public MutableLiveData<Boolean> getSorted() {
        return sorted;
    }

    public void setImages(LiveData<List<Image>> images) {
        this.images = images;
    }

    public void addNewImage(Image image) {
        imageRepository.addImage(image);
    }

    public void deleteImage(Image image) {
        imageRepository.deleteImage(image);
    }

}
