package com.androidexam.stayfinder.ui.chat.chatdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.data.repositories.ChatRepository;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatDetailViewModel extends BaseViewModel {
    Message message = new Message();
    ChatRepository chatRepository;
    MutableLiveData<UserFirebase> receiver = new MutableLiveData<>();
    MutableLiveData<UserFirebase> sender = new MutableLiveData<>();
    @Inject
    public ChatDetailViewModel(ChatRepository userRepository) {
        this.chatRepository = userRepository;
    }

    public void setReceiver(String id){
        receiver = chatRepository.getUserById(id);
    }
    public LiveData<UserFirebase> loadReceiver(){
        return receiver;
    }
    public void setSender(){
        sender = chatRepository.getCurrentUser();
    }
    public LiveData<UserFirebase> loadSender(){
        return sender;
    }
    public void sendMessage(){
        message.setSender(sender.getValue().getId());
        message.setReceiver(receiver.getValue().getId());
        message.setSeen(false);
        Date currentDate = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String time = timeFormat.format(currentDate);
        message.setTime(time);
        chatRepository.sendMessage(message.getSender(),message.getReceiver(),message);
        chatRepository.sendMessage(message.getReceiver(),message.getSender(),message);
    }
    public DatabaseReference getReferenceChatSender(String owner, String participantId){
        return chatRepository.getReferenceChatSender(owner, participantId);
    }
    public void seenMessage(String sender, String receiver){
        chatRepository.seenMessage(sender, receiver);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
