package com.androidexam.stayfinder.ui.client.addpost;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.databinding.ItemImageStayBinding;
import com.androidexam.stayfinder.databinding.ItemsAdminPostBinding;
import com.androidexam.stayfinder.ui.admin.home.PostAdminAdapter;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private ArrayList<Bitmap> bitmaps;
    public ImageAdapter(ArrayList<Bitmap> bitmaps){
        this.bitmaps = bitmaps;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemImageStayBinding binding =
                ItemImageStayBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);
        return new ImageAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.imgHome.setOnClickListener(v -> {
            if(holder.binding.btnRemove.getVisibility() == View.GONE){
                holder.binding.btnRemove.setVisibility(View.VISIBLE);
            }else{
                holder.binding.btnRemove.setVisibility(View.GONE);
            }
        });
        int positionRP = position;
        holder.binding.btnRemove.setOnClickListener(v -> {
            bitmaps.remove(positionRP);
            notifyDataSetChanged();
        });
        holder.binding.imgHome.setImageBitmap(bitmaps.get(position));
        holder.binding.imgHome.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemImageStayBinding binding;

        public ViewHolder( ItemImageStayBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
