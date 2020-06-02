package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.R;

public class SearchDestinationsActivity extends AppCompatActivity {
    private Button searchFromButton, searchToButton, searchButton;
    private DestinationModel predictionsModelDestFrom, predictionsModelDestTo;
    private ImageButton backButton;
    //private boolean fromFlag = false, toFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_destinations);

        searchFromButton = findViewById(R.id.ActivitySearchDestinations_destFrom_button);
        searchToButton = findViewById(R.id.ActivitySearchDestinations_destΤο_button);
        searchButton = findViewById(R.id.ActivitySearchDestinations_search_button);
        backButton = findViewById(R.id.ActivitySearchDestinations_back_button);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchFromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDestinationsActivity.this, SearchLocationActivity.class);
                startActivityForResult(mainIntent, 1);
            }
        });


        searchToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDestinationsActivity.this, SearchLocationActivity.class);
                startActivityForResult(mainIntent, 2);
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (predictionsModelDestFrom != null && predictionsModelDestTo != null) {
                    Intent mainIntent = new Intent(SearchDestinationsActivity.this, SearchResultsActivity.class);
                    mainIntent.putExtra("startDestination", predictionsModelDestFrom);
                    mainIntent.putExtra("endDestination", predictionsModelDestTo);
                    startActivity(mainIntent);
                } else
                    Toast.makeText(SearchDestinationsActivity.this, "Πρέπει να συμπληρώσετε τα πεδία της αναζήτησης", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                DestinationModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                predictionsModelDestFrom = resultPredictionsModel;
                searchFromButton.setText(resultPredictionsModel.getTitle());
                searchFromButton.setTextColor(getResources().getColor(R.color.black_color));

               // fromFlag = true;

            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                DestinationModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                predictionsModelDestTo = resultPredictionsModel;
                searchToButton.setText(resultPredictionsModel.getTitle());
                searchToButton.setTextColor(getResources().getColor(R.color.black_color));
               // toFlag = true;

                //Toast.makeText(getContext(),predictionsModelDestFrom.toString(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(),predictionsModelDestTo.toString(), Toast.LENGTH_SHORT).show();

            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
            }
        }
    }
}
