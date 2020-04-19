package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fellow_traveller.R;


public class NewOfferStage6Fragment extends Fragment {
    private View view;


    public NewOfferStage6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_new_offer_stage6, container, false);
        return view;
    }
    public String toString() {
        return "NewOfferStage6Fragment";
    }

    public int getRank() {
        return 6;
    }

}
