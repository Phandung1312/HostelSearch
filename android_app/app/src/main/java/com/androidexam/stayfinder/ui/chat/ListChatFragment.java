package com.androidexam.stayfinder.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.databinding.ItemsUserBinding;
import com.androidexam.stayfinder.databinding.ListChatClass;
import com.firebase.ui.auth.data.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListChatFragment extends BaseFragment<ListChatClass> {
    @Inject
    FirebaseDatabase database;
    public ListChatFragment(){super(ListChatClass::inflate);}
    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataBinding.rvChat.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        setAdapter();
    }
    private void setAdapter(){
        FirebaseRecyclerOptions<UserFirebase> options = new FirebaseRecyclerOptions.Builder<UserFirebase>()
                .setQuery(database.getReference().child("User"), UserFirebase.class)
                .setLifecycleOwner(this)
                .build();
        FirebaseRecyclerAdapter<UserFirebase, UserFirebaseHolder> adapter = new FirebaseRecyclerAdapter<UserFirebase,UserFirebaseHolder>(options){
            @NonNull
            @Override
            public UserFirebaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                ItemsUserBinding binding = ItemsUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new UserFirebaseHolder(binding);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserFirebaseHolder holder, int position, @NonNull UserFirebase model) {
                holder.binding.setUser(model);
                holder.binding.executePendingBindings();
                holder.binding.cardViewUser.setOnClickListener(view ->{
                    Navigation.findNavController(dataBinding.getRoot()).navigate(ListChatFragmentDirections
                            .actionListChatFragmentToChatDetailFragment(model.getId()));
                });
            }

        };
        dataBinding.rvChat.setAdapter(adapter);
        adapter.startListening();
    }
    class UserFirebaseHolder extends RecyclerView.ViewHolder {
        private ItemsUserBinding binding;

        public UserFirebaseHolder(ItemsUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
