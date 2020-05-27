package com.example.fellow_traveller.Trips;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchResultsAdapter;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_trip_layout_item, parent, false);
        ActiveTripsViewHolder evh = new ActiveTripsViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveTripsViewHolder holder, int position) {

        TripInvolvedModel currentItem = activeTripsList.get(position);

        //Destination description
        holder.destinations.setText(currentItem.getDestFrom().getTitle() + " - " + currentItem.getDestTo().getTitle());
        // TODO cast this to double
        //Seats Availability
        int seatsAvailable = currentItem.getMaxSeats() - currentItem.getCurrentSeats();
        if(seatsAvailable == 0)
            holder.seats.setText("Δεν υπάρχουν ελεύθερες θέσεις");
        else if(seatsAvailable == 1)
            holder.seats.setText("Υπάρχει 1 ελεύθερη θέση");
        else
            holder.seats.setText("Υπάρχουν " + String.valueOf(seatsAvailable) + " ελεύθερες θέσεις");

        //Bags Availability
        int bagsAvailable = currentItem.getMaxBags() - currentItem.getCurrentBags();
        if(bagsAvailable == 0)
            holder.bags.setText("Δεν υπάρχει άλλος χώρος για αποσκευές");
        else if (bagsAvailable == 1)
            holder.bags.setText("Υπάρχει χώρος για 1 αποσκευή ακόμα");
        else
            holder.bags.setText("Υπάρχει χώρος για " + String.valueOf(bagsAvailable) + " αποσκευές ακόμα");



        //Date
        holder.date.setText(currentItem.getDate());
        //Time
        holder.time.setText(currentItem.getTime());
        //Price
        //Delete the 0 decimals
        if(currentItem.getPrice().intValue() == currentItem.getPrice())
            holder.price.setText(currentItem.getPrice().intValue() + "€");
        else
            holder.price.setText(currentItem.getPrice()+ "€");

    }

    @Override
    public int getItemCount() {
        return activeTripsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ActiveTripsViewHolder extends RecyclerView.ViewHolder {
        public TextView destinations, seats, bags, date, time, price;


        public ActiveTripsViewHolder(@NonNull View itemView, final ActiveTripsAdapter.OnItemClickListener listener) {
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
