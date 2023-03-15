package com.example.oblig2dat153.data;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.oblig2dat153.dao.ImageDAO;
import com.example.oblig2dat153.database.ImageDatabase;
import com.example.oblig2dat153.model.Image;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ImageDatabaseTest {

    private ImageDAO imageDAO;
    private ImageDatabase imageDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        imageDatabase = Room.inMemoryDatabaseBuilder(context, ImageDatabase.class).build();
        imageDAO = imageDatabase.imageDAO();
    }

    @After
    public void closeDb() throws IOException {
        imageDatabase.close();
    }

    @Test
    public void insertImageAndGetImage() {
        Image image = new Image();
        image.setImageName("test Name");
        imageDAO.insert(image);
        List<Image> list = imageDAO.getAllImagesList();
        assertNotNull(list);
        boolean value = list.stream().filter(image1 -> image1.getImageName().equals(image.getImageName())).findAny().isPresent();
        assertTrue(value);
    }

    @Test
    public void insertImageAndDeleteImage() {
        Image image = new Image();
        image.setImageName("test Name");
        imageDAO.insert(image);
        Image byName = imageDAO.findImageByName("test Name");
        imageDAO.delete(byName);
        List<Image> list = imageDAO.getAllImagesList();
        assertNotNull(list);
        boolean value = list.stream().filter(image1 -> image1.getImageName().equals(image.getImageName())).findAny().isPresent();
        assertFalse(value);
    }


}