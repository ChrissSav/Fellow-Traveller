package com.example.fellow_traveller.Chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.MainActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private ArrayList<MessageItem> messagesList;
    private Context myContext;
    public static final int SEND_SINGLE = 0;
    public static final int SEND_TOP = 1;
    public static final int SEND_MIDDLE = 2;
    public static final int SEND_BOTTOM = 3;
    public static final int RECEIVE_SINGLE = 4;
    public static final int RECEIVE_TOP = 5;
    public static final int RECEIVE_MIDDLE = 6;
    public static final int RECEIVE_BOTTOM = 7;
    private GlobalClass globalClass;
    private int myId;


    public static class MessagesViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView name;
        public TextView date;


        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.message);
            name = itemView.findViewById(R.id.sender_message);
            date = itemView.findViewById(R.id.message_time);
        }
    }

    public MessagesAdapter(ArrayList<MessageItem> messList, Context context) {
        messagesList = messList;
        myContext = context;

    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SEND_SINGLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send_single, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        } else if (viewType == SEND_BOTTOM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send_bottom, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        } else if (viewType == SEND_MIDDLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send_middle, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        } else if (viewType == SEND_TOP) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send_top, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        }else if (viewType == RECEIVE_TOP) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_receive_top, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        } else if (viewType == RECEIVE_BOTTOM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_receive_bottom, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        }else if (viewType == RECEIVE_MIDDLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_receive_middle, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        }else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_receive_single, parent, false);
            MessagesViewHolder evh = new MessagesViewHolder(v);
            return evh;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessagesViewHolder  holder, int position) {
        MessageItem currentItem = messagesList.get(position);

        holder.text.setText(currentItem.getText());

        Date currentDate = new Date(currentItem.getTimestamp()*1000);
        DateFormat dateFormat = convertDateFormat(currentItem.getTimestamp()*1000);
        holder.date.setText(dateFormat.format(currentDate));


        //Retrieve the id of sender of the message, to get the sender's username
        DatabaseReference userName = FirebaseDatabase.getInstance().getReference("Users").child(Integer.toString(currentItem.getId()));

        userName.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nameFirebase = (String) dataSnapshot.child("name").getValue(String.class);
                holder.name.setText(nameFirebase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private DateFormat convertDateFormat(long myTimestamp) {

        //Get The year and date of our timestamp and from current year
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(myTimestamp);
        int compareYear = cal.get(Calendar.YEAR);
        int compareDay = cal.get(Calendar.DATE);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int thisDate = Calendar.getInstance().get(Calendar.DATE);

        DateFormat dateFormat;

        if(thisDate==compareDay){
            dateFormat = new SimpleDateFormat("h:mm a");
            return dateFormat;
        }else if(thisYear==compareYear){
            dateFormat = new SimpleDateFormat("EEE, d MMM - h:mm a");
            return dateFormat;
        }else{
            dateFormat = new SimpleDateFormat("d MMM yyyy - h:mm a");
            return dateFormat;
        }
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageItem curr = messagesList.get(position);
        int current = curr.getId();
        int currNext = -1;
        int currPrev = -1;

        //Get myId from Global Class
        globalClass = (GlobalClass) myContext.getApplicationContext();
        myId = globalClass.getCurrent_user().getId();
        //Initialize and check if we run out of the list
        if (position + 1 < messagesList.size()) {
            currNext = messagesList.get(position + 1).getId();
        }
        if (position - 1 > -1) {
            currPrev = messagesList.get(position - 1).getId();
        }
        //Finished initialization


        if (current == myId) {

            if ((currNext == current) && (current == currPrev)) {
                return SEND_MIDDLE;
            } else if ((current != currNext) && (current == currPrev)) {
                return SEND_BOTTOM;
            } else if ((current != currPrev) && (current == currNext)) {
                return SEND_TOP;
            } else { return SEND_SINGLE;
                }
        }else {

            if ((currNext == current) && (current == currPrev)) {
                return RECEIVE_MIDDLE;
            } else if ((current != currNext) && (current == currPrev)) {
                return RECEIVE_BOTTOM;
            } else if ((current != currPrev) && (current == currNext)) {
                return RECEIVE_TOP;
            } else {
                return RECEIVE_SINGLE;
            }
        }


    }
}
