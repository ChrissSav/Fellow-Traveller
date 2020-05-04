package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchDetailsActivity extends AppCompatActivity {
    private Button bookButton;
    private RecyclerView mRecyclerView;
    private PassengersAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);

        bookButton = findViewById(R.id.book_details_search_button);
        mRecyclerView = findViewById(R.id.ActivitySearchDetails_passenger_recycler_view);
        ArrayList<PassengerModel> passengersList = new ArrayList<>();


        mRecyclerView = findViewById(R.id.search_results_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PassengersAdapter(passengersList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, BookActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
