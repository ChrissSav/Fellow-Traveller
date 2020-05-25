package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private UserBaseModel userBaseModel;
    private TextView userNameTextView, ratingTextView, reviewsTextView, aboutMeTextView;
    private Button seeAllReviewsButton, noReviewsButton;
    private ImageButton closeButton;
    private GlobalClass globalClass;
    private ArrayList<ReviewModel> reviewsList = new ArrayList<>();
    private ConstraintLayout reviewsLayout, noReviewsLayout;
    private CircleImageView userProfile, reviewerCircleImage;
    private TextView reviewerNameTextView, reviewDateTextView, reviewCommentsTextView, reviewRatingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        globalClass = (GlobalClass) getApplicationContext();

        userProfile = findViewById(R.id.ActivityProfile_user_image);
        userNameTextView = findViewById(R.id.ActivityProfile_user_name);
        ratingTextView = findViewById(R.id.ActivityProfile_rate);
        reviewsTextView = findViewById(R.id.ActivityProfile_reviews);
        aboutMeTextView = findViewById(R.id.ActivityProfile_about_me_info);
        seeAllReviewsButton = findViewById(R.id.ActivityProfile_more_reviews_button);
        closeButton = findViewById(R.id.ActivityProfile_close_button);
        reviewsLayout = findViewById(R.id.ActivityProfile_reviews_section);
        noReviewsLayout = findViewById(R.id.ActivityProfile_no_reviews_section);
        reviewerCircleImage = findViewById(R.id.ActivityProfile_review_user_image);
        reviewerNameTextView = findViewById(R.id.ActivityProfile_review_name);
        reviewDateTextView = findViewById(R.id.ActivityProfile_review_date);
        reviewCommentsTextView = findViewById(R.id.ActivityProfile_review_comments);
        reviewRatingTextView = findViewById(R.id.ActivityProfile_review_rating);
        noReviewsButton = findViewById(R.id.ActivityProfile_no_reviews_button);

        final Intent intent = getIntent();
        userBaseModel = (UserBaseModel) intent.getExtras().getParcelable("ThisUser");

       getUserProfileInfo();
       //TODO we need to load user info again to retrive the last review, now we only putExtra the non notified userBaseModel

        getUserReviewsInfo();

        seeAllReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ProfileActivity.this, ReviewsActivity.class);
                mainIntent.putExtra("userToReview", (Parcelable) userBaseModel);
                startActivity(mainIntent);
            }
        });
        noReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(ProfileActivity.this, ReviewsActivity.class);
                mainIntent.putExtra("userToReview", (Parcelable) userBaseModel);
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
    public void getUserReviewsInfo(){
        new FellowTravellerAPI(globalClass).getUserReviews(userBaseModel.getId(), new ReviewModelCallBack() {
            @Override
            public void onSuccess(ArrayList<ReviewModel> reviews) {
                reviewsList = reviews;
                if (reviewsList.size() == 0) {
                    reviewsLayout.setVisibility(View.GONE);
                    noReviewsLayout.setVisibility(View.VISIBLE);
                    //Toast.makeText(ProfileActivity.this, reviewsList.get(0).getDescription() , Toast.LENGTH_SHORT).show();
                }else {
                    reviewsLayout.setVisibility(View.VISIBLE);
                    noReviewsLayout.setVisibility(View.GONE);
                    reviewerNameTextView.setText(reviewsList.get(0).getUser().getFullName());
                    reviewCommentsTextView.setText(reviewsList.get(0).getDescription());
                    reviewDateTextView.setText(reviewsList.get(0).getDate());
                    reviewRatingTextView.setText(String.valueOf(reviewsList.get(0).getRate()));

                }
            }
            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
    public void getUserProfileInfo(){
        //Picasso.get().load(userBaseModel.getPicture()).into(userProfile);
        if(userBaseModel.getPicture() != null)
            Picasso.get().load(userBaseModel.getPicture()).into(userProfile);
        userNameTextView.setText(userBaseModel.getFullName());
        ratingTextView.setText(String.valueOf(userBaseModel.getRate()));
        reviewsTextView.setText(String.valueOf(userBaseModel.getReviews()));
        if(userBaseModel.getAboutMe()==null)
            aboutMeTextView.setText("Ο χρήστης " + userBaseModel.getFullName() + " δεν έχει ορίσει κάποια περιγραφή για τον εαυτό του" );
        else {
            if(userBaseModel.getAboutMe().trim().isEmpty())
                aboutMeTextView.setText("Ο χρήστης " + userBaseModel.getFullName() + " δεν έχει ορίσει κάποια περιγραφή για τον εαυτό του" );
            else
                aboutMeTextView.setText(userBaseModel.getAboutMe());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserProfileInfo();
        getUserReviewsInfo();
    }
}
