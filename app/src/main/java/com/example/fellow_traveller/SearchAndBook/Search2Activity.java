package com.example.fellow_traveller.SearchAndBook;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAdapter;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class Search2Activity extends AppCompatActivity {
    // private AutoCompleteTextView destinationAutoComplete;
    private EditText destinationAutoComplete;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    private ConstraintLayout suggestSection, resultsSection;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private DestinationModel startDestinationModel, endDestinationModel;
    private int previousChoice;
    private Button athensButton, thessalonikiButton, ioanninaButton, patraButton, larisaButton;


    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        globalClass = (GlobalClass) getApplicationContext();

        destinationAutoComplete = findViewById(R.id.ActivitySearch2_end_dest_editText);
        backButton = findViewById(R.id.ActivitySearch2_back_button);
        eraseButton = findViewById(R.id.ActivitySearch2_erase_button);
        searchIcon = findViewById(R.id.ActivitySearch2_search_image);
        suggestSection = findViewById(R.id.ActivitySearch2_suggest_section);
        resultsSection = findViewById(R.id.ActivitySearch2_results_section);

        athensButton = findViewById(R.id.ActivitySearch2_athens_button);
        thessalonikiButton = findViewById(R.id.ActivitySearch2_thessaloniki_button);
        ioanninaButton = findViewById(R.id.ActivitySearch2_ioannina_button);
        patraButton = findViewById(R.id.ActivitySearch2_patra_button);
        larisaButton = findViewById(R.id.ActivitySearch2_larisa_button);


        final Intent intent = getIntent();
        startDestinationModel = (DestinationModel) intent.getExtras().getParcelable("startDestination");
        previousChoice = intent.getIntExtra("DestStartChoice", 0);

        Toast.makeText(Search2Activity.this, startDestinationModel.getTitle() + " " + startDestinationModel.getPlaceId() + " " + startDestinationModel.getLatitude() + " " + startDestinationModel.getLongitude(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(Search2Activity.this, String.valueOf(previousChoice) + "", Toast.LENGTH_SHORT).show();
        unrecommendDestination();
        //If we search for something suggest section disappears
        destinationAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    suggestSection.setVisibility(View.GONE);
                    eraseButton.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.INVISIBLE);
                    resultsSection.setVisibility(View.VISIBLE);
                } else {
                    suggestSection.setVisibility(View.VISIBLE);
                    eraseButton.setVisibility(View.INVISIBLE);
                    searchIcon.setVisibility(View.VISIBLE);
                    resultsSection.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().trim().isEmpty()) {
                    suggestSection.setVisibility(View.GONE);
                    eraseButton.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.INVISIBLE);
                    resultsSection.setVisibility(View.VISIBLE);

                } else {
                    suggestSection.setVisibility(View.VISIBLE);
                    eraseButton.setVisibility(View.INVISIBLE);
                    searchIcon.setVisibility(View.VISIBLE);
                    resultsSection.setVisibility(View.GONE);

                }
                GetPlaces(editable.toString());

            }
        });


        //destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(Search2Activity.this, android.R.layout.simple_list_item_1));


        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationAutoComplete.setText("");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void unrecommendDestination() {

        //Check if we selected one of the recommended destinations or search one of them with autocomplete, to unrecommended them
        if(previousChoice == 2 || startDestinationModel.getPlaceId().equals("ChIJ8UNwBh-9oRQR3Y1mdkU1Nic"))
            athensButton.setVisibility(View.GONE);
        else if(previousChoice == 3 || startDestinationModel.getPlaceId().equals("ChIJ7eAoFPQ4qBQRqXTVuBXnugk"))
            thessalonikiButton.setVisibility(View.GONE);
        else if(previousChoice == 4 || startDestinationModel.getPlaceId().equals("ChIJZ93-3qLpWxMRwJe54iy9AAQ"))
            ioanninaButton.setVisibility(View.GONE);
        else if(previousChoice == 5 || startDestinationModel.getPlaceId().equals("ChIJLe0kpZk1XhMRoIy54iy9AAQ"))
            patraButton.setVisibility(View.GONE);
        else if(previousChoice == 6 || startDestinationModel.getPlaceId().equals("ChIJoUddWVyIWBMRMJy54iy9AAQ"))
            larisaButton.setVisibility(View.GONE);
    }

    public void GetPlaces(String input) {

        new PlaceApiConnection(globalClass).getPlaces(input, new PlaceApiCallBack() {
            @Override
            public void onSuccess(PlaceAPiModel placeAPiModel) {
                places_list = placeAPiModel.getPredictions();
                buildRecyclerView();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.ActivitySearch2_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlaceAdapter(places_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        final Intent intent = getIntent();
        mAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // SetNotificationsRead(mExampleList.get(position).getId(),position);
                destinationAutoComplete.setText(places_list.get(position).getDescription());
                Intent mainIntent = new Intent(Search2Activity.this, SearchResultsActivity.class);
                mainIntent.putExtra("ToPlace", destinationAutoComplete.getText().toString());
                // TODO fix this error when no re-searching for a trip
                mainIntent.putExtra("FromPlace", intent.getExtras().getString("FromPlace"));
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

