package com.example.oblig2dat153.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.oblig2dat153.dao.ImageDAO;
import com.example.oblig2dat153.database.ImageDatabase;
import com.example.oblig2dat153.model.Image;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageRepository {

    public ImageDAO imageDAO;

    private LiveData<List<Image>> images ;

    public ImageRepository(Application application) {
        ImageDatabase imageDatabase = ImageDatabase.getInstance(application);
        this.imageDAO = imageDatabase.imageDAO();
    }

    public LiveData<List<Image>> getAllImages() {
        images = imageDAO.getAllImages();
        return images;
    }

    public LiveData<List<Image>> getAllImagesSortedAZ() {
        images = imageDAO.getAllImagesSortedAZ();
        return images;
    }

    public LiveData<List<Image>> getAllImagesSortedZA() {
        images = imageDAO.getAllImagesSortedZA();
        return images;
    }

    public void addImage(Image image) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Inserting image
                imageDAO.insert(image);
            }
        });
    }

    public void deleteImage(Image image) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Deleting image
                imageDAO.delete(image);
            }
        });
    }
}
