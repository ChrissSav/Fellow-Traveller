package com.example.fellow_traveller.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fellow_traveller.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>{
    private ArrayList<ConversationItem> conversationList;
    public static class ConversationViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView description;
        public TextView date;


        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.name_chat);
            description = itemView.findViewById(R.id.description_chat);
            date = itemView.findViewById(R.id.message_date_chat);
        }
    }
    public ConversationAdapter(ArrayList<ConversationItem> convList){
        conversationList = convList;

    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_item, parent, false);
        ConversationViewHolder evh = new ConversationViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        ConversationItem currentItem = conversationList.get(position);
        holder.userName.setText(currentItem.getTripName());
        holder.description.setText(currentItem.getDescription());
        holder.date.setText(Long.toString(currentItem.getDate()));
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }
}
