package com.androidexam.stayfinder.common;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.androidexam.stayfinder.R;
import com.bumptech.glide.Glide;

public class BindingAdapters {
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
       if(url != null){
           Glide.with(imageView.getContext())
                   .load(url)
                   .into(imageView);
       }
    }
}