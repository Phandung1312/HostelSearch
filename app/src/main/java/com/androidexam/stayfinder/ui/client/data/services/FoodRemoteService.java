package com.androidexam.stayfinder.ui.client.data.services;


import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

//Example
public class FoodRemoteService {
    FirebaseDatabase firebaseDatabase;
    @Inject
    public FoodRemoteService(FirebaseDatabase firebaseDatabase){
        this.firebaseDatabase = firebaseDatabase;
    }

}
