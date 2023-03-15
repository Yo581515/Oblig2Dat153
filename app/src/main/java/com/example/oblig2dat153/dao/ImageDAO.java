package com.example.oblig2dat153.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.oblig2dat153.model.Image;

import java.util.List;

@Dao
public interface ImageDAO {

    @Insert
    void insert(Image image);

    @Delete
    void delete(Image image);

    @Query("SELECT * FROM image")
    LiveData<List<Image>> getAllImages();

    @Query("SELECT * FROM Image ORDER BY LOWER(image_name) ASC")
    LiveData<List<Image>> getAllImagesSortedAZ();

    @Query("SELECT * FROM Image ORDER BY LOWER(image_name) DESC")
    LiveData<List<Image>> getAllImagesSortedZA();

    @Query("SELECT * FROM Image WHERE image_name = :imageName LIMIT 1")
    Image findImageByName(String imageName);

    @Query("SELECT * FROM image")
    List<Image> getAllImagesList();

}
