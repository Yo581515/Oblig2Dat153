package com.example.oblig2dat153.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.oblig2dat153.model.Image;
import com.example.oblig2dat153.repository.ImageRepository;

import java.util.List;

public class ImageListActivityViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;

    private LiveData<List<Image>> images;

    public ImageListActivityViewModel(@NonNull Application application) {
        super(application);
        imageRepository = new ImageRepository(application);
    }

    public LiveData<List<Image>> getAllImages() {
        images = imageRepository.getAllImages();
        return images;
    }

    public void addNewImage(Image image){
        imageRepository.addImage(image);
    }

    public void deleteImage(Image image){
        imageRepository.deleteImage(image);
    }



    
}
