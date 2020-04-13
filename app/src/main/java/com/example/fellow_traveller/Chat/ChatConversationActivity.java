package com.example.fellow_traveller.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.MessagesNotification.APIService;
import com.example.fellow_traveller.MessagesNotification.Client;
import com.example.fellow_traveller.MessagesNotification.Data;
import com.example.fellow_traveller.MessagesNotification.MyResponse;
import com.example.fellow_traveller.MessagesNotification.Sender;
import com.example.fellow_traveller.MessagesNotification.Token;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatConversationActivity extends AppCompatActivity {
    private EditText writeEdtText;
    private ConstraintLayout borderOfEdtText;
    private ImageButton plusButton, sendButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<MessageItem> messagesList = new ArrayList<>();
    private ArrayList<Integer> convParticipantsId  = new ArrayList<>();
    private static final int TOTAL_ITEMS_TO_LOAD = 20;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private String lastKey = "";
    private String prevKey = "";
    private SwipeRefreshLayout mRefreshLayout;
    APIService apiService;
    boolean notify = false;
    private GlobalClass globalClass;
    private int myId;
    private int groupId;
    private boolean updateStatus = false;
    ValueEventListener seenListener, updateListener;
    DatabaseReference reference, referenceStatus ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        writeEdtText = findViewById(R.id.write_et_chat);
        borderOfEdtText = findViewById(R.id.border_of_et);
        plusButton = findViewById(R.id.plus_button_chat);
        sendButton = findViewById(R.id.send_chat);
        mRefreshLayout = findViewById(R.id.swipe_refresh_chat_conversation);


        //Retrieve current user's id
        globalClass = (GlobalClass) getApplicationContext();
        myId = globalClass.getCurrent_user().getId();

        //Retrieve groupChat id
        Intent intent = getIntent();
        groupId = intent.getIntExtra("groupId", 0);

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        mRecyclerView = findViewById(R.id.messages_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessagesAdapter(messagesList, this.getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        readMessages();
        if(!updateStatus){
            updateSeenStatus(Integer.toString(myId),Integer.toString(groupId));
            updateStatus = false;

        }

        getAllParticipantsId(Integer.toString(groupId));


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String message = writeEdtText.getText().toString();

                if(!message.trim().isEmpty()) {
                    sendMessage(myId, groupId, message);
                    writeEdtText.setText("");
                }
            }
        });

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage++; //increase the number of loading messages when we swipe
                itemPos = 0;
                readMoreMessages();
            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendMessage(int myId, int groupId, String message) {
        //We send message to the id which we putted extra from conversation list

        DatabaseReference referenceMessage = FirebaseDatabase.getInstance().getReference().child("Messages").child(Integer.toString(groupId));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", myId);
        hashMap.put("groupId", groupId);
        hashMap.put("text", message);
        hashMap.put("timestamp", System.currentTimeMillis()/1000);
        hashMap.put("senderName", globalClass.getCurrent_user().getName());

        referenceMessage.push().setValue(hashMap);

        //To who we sent notification
        final String msg = message;
        if (notify) {
            sendNotification(Integer.toString(groupId), globalClass.getCurrent_user().getName(), msg, groupId);
        }
        notify = false;
        updateParticipantsInfo(Integer.toString(groupId));
    }




    private void sendNotification(final String  receiver, final String username, final String message, final Integer groupId ){

        //WARNING HAVE TO CHANGE THE SENTET FROM 1 TO ANOTHER ID

        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(Integer.toString(myId), R.drawable.ic_logo, username + ": " + message, "Νέο μήνυμα", receiver, Integer.toString(groupId));

                    Sender sender = new Sender(data, token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code() == 200){
                                        if(response.body().success != 1){
                                            Toast.makeText(ChatConversationActivity.this, "Απέτυχε", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void readMessages(){
        //Bring the messages only for trip with id 7.. When we click the conversations list puts extra the trip id
        DatabaseReference  messageRef = FirebaseDatabase.getInstance().getReference("Messages").child(Integer.toString(groupId));
        Query messageQuery = messageRef.limitToLast(mCurrentPage*TOTAL_ITEMS_TO_LOAD);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageItem item = dataSnapshot.getValue(MessageItem.class);
                //The key at the top of the list its time, we storing his message key to continue loading from this key and above
                itemPos++;
                if(itemPos==1){
                    String messageKey = dataSnapshot.getKey();
                    lastKey = messageKey;
                    prevKey = messageKey;
                }

                messagesList.add(item);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(messagesList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readMoreMessages(){

        DatabaseReference  messageRef = FirebaseDatabase.getInstance().getReference("Messages").child(Integer.toString(groupId));
        Query messageQuery = messageRef.orderByKey().endAt(lastKey).limitToLast(20);

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageItem item = dataSnapshot.getValue(MessageItem.class);

                String messageKey = dataSnapshot.getKey();

                if(!prevKey.equals(messageKey)){
                    messagesList.add(itemPos++, item);
                }else{
                    prevKey = lastKey;
                }

                if(itemPos==1){
                    lastKey = messageKey;
                }

                mAdapter.notifyDataSetChanged();
                mLayoutManager.scrollToPositionWithOffset(18,0);

                mRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateToken(String token){

        DatabaseReference referenceTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        referenceTokens.child(Integer.toString(myId)).setValue(token1);

    }
    private void updateSeenStatus(String myId, String groupId){
        reference = FirebaseDatabase.getInstance().getReference("Trips").child(myId).child(groupId);
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("seen", true);
                dataSnapshot.getRef().updateChildren(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void getAllParticipantsId(String grouId){

        convParticipantsId.clear();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TripsAndParticipants").child(grouId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    int aId = snapshot.child("userId").getValue(Integer.class);
                    convParticipantsId.add(aId);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//    private void updateParticipantConvInfo(String groupId){
//        for(int  i=0; i < convParticipantsId.size(); i++) {
//            referenceStatus = FirebaseDatabase.getInstance().getReference("Trips").child(Integer.toString(convParticipantsId.get(i))).child(groupId);
//            if(convParticipantsId.get(i) != myId) {
//
//              referenceStatus.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        HashMap<String, Object> hashMap = new HashMap<>();
//                        hashMap.put("seen", false);
//                        dataSnapshot.getRef().updateChildren(hashMap);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//
//
//            referenceStatus.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    HashMap<String, Object> hashMap = new HashMap<>();
//                    hashMap.put("date", System.currentTimeMillis() / 1000);
//                    dataSnapshot.getRef().updateChildren(hashMap);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
//
//    }
    private void updateParticipantsInfo(String groupId){
        for(int  i=0; i < convParticipantsId.size(); i++) {
            referenceStatus = FirebaseDatabase.getInstance().getReference("Trips").child(Integer.toString(convParticipantsId.get(i))).child(groupId);
            if(convParticipantsId.get(i) != myId) {

                referenceStatus.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("seen", false);
                        dataSnapshot.getRef().updateChildren(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            referenceStatus.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("date", System.currentTimeMillis() / 1000);
                    dataSnapshot.getRef().updateChildren(hashMap);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        writeEdtText.clearFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);

    }
}
