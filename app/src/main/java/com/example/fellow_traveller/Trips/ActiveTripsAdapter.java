package com.example.fellow_traveller.Trips;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ActiveTripsAdapter extends RecyclerView.Adapter<ActiveTripsAdapter.ActiveTripsViewHolder> {
    private ArrayList<TripInvolvedModel> activeTripsList;
    private ActiveTripsAdapter.OnItemClickListener mListener;

    public ActiveTripsAdapter(ArrayList<TripInvolvedModel> tripsList) {
        activeTripsList = tripsList;

    }

    public void setOnItemClickListener(ActiveTripsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ActiveTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_trip_item_layout, parent, false);
        ActiveTripsViewHolder evh = new ActiveTripsViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveTripsViewHolder holder, int position) {

        TripInvolvedModel currentItem = activeTripsList.get(position);

        holder.destination.setText(currentItem.getDestFrom().getTitle() + " - " + currentItem.getDestTo().getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
        if((Math.round(currentItem.getPrice())) == currentItem.getPrice())
            holder.price.setText(Math.round(currentItem.getPrice()) + "€");
        else
            holder.price.setText(currentItem.getPrice() + "€");

        holder.name.setText(currentItem.getCreatorUser().getFullName());
        holder.rate.setText(currentItem.getCreatorUser().getRate() + " (" + currentItem.getCreatorUser().getReviews() + ")");

        try {
            Picasso.get().load(currentItem.getCreatorUser().getPicture()).into(holder.creatorImage);
        } catch (Exception e) {
            //  Block of code to handle errors
        }

        // TODO cast this to double


    }

    @Override
    public int getItemCount() {
        return activeTripsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ActiveTripsViewHolder extends RecyclerView.ViewHolder {
        public TextView destination, name, rate, date, time, price, bookDetails;
        public CircleImageView creatorImage;


        public ActiveTripsViewHolder(@NonNull View itemView, final ActiveTripsAdapter.OnItemClickListener listener) {
            super(itemView);

            destination = itemView.findViewById(R.id.Active_trip_layout_item_destinations_TextView);
            name = itemView.findViewById(R.id.Active_trip_layout_item_creator_name);
            rate = itemView.findViewById(R.id.Active_trip_layout_item_creator_rate_and_reviews);
            date = itemView.findViewById(R.id.Active_trip_layout_item_date_textView);
            time = itemView.findViewById(R.id.Active_trip_layout_item_time_textView);
            price = itemView.findViewById(R.id.Active_trip_layout_item_price_textView);
            bookDetails = itemView.findViewById(R.id.Active_trip_layout_item_my_book_details_textView);
            creatorImage = itemView.findViewById(R.id.Active_trip_layout_item_creator_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
}
