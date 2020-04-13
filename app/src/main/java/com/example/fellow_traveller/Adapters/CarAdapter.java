package com.example.fellow_traveller.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ExampleViewHolder> {
    private ArrayList<Car> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public ConstraintLayout constraintLayout;


        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            description = itemView.findViewById(R.id.car_item_textView_title);
            constraintLayout = itemView.findViewById(R.id.car_item_ConstraintLayout);

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

    public CarAdapter(ArrayList<Car> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Car currentItem = mExampleList.get(position);
        holder.description.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}