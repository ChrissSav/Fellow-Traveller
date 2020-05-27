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

public class CompletedTripsAdapter extends RecyclerView.Adapter<CompletedTripsAdapter.CompletedTripsViewHolder> {
    private ArrayList<TripInvolvedModel> completedTripsList;
    private CompletedTripsAdapter.OnItemClickListener mListener;

    public CompletedTripsAdapter(ArrayList<TripInvolvedModel> tripsList) {
        completedTripsList = tripsList;

    }

    public void setOnItemClickListener(CompletedTripsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CompletedTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_trip_completed_layout_item, parent, false);
        CompletedTripsViewHolder evh = new CompletedTripsViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTripsViewHolder holder, int position) {
        TripInvolvedModel currentItem = completedTripsList.get(position);

        holder.startDate.setText(currentItem.getDestFrom().getTitle());
        holder.endDate.setText(currentItem.getDestTo().getTitle());
        // TODO cast this to double

        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());



    }

    @Override
    public int getItemCount() {
        return completedTripsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class CompletedTripsViewHolder extends RecyclerView.ViewHolder {
        public TextView startDate, endDate, date, time;


        public CompletedTripsViewHolder(@NonNull View itemView, final CompletedTripsAdapter.OnItemClickListener listener) {
            super(itemView);

            startDate = itemView.findViewById(R.id.fragment_trip_completed_dest_from);
            endDate = itemView.findViewById(R.id.fragment_trip_completed_dest_to);
            date = itemView.findViewById(R.id.fragment_trip_completed_date);
            time = itemView.findViewById(R.id.fragment_trip_completed_time);

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
