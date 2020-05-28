package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.fellow_traveller.Chat.MessagesAdapter;
import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.ProfileActivity;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchPassengersActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PassengersAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ImageButton closeButton;
    private ArrayList<PassengerModel> passengersList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_passengers);

        passengersList = this.getIntent().getExtras().getParcelableArrayList("tripPassengers");
       // passengersList = getIntent().getParcelableExtra("tripPassengers");
//        UserBaseModel userBaseModel = new UserBaseModel(1, "Tyler", "Joseph", 4.7, 34, "default");
//        PassengerModel passengerModel = new PassengerModel(userBaseModel, 3, true);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);


        closeButton = findViewById(R.id.ActivitySearchPassengers_close_button);
        mRecyclerView = findViewById(R.id.ActivitySearchPassengers_recycler_view);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PassengersAdapter(passengersList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PassengersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mainIntent = new Intent(SearchPassengersActivity.this, ProfileActivity.class);
                mainIntent.putExtra("ThisUser", passengersList.get(position).getUser());
                startActivity(mainIntent);
                finish();
            }
        });



        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
