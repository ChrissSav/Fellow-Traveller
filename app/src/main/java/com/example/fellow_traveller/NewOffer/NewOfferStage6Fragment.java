package com.example.fellow_traveller.NewOffer;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fellow_traveller.R;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.format.TextStyle;


public class NewOfferStage6Fragment extends Fragment {
    private View view;
    private TextView textView_from, textView_to, textView_date, textView_time,
            textView_seats, textView_bags, textView_pets, textView_price, textView_car, textView_msg;
    private String from, to, date, time, seats, bags, pets, price, car, msg;


    public NewOfferStage6Fragment() {
        this.from = "";
        this.to = "";
        this.date = "";
        this.time = "";
        this.seats = "";
        this.bags = "";
        this.pets = "";
        this.price = "";
        this.car = "";
        this.msg = "";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage6, container, false);

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

        textView_from.setText(from);
        textView_to.setText(to);

        textView_date.setText(date);
        textView_time.setText(time);

        textView_seats.setText(seats);
        textView_bags.setText(bags);

        textView_pets.setText(pets);
        textView_price.setText(price);

        textView_car.setText(car);
        textView_msg.setText(msg);


        return view;
    }

    public String toString() {
        return "NewOfferStage6Fragment";
    }

    public int getRank() {
        return 6;
    }


    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setDate(String date) {
        int month = Integer.parseInt(date.substring(3, 5));
        String month_str = "";
        switch (month) {
            case 1:
                month_str = "Ιαν";
                break;
            case 2:
                month_str = "Φεβ";
                break;
            case 3:
                month_str = "Μαρ";
                break;
            case 4:
                month_str = "Απρ";
                break;
            case 5:
                month_str = "Μαΐ";
                break;
            case 6:
                month_str = "Ιουν";
                break;
            case 7:
                month_str = "Ιουλ";
                break;
            case 8:
                month_str = "Αυγ";
                break;
            case 9:
                month_str = "Σεπ";
                break;

            case 10:
                month_str = "Οκτ";
                break;
            case 11:
                month_str = "Νοε";
                break;

            case 12:
                month_str = "Δεκ";
                break;
        }
        this.date = date.substring(0, 2) + "\n" + month_str;
    }

    public void setTime(String time) {
        int hourOfDay = Integer.parseInt(time.substring(0,2));
        int minute = Integer.parseInt(time.substring(3,5));
        time =  ((hourOfDay > 12) ? hourOfDay % 12 : hourOfDay) + ":" + (minute < 10 ? ("0" + minute) : minute) + "\n" + ((hourOfDay >= 12) ? "ΜΜ" : "ΠΜ");
        this.time = time;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setBags(String bags) {
        this.bags = bags;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
