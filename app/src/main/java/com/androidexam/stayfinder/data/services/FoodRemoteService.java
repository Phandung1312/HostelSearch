package com.androidexam.stayfinder.data.services;


import com.androidexam.stayfinder.data.repositories.FoodRepositories;
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
