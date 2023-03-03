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
}
