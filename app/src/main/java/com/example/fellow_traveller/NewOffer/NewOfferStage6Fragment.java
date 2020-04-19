package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fellow_traveller.R;


public class NewOfferStage6Fragment extends Fragment {
    private View view;
    private TextView textView_from,textView_to,textView_date,textView_time,
            textView_seats,textView_bags,textView_pets,textView_price,textView_car,textView_msg;


    public NewOfferStage6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_new_offer_stage6, container, false);
        textView_from = view.findViewById(R.id.NewOfferStage6Fragment_textView_from);
        textView_to = view.findViewById(R.id.NewOfferStage6Fragment_textView_to);
        textView_date = view.findViewById(R.id.NewOfferStage6Fragment_textView_date);
        textView_time = view.findViewById(R.id.NewOfferStage6Fragment_textView_time);
        textView_seats = view.findViewById(R.id.NewOfferStage6Fragment_textView_seats);
        textView_bags = view.findViewById(R.id.NewOfferStage6Fragment_textView_bags);
        textView_pets = view.findViewById(R.id.NewOfferStage6Fragment_textView_pets);
        textView_price = view.findViewById(R.id.NewOfferStage6Fragment_textView_price);
        textView_car = view.findViewById(R.id.NewOfferStage6Fragment_textView_car);
        textView_msg = view.findViewById(R.id.NewOfferStage6Fragment_textView_msg);

        return view;
    }
    public String toString() {
        return "NewOfferStage6Fragment";
    }

    public int getRank() {
        return 6;
    }



    public void setTextView_from(String textView_from) {
        this.textView_from.setText(textView_from);
    }

    public void setTextView_to(String textView_to) {
        this.textView_to.setText(textView_to);
    }

    public void setTextView_date(String textView_date) {
        this.textView_date.setText(textView_date);
    }

    public void setTextView_time(String textView_time) {
        this.textView_time.setText(textView_time);
    }

    public void setTextView_seats(String textView_seats) {
        this.textView_seats.setText(textView_seats);
    }

    public void setTextView_bags(String textView_bags) {
        this.textView_bags.setText(textView_bags);
    }

    public void setTextView_pets(String textView_pets) {
        this.textView_pets.setText(textView_pets);
    }

    public void setTextView_price(String textView_price) {
        this.textView_price.setText(textView_price);
    }

    public void setTextView_car(String textView_car) {
        this.textView_car.setText(textView_car);
    }

    public void setTextView_msg(String textView_msg) {
        this.textView_msg.setText(textView_msg);
    }
}
