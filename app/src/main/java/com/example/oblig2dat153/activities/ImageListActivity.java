package com.example.oblig2dat153.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.adapters.ImageAdapter;
import com.example.oblig2dat153.databinding.ActivityImageListBinding;
import com.example.oblig2dat153.model.Image;
import com.example.oblig2dat153.viewmodel.ImageListActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {

    private ImageListActivityViewModel imageListActivityViewModel;

    ActivityImageListBinding activityImageListBinding;

    private ImageListActivityClickHandler clickHandler;

    // RecyclerView
    private ArrayList<Image> imageList;

    RecyclerView imageRecyclerView;

    private ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        imageListActivityViewModel = new ViewModelProvider(this).get(ImageListActivityViewModel.class);

        activityImageListBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_list);
        clickHandler = new ImageListActivityClickHandler();
        activityImageListBinding.setClickHandler(clickHandler);

        imageListActivityViewModel.getAllImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageList = (ArrayList<Image>) images;

                for (Image i : images) {
                    Log.i("Yosafe", i.getId() + "    " + i.getImageName());
                }
                LoadRecyclerView();
            }
        });
    }

    private void LoadRecyclerView() {
        imageRecyclerView = activityImageListBinding.recyclerView;
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        imageAdapter = new ImageAdapter();
        imageAdapter.setImages(imageList);
        imageAdapter.setOnDeleteClickListener(new ImageAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Image image) {
                Log.d("Yosafe", "delete... image with id " + image.getId());
                imageListActivityViewModel.deleteImage(image);
            }
        });
        imageRecyclerView.setAdapter(imageAdapter);
    }


    public class ImageListActivityClickHandler {


        public void onAddClicked(View view) {
            // insert Image from here
        }


    }
}