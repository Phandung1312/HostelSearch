package com.androidexam.stayfinder.ui.chat;

import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class ListChatViewModel extends BaseViewModel {
    MutableLiveData<List<UserFirebase>> listUser = new MutableLiveData<>();

}
