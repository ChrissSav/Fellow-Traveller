package com.example.fellow_traveller.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        writeEdtText = findViewById(R.id.write_et_chat);
        borderOfEdtText = findViewById(R.id.border_of_et);
        plusButton = findViewById(R.id.plus_button_chat);
        sendButton = findViewById(R.id.send_chat);
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
    }

    private void sendMessage(int myId, int groupId, String msg) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Messages").child("7");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", myId);
        hashMap.put("groupId", groupId);
        hashMap.put("text", msg);
        hashMap.put("timestamp", System.currentTimeMillis()/1000);

        reference.push().setValue(hashMap);
    }

    public void readMessages(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages").child("7");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messagesList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MessageItem item = snapshot.getValue(MessageItem.class);

                    messagesList.add(item);
                    mAdapter.notifyDataSetChanged();
                }

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
