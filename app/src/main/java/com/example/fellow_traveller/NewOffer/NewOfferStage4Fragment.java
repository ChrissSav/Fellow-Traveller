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
    private TextView textView_total;
    private EditText editText_price;
    private int num_of_passengers = 2;

    public NewOfferStage4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage4, container, false);

        textView_total = view.findViewById(R.id.NewOfferStage4Fragment_textView_total_price);
        editText_price = view.findViewById(R.id.NewOfferStage4Fragment_editText_price);
        editText_price.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 1)
                    textView_total.setText((Float.parseFloat(s.toString()) * num_of_passengers) + " "+getResources().getString(R.string.euro_symbol));
                else
                    textView_total.setText( "0 "+getResources().getString(R.string.euro_symbol));
            }
        });

        return view;
    }


    public String toString() {
        return "NewOfferStage4Fragment";
    }

    public int getRank() {
        return 4;
    }

    public boolean isOk() {
        return true;
    }
}
