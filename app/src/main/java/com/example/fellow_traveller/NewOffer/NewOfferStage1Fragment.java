package com.example.fellow_traveller.NewOffer;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.fellow_traveller.Util.SomeMethods.createSnackBar;


public class NewOfferStage1Fragment extends Fragment {
    private View view;
    private Button buttonFrom, buttonTo;
    private final String FROM_TITLE = "Διάλεξε την αφετηρία ...";
    private final String TO_TITLE = "Διάλεξε τον προορισμό ...";
    private int witchFieldIsCLick = 0;
    private PredictionsModel predictionsModelDestTo, predictionsModelDestFrom;

    public NewOfferStage1Fragment() {
        predictionsModelDestTo = new PredictionsModel();
        predictionsModelDestFrom = new PredictionsModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_offer_stage1, container, false);
        buttonFrom = view.findViewById(R.id.NewOfferStage1Fragment_button_from);
        buttonTo = view.findViewById(R.id.NewOfferStage1Fragment_button_to);


        buttonFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                //witchFieldIsCLick = 1;
                 startActivityForResult(intent, 1);

            }
        });

        buttonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                startActivityForResult(intent, 2);
                //witchFieldIsCLick = 2;


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
                predictionsModelDestFrom = resultPredictionsModel;
                buttonFrom.setText(resultPredictionsModel.getDescription());
            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                PredictionsModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                predictionsModelDestTo = resultPredictionsModel;
                buttonTo.setText(resultPredictionsModel.getDescription());
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
        if (predictionsModelDestFrom.getDescription().length() > 1) {
            buttonFrom.setText(predictionsModelDestFrom.getDescription());
            buttonFrom.setTextColor(getResources().getColor(R.color.black_color));

        }
        if (predictionsModelDestTo.getDescription().length() > 1) {
            buttonTo.setText(predictionsModelDestTo.getDescription());
            buttonTo.setTextColor(getResources().getColor(R.color.black_color));
        }
    }


    public Boolean validateFragment() {
        if (buttonFrom.getText().equals(FROM_TITLE)) {
            createSnackBar(view, "Παρακαλώ επιλέξτε σημείο αφετηρίας");
            return false;
        }
        if (buttonTo.getText().equals(TO_TITLE)) {
            createSnackBar(view, "Παρακαλώ επιλέξτε σημείο προορισμού");
            return false;
        }
        return true;
    }


    public String getDestFrom() {
        return buttonFrom.getText().toString();
    }

    public String getDestTo() {
        return buttonTo.getText().toString();
    }


    public PredictionsModel getDestToModel() {
        return predictionsModelDestTo;
    }

    public PredictionsModel getDestFromModel() {
        return predictionsModelDestFrom;
    }

}
