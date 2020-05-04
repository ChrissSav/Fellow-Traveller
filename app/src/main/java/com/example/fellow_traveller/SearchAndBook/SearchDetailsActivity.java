package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

public class SearchDetailsActivity extends AppCompatActivity {
    private Button bookButton;
    private TextView textViewDestFrom, textViewDestTo, textViewDate,
            textViewTime, textViewSeats, textViewBags, textViewPets, textViewCar, textViewMsg,textViewPrice;
    private Button buttonCreator, buttonRate;
    private TripModel tripModel;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_details);
        globalClass = (GlobalClass) getApplicationContext();

        tripModel = getIntent().getParcelableExtra("trip");

        bookButton = findViewById(R.id.book_details_search_button);
        buttonCreator = findViewById(R.id.user_name_search_details_button);
        buttonRate = findViewById(R.id.rate_and_reviews_search_details_button);
        textViewPrice = findViewById(R.id. price_search_details);
        textViewDestFrom = findViewById(R.id.from_detail_search);
        textViewDestTo = findViewById(R.id.to_detail_search);
        textViewDate = findViewById(R.id.date_search_details);
        textViewTime = findViewById(R.id.time_search_details);
        textViewSeats = findViewById(R.id.seats_search_details);
        textViewBags = findViewById(R.id.bags_search_details);
        textViewPets = findViewById(R.id.pet_search_details);
        textViewCar = findViewById(R.id.car_search_details);
        textViewMsg = findViewById(R.id.driver_message_search_details_tv);
        textViewDestFrom.setText(tripModel.getDestFrom());
        textViewDestTo.setText(tripModel.getDestTo());
        textViewDate.setText(tripModel.getDate());
        textViewTime.setText(tripModel.getTime());
        textViewSeats.setText(tripModel.getSeatStatus());
        textViewBags.setText(tripModel.getBagsStatus());
        textViewPets.setText(tripModel.getPetString());
        textViewCar.setText(tripModel.getCar().getDescription());
        textViewMsg.setText(tripModel.getMsg());
        textViewPrice.setText(tripModel.getPrice()+getResources().getString(R.string.euro_symbol));
        buttonCreator.setText(tripModel.getCreatorUser().getFullName());
        buttonRate.setText(tripModel.getCreatorUser().getRate() + " | " + tripModel.getCreatorUser().getReviews());

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchDetailsActivity.this, BookActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
