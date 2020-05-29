package com.example.fellow_traveller.Notification;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fellow_traveller.Util.SomeMethods.currentTimeStamp;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ExampleViewHolder> {

    private ArrayList<NotificationModel> mExampleList;
    private OnItemClickListener mListener;
    private Context context;


    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayout;
        private CircleImageView circleImageView;
        private TextView textViewDes, textViewRead, textViewTime;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.NotificationItem_ConstraintLayout);
            circleImageView = itemView.findViewById(R.id.NotificationItem_profile_picture);
            textViewDes = itemView.findViewById(R.id.NotificationItem_textView_des);
            textViewRead = itemView.findViewById(R.id.NotificationItem_textView_read);
            textViewTime = itemView.findViewById(R.id.NotificationItem_textView_time);
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
        this.context = v.getContext();
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        NotificationModel currentItem = mExampleList.get(position);

        if(currentItem.getUser().getPicture() != null && currentItem.getUser().getPicture().length() > 1){
            Picasso.get().load(currentItem.getUser().getPicture()).into(holder.circleImageView);
        }

        if (currentItem.getTypeOf().equals("passenger")) {
            holder.textViewDes.setText(context.getResources().getString(R.string.passenger_notification).replace("%", currentItem.getUser().getFullName()));

        } else {
            holder.textViewDes.setText(context.getResources().getString(R.string.rate_notification).replace("%", currentItem.getUser().getFullName()));
        }
        if (currentItem.getHasRead()) {
            holder.textViewRead.setVisibility(View.GONE);
        }
        String label;
        Long timeDifferenceMinutes = (currentTimeStamp() - currentItem.getTimestamp()) / (60);
        if (timeDifferenceMinutes < 60) {
            label = " λεπτά";
            if (timeDifferenceMinutes == 1)
                label = " λεπτό";
            holder.textViewTime.setText("πριν από " + timeDifferenceMinutes + label);
            return;
        }
        if (timeDifferenceMinutes < (60 * 24)) {
            label = " ώρες";
            if (timeDifferenceMinutes / 60 == 1)
                label = " ώρα";
            holder.textViewTime.setText("πριν από " + timeDifferenceMinutes / 60 + label);
            return;
        }
        if (timeDifferenceMinutes < (60 * 24 * 7)) {
            label = " μέρες";
            if ((timeDifferenceMinutes / (60 * 24)) == 1)
                label = " μέρα";
            holder.textViewTime.setText("πριν από " + timeDifferenceMinutes / (60 * 24) + label);
            return;
        }
        holder.textViewTime.setText(currentItem.getDate() + " στις " + currentItem.getTime());


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


}