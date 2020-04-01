package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOfferStage1Fragment extends Fragment {


    public NewOfferStage1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_offer_stage1, container, false);
    }

    public String toString() {
        return "NewOfferStage1Fragment";
    }

    public int getRank() {
        return 1;
    }
}
