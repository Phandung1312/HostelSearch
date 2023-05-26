package com.androidexam.stayfinder.ui.admin.post;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.adapters.ImageViewBindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.databinding.ItemCommentBinding;
import com.androidexam.stayfinder.databinding.ItemImageClass;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private ArrayList<String> imageURLList;
    public ImageAdapter(ArrayList<String> imageURLList){
        this.imageURLList = imageURLList;
    }
    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageClass binding =
                ItemImageClass.inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);
        return new ImageAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setImageURL(imageURLList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageURLList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemImageClass binding;

        public ViewHolder(ItemImageClass itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
