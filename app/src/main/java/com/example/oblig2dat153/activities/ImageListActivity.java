package com.example.oblig2dat153.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
import java.util.List;

public class ImageListActivity extends AppCompatActivity {

    private ImageListActivityViewModel imageListActivityViewModel;
    ActivityImageListBinding activityImageListBinding;
    private ImageListActivityClickHandler clickHandler;

    // RecyclerView
    private ArrayList<Image> imageList;
    private ArrayList<Image> imageListSortedById;
    private ArrayList<Image> imageListSortedAZ;
    private ArrayList<Image> imageListSortedZA;
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
        activityImageListBinding.setIsLoading(true);

        imageListActivityViewModel.getAllImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageListSortedById = (ArrayList<Image>) images;
                Integer sorted = imageListActivityViewModel.getSorted().getValue();
                if (sorted == 0) {
                    activityImageListBinding.setIsLoading(true);
                    imageList = imageListSortedById;
                    LoadRecyclerView();
                }
            }
        });

        imageListActivityViewModel.getAllImagesAZ().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageListSortedAZ = (ArrayList<Image>) images;
                Integer sorted = imageListActivityViewModel.getSorted().getValue();
                if (sorted == 1) {
                    activityImageListBinding.setIsLoading(true);
                    imageList = imageListSortedAZ;
                    LoadRecyclerView();

                }
            }
        });

        imageListActivityViewModel.getAllImagesZA().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(List<Image> images) {
                imageListSortedZA = (ArrayList<Image>) images;
                Integer sorted = imageListActivityViewModel.getSorted().getValue();
                if (sorted == -1) {
                    activityImageListBinding.setIsLoading(true);
                    imageList = imageListSortedZA;
                    LoadRecyclerView();
                }
            }
        });

        imageAdapter.setOnDeleteClickListener(new ImageAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(Image image) {
                Log.d("Yosafe", "delete.... image with id " + image.getId());
                imageListActivityViewModel.deleteImage(image);
                activityImageListBinding.setIsLoading(true);
            }
        });
        imageRecyclerView = activityImageListBinding.recyclerView;
        imageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageRecyclerView.setAdapter(imageAdapter);
    }

    private void LoadRecyclerView() {
        for (Image i : imageList) {
            Log.i("Yosafe", i.getId() + "    " + i.getImageName());
        }
        if (imageList != null && imageList.size() > 0) {
            activityImageListBinding.setIsLoading(false);
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
                    activityImageListBinding.setIsLoading(true);

                }
            });
            insertImageFragment.show(getSupportFragmentManager(), "adding new image");
        }


        public void onSortClicked(View view) {

            Button btn = (Button) view;
            Integer sortTo = Integer.parseInt(btn.getTag().toString());
            imageListActivityViewModel.setSorted(sortTo);

            sortTo = imageListActivityViewModel.getSorted().getValue();
            if (sortTo == 0 && imageList != imageListSortedById) {
                imageList = imageListSortedById;
                LoadRecyclerView();
            } else if (sortTo == 1 && imageList != imageListSortedAZ) {
                imageList = imageListSortedAZ;
                LoadRecyclerView();
            } else if (sortTo == -1 && imageList != imageListSortedZA) {
                imageList = imageListSortedZA;
                LoadRecyclerView();
            }
        }
    }
}