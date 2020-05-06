package com.example.fellow_traveller.SearchAndBook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.ClientAPI.Models.LatLongModel;
import com.example.fellow_traveller.ClientAPI.Models.SearchDestinationsModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiResultCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.ResultModel;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResultsActivity extends AppCompatActivity {
    private TextView endDestTextView, startDestTextView, searchResultsCount;
    private RecyclerView mRecyclerView;
    private SearchResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton filterButton, swapButton, backButton;
    private DestinationModel startDestinationModel, endDestinationModel;
    private GlobalClass globalClass;
    private LatLongModel latlongModelStart, latlongModelEnd;
    private SearchDestinationsModel searchDestinationsModel;
    private ArrayList<TripModel> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        globalClass = (GlobalClass) getApplicationContext();

        startDestTextView = findViewById(R.id.ActivitySearchResults_from_textView);
        endDestTextView = findViewById(R.id.ActivitySearchResults_to_textView);
        filterButton = findViewById(R.id.ActivitySearchResults_filter_button);
        swapButton = findViewById(R.id.ActivitySearchResults_swap_button);
        backButton = findViewById(R.id.ActivitySearchResults_close_button);
        searchResultsCount = findViewById(R.id.ActivitySearchResults_results_label);


        //Get the Start-End DestinationModels
        final Intent intent = getIntent();
        startDestinationModel = (DestinationModel) intent.getExtras().getParcelable("startDestination");
        endDestinationModel = (DestinationModel) intent.getExtras().getParcelable("endDestination");
        startDestTextView.setText(startDestinationModel.getTitle());
        endDestTextView.setText(endDestinationModel.getTitle());

        getLatLongFromPlaceId(startDestinationModel);
        getLatLongFromPlaceId(endDestinationModel);

        latlongModelStart = new LatLongModel(startDestinationModel.getLatitude(), startDestinationModel.getLongitude());
        latlongModelEnd = new LatLongModel(endDestinationModel.getLatitude(), endDestinationModel.getLongitude());

        searchDestinationsModel = new SearchDestinationsModel(latlongModelStart, latlongModelEnd);
//
//
        Toast.makeText(SearchResultsActivity.this, searchDestinationsModel.getDestFrom().getLatitude().toString() + "", Toast.LENGTH_SHORT).show();
//        SearchDestinationsModel searchDestinationsModel = null;
//        searchDestinationsModel.setDestFrom(latlongModelStart);
//        searchDestinationsModel.setDestTo(latlongModelEnd);

        LatLongModel latLongModel1 = new LatLongModel(startDestinationModel.getLatitude(), startDestinationModel.getLongitude());
        LatLongModel latLongModel2 = new LatLongModel(endDestinationModel.getLatitude(), endDestinationModel.getLongitude());

        SearchDestinationsModel destinationsModel = new SearchDestinationsModel(latLongModel1,latLongModel2);
        new FellowTravellerAPI(globalClass).getTrips(destinationsModel, null, null, null, null, null, null,
                null, null, null, null, new SearchTripsCallback() {
                    @Override
                    public void onSuccess(ArrayList<TripModel> trips) {
                        // TODO πρωτα να ελεγξεις αμα εχει ταξιδια και μετα να παρεις το πρωτο στοιχειο
                        //     Toast.makeText(SearchResultsActivity.this, trips.get(0).getCreatorUser().getFullName() + "", Toast.LENGTH_SHORT).show();

                        resultList = trips;
                        mRecyclerView = findViewById(R.id.ActivitySearchResults_recycler_view);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mAdapter = new SearchResultsAdapter(resultList);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);

                        searchResultsCount.setText(String.format("Βρέθηκαν %d ταξίδια.", resultList.size()));

                        mAdapter.setOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent mainIntent = new Intent(SearchResultsActivity.this, SearchDetailsActivity.class);
                                mainIntent.putExtra("trip", resultList.get(position));
                                startActivity(mainIntent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.d("FILTER", "Couldnt find any trips");
                    }
                });


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, FiltersActivity.class);
                startActivity(mainIntent);
            }
        });
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String swapString = startDestTextView.getText().toString();
                startDestTextView.setText(endDestTextView.getText().toString());
                endDestTextView.setText(swapString);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, Search2Activity.class);
                startActivity(mainIntent);

            }
        });
    }

    public void getLatLongFromPlaceId(final DestinationModel destModel) {
        if (!destModel.getPlaceId().equals("default")) {
            new PlaceApiConnection(globalClass, true).getLatLonFromPlace(destModel.getPlaceId(), new PlaceApiResultCallBack() {
                @Override
                public void onSuccess(ResultModel resultModel) {

                    destModel.setLatitude(resultModel.getGeometry().getLocation().getLatitude());
                    destModel.setLongitude(resultModel.getGeometry().getLocation().getLongitude());
                    Toast.makeText(SearchResultsActivity.this, String.valueOf(destModel.getLatitude() + " " + String.valueOf(destModel.getLongitude())), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
