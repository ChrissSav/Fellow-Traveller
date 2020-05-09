package com.example.fellow_traveller.Notification;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ExampleViewHolder> {

    private ArrayList<NotificationModel> mExampleList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private CircleImageView circleImageView;
        private TextView textViewDes,textViewRead,textViewTime;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.NotificationItem_ConstraintLayout);
            circleImageView = itemView.findViewById(R.id.NotificationItem_profile_picture);
            textViewDes = itemView.findViewById(R.id.NotificationItem_textView_des);
            textViewRead = itemView.findViewById(R.id.NotificationItem_textView_read);
            textViewTime=  itemView.findViewById(R.id.NotificationItem_textView_time);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public NotificationAdapter(ArrayList<NotificationModel> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        NotificationModel currentItem = mExampleList.get(position);
        holder.textViewDes.setText(currentItem.getUser().getFullName());
        if (currentItem.getHasRead()) {
            holder.textViewRead.setVisibility(View.GONE);
        }
        holder.textViewTime.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}