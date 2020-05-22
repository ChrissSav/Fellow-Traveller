package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fellow_traveller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOfferStage4Fragment extends Fragment {

    private View view;
    private TextView textViewTotal;
    private EditText editTextPrice;
    private int numOfPassengers = 2;

    public NewOfferStage4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage4, container, false);

        textViewTotal = view.findViewById(R.id.NewOfferStage4Fragment_textView_total_price);
        editTextPrice = view.findViewById(R.id.NewOfferStage4Fragment_editText_price);
        editTextPrice.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0)
                    textViewTotal.setText((Float.parseFloat(s.toString()) * numOfPassengers) + " " + getResources().getString(R.string.euro_symbol));
                else
                    textViewTotal.setText("0 " + getResources().getString(R.string.euro_symbol));
            }
        });

        return view;
    }


    public String toString() {
        return "NewOfferStage4Fragment";
    }

    public int getRank() {
        return 5;
    }

    public boolean validateFragment() {
        return true;
    }

    public String getPrice() {
        if (editTextPrice.getText().toString().length() < 1)
            return "0";
        return editTextPrice.getText().toString();
    }


    public void setNum_of_passengers(int numOfPassengers) {
        this.numOfPassengers = numOfPassengers;
    }
}
