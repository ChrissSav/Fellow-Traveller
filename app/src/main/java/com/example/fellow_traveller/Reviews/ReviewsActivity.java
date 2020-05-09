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

import com.example.fellow_traveller.Chat.MessageItem;
import com.example.fellow_traveller.Chat.MessagesAdapter;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ReviewItem> reviewsList = new ArrayList<>();
    private ImageButton writeReviewButton;
    private UserBaseModel userBaseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        writeReviewButton = findViewById(R.id.ActivityReviews_write_button);
        final Intent intent = getIntent();
        userBaseModel = (UserBaseModel) intent.getExtras().getParcelable("userToReview");

        reviewsList.add(new ReviewItem("Γιασεμής Μαραμένος", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Tyler Joseph" , "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Martin Garrix", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Tyler Joseph" , "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Martin Garrix", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Josh Dun", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Kshmr", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("21 Pilots Limouzin", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));


        mRecyclerView = findViewById(R.id.reviews_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ReviewsAdapter(reviewsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        writeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewsActivity.this, WriteReviewActivity.class);
                intent.putExtra("userToReview", (Parcelable) userBaseModel);
                startActivity(intent);
            }
        });
    }
}
