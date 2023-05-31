package com.androidexam.stayfinder.ui.chat.listchat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.databinding.ItemsUserBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserAdapter extends FirebaseRecyclerAdapter<UserFirebase, UserAdapter.UserFirebaseHolder> {
   private ItemRecyclerViewListener item;
   private ListChatViewModel viewModel;
   private LifecycleOwner lifecycleOwner;
   private String myId;
    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserFirebase> options,
                       ItemRecyclerViewListener item,
                       ListChatViewModel viewModel,
                       LifecycleOwner lifecycleOwner,
                       String myId) {
        super(options);
        this.item = item;
        this.viewModel = viewModel;
        this.myId = myId;
        this.lifecycleOwner = lifecycleOwner;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserFirebaseHolder holder, int position, @NonNull UserFirebase model) {
        holder.binding.setUser(model);
        holder.binding.cardViewUser.setOnClickListener(view ->{
            item.onClick(view, model);
        });
        viewModel.getLastMessage(myId, model.getId()).observe(lifecycleOwner,message ->{
            holder.binding.setMessage(message);
                });
        holder.binding.executePendingBindings();
    }

    @NonNull
    @Override
    public UserFirebaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsUserBinding binding = ItemsUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserFirebaseHolder(binding);
    }

    static class UserFirebaseHolder extends RecyclerView.ViewHolder {
        private ItemsUserBinding binding;

        public UserFirebaseHolder(ItemsUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
