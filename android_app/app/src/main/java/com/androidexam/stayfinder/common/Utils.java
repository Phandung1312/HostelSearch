package com.androidexam.stayfinder.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

public class Utils {
    public static final String BASE_URL="http://192.168.1.6:8080/api/";
    public static void convertUrlToByteString(Context context, String url, ImageConvertResult<String> imageConvertResult){
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        imageConvertResult.onSuccess(new String(byteArray));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        imageConvertResult.onError();
                    }
                });
    }
}
