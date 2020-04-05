package com.example.fellow_traveller.NewOffer;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOfferStage1Fragment extends Fragment {
    private View view;
    private TextInputLayout textInputLayout_from, textInputLayout_to;
    private String from = "";
    private String to = "";

    public NewOfferStage1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_new_offer_stage1, container, false);
        textInputLayout_from = view.findViewById(R.id.NewOfferStage1Fragment_TextInputLayout_from);
        textInputLayout_to = view.findViewById(R.id.NewOfferStage1Fragment_TextInputLayout_to);
        textInputLayout_from.getEditText().setText(from);
        textInputLayout_to.getEditText().setText(to);

        return view;
    }

    public String toString() {
        return "NewOfferStage1Fragment";
    }

    public int getRank() {
        return 1;
    }

    @Override
    public void onDestroy() {
        // Log.i("textInputLayout_pass_1", "onDestroy");
        from = textInputLayout_from.getEditText().getText().toString();
        to = textInputLayout_to.getEditText().getText().toString();
        super.onDestroy();
    }
}
