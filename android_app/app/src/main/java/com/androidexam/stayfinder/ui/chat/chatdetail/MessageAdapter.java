package com.androidexam.stayfinder.ui.chat.chatdetail;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.BR;
import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.databinding.ItemsChatLeftBinding;
import com.androidexam.stayfinder.databinding.ItemsChatRightBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder> {
    static final int SENDER_TYPE = 0; // FOR ME
    static final int RECEIVER_TYPE = 1; // FOR RECEIVER
    UserFirebase sender;
    UserFirebase receiver;
    ItemChangeListener itemChangeListener;
    public MessageAdapter(@NonNull FirebaseRecyclerOptions<Message> options,
                          UserFirebase sender,
                          UserFirebase receiver,
                          ItemChangeListener itemChangeListener) {
        super(options);
        this.sender = sender;
        this.receiver = receiver;
        this.itemChangeListener = itemChangeListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageViewHolder holder, int position, @NonNull Message model) {
        holder.binding.setVariable(BR.message, model);
        if(model.getSender().equals(sender.getId())){
            holder.binding.setVariable(BR.user,sender);
        }
        else{
            holder.binding.setVariable(BR.user,receiver);
        }
        if(position == getItemCount() - 1){
            holder.binding.setVariable(BR.seen, model.isSeen());
            if(model.getSender().equals(receiver.getId())){
                itemChangeListener.seen(model);
            }
        }
        else{
            holder.binding.setVariable(BR.seen, null);
        }
        holder.binding.executePendingBindings();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        itemChangeListener.scrollRecyclerView(getItemCount());
        this.notifyItemChanged(getItemCount() -2 );
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == RECEIVER_TYPE){
            ItemsChatLeftBinding binding = ItemsChatLeftBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MessageViewHolder(binding);
        } else if(viewType == SENDER_TYPE){
            ItemsChatRightBinding binding = ItemsChatRightBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MessageViewHolder(binding);
        }
        return null;
    }
    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals(sender.getId(),getItem(position).getSender())){
            return SENDER_TYPE;
        }
        else{
            return RECEIVER_TYPE;
        }
    }
    static class MessageViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;
        public MessageViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
