package com.example.fellow_traveller.SearchAndBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAdapter;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchLocationActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private EditText destinationsEditText;
    private ImageButton backButton, clearButton;
    private ProgressBar progressBar;
    private DestinationModel destinationModelToReturn;
    private ConstraintLayout suggestSectionLayout;
    private Button yourLocationButton, athensButton, thessalonikiButton, ioanninaButton, patraButton, larissaButton;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        destinationsEditText = findViewById(R.id.ActivitySearchLocation_edit_text);
        backButton = findViewById(R.id.ActivitySearchLocation_back_button);
        clearButton = findViewById(R.id.ActivitySearchLocation_erase_button);
        progressBar = findViewById(R.id.ActivitySearchLocation_progress_bar);
        suggestSectionLayout = findViewById(R.id.ActivitySearchLocation_suggest_section);
        mRecyclerView = findViewById(R.id.ActivitySearchLocation_recycler_view);
        yourLocationButton = findViewById(R.id.ActivityLocationSearch_your_location_button);
        athensButton = findViewById(R.id.ActivityLocationSearch_athens_button);
        thessalonikiButton = findViewById(R.id.ActivityLocationSearch_thessaloniki_button);
        ioanninaButton = findViewById(R.id.ActivityLocationSearch_ioannina_button);
        patraButton = findViewById(R.id.ActivityLocationSearch_patra_button);
        larissaButton = findViewById(R.id.ActivityLocationSearch_larisa_button);

        //Assign buttons to a button listener
        yourLocationButton.setOnClickListener(this);
        athensButton.setOnClickListener(this);
        thessalonikiButton.setOnClickListener(this);
        ioanninaButton.setOnClickListener(this);
        patraButton.setOnClickListener(this);
        larissaButton.setOnClickListener(this);

        //initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        destinationsEditText.requestFocus();
        destinationsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.i("addTextChangedListener", "beforeTextChanged");
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.i("addTextChangedListener", "onTextChanged");
                if (!charSequence.toString().trim().isEmpty()) {
                    suggestSectionLayout.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    clearButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    suggestSectionLayout.setVisibility(View.VISIBLE);
                    clearButton.setVisibility(View.INVISIBLE);
                }
                GetPlaces(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Log.i("addTextChangedListener", "afterTextChanged "+s);


            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationsEditText.setText("");
            }
        });
    }
    public void buildRecyclerView() {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlaceAdapter(places_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                destinationsEditText.setText(places_list.get(position).getDescription());
                Intent resultIntent = new Intent();
                destinationModelToReturn = new DestinationModel(places_list.get(position).getPlaceId(), places_list.get(position).getDescription(), Float.valueOf(0), Float.valueOf(0));
                resultIntent.putExtra("resultPredictionsModel", destinationModelToReturn);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public void GetPlaces(String input) {

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        new PlaceApiConnection(globalClass).getPlaces(input,new PlaceApiCallBack() {
            @Override
            public void onSuccess(PlaceAPiModel placeAPiModel) {
                places_list = placeAPiModel.getPredictions();
                buildRecyclerView();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String errorMsg) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent mainIntent = new Intent(SearchLocationActivity.this, SearchDestinationsActivity.class);
        switch (view.getId()) {

            case R.id.ActivityLocationSearch_your_location_button:
                if (ActivityCompat.checkSelfPermission(SearchLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    //Intent new Activity
                    //Toast.makeText(SearchActivity.this, "Permission already allowed", Toast.LENGTH_SHORT).show();
                    getMyLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(SearchLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                break;

            case R.id.ActivityLocationSearch_athens_button:
                destinationModelToReturn = new DestinationModel("default", "Αθήνα",    (float) 37.97534 ,  (float) 23.736151);
                mainIntent.putExtra("resultPredictionsModel", (Parcelable) destinationModelToReturn);
                //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                mainIntent.putExtra("DestStartChoice", 2);
                setResult(RESULT_OK, mainIntent);
                finish();
                break;
            case R.id.ActivityLocationSearch_thessaloniki_button:
                destinationModelToReturn = new DestinationModel("default", "Θεσσαλονίκη",    (float) 40.634781 ,  (float) 22.943090);
                mainIntent.putExtra("resultPredictionsModel", (Parcelable) destinationModelToReturn);
                //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                mainIntent.putExtra("DestStartChoice", 3);
                setResult(RESULT_OK, mainIntent);
                finish();
                break;
            case R.id.ActivityLocationSearch_ioannina_button:
                destinationModelToReturn = new DestinationModel("default", "Ιωάννινα",    (float) 39.674530 ,  (float) 20.840210);
                mainIntent.putExtra("resultPredictionsModel", (Parcelable) destinationModelToReturn);
                //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                mainIntent.putExtra("DestStartChoice", 4);
                setResult(RESULT_OK, mainIntent);
                finish();
                break;
            case R.id.ActivityLocationSearch_patra_button:
                destinationModelToReturn = new DestinationModel("default", "Πάτρα",    (float) 38.246639 ,  (float) 21.734573);
                mainIntent.putExtra("resultPredictionsModel", (Parcelable) destinationModelToReturn);
                //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                mainIntent.putExtra("DestStartChoice", 5);
                setResult(RESULT_OK, mainIntent);
                finish();
                break;
            case R.id.ActivityLocationSearch_larisa_button:
                destinationModelToReturn = new DestinationModel("default", "Λάρισα",    (float) 39.638779,  (float) 22.415979);
                mainIntent.putExtra("resultPredictionsModel", (Parcelable) destinationModelToReturn);
                //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                mainIntent.putExtra("DestStartChoice", 6);
                setResult(RESULT_OK, mainIntent);
                finish();
                break;



        }
    }
    private void getMyLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        //initialize Geocode
                        Geocoder geocoder = new Geocoder(SearchLocationActivity.this, Locale.getDefault());
                        //initialize address
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //Toast.makeText(SearchActivity.this, "Περιοχή " + addresses.get(0).getLocality() + " Latitude: " + String.valueOf(addresses.get(0).getLatitude()) + " Longtitude: " + String.valueOf(addresses.get(0).getLongitude()), Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(SearchLocationActivity.this, SearchDestinationsActivity.class);
                        destinationModelToReturn = new DestinationModel("default", addresses.get(0).getLocality(),  (float) addresses.get(0).getLatitude(),  (float) addresses.get(0).getLongitude());
                        mainIntent.putExtra("resultPredictionsModel", (Parcelable) destinationModelToReturn);
                        //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                        mainIntent.putExtra("DestStartChoice", 1);
                        setResult(RESULT_OK, mainIntent);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}
