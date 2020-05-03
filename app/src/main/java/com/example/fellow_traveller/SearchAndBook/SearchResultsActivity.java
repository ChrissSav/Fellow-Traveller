package com.example.fellow_traveller.SearchAndBook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private TextView to, from;
    private RecyclerView mRecyclerView;
    private SearchResultsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton filterButton, swapButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        GlobalClass globalClass = (GlobalClass) getApplicationContext();

        from = findViewById(R.id.from_search_results);
        to = findViewById(R.id.to_search_results);
        filterButton = findViewById(R.id.filter_search_result_button);
        swapButton = findViewById(R.id.swap_search_results_button);
        backButton = findViewById(R.id.close_search_result_button);

        Intent intent = getIntent();
        final String toString = intent.getExtras().getString("ToPlace");
        Intent intent2 = getIntent();
        final String fromString = intent.getExtras().getString("FromPlace");
        to.setText(toString);
        from.setText(fromString);

        final ArrayList<TripModel> resultList = new ArrayList<>();
//        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));
//        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));
//        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));
//        resultList.add(new SearchResultItem("Martin Garrix","4.9","1033","Athens","Filotas", "7 Apr 2020", "10:00"));


        new FellowTravellerAPI(globalClass).getTrips(toString, fromString, null, null, null, null, null, null,
                null, null, null, new SearchTripsCallback() {
                    @Override
                    public void onSuccess(ArrayList<TripModel> trips) {
                        resultList.addAll(trips);
                        mRecyclerView = findViewById(R.id.search_results_recycler_view);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mAdapter = new SearchResultsAdapter(resultList);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(mAdapter);

                        mAdapter.setOnItemClickListener(new SearchResultsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent mainIntent = new Intent(SearchResultsActivity.this, SearchDetailsActivity.class);
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
                String swapString = from.getText().toString();
                from.setText(to.getText().toString());
                to.setText(swapString);
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
}
