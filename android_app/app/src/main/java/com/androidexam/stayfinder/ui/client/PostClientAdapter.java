package com.androidexam.stayfinder.ui.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.common.BindingAdapters;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.databinding.ItemsAdminPostBinding;
import com.androidexam.stayfinder.ui.admin.home.PostAdminAdapter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostClientAdapter extends RecyclerView.Adapter<PostClientAdapter.ViewHolder> {
    private ArrayList<Post> postList;
    private ClientHomeViewModel clientHomeViewModel;
    private LifecycleOwner lifecycleOwner;

    public PostClientAdapter( ArrayList<Post> postList,ClientHomeViewModel clientHomeViewModel , LifecycleOwner lifecycleOwner ){
        this.postList = postList;
        this.clientHomeViewModel = clientHomeViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsAdminPostBinding binding =
                ItemsAdminPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostClientAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);
        if(post.getHostel() == null){
            MutableLiveData<Hostel> hostelLiveData = new MutableLiveData<>();
            clientHomeViewModel.getHostelById(post.getHostelId())
                    .observe(lifecycleOwner,hostel -> {
                        hostelLiveData.setValue(hostel);
                    });
            hostelLiveData.observe(lifecycleOwner, hostel -> {
                post.setHostel(hostel);
                holder.binding.setPost(post);
                holder.binding.cardViewPostAdmin.setOnClickListener(view ->{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post",post);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.postDetailAdminFragment,bundle);
                });
                holder.binding.executePendingBindings();
            });
        }else{
            holder.binding.setPost(post);
            holder.binding.cardViewPostAdmin.setOnClickListener(view ->{
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",post);
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.postDetailAdminFragment,bundle);
            });
            holder.binding.executePendingBindings();
        }
        holder.binding.executePendingBindings();
        holder.binding.accept.setVisibility(View.GONE);
        holder.binding.chat.setVisibility(View.GONE);
        holder.binding.delete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private  ItemsAdminPostBinding binding;

        public ViewHolder( ItemsAdminPostBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
