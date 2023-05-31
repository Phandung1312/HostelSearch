package com.androidexam.stayfinder.ui.chat.listchat;

import android.view.View;

import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;

public interface ItemRecyclerViewListener {
    void onClick(View view, UserFirebase userFirebase);

}
