package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.ReviewModelCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Reviews.ReviewsActivity;


import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private UserBaseModel userBaseModel;
    private TextView userNameTextView, ratingTextView, reviewsTextView, aboutMeTextView;
    private Button seeAllReviewsButton;
    private ImageButton closeButton;
    private GlobalClass globalClass;
    private ArrayList<ReviewModel> reviewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        globalClass = (GlobalClass) getApplicationContext();

        userNameTextView = findViewById(R.id.ActivityProfile_user_name);
        ratingTextView = findViewById(R.id.ActivityProfile_rate);
        reviewsTextView = findViewById(R.id.ActivityProfile_reviews);
        aboutMeTextView = findViewById(R.id.ActivityProfile_about_me_info);
        seeAllReviewsButton = findViewById(R.id.ActivityProfile_more_reviews_button);
        closeButton = findViewById(R.id.ActivityProfile_close_button);

        final Intent intent = getIntent();
        userBaseModel = (UserBaseModel) intent.getExtras().getParcelable("ThisUser");

        userNameTextView.setText(userBaseModel.getFullName());
        ratingTextView.setText(String.valueOf(userBaseModel.getRate()));
        reviewsTextView.setText(String.valueOf(userBaseModel.getReviews()));
        aboutMeTextView.setText(userBaseModel.getAboutMe());


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

        new FellowTravellerAPI(globalClass).getUserReviews(userBaseModel.getId(), new ReviewModelCallBack() {
            @Override
            public void onSuccess(ArrayList<ReviewModel> reviews) {
                reviewsList = reviews;


            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }
}
