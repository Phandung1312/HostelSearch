package com.androidexam.stayfinder.ui.chat.listchat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.data.repositories.ChatRepository;
import com.google.firebase.firestore.auth.User;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ListChatViewModel extends BaseViewModel {
    ChatRepository chatRepository;
    @Inject
    public ListChatViewModel(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
    public LiveData<List<String>> loadIds(String id){
        return chatRepository.getListIdUser(id);
    }

    public LiveData<UserFirebase> loadCurrentUser(){
        return chatRepository.getCurrentUser();
    }
    public LiveData<Message> getLastMessage(String myId, String friendId){
        return chatRepository.getLastMessage(myId, friendId);
    }
}
