package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;

public class SearchDestinationsActivity extends AppCompatActivity {
    private Button searchFromButton, searchToButton, searchButton;
    private int witchFieldIsCLick = 0;
    private DestinationModel predictionsModelDestFrom, predictionsModelDestTo;
    private boolean fromFlag = false, toFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_destinations);

        searchFromButton = findViewById(R.id.ActivitySearchDestinations_destFrom_button);
        searchToButton = findViewById(R.id.ActivitySearchDestinations_destΤο_button);
        searchButton = findViewById(R.id.ActivitySearchDestinations_search_button);




        searchFromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDestinationsActivity.this, SearchLocationActivity.class);
                witchFieldIsCLick = 1;
                startActivityForResult(mainIntent, 1);
            }
        });
        searchToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDestinationsActivity.this, SearchLocationActivity.class);
                witchFieldIsCLick = 2;
                startActivityForResult(mainIntent, 1);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromFlag && toFlag) {
                    Intent mainIntent = new Intent(SearchDestinationsActivity.this, SearchResultsActivity.class);
                    mainIntent.putExtra("startDestination", predictionsModelDestFrom);
                    mainIntent.putExtra("endDestination", predictionsModelDestTo);
                    startActivity(mainIntent);
                }else
                    Toast.makeText(SearchDestinationsActivity.this, "Πρέπει να συμπληρώσετε τα πeδία της αναζήτησης", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                DestinationModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                if (witchFieldIsCLick == 1) {
                    predictionsModelDestFrom = resultPredictionsModel;
                    searchFromButton.setText(resultPredictionsModel.getTitle());
                    fromFlag = true;

                } else if (witchFieldIsCLick == 2) {
                    predictionsModelDestTo = resultPredictionsModel;
                    searchToButton.setText(resultPredictionsModel.getTitle());
                    toFlag = true;

                }
                //Toast.makeText(getContext(),predictionsModelDestFrom.toString(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(),predictionsModelDestTo.toString(), Toast.LENGTH_SHORT).show();

            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
    }
}
