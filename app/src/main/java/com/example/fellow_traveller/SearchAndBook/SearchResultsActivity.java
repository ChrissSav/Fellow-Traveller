package com.example.fellow_traveller.SearchAndBook;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.ClientAPI.Models.FilterModel;
import com.example.fellow_traveller.ClientAPI.Models.LatLongModel;
import com.example.fellow_traveller.ClientAPI.Models.SearchDestinationsModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiResultCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
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
    private boolean destFromDone = false;
    private boolean destToDone = false;
    private ImageView notFoundImage;
    private FilterModel  filterModel;

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
        notFoundImage = findViewById(R.id.ActivitySearchResults_not_found_image);

        filterModel = new FilterModel();

        //Get the Start-End DestinationModels
        final Intent intent = getIntent();
        startDestinationModel = (DestinationModel) intent.getParcelableExtra("startDestination");
        endDestinationModel = (DestinationModel) intent.getParcelableExtra("endDestination");
        startDestTextView.setText(startDestinationModel.getTitle());
        endDestTextView.setText(endDestinationModel.getTitle());

        // getLatLongFromPlaceId(startDestinationModel);
        //getLatLongFromPlaceId(endDestinationModel);
        getLatLongFromPlaceIFrom();
        getLatLongFromPlaceITo();
        Trip();
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
        /*new FellowTravellerAPI(globalClass).getTrips(destinationsModel, null, null, null, null, null, null,
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
                });*/


        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, FiltersActivity.class);
                mainIntent.putExtra("getSelections", filterModel);
                startActivityForResult(mainIntent, 1);


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
                abortDialog();
            }
        });
    }

    public void getLatLongFromPlaceId(final DestinationModel destModel) {
        Log.i("default",destModel.getPlaceId().equals("default")+"");
        if (!destModel.getPlaceId().equals("default")) {
            new PlaceApiConnection(globalClass, true).getLatLonFromPlace(destModel.getPlaceId(), new PlaceApiResultCallBack() {
                @Override
                public void onSuccess(ResultModel resultModel) {

                    destModel.setLatitude(resultModel.getGeometry().getLocation().getLatitude());
                    destModel.setLongitude(resultModel.getGeometry().getLocation().getLongitude());
                    Toast.makeText(SearchResultsActivity.this, destModel.getLatitude() + " " + (destModel.getLongitude()), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    //getLatLongFromPlaceId(startDestinationModel);
    public void getLatLongFromPlaceIFrom() {
        Log.i("default",startDestinationModel.getPlaceId().equals("default")+"");
        if (!startDestinationModel.getPlaceId().equals("default")) {
            new PlaceApiConnection(globalClass, true).getLatLonFromPlace(startDestinationModel.getPlaceId(), new PlaceApiResultCallBack() {
                @Override
                public void onSuccess(ResultModel resultModel) {

                    startDestinationModel.setLatitude(resultModel.getGeometry().getLocation().getLatitude());
                    startDestinationModel.setLongitude(resultModel.getGeometry().getLocation().getLongitude());
                    Toast.makeText(SearchResultsActivity.this, startDestinationModel.getLatitude() + " " + (startDestinationModel.getLongitude()), Toast.LENGTH_SHORT).show();
                    destFromDone = true;
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    destFromDone = true;
                }
            });
        }
        else {
            destFromDone = true;
        }
    }

    public void getLatLongFromPlaceITo() {
        Log.i("default",endDestinationModel.getPlaceId().equals("default")+"");
        if (!endDestinationModel.getPlaceId().equals("default")) {
            new PlaceApiConnection(globalClass, true).getLatLonFromPlace(endDestinationModel.getPlaceId(), new PlaceApiResultCallBack() {
                @Override
                public void onSuccess(ResultModel resultModel) {

                    endDestinationModel.setLatitude(resultModel.getGeometry().getLocation().getLatitude());
                    endDestinationModel.setLongitude(resultModel.getGeometry().getLocation().getLongitude());
                    Toast.makeText(SearchResultsActivity.this, endDestinationModel.getLatitude() + " " + (endDestinationModel.getLongitude()), Toast.LENGTH_SHORT).show();
                    destToDone = true;
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    destToDone = true;
                }
            });
        }else {
            destToDone = true;
        }
    }



    public void Trip() {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                while (!(destFromDone && destToDone)) {

                }
                //if (destinationModelFrom.getTitle() == null && destinationModelTo.getTitle() == null)
                //       return false;
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aVoid) {
                if (aVoid) {

                    latlongModelStart = new LatLongModel(startDestinationModel.getLatitude(), startDestinationModel.getLongitude());
                    latlongModelEnd = new LatLongModel(endDestinationModel.getLatitude(), endDestinationModel.getLongitude());
                    searchDestinationsModel = new SearchDestinationsModel(latlongModelStart, latlongModelEnd);


                    new FellowTravellerAPI(globalClass).getTrips(searchDestinationsModel, filterModel.getTimestampMin(), filterModel.getTimestampMax(),
                            filterModel.getSeatsMin(),filterModel.getSeatsMax(), filterModel.getBagsMin(), filterModel.getBagsMax(),
                            filterModel.getPriceMin(), filterModel.getPriceMax(),
                            filterModel.getHavePet(), filterModel.getRange(), new SearchTripsCallback() {
                                @Override
                                public void onSuccess(ArrayList<TripModel> trips) {
                                    if(trips.size()==0)
                                        notFoundImage.setVisibility(View.VISIBLE);
                                    else
                                        notFoundImage.setVisibility(View.GONE);

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
                                            mainIntent.putExtra("example", "resultList.get(position)");
                                            startActivity(mainIntent);
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(String errorMsg) {
                                    Log.d("FILTER", "Couldnt find any trips");
                                }
                            });
                } else {
                    Toast.makeText(SearchResultsActivity.this, "Υπάρχει κάποιο πρόβλημα επικοινωνίας", Toast.LENGTH_SHORT).show();
                }
                super.onPostExecute(aVoid);
            }
        };

        task.execute();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("result", 0);
                filterModel = data.getParcelableExtra("resultFilterModel");
                Trip();

            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
    public void abortDialog(){
        final Dialog myDialog = new Dialog(SearchResultsActivity.this, R.style.Theme_Dialog_Abort);
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.abort_dialog);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);

        //get elements

        Button abortButton = myDialog.findViewById(R.id.abort_dialog_abort_button);
        Button stayButton = myDialog.findViewById(R.id.abort_dialog_stay_button);

        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
                Intent mainIntent = new Intent(SearchResultsActivity.this, HomeActivity.class);
                startActivity(mainIntent);

            }
        });
        stayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        abortDialog();
    }
}
