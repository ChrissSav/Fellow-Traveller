package com.example.fellow_traveller.Trips;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchResultsAdapter;

import java.util.ArrayList;

public class CompletedTripsAdapter extends RecyclerView.Adapter<CompletedTripsAdapter.CompletedTripsViewHolder> {
    private ArrayList<TripModel> completedTripsList;
    private CompletedTripsAdapter.OnItemClickListener mListener;

    public CompletedTripsAdapter(ArrayList<TripModel> tripsList) {
        completedTripsList = tripsList;

    }

    public void setOnItemClickListener(CompletedTripsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CompletedTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_trip_layout_item, parent, false);
        CompletedTripsViewHolder evh = new CompletedTripsViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTripsViewHolder holder, int position) {
        TripModel currentItem = completedTripsList.get(position);

        holder.destinations.setText(currentItem.getDestFrom().getTitle() + " - " + currentItem.getDestTo().getTitle());
        // TODO cast this to double
        holder.seats.setText(currentItem.getSeatStatus());
        holder.bags.setText(currentItem.getBagsStatus());


        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        //Delete the 0 decimals
        if(currentItem.getPrice().intValue() == currentItem.getPrice())
            holder.price.setText(currentItem.getPrice().intValue() + "€");
        else
            holder.price.setText(currentItem.getPrice()+ "€");

    }

    @Override
    public int getItemCount() {
        return completedTripsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CompletedTripsViewHolder extends RecyclerView.ViewHolder {
        public TextView destinations, seats, bags, date, time, price;


        public CompletedTripsViewHolder(@NonNull View itemView, final CompletedTripsAdapter.OnItemClickListener listener) {
            super(itemView);

            destinations = itemView.findViewById(R.id.fragment_trip_layout_item_destination);
            seats = itemView.findViewById(R.id.fragment_trip_layout_item_seats);
            bags = itemView.findViewById(R.id.fragment_trip_layout_item_bags);
            date = itemView.findViewById(R.id.fragment_trip_layout_item_date_textView);
            time = itemView.findViewById(R.id.fragment_trip_layout_item_time_textView);
            price = itemView.findViewById(R.id.fragment_trip_layout_item_price_textView);
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
