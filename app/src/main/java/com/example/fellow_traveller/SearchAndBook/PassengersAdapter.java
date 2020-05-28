package com.example.fellow_traveller.SearchAndBook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.PassengersViewHolder>{
    private ArrayList<PassengerModel> passengersList;
    private OnItemClickListener mListener;
    private Context myContext;
    private GlobalClass globalClass;
    private int myId;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public static class PassengersViewHolder extends RecyclerView.ViewHolder{
        public TextView userName, rating, reviews, havePet, numOfBags;
        public CircleImageView userImage;




        public PassengersViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            userName = itemView.findViewById(R.id.passenger_item_user_name);
            rating = itemView.findViewById(R.id.ActivitySearchDetails_rating_tv);
            reviews = itemView.findViewById(R.id.ActivitySearchDetails_reviews_tv);
            havePet = itemView.findViewById(R.id.passenger_item_have_pet);
            numOfBags = itemView.findViewById(R.id.passenger_item_number_of_bags);
            userImage = itemView.findViewById(R.id.passenger_item_user_image);


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
    public PassengersAdapter(ArrayList<PassengerModel> aPassengerList, Context context){
        passengersList = aPassengerList;
        myContext = context;

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

        //Get user's id
        int userId = currentItem.getUser().getId();
        //Get myId from Global Class
        globalClass = (GlobalClass) myContext.getApplicationContext();
        myId = globalClass.getCurrentUser().getId();
        if(myId == userId)
            holder.userName.setText("(Εσείς) " + currentItem.getUser().getFullName());
        else
            holder.userName.setText(currentItem.getUser().getFullName());

        holder.rating.setText(String.valueOf(currentItem.getUser().getRate()));
        holder.reviews.setText(String.valueOf(currentItem.getUser().getReviews()));
        if(currentItem.getPet())
            holder.havePet.setText("Ναι");
        else
            holder.havePet.setText("Όχι");
        holder.numOfBags.setText(String.valueOf(currentItem.getBags()));
        if(currentItem.getUser().getPicture() != null)
            Picasso.get().load(currentItem.getUser().getPicture()).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }
}
