package com.androidexam.stayfinder.base.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

public interface Inflate<T> {
    T inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent);

}