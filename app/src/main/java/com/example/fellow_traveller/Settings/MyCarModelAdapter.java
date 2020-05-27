package com.example.fellow_traveller.Settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchResultsAdapter;
import com.example.fellow_traveller.Settings.MyCarItem;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCarModelAdapter extends RecyclerView.Adapter<MyCarModelAdapter.MyCarViewHolder>{
    private ArrayList<CarModel> carsList;
    private MyCarModelAdapter.OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(MyCarModelAdapter.OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public static class MyCarViewHolder extends RecyclerView.ViewHolder{
        public TextView carSpecs;




        public MyCarViewHolder(@NonNull View itemView, final MyCarModelAdapter.OnItemClickListener listener) {
            super(itemView);

            carSpecs = itemView.findViewById(R.id.car_item_layout_textView);

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
    public MyCarModelAdapter(ArrayList<CarModel> aList){
        carsList = aList;

    }

    @NonNull
    @Override
    public MyCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_layout, parent, false);
        MyCarViewHolder evh = new MyCarViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCarViewHolder holder, int position) {
        CarModel currentItem = carsList.get(position);
        holder.carSpecs.setText(currentItem.getBrand() + " " + currentItem.getModel() + ", " + currentItem.getColor());
    }



    @Override
    public int getItemCount() {
        return carsList.size();
    }
}
