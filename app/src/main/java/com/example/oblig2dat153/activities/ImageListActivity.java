package com.example.oblig2dat153.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.adapters.ImageAdapter;
import com.example.oblig2dat153.databinding.ActivityImageListBinding;
import com.example.oblig2dat153.fragments.InsertImageFragment;
import com.example.oblig2dat153.model.Image;
import com.example.oblig2dat153.viewmodel.ImageListActivityViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        imageAdapter = new ImageAdapter();

        imageListActivityViewModel.getSorted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("Yosafe", "sorted is set to ------> " + imageListActivityViewModel.getSorted().getValue());

                imageAdapter.setSorted(aBoolean);
            }
        });

        imageListActivityViewModel.getAllImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageList = (ArrayList<Image>) images;
                Log.d("Yosafe", "getAllimages observable " + imageListActivityViewModel.getSorted().getValue());
                LoadRecyclerView(imageListActivityViewModel.getSorted().getValue());
            }
        });


        imageAdapter.setOnDeleteClickListener(new ImageAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Image image) {
                Log.d("Yosafe", "delete.... image with id " + image.getId());
                imageListActivityViewModel.deleteImage(image);
            }
        });
        imageRecyclerView = activityImageListBinding.recyclerView;
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageRecyclerView.setAdapter(imageAdapter);
    }

    private void LoadRecyclerView(boolean sorted) {
        for (Image i : imageList) {
            Log.i("Yosafe", i.getId() + "    " + i.getImageName());
        }
        imageAdapter.setImages(imageList);
    }


    public class ImageListActivityClickHandler {
        public void onAddClicked(View view) {
            // insert Image from here
            InsertImageFragment insertImageFragment = new InsertImageFragment();
            insertImageFragment.setOnAddClickListener(new InsertImageFragment.OnAddClickListener() {
                @Override
                public void onAddClick(Image image) {
                    Toast.makeText(ImageListActivity.this, "adding " + image.getImageName(), Toast.LENGTH_SHORT).show();
                    imageListActivityViewModel.addNewImage(image);
                }
            });
            insertImageFragment.show(getSupportFragmentManager(), "adding new image");
        }

        public void onSortClicked(View view) {
            imageListActivityViewModel.setSorted(!imageListActivityViewModel.getSorted().getValue());
            LoadRecyclerView(imageListActivityViewModel.getSorted().getValue());
        }
    }

}