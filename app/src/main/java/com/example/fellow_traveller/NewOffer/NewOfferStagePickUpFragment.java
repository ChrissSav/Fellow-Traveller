package com.example.fellow_traveller.NewOffer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class NewOfferStagePickUpFragment extends Fragment {
    private View view;
    private TextInputLayout textInputLayoutPickUp;
    private PredictionsModel predictionsModelDestPickUp;


    public NewOfferStagePickUpFragment() {
        predictionsModelDestPickUp = new PredictionsModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage_pick_up, container, false);
        textInputLayoutPickUp = view.findViewById(R.id.NewOfferStagePickUpFragment_TextInputLayout_to);
        textInputLayoutPickUp.getEditText().setText(predictionsModelDestPickUp.getDescription());

        textInputLayoutPickUp.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                startActivityForResult(intent, 1);

            }
        });


        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
        if (textInputLayoutPickUp.getEditText().getText().length() > 0)
            textInputLayoutPickUp.setError(null);

    }

    public String toString() {
        return "NewOfferStagePickUpFragment";
    }

    public int getRank() {
        return 2;
    }

    public Boolean validateFragment() {
        if (textInputLayoutPickUp.getEditText().getText().length() < 1) {
            textInputLayoutPickUp.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        }
        textInputLayoutPickUp.setError(null);
        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                PredictionsModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                predictionsModelDestPickUp = resultPredictionsModel;
                textInputLayoutPickUp.getEditText().setText(resultPredictionsModel.getDescription());
            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
    }
    public PredictionsModel getDestPickUpModel() {
        return predictionsModelDestPickUp;
    }

}
