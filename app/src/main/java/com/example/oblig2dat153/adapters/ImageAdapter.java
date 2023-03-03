package com.example.oblig2dat153.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oblig2dat153.R;
import com.example.oblig2dat153.Utils.ImageDiffCallback;
import com.example.oblig2dat153.databinding.ImageItemBinding;
import com.example.oblig2dat153.model.Image;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<Image> imageList = new ArrayList<>();

    private OnDeleteClickListener onDeleteClickListener;


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


    public void setImages(ArrayList<Image> newImageList) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff
                (new ImageDiffCallback(imageList, newImageList), false);

        imageList = newImageList;
        result.dispatchUpdatesTo(ImageAdapter.this);

    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Image image);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

}
