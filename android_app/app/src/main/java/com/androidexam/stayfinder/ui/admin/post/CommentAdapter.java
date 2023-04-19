package com.androidexam.stayfinder.ui.admin.post;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.activities.MainActivity;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.data.repositories.CommentRepository;
import com.androidexam.stayfinder.databinding.ItemCommentBinding;
import com.androidexam.stayfinder.databinding.ItemsAdminPostBinding;

import java.util.ArrayList;

import javax.inject.Inject;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<Comment> commentList;
    private Account account;
    private PostDetailAdminViewModel postDetailAdminViewModel;
    private LifecycleOwner lifecycleOwner;
    public CommentAdapter(ArrayList<Comment> commentList,Account account,PostDetailAdminViewModel postDetailAdminViewModel, LifecycleOwner lifecycleOwner){
        this.commentList = commentList;
        this.account = account;
        this.postDetailAdminViewModel = postDetailAdminViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding =
                ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.binding.setComment(commentList.get(position));
//        if(account.getAccountName() != commentList.get(position).getAccount().getAccountName())
//        {
//            holder.binding.btnRemove.setVisibility(View.GONE);
//
//        }
        int id = commentList.get(position).getId();
        Log.d("KiemTraID", String.valueOf(commentList.get(position).getId()));
        holder.binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDetailAdminViewModel.deleteComment(id)
                        .observe(lifecycleOwner, response ->{
                            if(response){
                                Toast.makeText(v.getContext().getApplicationContext(), "Delete comment success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(v.getContext().getApplicationContext(), "Delete comment failure", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemCommentBinding binding;

        public ViewHolder( ItemCommentBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
