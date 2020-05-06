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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAdapter;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
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

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText destinationStartEditText;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    private ConstraintLayout suggestSection, resultsSection;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private GlobalClass globalClass;

    private Button yourLocationButton, athensButton, thessalonikiButton, ioanninaButton, patraButton, larissaButton;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        globalClass = (GlobalClass) getApplicationContext();

        destinationStartEditText = findViewById(R.id.ActivitySearch_start_dest_editText);
        backButton = findViewById(R.id.ActivitySearch_back_button);
        eraseButton = findViewById(R.id.ActivitySearch_erase_button);
        searchIcon = findViewById(R.id.ActivitySearch_search_image);
        suggestSection = findViewById(R.id.ActivitySearch_suggest_section);
        resultsSection = findViewById(R.id.ActivitySearch_results_section);
        yourLocationButton = findViewById(R.id.ActivitySearch_your_location_button);
        athensButton = findViewById(R.id.ActivitySearch_athens_button);
        thessalonikiButton = findViewById(R.id.ActivitySearch_thessaloniki_button);
        ioanninaButton = findViewById(R.id.ActivitySearch_ioannina_button);
        patraButton = findViewById(R.id.ActivitySearch_patra_button);
        larissaButton = findViewById(R.id.ActivitySearch_larisa_button);

        //Assign buttons to a button listener
        yourLocationButton.setOnClickListener(this);
        athensButton.setOnClickListener(this);
        thessalonikiButton.setOnClickListener(this);
        ioanninaButton.setOnClickListener(this);
        patraButton.setOnClickListener(this);
        larissaButton.setOnClickListener(this);


        //Alternative way to create places API with adapter
        //destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(SearchActivity.this, android.R.layout.simple_list_item_1));

        //initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//

        //If we search for something suggest section disappears
        destinationStartEditText.addTextChangedListener(new TextWatcher() {
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


        //Erase button to clear text
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationStartEditText.setText("");
            }
        });

        //Back Button to Home Activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(mainIntent);
            }
        });


    }

    private void getMyLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {

                    try {
                        //initialize Geocode
                        Geocoder geocoder = new Geocoder(SearchActivity.this, Locale.getDefault());
                        //initialize address
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                        Toast.makeText(SearchActivity.this, String.valueOf(addresses.get(0).getLatitude()), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(SearchActivity.this, String.valueOf(addresses.get(0).getLongitude()), Toast.LENGTH_SHORT).show();
                        Toast.makeText(SearchActivity.this, "Περιοχή " + addresses.get(0).getLocality() + " Latitude: " + String.valueOf(addresses.get(0).getLatitude()) + " Longtitude: " + String.valueOf(addresses.get(0).getLongitude()), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
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
        mRecyclerView = findViewById(R.id.ActivitySearch_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlaceAdapter(places_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // SetNotificationsRead(mExampleList.get(position).getId(),position);
                destinationStartEditText.setText(places_list.get(position).getDescription());
                Intent mainIntent = new Intent(SearchActivity.this, Search2Activity.class);
                mainIntent.putExtra("FromPlace", destinationStartEditText.getText().toString());
                //We also parse a value code to check if we clicked on a suggested destination to disable to the next step
                mainIntent.putExtra("DestStartChoice", 0);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ActivitySearch_your_location_button:
                if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    //putExtra GeocodeModel
                    //Intent new Activity
                    Toast.makeText(SearchActivity.this, "Permission already allowed", Toast.LENGTH_SHORT).show();
                    getMyLocation();
                } else {
                    //When permission denied
                    ActivityCompat.requestPermissions(SearchActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                break;

            case R.id.ActivitySearch_athens_button:
            case R.id.ActivitySearch_thessaloniki_button:
            case R.id.ActivitySearch_ioannina_button:
            case R.id.ActivitySearch_patra_button:
            case R.id.ActivitySearch_larisa_button:


        }
    }
}
