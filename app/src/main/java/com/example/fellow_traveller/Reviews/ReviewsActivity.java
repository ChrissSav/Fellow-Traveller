package com.example.fellow_traveller.Reviews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.Chat.MessageItem;
import com.example.fellow_traveller.Chat.MessagesAdapter;
import com.example.fellow_traveller.ClientAPI.Callbacks.ReviewModelCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchResultsActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ReviewsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<ReviewModel> reviewsList = new ArrayList<>();
    private ImageButton backButton, sortButton;
    private UserBaseModel userBaseModel;
    private GlobalClass globalClass;
    private TextView numberOfReviews , sortMethodTextView;
    private CoordinatorLayout mainCoordinatorLayout;
    private int sortListMethodFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        globalClass = (GlobalClass) getApplicationContext();


        numberOfReviews = findViewById(R.id.reviews_num_of_reviews);
        sortMethodTextView = findViewById(R.id.reviews_sort_by);
        backButton = findViewById(R.id.reviews_back_button);
        sortButton = findViewById(R.id.reviews_filter_button);
        mainCoordinatorLayout = findViewById(R.id.ActivityReviews_main_coordinatorLayout);


        final Intent intent = getIntent();
        userBaseModel = (UserBaseModel) intent.getExtras().getParcelable("userToReview");

        if(userBaseModel.getReviews() == 0)
            numberOfReviews.setText("Καμία κριτική");
        else if(userBaseModel.getReviews() == 1)
            numberOfReviews.setText("1 κριτική");
        else
            numberOfReviews.setText(userBaseModel.getReviews() + " κριτικές");


        new FellowTravellerAPI(globalClass).getUserReviews(userBaseModel.getId(), new ReviewModelCallBack() {
            @Override
            public void onSuccess(ArrayList<ReviewModel> reviews) {
                reviewsList = reviews;

                if(reviewsList.size() > 0){

                    if(sortListMethodFlag==0)
                        Collections.sort(reviewsList, ReviewModel.DateComparator);
                    else if(sortListMethodFlag==1)
                        Collections.sort(reviewsList, ReviewModel.RatesComparator);

                    buildRecyclerView();

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
        sortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                sortDialog();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sortDialog(){
        final Dialog myDialog = new Dialog(ReviewsActivity.this, R.style.Theme_Dialog);
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.sort_reviews_options_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mainCoordinatorLayout.setForeground(new ColorDrawable(getResources().getColor(R.color.greyBlack_transparent_color)));
        window.setAttributes(wlp);
        myDialog.show();

        final Button moreRelevant = myDialog.findViewById(R.id.sort_reviews_options_dialog_label_relevant_button);
        final Button byRates = myDialog.findViewById(R.id.sort_reviews_options_dialog_label_rates_button);


        moreRelevant.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                mainCoordinatorLayout.setForeground(null);
                sortListMethodFlag = 0;
                Collections.sort(reviewsList, ReviewModel.DateComparator);
                sortMethodTextView.setText("Πιο πρόσφατα");
                mAdapter.notifyDataSetChanged();
                myDialog.dismiss();

            }
        });
        byRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainCoordinatorLayout.setForeground(null);
                sortListMethodFlag = 1;
                Collections.sort(reviewsList, ReviewModel.RatesComparator);
                sortMethodTextView.setText("Αξιολογήσεις");
                mAdapter.notifyDataSetChanged();
                myDialog.dismiss();
            }

        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mainCoordinatorLayout.setForeground(null);
            }
        });

    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.reviews_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ReviewsActivity.this);
        mAdapter = new ReviewsAdapter(reviewsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
