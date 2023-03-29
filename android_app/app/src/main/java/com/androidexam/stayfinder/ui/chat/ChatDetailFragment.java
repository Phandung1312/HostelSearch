package com.androidexam.stayfinder.ui.chat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.BR;
import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.databinding.ChatDetailClass;
import com.androidexam.stayfinder.databinding.ItemsChatLeftBinding;
import com.androidexam.stayfinder.databinding.ItemsChatRightBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatDetailFragment extends BaseFragment<ChatDetailClass> {
    ChatDetailViewModel viewModel;
    static final int MESSAGE_RIGHT = 0; // FOR ME
    static final int MESSAGE_LEFT = 1; // FOR RECEIVER
    public ChatDetailFragment() {
        super(ChatDetailClass::inflate);
    }
    @Override
    public void initView() {
        viewModel = new ViewModelProvider(this).get(ChatDetailViewModel.class);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);
        navBar.setVisibility(View.GONE);
    }
    @Override
    public void initListeners() {
        dataBinding.imageBack.setOnClickListener(view ->{
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
            bottomNavigationView.setVisibility(View.VISIBLE);
            Navigation.findNavController(dataBinding.getRoot()).navigateUp();
        });
        dataBinding.etInputMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().length() > 0){
                    dataBinding.btnSend.setEnabled(true);
                }
                else{
                    dataBinding.btnSend.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = dataBinding.etInputMessage.getText().toString();
                if(!text.startsWith(" ")){
                    dataBinding.etInputMessage.getText().insert(0," ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.message.setContent(s.toString());
            }
        });
        dataBinding.btnSend.setOnClickListener(view ->{
            viewModel.sendMessage();
            dataBinding.etInputMessage.setText("");
        });
    }
    @Override
    public void initData() {
        String id = ChatDetailFragmentArgs.fromBundle(getArguments()).getId();
        if(id != null){
            viewModel.setReceiver(id);
            viewModel.loadReceiver().observe(getViewLifecycleOwner(),user ->{
                dataBinding.tvName.setText(user.getUsername());
            });
            viewModel.setSender();
            viewModel.loadSender().observe(getViewLifecycleOwner(),user ->{
                Log.d("STATUS","Get current user success " + user.getId());
                setAdapter();
            });
        }
        else{
            Toast.makeText(getContext(), "Id passed is null", Toast.LENGTH_SHORT).show();
        }

    }
    private void setAdapter(){
        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(viewModel.getReferenceChatSender(viewModel.sender.getValue().getId()), Message.class)
                .setLifecycleOwner(this)
                .build();
        FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
                holder.binding.setVariable(BR.message, model);
                if(model.getSender().equals(viewModel.sender.getValue().getId())){
                    holder.binding.setVariable(BR.user,viewModel.sender.getValue());
                }
                else{
                    holder.binding.setVariable(BR.user,viewModel.receiver.getValue());
                }
                holder.binding.executePendingBindings();
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType == MESSAGE_LEFT){
                    ItemsChatLeftBinding binding = ItemsChatLeftBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                    return new MessageViewHolder(binding);
                } else if(viewType == MESSAGE_RIGHT){
                    ItemsChatRightBinding binding = ItemsChatRightBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                    return new MessageViewHolder(binding);
                }
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                if(TextUtils.equals(viewModel.sender.getValue().getId(),getItem(position).getSender())){
                    return MESSAGE_RIGHT;
                }
                else{
                    return MESSAGE_LEFT;
                }
            }
        };
        dataBinding.rvChatDetail.setAdapter(adapter);
        adapter.startListening();
    }
    class MessageViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;
        public MessageViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
