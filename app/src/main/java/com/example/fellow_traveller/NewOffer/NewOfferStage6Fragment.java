package com.example.fellow_traveller.NewOffer;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.R;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.format.TextStyle;


public class NewOfferStage6Fragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView textViewFrom, textViewTo, textViewDate, textViewTime,
            textViewSeats, textViewBags, textViewPets, textViewPrice, textViewCar, textViewMsg;
    private String from, to, date, time, seats, bags, pets, price, car, msg;
    private View.OnClickListener clickListener;

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

        textViewFrom = view.findViewById(R.id.NewOfferStage6Fragment_textView_from);
        textViewTo = view.findViewById(R.id.NewOfferStage6Fragment_textView_to);
        textViewDate = view.findViewById(R.id.NewOfferStage6Fragment_textView_date);
        textViewTime = view.findViewById(R.id.NewOfferStage6Fragment_textView_time);
        textViewSeats = view.findViewById(R.id.NewOfferStage6Fragment_textView_seats);
        textViewBags = view.findViewById(R.id.NewOfferStage6Fragment_textView_bags);
        textViewPets = view.findViewById(R.id.NewOfferStage6Fragment_textView_pets);
        textViewPrice = view.findViewById(R.id.NewOfferStage6Fragment_textView_price);
        textViewCar = view.findViewById(R.id.NewOfferStage6Fragment_textView_car);
        textViewMsg = view.findViewById(R.id.NewOfferStage6Fragment_textView_msg);

        textViewFrom.setOnClickListener(this);
        textViewTo.setOnClickListener(this);
        textViewDate.setOnClickListener(this);
        textViewTime.setOnClickListener(this);
        textViewSeats.setOnClickListener(this);
        textViewBags.setOnClickListener(this);
        textViewPets.setOnClickListener(this);
        textViewPrice.setOnClickListener(this);
        textViewCar.setOnClickListener(this);
        textViewMsg.setOnClickListener(this);


        textViewFrom.setText(from);
        textViewTo.setText(to);

        textViewDate.setText(date);
        textViewTime.setText(time);

        textViewSeats.setText(seats);
        textViewBags.setText(bags);

        textViewPets.setText(pets);
        textViewPrice.setText(price);

        textViewCar.setText(car);
        textViewMsg.setText(msg);


        return view;


    }

    @Override
    public void onClick(View v) {
        NewOfferActivity newOfferActivity = (NewOfferActivity) getActivity();

        newOfferActivity.getDateFromFragment(v.getTag().toString());

    }

    public String toString() {
        return "NewOfferStage6Fragment";
    }

    public int getRank() {
        return 7;
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
        int hourOfDay = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(3, 5));
        time = ((hourOfDay > 12) ? hourOfDay % 12 : hourOfDay) + ":" + (minute < 10 ? ("0" + minute) : minute) + "\n" + ((hourOfDay >= 12) ? "ΜΜ" : "ΠΜ");
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
