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

public class MyCarAdapter extends RecyclerView.Adapter<MyCarAdapter.MyCarViewHolder>{
    private ArrayList<CarModel> carsList;
    private MyCarAdapter.OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(MyCarAdapter.OnItemClickListener onItemClickListener) {
        mListener = onItemClickListener;
    }

    public static class MyCarViewHolder extends RecyclerView.ViewHolder{
        public TextView carSpecs;




        public MyCarViewHolder(@NonNull View itemView, final MyCarAdapter.OnItemClickListener listener) {
            super(itemView);

            carSpecs = itemView.findViewById(R.id.car_speces_settings_adapter);

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
    public MyCarAdapter(ArrayList<CarModel> aList){
        carsList = aList;

    }

    @NonNull
    @Override
    public MyCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_car_item, parent, false);
        MyCarViewHolder evh = new MyCarViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCarViewHolder holder, int position) {
        CarModel currentItem = carsList.get(position);
        holder.carSpecs.setText(currentItem.getDescription());
    }



    @Override
    public int getItemCount() {
        return carsList.size();
    }
}
