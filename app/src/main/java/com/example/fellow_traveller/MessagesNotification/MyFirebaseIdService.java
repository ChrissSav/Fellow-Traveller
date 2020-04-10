package com.example.fellow_traveller.MessagesNotification;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String myId = "1";
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        if(myId!=null){
            updateToken(refreshToken);
        }
    }

    private void updateToken(String refreshToken){
        String myId = "1";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(refreshToken);
        reference.child(myId).setValue(token);

    }
}
