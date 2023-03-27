package com.androidexam.stayfinder.common;

public interface ImageConvertResult<T> {
    void onSuccess(T result);
    void onError();
}
