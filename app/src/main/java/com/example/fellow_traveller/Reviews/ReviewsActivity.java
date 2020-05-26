package com.example.fellow_traveller.Reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.Chat.MessageItem;
import com.example.fellow_traveller.Chat.MessagesAdapter;
import com.example.fellow_traveller.ClientAPI.Callbacks.ReviewModelCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ReviewModel> reviewsList = new ArrayList<>();
    private ImageButton backButton, sortButton;
    private UserBaseModel userBaseModel;
    private GlobalClass globalClass;
    private TextView numberOfReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        globalClass = (GlobalClass) getApplicationContext();


        numberOfReviews = findViewById(R.id.reviews_num_of_reviews);
        backButton = findViewById(R.id.reviews_back_button);
        sortButton = findViewById(R.id.reviews_filter_button);


        final Intent intent = getIntent();
        userBaseModel = (UserBaseModel) intent.getExtras().getParcelable("userToReview");

        if(userBaseModel.getReviews() == 0)
            numberOfReviews.setText("Καμία κριτική");
        else if(userBaseModel.getReviews() == 1)
            numberOfReviews.setText("1 κριτική");
        else
            numberOfReviews.setText(userBaseModel.getReviews() + " κριτικές");

//        reviewsList.add(new ReviewItem("Γιασεμής Μαραμένος", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("Tyler Joseph" , "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("Martin Garrix", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("Tyler Joseph" , "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("Martin Garrix", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("Josh Dun", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("Kshmr", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
//        reviewsList.add(new ReviewItem("21 Pilots Limouzin", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));

        new FellowTravellerAPI(globalClass).getUserReviews(userBaseModel.getId(), new ReviewModelCallBack() {
            @Override
            public void onSuccess(ArrayList<ReviewModel> reviews) {
                reviewsList = reviews;

                if(reviewsList.size() > 0){
                    mRecyclerView = findViewById(R.id.reviews_recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(ReviewsActivity.this);
                    mAdapter = new ReviewsAdapter(reviewsList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
