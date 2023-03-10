package com.example.oblig2dat153.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.Utils.ImageDiffCallback;
import com.example.oblig2dat153.databinding.ImageItemBinding;
import com.example.oblig2dat153.model.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Image> imageList = new ArrayList<>();

    private OnDeleteClickListener onDeleteClickListener;

    private Integer sorted = 0;


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageItemBinding imageItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.image_item,
                parent,
                false
        );
        return new ImageViewHolder(imageItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = imageList.get(position);
        holder.imageItemBinding.setImage(image);

        holder.imageItemBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.onDeleteClick(image);
            }

        });
    }

    @Override
    public int getItemCount() {
        return imageList == null ? 0 : imageList.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageItemBinding imageItemBinding;

        public ImageViewHolder(@NonNull ImageItemBinding imageItemBinding) {
            super(imageItemBinding.getRoot());
            this.imageItemBinding = imageItemBinding;
        }
    }

    public void setSorted(Integer sorted) {
        this.sorted = sorted;
    }

    public void setImages(ArrayList<Image> newImageList) {

        ArrayList<Image> tempList = (ArrayList<Image>) newImageList.stream().collect(Collectors.toList());

        DiffUtil.DiffResult result = DiffUtil.calculateDiff
                (new ImageDiffCallback(imageList, tempList), false);

        imageList = tempList;

        Log.i("Yosafe", "sorted value is + "+sorted);

        if (sorted != 0) {
            Collections.sort(imageList);
            Log.i("Yosafe", "sorting A-Z");
            if (sorted == -1) {
                Collections.reverse(imageList);
                Log.i("Yosafe", "sorting Z-A");
            }
        }
        result.dispatchUpdatesTo(ImageAdapter.this);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Image image);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

}
