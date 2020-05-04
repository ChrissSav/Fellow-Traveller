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

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.PassengersViewHolder>{
    private ArrayList<PassengerModel> passengersList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class PassengersViewHolder extends RecyclerView.ViewHolder{
        public TextView userName, rating, reviews, havePet, numOfBags;




        public PassengersViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.passenger_item_user_name);
            rating = itemView.findViewById(R.id.passenger_item_rating);
            reviews = itemView.findViewById(R.id.passenger_item_reviews);
            havePet = itemView.findViewById(R.id.passenger_item_have_pet);
            numOfBags = itemView.findViewById(R.id.passenger_item_number_of_bags);


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
    public PassengersAdapter(ArrayList<PassengerModel> aPassengerList){
        passengersList = aPassengerList;

    }

    @NonNull
    @Override
    public PassengersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, parent, false);
        PassengersViewHolder evh = new PassengersViewHolder (v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengersViewHolder holder, int position) {
        PassengerModel currentItem = passengersList.get(position);

        holder.userName.setText(currentItem.getUserName());
        holder.rate.setText(currentItem.getRate());
        holder.review.setText(currentItem.getReview());
        holder.from.setText(currentItem.getFrom());
        holder.to.setText(currentItem.getTo());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }
}
