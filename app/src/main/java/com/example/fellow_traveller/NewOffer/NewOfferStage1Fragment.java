package com.example.fellow_traveller.NewOffer;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class NewOfferStage1Fragment extends Fragment {
    private View view;
    private TextInputLayout textInputLayoutFrom, textInputLayoutTo;
    // private String from = "";
    //private String to = "";
    private int witchFieldIsCLick = 0;
    private PredictionsModel predictionsModelDestTo, predictionsModelDestFrom;

    public NewOfferStage1Fragment() {
        // Required empty public constructor
        predictionsModelDestTo = new PredictionsModel();
        predictionsModelDestFrom = new PredictionsModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage1, container, false);
        textInputLayoutFrom = view.findViewById(R.id.NewOfferStage1Fragment_TextInputLayout_from);
        textInputLayoutTo = view.findViewById(R.id.NewOfferStage1Fragment_TextInputLayout_to);
        // textInputLayoutFrom.getEditText().setText(from);
        // textInputLayoutTo.getEditText().setText(to);

        textInputLayoutFrom.getEditText().setText(predictionsModelDestFrom.getDescription());
        textInputLayoutTo.getEditText().setText(predictionsModelDestTo.getDescription());

        textInputLayoutFrom.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                witchFieldIsCLick = 1;
                startActivityForResult(intent, 1);

            }
        });

        textInputLayoutTo.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                startActivityForResult(intent, 1);
                witchFieldIsCLick = 2;


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                PredictionsModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                if (witchFieldIsCLick == 1) {
                    predictionsModelDestFrom = resultPredictionsModel;
                    textInputLayoutFrom.getEditText().setText(resultPredictionsModel.getDescription());

                } else if (witchFieldIsCLick == 2) {
                    predictionsModelDestTo = resultPredictionsModel;
                    textInputLayoutTo.getEditText().setText(resultPredictionsModel.getDescription());

                }
                //Toast.makeText(getContext(),predictionsModelDestFrom.toString(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(),predictionsModelDestTo.toString(), Toast.LENGTH_SHORT).show();


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
    public void onStart() {
        super.onStart();
        if (textInputLayoutFrom.getEditText().getText().length() > 0)
            textInputLayoutFrom.setError(null);
        if (textInputLayoutTo.getEditText().getText().length() > 0)
            textInputLayoutTo.setError(null);
    }

    @Override
    public void onDestroy() {
        // Log.i("textInputLayout_pass_1", "onDestroy");
        // from = textInputLayoutFrom.getEditText().getText().toString();
        // to = textInputLayoutTo.getEditText().getText().toString();
        super.onDestroy();
    }

    public Boolean validateFragment() {
        if (textInputLayoutFrom.getEditText().getText().length() < 1) {
            textInputLayoutFrom.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            textInputLayoutFrom.setError(null);
        }
        if (textInputLayoutTo.getEditText().getText().length() < 1) {
            textInputLayoutTo.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            textInputLayoutTo.setError(null);
        }
        return true;
    }


    public String getDestFrom() {
        return textInputLayoutFrom.getEditText().getText().toString();
    }

    public String getDestTo() {
        return textInputLayoutTo.getEditText().getText().toString();
    }


    public PredictionsModel getDestToModel() {
        return predictionsModelDestTo;
    }

    public PredictionsModel getDestFromModel() {
        return predictionsModelDestFrom;
    }

}
