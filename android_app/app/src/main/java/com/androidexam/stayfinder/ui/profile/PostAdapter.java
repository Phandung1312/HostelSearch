package com.androidexam.stayfinder.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.PostRequest;
import com.androidexam.stayfinder.databinding.ItemsAdminPostBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<PostRequest> posts;

    public PostAdapter(ArrayList<PostRequest> posts){
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsAdminPostBinding binding = ItemsAdminPostBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        PostRequest postRequest = posts.get(position);
        postRequest.getHostel().setPost(new Post(postRequest));
        holder.binding.setPost(new Post(postRequest));

        holder.binding.cardViewPostAdmin.setOnClickListener(view ->{
            PostRequest post = posts.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("hostelId", post.getHostel().getId());
            bundle.putSerializable("postId",post.getId());
            Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.postDetailAdminFragment,bundle);
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if(posts != null) return posts.size();
        else return 0;
    }

    public void updateList(ArrayList<PostRequest> newList) {
        posts = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemsAdminPostBinding binding;

        public ViewHolder(@NonNull ItemsAdminPostBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

        }
    }

}
