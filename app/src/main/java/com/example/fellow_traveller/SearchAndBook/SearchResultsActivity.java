package com.example.fellow_traveller.SearchAndBook;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class SearchResultsActivity extends AppCompatActivity {
    private TextView endDestTextView, startDestTextView, searchResultsCount, filterHeaderOverView, sortByTextView;
    private RecyclerView mRecyclerView;
    private SearchResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton filterButton, swapButton, backButton, sortButton;
    private DestinationModel startDestinationModel, endDestinationModel, tempDestination;
    private GlobalClass globalClass;
    private LatLongModel latlongModelStart, latlongModelEnd;
    private SearchDestinationsModel searchDestinationsModel;
    private ArrayList<TripModel> resultList = new ArrayList<>();
    private boolean destFromDone = false, destToDone = false, destOnReturnDone = true;
    private ImageView notFoundImage;
    private FilterModel  filterModel;
    private CoordinatorLayout mainCoordinatorLayout;
    private int sortListMethodFlag = 0;
    private ProgressDialog pd;
    private ProgressBar progressBar;
    private Button startDestButton, endDestButton;
    private DestinationModel onReturnDestinationModel;
    private int witchFieldIsCLick = 0;
    private float averagePrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        globalClass = (GlobalClass) getApplicationContext();

        startDestTextView = findViewById(R.id.ActivitySearchResults_from_textView);
        endDestTextView = findViewById(R.id.ActivitySearchResults_to_textView);
        filterHeaderOverView = findViewById(R.id.ActivitySearchResults_filters_textView);
        filterButton = findViewById(R.id.ActivitySearchResults_filter_button);
        swapButton = findViewById(R.id.ActivitySearchResults_swap_button);
        backButton = findViewById(R.id.ActivitySearchResults_close_button);
        sortButton = findViewById(R.id.ActivitySearchResults_sort_button);
        searchResultsCount = findViewById(R.id.ActivitySearchResults_results_label);
        notFoundImage = findViewById(R.id.ActivitySearchResults_not_found_image);
        mainCoordinatorLayout = findViewById(R.id.ActivitySearchResults_main_coordinator_layout);
        progressBar = findViewById(R.id.ActivitySearchResults_progress_bar);
        startDestButton = findViewById(R.id.ActivitySearchResults_from_button);
        endDestButton = findViewById(R.id.ActivitySearchResults_to_button);
        sortByTextView = findViewById(R.id.ActivitySearchResults_sort_TextView);

        pd = new ProgressDialog(SearchResultsActivity.this);
        pd.setMessage("Αναζήτηση... ");
        //pd.show();

        filterModel = new FilterModel();

        //Get the Start-End DestinationModels
        final Intent intent = getIntent();
        startDestinationModel = (DestinationModel) intent.getParcelableExtra("startDestination");
        endDestinationModel = (DestinationModel) intent.getParcelableExtra("endDestination");
        startDestTextView.setText(startDestinationModel.getTitle());
        endDestTextView.setText(endDestinationModel.getTitle());

        startDestButton.setText(startDestinationModel.getTitle());
        endDestButton.setText(endDestinationModel.getTitle());

        // getLatLongFromPlaceId(startDestinationModel);
        //getLatLongFromPlaceId(endDestinationModel);
        getLatLongFromPlaceIFrom();
        getLatLongFromPlaceITo();
        Trip();
        latlongModelStart = new LatLongModel(startDestinationModel.getLatitude(), startDestinationModel.getLongitude());
        latlongModelEnd = new LatLongModel(endDestinationModel.getLatitude(), endDestinationModel.getLongitude());

        searchDestinationsModel = new SearchDestinationsModel(latlongModelStart, latlongModelEnd);


        LatLongModel latLongModel1 = new LatLongModel(startDestinationModel.getLatitude(), startDestinationModel.getLongitude());
        LatLongModel latLongModel2 = new LatLongModel(endDestinationModel.getLatitude(), endDestinationModel.getLongitude());

        SearchDestinationsModel destinationsModel = new SearchDestinationsModel(latLongModel1,latLongModel2);




        //Button listeners
        startDestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, SearchLocationActivity.class);
                witchFieldIsCLick = 1;
                startActivityForResult(mainIntent, 2);
            }
        });
        endDestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, SearchLocationActivity.class);
                witchFieldIsCLick = 2;
                startActivityForResult(mainIntent, 2);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchResultsActivity.this, FiltersActivity.class);
                mainIntent.putExtra("getSelections", filterModel);
                mainIntent.putExtra("averagePrice", averagePrice);
                startActivityForResult(mainIntent, 1);


            }
        });
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                //Change button names
                String swapString = startDestButton.getText().toString();
                startDestButton.setText(endDestButton.getText().toString());
                endDestButton.setText(swapString);

                //Change destination models
                tempDestination = startDestinationModel;
                startDestinationModel = endDestinationModel;
                endDestinationModel = tempDestination;
                Trip();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abortDialog();
            }
        });
        sortButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                sortDialog();
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
                   // Toast.makeText(SearchResultsActivity.this, destModel.getLatitude() + " " + (destModel.getLongitude()), Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText(SearchResultsActivity.this, startDestinationModel.getLatitude() + " " + (startDestinationModel.getLongitude()), Toast.LENGTH_SHORT).show();
                    destFromDone = true;
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    destFromDone = true;
                    pd.dismiss();
                    progressBar.setVisibility(View.GONE);
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
                   // Toast.makeText(SearchResultsActivity.this, endDestinationModel.getLatitude() + " " + (endDestinationModel.getLongitude()), Toast.LENGTH_SHORT).show();
                    destToDone = true;
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    destToDone = true;
                    pd.dismiss();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else {
            destToDone = true;
        }
    }
    public void getLatLongFromPlace(final DestinationModel destinationModel) {

        destOnReturnDone = false;

        Log.i("default",destinationModel.getPlaceId().equals("default")+"");
        if (!destinationModel.getPlaceId().equals("default")) {
            new PlaceApiConnection(globalClass, true).getLatLonFromPlace(destinationModel.getPlaceId(), new PlaceApiResultCallBack() {
                @Override
                public void onSuccess(ResultModel resultModel) {

                    destinationModel.setLatitude(resultModel.getGeometry().getLocation().getLatitude());
                    destinationModel.setLongitude(resultModel.getGeometry().getLocation().getLongitude());
                    // Toast.makeText(SearchResultsActivity.this, endDestinationModel.getLatitude() + " " + (endDestinationModel.getLongitude()), Toast.LENGTH_SHORT).show();
                    destOnReturnDone = true;
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(SearchResultsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    destOnReturnDone = true;
                    pd.dismiss();
                    progressBar.setVisibility(View.GONE);
                }
            });
        }else {
            destOnReturnDone = true;
        }
    }


    public void Trip() {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                while (!(destFromDone && destToDone && destOnReturnDone)) {

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
                            filterModel.getHavePet(), filterModel.getRangeStart(), new SearchTripsCallback() {
                                @Override
                                public void onSuccess(ArrayList<TripModel> trips) {
                                    if(trips.size()==0) {
                                        notFoundImage.setVisibility(View.VISIBLE);
                                        sortButton.setVisibility(View.INVISIBLE);
                                        sortByTextView.setVisibility(View.INVISIBLE);
                                    }else {
                                        notFoundImage.setVisibility(View.GONE);
                                        sortButton.setVisibility(View.VISIBLE);
                                        sortByTextView.setVisibility(View.VISIBLE);
                                    }

                                    resultList = trips;

                                    //Check when we retrieve again data from server, which sort method was previous selected
                                    if(sortListMethodFlag==0)
                                        Collections.sort(resultList, TripModel.DateComparator);
                                    else if(sortListMethodFlag==1)
                                        Collections.sort(resultList, TripModel.PriceComparator);
                                    else
                                        Collections.sort(resultList, TripModel.RatesComparator);

                                    buildRecyclerView();
                                    pd.dismiss();
                                    progressBar.setVisibility(View.GONE);


                                    searchResultsCount.setText(String.format("Βρέθηκαν %d ταξίδια.", resultList.size()));
                                    getAveragePriceOfTrips();

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
                                    Log.d("FILTER", "Couldn't find any trips");
                                    pd.dismiss();
                                    progressBar.setVisibility(View.GONE);
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

        //Return a filter Model and a header
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //int result = data.getIntExtra("result", 0);

                //Parse the header filter to display which filters was selected
                String filterHeader = data.getStringExtra("filterHeaderOverview");
                if(filterHeader.equals(""))
                    filterHeaderOverView.setText("Δεν έχετε ορίσει κάποιο φίλτρο");
                else
                    filterHeaderOverView.setText(filterHeader);

                //Parse a filter model to for our search results
                if(!filterModel.equals(data.getParcelableExtra("resultFilterModel"))){
                    filterModel = data.getParcelableExtra("resultFilterModel");
                    progressBar.setVisibility(View.VISIBLE);
                    Trip();
                }

            }
            if (resultCode == RESULT_CANCELED) {

            }
            //Return a destination from autocomplete
        }else if(requestCode == 2){
            if (resultCode == RESULT_OK) {
                DestinationModel resultPredictionsModel = data.getParcelableExtra("resultPredictionsModel");
                if (witchFieldIsCLick == 1) {
                    onReturnDestinationModel = resultPredictionsModel;
                    startDestButton.setText(resultPredictionsModel.getTitle());
                    startDestinationModel = onReturnDestinationModel;
                    getLatLongFromPlace(startDestinationModel);
                    progressBar.setVisibility(View.VISIBLE);
                    Trip();


                } else if (witchFieldIsCLick == 2) {
                    onReturnDestinationModel = resultPredictionsModel;
                    endDestButton.setText(resultPredictionsModel.getTitle());
                    endDestinationModel = onReturnDestinationModel;
                    getLatLongFromPlace(endDestinationModel);
                    progressBar.setVisibility(View.VISIBLE);
                    Trip();

                }
                //Toast.makeText(getContext(),predictionsModelDestFrom.toString(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getContext(),predictionsModelDestTo.toString(), Toast.LENGTH_SHORT).show();

            }
            if (resultCode == RESULT_CANCELED) {
                // mTextViewResult.setText("Nothing selected");
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sortDialog(){
        final Dialog myDialog = new Dialog(SearchResultsActivity.this, R.style.Theme_Dialog);
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.sort_options_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mainCoordinatorLayout.setForeground(new ColorDrawable(getResources().getColor(R.color.greyBlack_transparent_color)));
        window.setAttributes(wlp);
        myDialog.show();

        final Button moreRelevant = myDialog.findViewById(R.id.sort_options_dialog_label_relevant_button);
        final Button byPrice = myDialog.findViewById(R.id.sort_options_dialog_label_price_button);
        final Button byRates = myDialog.findViewById(R.id.sort_options_dialog_label_rates_button);


        moreRelevant.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                mainCoordinatorLayout.setForeground(null);
                sortListMethodFlag = 0;
                Collections.sort(resultList, TripModel.DateComparator);
                sortByTextView.setText("Πιο σχετικά");
                mAdapter.notifyDataSetChanged();
                myDialog.dismiss();

            }
        });
        byPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mainCoordinatorLayout.setForeground(null);
                sortListMethodFlag = 1;
                Collections.sort(resultList, TripModel.PriceComparator);
                sortByTextView.setText("Τιμή");
                mAdapter.notifyDataSetChanged();
                myDialog.dismiss();
            }
        });
       byRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainCoordinatorLayout.setForeground(null);
                sortListMethodFlag = 2;
                Collections.sort(resultList, TripModel.RatesComparator);
                sortByTextView.setText("Αξιολογήσεις");
                mAdapter.notifyDataSetChanged();
                myDialog.dismiss();
            }

        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mainCoordinatorLayout.setForeground(null);
            }
        });

    }
    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.ActivitySearchResults_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new SearchResultsAdapter(resultList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getAveragePriceOfTrips(){
        averagePrice = 0;

        for(int i = 0; i < resultList.size(); i++){
            averagePrice = averagePrice + resultList.get(i).getPrice();
        }
        if (resultList.size() != 0 && resultList != null)
            averagePrice = averagePrice / resultList.size();


        int integerPart = (int) averagePrice;
        float decimalPart = averagePrice - (float) integerPart;
        float priceRounded;

        if(decimalPart < 0.25 || decimalPart == 0.25)
            priceRounded = integerPart;
        else if(decimalPart < 0.75 || decimalPart == 0.75)
            priceRounded = integerPart  + (float) 0.5;
        else
            priceRounded = integerPart + 1;

        averagePrice = priceRounded;
        Toast.makeText(this, "Μέση τιμή " + averagePrice, Toast.LENGTH_SHORT).show();


        //TODO if there is only 1 trip with price for example 10.7€ it will display as average price 10.5€

//        averagePrice /= Math.pow(10, (int) Math.log10(averagePrice));
//        averagePrice = ((int) (averagePrice * 10)) / 10.0f; // <-- performs one digit floor
//        Toast.makeText(this, "Ακέραιο " + integerPart + " Δεκαδικό " + decimalPart, Toast.LENGTH_SHORT).show();


    }
}
