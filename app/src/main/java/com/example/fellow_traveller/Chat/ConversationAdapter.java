package com.example.fellow_traveller.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchResultsAdapter;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>{
    private ArrayList<ConversationItem> conversationList;
    private ConversationAdapter.OnItemClickListener mListener;

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
        holder.description.setText(currentItem.getDescription());


        Date currentDate = new Date(currentItem.getDate()*1000);
        DateFormat dateFormat =  convertDateFormat(currentItem.getDate()*1000);
        holder.date.setText(dateFormat.format(currentDate));
//        Long.toString(currentItem.getDate())
    }
    private DateFormat convertDateFormat(long myTimestamp) {
        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;
        final int YEAR_MILLIS = 365 * HOUR_MILLIS;

        long now = System.currentTimeMillis();
        final long diff = now - myTimestamp;

        DateFormat dateFormat;
        if(diff<24*HOUR_MILLIS){
            dateFormat = new SimpleDateFormat("h:mm a");
            return dateFormat;
        }else if(diff<YEAR_MILLIS){
            dateFormat = new SimpleDateFormat("EEE, d/M");
            return dateFormat;
        }else{
            dateFormat = new SimpleDateFormat(" d/M/yyyy");
            return dateFormat;
        }
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }
}
