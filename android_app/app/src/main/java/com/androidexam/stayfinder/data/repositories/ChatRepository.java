package com.androidexam.stayfinder.data.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.data.models.firebase.Message;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ChatRepository {
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    @Inject
    public ChatRepository(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        this.firebaseDatabase = firebaseDatabase;
        this.mAuth = firebaseAuth;
    }
    public MutableLiveData<UserFirebase> getUserById(String id){
        MutableLiveData<UserFirebase> userLiveData = new MutableLiveData<>();
        DatabaseReference reference = firebaseDatabase.getReference().child("User").child(id);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserFirebase user = snapshot.getValue(UserFirebase.class);
                if(user != null){
                    userLiveData.postValue(user);
                }
                else{
                    Log.d("Error","User not found by Id");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","DatabaseError");
            }
        };
        reference.addValueEventListener(listener);
        return userLiveData;
    }
    public MutableLiveData<UserFirebase> getCurrentUser(){
        String id = mAuth.getCurrentUser().getUid();
        return getUserById(id);
    }
    public void sendMessage(String ownerId,String participantId,Message message){
        firebaseDatabase.getReference().child("Chats")
                .child(ownerId).child(participantId).push().setValue(message);
    }
    public DatabaseReference getReferenceChatSender(String ownerId, String participantId){
        return firebaseDatabase.getReference().child("Chats").child(ownerId).child(participantId);
    }
    public MutableLiveData<List<String>> getListIdUser(String id){
        MutableLiveData<List<String>> listId = new MutableLiveData<>();
        DatabaseReference reference = firebaseDatabase.getReference().child("Chats").child(id);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> ids = new ArrayList<>();
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String childKey = childSnapshot.getKey();
                    ids.add(childKey);
                }
                listId.postValue(ids);
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","DatabaseError");
            }
        };
        reference.addValueEventListener(listener);
        return listId;
    }
    public void seenMessage(String sender,String receiver){
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Chats")
                .child(receiver).child(sender);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    Message message = ds.getValue(Message.class);
                    if(message != null){
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("seen", true);
                        ds.getRef().updateChildren(hashMap);
                    }
                    databaseReference.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public MutableLiveData<Message> getLastMessage(String myId, String friendId){
        MutableLiveData<Message> messageLiveData = new MutableLiveData<>();
        Query query = firebaseDatabase.getReference().child("Chats")
                .child(myId).child(friendId).orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Message message = null;
                for(DataSnapshot ds : snapshot.getChildren()){
                    message = ds.getValue(Message.class);
                }
                if(message != null){
                    messageLiveData.postValue(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return messageLiveData;
    }
}
