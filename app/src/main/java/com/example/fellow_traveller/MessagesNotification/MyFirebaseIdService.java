package com.example.fellow_traveller.MessagesNotification;

import com.example.fellow_traveller.Models.GlobalClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {
    private GlobalClass globalClass;
    private int myId;



    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //Retrieve current user's id
        globalClass = (GlobalClass) getApplicationContext();
        myId = globalClass.getCurrent_user().getId();

        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if(Integer.toString(myId)!=null){
            updateToken(refreshToken);
        }
    }

    private void updateToken(String refreshToken){
        //Retrieve current user's id
        globalClass = (GlobalClass) getApplicationContext();
        myId = globalClass.getCurrent_user().getId();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(refreshToken);
        reference.child(Integer.toString(myId)).setValue(token);

    }
}
