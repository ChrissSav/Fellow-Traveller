package com.example.fellow_traveller.Notification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fellow_traveller.R;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ExampleViewHolder> {

    private ArrayList<NotificationItem> mExampleList;
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
        private TextView textView;



        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.NotificationItem_ConstraintLayout);
            circleImageView = itemView.findViewById(R.id.NotificationItem_profile_picture);
            textView = itemView.findViewById(R.id.NotificationItem_textView_des);

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

    public NotificationAdapter(ArrayList<NotificationItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        NotificationItem currentItem = mExampleList.get(position);
        holder.textView.setText(currentItem.getUser().getFullName());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}