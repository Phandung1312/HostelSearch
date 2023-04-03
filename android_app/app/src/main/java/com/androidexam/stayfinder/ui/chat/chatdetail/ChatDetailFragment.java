package com.androidexam.stayfinder.ui.chat.chatdetail;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.databinding.ChatDetailClass;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatDetailFragment extends BaseFragment<ChatDetailClass> implements ItemChangeListener {
    ChatDetailViewModel viewModel;
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
            Navigation.findNavController(dataBinding.getRoot()).popBackStack();
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
        viewModel.setReceiver(id);
        viewModel.loadReceiver().observe(getViewLifecycleOwner(),receiver ->{
            dataBinding.tvName.setText(receiver.getUsername());
            viewModel.setSender();
            viewModel.loadSender().observe(getViewLifecycleOwner(),sender ->{
                setAdapter();
                viewModel.seenMessage(sender.getId(), receiver.getId());
            });
        });

    }
    private void setAdapter(){
        FirebaseRecyclerOptions<Message> options = new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(viewModel.getReferenceChatSender(viewModel.sender.getValue().getId(),
                        viewModel.receiver.getValue().getId()), Message.class)
                .setLifecycleOwner(this)
                .build();
        MessageAdapter adapter = new MessageAdapter(options,
                viewModel.sender.getValue(),
                viewModel.receiver.getValue(),
                this);
        dataBinding.rvChatDetail.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void seen(Message message) {
        viewModel.seenMessage(message.getReceiver() , message.getSender());
    }

    @Override
    public void scrollRecyclerView(int position) {
        dataBinding.rvChatDetail.scrollToPosition(position - 1);
    }
}
