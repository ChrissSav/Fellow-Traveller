package com.example.fellow_traveller.NewOffer;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputLayout;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.fellow_traveller.Util.SomeMethods.createSnackBar;


public class NewOfferStagePickUpFragment extends Fragment {
    private View view;
    private Button buttonPickUp;
    private PredictionsModel predictionsModelDestPickUp;
    private final String PICKUP_TITLE = "Διάλεξε τον προορισμό ...";


    public NewOfferStagePickUpFragment() {
        predictionsModelDestPickUp = new PredictionsModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_stage_pick_up, container, false);
        buttonPickUp = view.findViewById(R.id.NewOfferStage1Fragment_button_pickup);

        buttonPickUp.setOnClickListener(new View.OnClickListener() {
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
        if (predictionsModelDestPickUp.getDescription().length() > 1) {
            buttonPickUp.setText(predictionsModelDestPickUp.getDescription());
            buttonPickUp.setTextColor(getResources().getColor(R.color.black_color));
        }
    }

    public String toString() {
        return "NewOfferStagePickUpFragment";
    }

    public int getRank() {
        return 2;
    }

    public Boolean validateFragment() {

        if (buttonPickUp.getText().equals(PICKUP_TITLE)) {
            createSnackBar(view, "Παρακαλώ επιλέξτε σημείο αναχώρησης");
            return false;
        }
        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                PredictionsModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                predictionsModelDestPickUp = resultPredictionsModel;
                buttonPickUp.setText(resultPredictionsModel.getDescription());
            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
    }
    public PredictionsModel getDestPickUpModel() {
        return predictionsModelDestPickUp;
    }

    public String getDestPickUp() {
        return buttonPickUp.getText().toString();
    }

}
