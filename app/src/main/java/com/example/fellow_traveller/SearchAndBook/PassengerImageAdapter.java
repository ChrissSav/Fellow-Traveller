package com.example.fellow_traveller.SearchAndBook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PassengerImageAdapter extends RecyclerView.Adapter<PassengerImageAdapter.PassengerImageViewHolder>{
    private ArrayList<PassengerModel> passengersList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class PassengerImageViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView userImage;
        public TextView userName;





        public PassengerImageViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.passenger_image_item_username);
           userImage = itemView.findViewById(R.id.passenger_image_item_image);


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
    public PassengerImageAdapter(ArrayList<PassengerModel> aPassengerList){
        passengersList = aPassengerList;

    }

    @NonNull
    @Override
    public PassengerImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_image_item, parent, false);
        PassengerImageViewHolder evh = new PassengerImageViewHolder (v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerImageViewHolder holder, int position) {
        PassengerModel currentItem = passengersList.get(position);

        holder.userName.setText(currentItem.getUser().getFirstName());

    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }
}