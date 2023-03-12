package com.example.oblig2dat153.Utils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.oblig2dat153.model.Image;

import java.util.ArrayList;

public class ImageDiffCallback extends DiffUtil.Callback{

    ArrayList<Image> oldImageList;
    ArrayList<Image> newImageList;

    public ImageDiffCallback(ArrayList<Image> oldImageList, ArrayList<Image> newImageList) {
        this.oldImageList = oldImageList;
        this.newImageList = newImageList;
    }

    @Override
    public int getOldListSize() {
        return oldImageList==null ? 0 : oldImageList.size();
    }

    @Override
    public int getNewListSize() {
        return  newImageList==null ? 0 : newImageList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldImageList.get(oldItemPosition).getId()==newImageList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldImageList.get(oldItemPosition).equals(newImageList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
