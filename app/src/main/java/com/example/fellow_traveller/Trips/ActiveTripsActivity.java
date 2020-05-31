package com.example.fellow_traveller.Trips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class ActiveTripsActivity extends AppCompatActivity {
    private ImageButton closeButton;
    private GlobalClass globalClass;
    private RecyclerView mRecyclerView;
    private ActiveTripsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TripInvolvedModel> activeTripsList = new ArrayList<>();
    private boolean isDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_trips);

        closeButton = findViewById(R.id.ActivityActiveTrips_close_button);


        activeTripsList = this.getIntent().getExtras().getParcelableArrayList("activeTripsList");
        Bundle bundle = getIntent().getExtras();
        isDriver = bundle.getBoolean("isDriver");

        mRecyclerView= findViewById(R.id.ActivityActiveTrips_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ActiveTripsAdapter(activeTripsList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ActiveTripsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mainIntent = new Intent(ActiveTripsActivity.this, TripPageDriverActivity.class);
                mainIntent.putExtra("isCompleted", false);
                if(isDriver)
                    mainIntent.putExtra("isDriver", true);
                else
                    mainIntent.putExtra("isDriver", false);
                mainIntent.putExtra("trip", activeTripsList.get(position));
                startActivity(mainIntent);
            }
        });

                closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
