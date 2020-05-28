package com.example.fellow_traveller.Trips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ActiveViewHolder>{


    private ArrayList<TripInvolvedModel> trips;

    public ActiveAdapter(ArrayList<TripInvolvedModel> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public ActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.fragment_trip_layout_item,parent,false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveViewHolder holder, int position) {
        holder.setData( trips.get( position ) );
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class ActiveViewHolder extends RecyclerView.ViewHolder{
        public TextView destinations, seats, bags, date, time, price;

        public ActiveViewHolder(@NonNull View itemView) {
            super( itemView );
            this.destinations = itemView.findViewById( R.id.fragment_trip_layout_item_destination);
            this.seats = itemView.findViewById(R.id.fragment_trip_layout_item_seats);
            this.bags = itemView.findViewById(R.id.fragment_trip_layout_item_bags);
            this.date = itemView.findViewById(R.id.fragment_trip_layout_item_date_textView);
            this.time = itemView.findViewById(R.id.fragment_trip_layout_item_time_textView);
            this.price = itemView.findViewById(R.id.fragment_trip_layout_item_price_textView);
        }

        void setData(TripInvolvedModel currentItem){
            //Destination description
            destinations.setText(currentItem.getDestFrom().getTitle() + " - " + currentItem.getDestTo().getTitle());
            // TODO cast this to double
            //Seats Availability
            int seatsAvailable = currentItem.getMaxSeats() - currentItem.getCurrentSeats();
            if(seatsAvailable == 0)
                seats.setText("Δεν υπάρχουν ελεύθερες θέσεις");
            else if(seatsAvailable == 1)
                seats.setText("Υπάρχει 1 ελεύθερη θέση");
            else
                seats.setText("Υπάρχουν " + String.valueOf(seatsAvailable) + " ελεύθερες θέσεις");

            //Bags Availability
            int bagsAvailable = currentItem.getMaxBags() - currentItem.getCurrentBags();
            if(bagsAvailable == 0)
                bags.setText("Δεν υπάρχει άλλος χώρος για αποσκευές");
            else if (bagsAvailable == 1)
                bags.setText("Υπάρχει χώρος για 1 αποσκευή ακόμα");
            else
                bags.setText("Υπάρχει χώρος για " + String.valueOf(bagsAvailable) + " αποσκευές ακόμα");



            //Date
            date.setText(currentItem.getDate());
            //Time
            time.setText(currentItem.getTime());
            //Price
            //Delete the 0 decimals
            if(currentItem.getPrice().intValue() == currentItem.getPrice())
                price.setText(currentItem.getPrice().intValue() + "€");
            else
                price.setText(currentItem.getPrice()+ "€");

        }

    }

}