package com.androidexam.stayfinder.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static final String BASE_URL="https://tricky-week-production.up.railway.app/api/";
    public static final  int ADMIN_ROLE = 2;
    public static final int CLIENT_ROLE = 1;
    public static void convertUrlToByteString(Context context, String url,
            ImageConvertResult<String> imageConvertResult) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                            @Nullable Transition<? super Bitmap> transition) {
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

    public static void convertUrlToImageFile(Context context, String url, ImageConvertResult<File> imageResult) {
        RequestOptions requestOptions = new RequestOptions().format(DecodeFormat.PREFER_ARGB_8888);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                            @Nullable Transition<? super Bitmap> transition) {
                        File file = new File(context.getCacheDir(), "image.png");
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageResult.onSuccess(file);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        imageResult.onError();
                    }
                });

    }
}
