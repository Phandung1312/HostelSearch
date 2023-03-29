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
import com.google.firebase.database.ValueEventListener;

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
        firebaseDatabase.getReference().child("Chats").child(ownerId).push().setValue(message);
        final DatabaseReference reference = firebaseDatabase.getReference("ChatsList").child(ownerId).child(participantId);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            reference.child("id").setValue(participantId);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    public DatabaseReference getReferenceChatSender(String id){
        return firebaseDatabase.getReference().child("Chats").child(id);
    }
}
