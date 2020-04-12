package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.fellow_traveller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOfferStage5Fragment extends Fragment {

    private View view;
    private EditText editText;


    public NewOfferStage5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage5, container, false);
        editText =view.findViewById(R.id.NewOfferStage5Fragment_editText_price);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("addTextChangedListener","beforeTextChanged" + s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("addTextChangedListener","onTextChanged " +s );
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("addTextChangedListener","afterTextChanged");
            }
        });


        return  view;
    }
    public String toString() {
        return "NewOfferStage5Fragment";
    }

    public int getRank() {
        return 5;
    }
}
