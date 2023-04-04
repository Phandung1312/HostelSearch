package com.androidexam.stayfinder.ui.chat.chatdetail;

import android.view.View;

import com.androidexam.stayfinder.data.models.firebase.Message;

public interface ItemChangeListener {
    public void seen(Message message);
    public void scrollRecyclerView(int position);
}
