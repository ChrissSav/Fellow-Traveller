package com.example.fellow_traveller.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatConversationActivity extends AppCompatActivity {
    private EditText writeEdtText;
    private ConstraintLayout borderOfEdtText;
    private ImageButton plusButton, sendButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MessageItem> messagesList = new ArrayList<>();
    private static final int TOTAL_ITEMS_TO_LOAD = 20;
    private int mCurrentPage = 1;
    private int itemPos = 0;
    private String lastKey = "";
    private String prevKey = "";
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        writeEdtText = findViewById(R.id.write_et_chat);
        borderOfEdtText = findViewById(R.id.border_of_et);
        plusButton = findViewById(R.id.plus_button_chat);
        sendButton = findViewById(R.id.send_chat);
        mRefreshLayout = findViewById(R.id.swipe_refresh_chat_conversation);
        //ArrayList<MessageItem> messagesList = new ArrayList<>();
//        messagesList.add(new MessageItem(1,"Επππsdadadsadad","George"));
//        messagesList.add(new MessageItem(1,"Εππsdsdassπ","George"));
//        messagesList.add(new MessageItem(1,"Επdadsdasdasππ","George"));
//        messagesList.add(new MessageItem(1,"Εππsadadsasdπ","George"));
//        messagesList.add(new MessageItem(1,"Εππsdadsdπ","George"));
//        messagesList.add(new MessageItem(2,"Τdadsadι λέεfι das","Manthos"));
//        messagesList.add(new MessageItem(2,"Τι λέει τasdadadfsafσακάλια","Manthos"));
//        messagesList.add(new MessageItem(3,"Που στε σκύλοιιι;;;","Neoklis"));
//        messagesList.add(new MessageItem(1,"Εππsdaasdasdsdπ","George"));
//        messagesList.add(new MessageItem(4,"Πάμεε Σdasdadsdaαλόνικαα;","Φάνος"));
//        messagesList.add(new MessageItem(4,"Πάμεε Σαλdasdadaόνικαα;","Φάνος"));
//        messagesList.add(new MessageItem(4,"Πάμεε dadsadads;","Φάνος"));
//        messagesList.add(new MessageItem(1,"Εππsadadsasdπ","George"));
//        messagesList.add(new MessageItem(1,"Εππsdsadafsadadsasdπ","George"));


        mRecyclerView = findViewById(R.id.messages_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessagesAdapter(messagesList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        readMessages();

        writeEdtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
               borderOfEdtText.setBackgroundResource( R.drawable.write_message_et_black_chat);
                plusButton.setBackgroundTintList(getResources().getColorStateList(R.color.profile));
                sendButton.setBackgroundTintList(getResources().getColorStateList(R.color.profile));
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = writeEdtText.getText().toString();

                if(!message.trim().isEmpty()) {
                    sendMessage(9,7, message);
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
    }

    private void sendMessage(int myId, int groupId, String msg) {
        //We send message to the id which we putted extra from conversation list

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Messages").child("7");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", myId);
        hashMap.put("groupId", groupId);
        hashMap.put("text", msg);
        hashMap.put("timestamp", System.currentTimeMillis()/1000);

        reference.push().setValue(hashMap);
    }

    public void readMessages(){
        //Bring the messages only for trip with id 7.. When we click the conversations list puts extra the trip id
        DatabaseReference  messageRef = FirebaseDatabase.getInstance().getReference("Messages").child("7");
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

        DatabaseReference  messageRef = FirebaseDatabase.getInstance().getReference("Messages").child("7");
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
                mRecyclerView.scrollBy(20,0);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        writeEdtText.clearFocus();
    }
}
