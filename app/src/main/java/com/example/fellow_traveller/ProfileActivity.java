package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Reviews.ReviewsActivity;
import com.example.fellow_traveller.SearchAndBook.Search2Activity;
import com.example.fellow_traveller.SearchAndBook.SearchActivity;
import com.example.fellow_traveller.SearchAndBook.SearchPassengersActivity;

public class ProfileActivity extends AppCompatActivity {
    private UserBaseModel userBaseModel;
    private TextView userNameTextView, ratingTextView, reviewsTextView;
    private Button seeAllReviewsButton;
    private ImageButton closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameTextView = findViewById(R.id.ActivityProfile_user_name);
        ratingTextView = findViewById(R.id.ActivityProfile_rate);
        reviewsTextView = findViewById(R.id.ActivityProfile_reviews);
        seeAllReviewsButton = findViewById(R.id.ActivityProfile_more_reviews_button);
        closeButton = findViewById(R.id.ActivityProfile_close_button);

        final Intent intent = getIntent();
        userBaseModel = (UserBaseModel) intent.getExtras().getParcelable("ThisUser");

        userNameTextView.setText(userBaseModel.getFullName());
        ratingTextView.setText(String.valueOf(userBaseModel.getRate()));
        reviewsTextView.setText(String.valueOf(userBaseModel.getReviews()));

        seeAllReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ProfileActivity.this, ReviewsActivity.class);
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
