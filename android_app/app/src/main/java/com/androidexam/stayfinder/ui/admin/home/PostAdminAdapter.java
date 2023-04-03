package com.androidexam.stayfinder.ui.admin.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.common.BindingAdapters;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.databinding.PostItemAdminClass;

import java.util.ArrayList;
import java.util.List;

public class PostAdminAdapter extends RecyclerView.Adapter<PostAdminAdapter.ViewHolder> implements Filterable {
    private static ArrayList<Post> postList;

    public PostAdminAdapter(ArrayList<Post> postList){
        this.postList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostItemAdminClass binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.post_item_admin,
                        parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdminAdapter.ViewHolder holder, int position) {
        BindingAdapters.setImageUrl(holder.binding.imgHome,postList.get(position).getImages().get(0).getSource());
        String rentPrice = postList.get(position).getHostel().getRentPrice() + "VND/phòng";
        holder.binding.costHome.setText(rentPrice);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String input = constraint.toString().toLowerCase();
                List<Post> filteredPost = new ArrayList<>();
                if(input.isEmpty()){
                    filteredPost.addAll(postList);
                }else {
                    for(Post post: postList){
                        if(post.getHostel().getName().toLowerCase().contains(input))
                        {
                            filteredPost.add(post);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPost;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                postList = new ArrayList<>();
                postList.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private PostItemAdminClass binding;

        public ViewHolder( PostItemAdminClass itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
