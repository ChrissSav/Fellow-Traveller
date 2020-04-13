package com.example.fellow_traveller.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchResultsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>{
    private ArrayList<ConversationItem> conversationList;
    private ConversationAdapter.OnItemClickListener mListener;
    String theLastMessage;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ConversationAdapter.OnItemClickListener listener){
        mListener = listener;
    }
    public static class ConversationViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView description;
        public TextView date;


        public ConversationViewHolder(@NonNull View itemView, final ConversationAdapter.OnItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.name_chat);
            description = itemView.findViewById(R.id.description_chat);
            date = itemView.findViewById(R.id.message_date_chat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public ConversationAdapter(ArrayList<ConversationItem> convList){
        conversationList = convList;

    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_item, parent, false);
        ConversationViewHolder evh = new ConversationViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        ConversationItem currentItem = conversationList.get(position);
        holder.userName.setText(currentItem.getTripName());

        //Call the method to get the last message and we parse the Trip Id to retrieve the messages and the TextView to set the text
        lastMessage(Integer.toString(currentItem.getTripId()), holder.description);



        Date currentDate = new Date(currentItem.getDate()*1000);
        DateFormat dateFormatFinal =  convertDateFormat(currentItem.getDate()*1000);
        holder.date.setText(dateFormatFinal.format(currentDate));
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
        }else if(compareYear==thisYear){
            dateFormat = new SimpleDateFormat(" EEE, d MMM");
            return dateFormat;
        }else{
            dateFormat = new SimpleDateFormat("d MMM yyyy");
            return dateFormat;
        }
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    //Check for the last message
    private void lastMessage(String groupId, final TextView last_message ) {

        theLastMessage = "default";
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Messages").child(groupId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MessageItem item = snapshot.getValue(MessageItem.class);
                    theLastMessage = item.getText();
                }


                switch (theLastMessage){
                    case "default":
                        last_message.setText("Δεν υπάρχει κάποιο μήνυμα");
                        break;

                    default:
                        last_message.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}
