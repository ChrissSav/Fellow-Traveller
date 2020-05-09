package com.example.fellow_traveller.SearchAndBook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.ProfileActivity;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchDetailsActivity extends AppCompatActivity {
    private Button bookButton;
    private RecyclerView mRecyclerView;
    private PassengerImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView textViewCreator, textViewRating, textViewReviews, textViewDestFrom, textViewDestTo, textViewDate,
            textViewTime, textViewSeats, textViewBags, textViewPets, textViewCar, textViewMsg, textViewPrice;
    private Button passengersButton;
    private TripModel tripModel;
    private ImageButton imageButtonBack;
    private GlobalClass globalClass;
    private ArrayList<PassengerModel> passengersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        globalClass = (GlobalClass) getApplicationContext();
        tripModel = getIntent().getParcelableExtra("trip");

        bookButton = findViewById(R.id.ActivitySearchDetails_book_button);
        imageButtonBack = findViewById(R.id.ActivitySearchDetails_back_button);

        textViewCreator = findViewById(R.id.ActivitySearchDetails_user_name);
        textViewRating = findViewById(R.id.ActivitySearchDetails_rating_tv);
        textViewReviews = findViewById(R.id.ActivitySearchDetails_reviews_tv);
        textViewPrice = findViewById(R.id.ActivitySearchDetails_price_tv);
        textViewDestFrom = findViewById(R.id.ActivitySearchDetails_from_dest_tv);
        textViewDestTo = findViewById(R.id.ActivitySearchDetails_to_dest_tv);
        textViewDate = findViewById(R.id.ActivitySearchDetails_date_tv);
        textViewTime = findViewById(R.id.ActivitySearchDetails_time_tv);
        textViewSeats = findViewById(R.id.ActivitySearchDetails_seats_tv);
        textViewBags = findViewById(R.id.ActivitySearchDetails_bags_tv);
        textViewPets = findViewById(R.id.ActivitySearchDetails_pets_tv);
        textViewCar = findViewById(R.id.ActivitySearchDetails_car_tv);
        textViewMsg = findViewById(R.id.ActivitySearchDetails_driver_message_tv);
        passengersButton = findViewById(R.id.ActivitySearchDetails_more_passengers_button);


        textViewDestFrom.setText(tripModel.getDestFrom().getTitle());
        textViewDestTo.setText(tripModel.getDestTo().getTitle());
        textViewDate.setText(tripModel.getDate());
        textViewTime.setText(tripModel.getTime());
        textViewSeats.setText(tripModel.getSeatStatus());
        textViewBags.setText(tripModel.getBagsStatus());
        textViewPets.setText(tripModel.getPetString());
        textViewCar.setText(tripModel.getCar().getBrand());
        textViewMsg.setText(tripModel.getMessage());

        //Delete 0 decimals
        if(tripModel.getPrice().intValue() == tripModel.getPrice())
            textViewPrice.setText(tripModel.getPrice().intValue() + getResources().getString(R.string.euro_symbol));
        else
            textViewPrice.setText(tripModel.getPrice() + getResources().getString(R.string.euro_symbol));

        textViewCreator.setText(tripModel.getCreatorUser().getFullName());
        textViewRating.setText(String.valueOf(tripModel.getCreatorUser().getRate()));
        textViewReviews.setText(String.valueOf(tripModel.getCreatorUser().getReviews()));
        passengersList = tripModel.getPassengers();


//        UserBaseModel userBaseModel = new UserBaseModel(1, "Tyler",   "Joseph", 4.7, 34, "default" );
//        PassengerModel passengerModel = new PassengerModel(userBaseModel, 3, true);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);
//        passengersList.add(passengerModel);

        try{
            Log.i("passengersList",passengersList.size()+"  |   "+tripModel.getPassengers().size());

        }catch (Exception e ){
            Log.i("passengersList",e.toString());

        }

        mRecyclerView = findViewById(R.id.ActivitySearchDetails_passenger_recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new PassengerImageAdapter(passengersList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);


        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        passengersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, SearchPassengersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("tripPassengers", tripModel.getPassengers());
                mainIntent.putExtras(bundle);
                //mainIntent.putExtra("tripPassengers",  tripModel.getPassengers());
                startActivity(mainIntent);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, BookActivity.class);
                mainIntent.putExtra("trip", tripModel);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}















