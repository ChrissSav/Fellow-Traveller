package com.example.fellow_traveller.NewOffer;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class NewOfferStage1Fragment extends Fragment {
    private View view;
    private TextInputLayout textInputLayout_from, textInputLayout_to;
    private String from = "";
    private String to = "";
    private int txt_click = 0;

    public NewOfferStage1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage1, container, false);
        textInputLayout_from = view.findViewById(R.id.NewOfferStage1Fragment_TextInputLayout_from);
        textInputLayout_to = view.findViewById(R.id.NewOfferStage1Fragment_TextInputLayout_to);
        textInputLayout_from.getEditText().setText(from);
        textInputLayout_to.getEditText().setText(to);

        textInputLayout_from.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                txt_click = 1;
                startActivityForResult(intent, 1);

            }
        });

        textInputLayout_to.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                startActivityForResult(intent, 1);
                txt_click = 2;


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                if (txt_click == 1) {
                    textInputLayout_from.getEditText().setText(result);

                } else if (txt_click == 2) {
                    textInputLayout_to.getEditText().setText(result);

                }

            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
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

    public Boolean validateFragment() {
        if (textInputLayout_from.getEditText().getText().length() < 1) {
            textInputLayout_from.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            textInputLayout_from.setError(null);
        }
        if (textInputLayout_to.getEditText().getText().length() < 1) {
            textInputLayout_to.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            textInputLayout_to.setError(null);
        }
        return true;
    }


    public String getDest_from() {
        return textInputLayout_from.getEditText().getText().toString();
    }

    public String getDest_to() {
        return textInputLayout_to.getEditText().getText().toString();
    }
}
