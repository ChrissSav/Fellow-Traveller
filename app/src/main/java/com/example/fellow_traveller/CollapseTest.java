package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fellow_traveller.Reviews.ReviewItem;
import com.example.fellow_traveller.Reviews.ReviewsAdapter;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CollapseTest extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ReviewItem> reviewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse_test);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        reviewsList.add(new ReviewItem("Γιασεμής Μαραμένος", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Tyler Joseph" , "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Martin Garrix", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Tyler Joseph" , "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Martin Garrix", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Josh Dun", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("Kshmr", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));
        reviewsList.add(new ReviewItem("21 Pilots Limouzin", "22 Ιαν 2020", "3,5", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam"));


        mRecyclerView = findViewById(R.id.ActivitySearchResults_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ReviewsAdapter(reviewsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }


}
