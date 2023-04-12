package com.androidexam.stayfinder.common;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindingAdapter("seen_text")
    public static  void setText(TextView textView, Boolean seen){
        if(seen != null){
            textView.setText(seen? "Đã xem" : "Đã gửi");
            textView.setVisibility(View.VISIBLE);
        }
        else{
            textView.setVisibility(View.GONE);
        }
    }
}