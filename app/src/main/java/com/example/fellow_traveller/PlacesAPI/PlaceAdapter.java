package com.example.fellow_traveller.PlacesAPI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;



public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ExampleViewHolder> {

    private ArrayList<PredictionsModel> mExampleList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView locationTextView;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            locationTextView= itemView.findViewById(R.id.place_item_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
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

    public PlaceAdapter(ArrayList<PredictionsModel> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        PredictionsModel currentItem = mExampleList.get(position);
        currentItem.setDescription(currentItem.getDescription().split(", Ελλάδα")[0]);
        holder.locationTextView.setText(currentItem.getDescription());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}


