package com.androidexam.stayfinder.ui.chat.listchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.databinding.ItemsUserBinding;
import com.androidexam.stayfinder.databinding.ListChatClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListChatFragment extends BaseFragment<ListChatClass> implements ItemRecyclerViewListener {
    @Inject
    FirebaseDatabase database;
    ListChatViewModel viewModel;
    public ListChatFragment(){super(ListChatClass::inflate);}
    @Override
    public void initView() {
        viewModel = new ViewModelProvider(this).get(ListChatViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataBinding.rvChat.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        viewModel.loadCurrentUser().observe(getViewLifecycleOwner(),currentUser -> {
            viewModel.loadIds(currentUser.getId()).observe(getViewLifecycleOwner(), listId ->
                    setAdapter(listId, currentUser.getId()));
        });
    }
    private void setAdapter(List<String> ids,String myId){
        Query query = database.getReference().child("User");
        if(ids.size() != 0){
            dataBinding.layoutEmpty.setVisibility(View.GONE);
            query = database.getReference().child("User").orderByChild("id").startAt(ids.get(0)).endAt(ids.get(ids.size() - 1));
            FirebaseRecyclerOptions<UserFirebase> options = new FirebaseRecyclerOptions.Builder<UserFirebase>()
                    .setQuery(query, UserFirebase.class)
                    .setLifecycleOwner(this)
                    .build();
            UserAdapter adapter = new UserAdapter(options,
                    this ,
                    viewModel,
                    getViewLifecycleOwner(),
                    myId);
            dataBinding.rvChat.setAdapter(adapter);
            adapter.startListening();
        }else{
            dataBinding.layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view, UserFirebase userFirebase) {
        Navigation.findNavController(dataBinding.getRoot()).navigate(ListChatFragmentDirections
                .actionListChatFragmentToChatDetailFragment(userFirebase.getId()));
    }

}
