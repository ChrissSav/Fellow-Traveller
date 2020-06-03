package com.example.fellow_traveller.HomeFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Callbacks.ReviewModelCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Reviews.ReviewsActivity;
import com.example.fellow_traveller.Settings.AddCarSettingsActivity;
import com.example.fellow_traveller.Settings.CarsSettingsActivity;
import com.example.fellow_traveller.Settings.EditCarSettingsActivity;
import com.example.fellow_traveller.Settings.MyCarModelAdapter;
import com.example.fellow_traveller.Settings.UserSettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AccountFragment extends Fragment {
    private ImageButton settingsButton;
    private Button newCarButton, reviewsButton, manageCarsButton;
    private TextView textViewUserName, textViewAboutMe, rateTextView, reviewsTextView;
    private CircleImageView userProfileImage, reviewerUserImage;
    private GlobalClass globalClass;
    private ArrayList<ReviewModel> myReviews;
    private ArrayList<CarModel> myCarsList;
    private ConstraintLayout noReviewsSectionLayout, reviewsSectionLayout, haveCarsSectionLayout, dontHaveCarsSectionLayout;
    private TextView reviewerNameTextview, reviewDateTextview, reviewRateTextView, reviewTextTextView;
    private RecyclerView mRecyclerView;
    private MyCarModelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View view;


    public AccountFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        userProfileImage = view.findViewById(R.id.FragmentAccount_user_image);
        settingsButton = view.findViewById(R.id.settings_button);
        newCarButton = view.findViewById(R.id.new_car_button_account);
        reviewsButton = view.findViewById(R.id.FragmentAccount_review_see_all_button);
        textViewUserName = view.findViewById(R.id.FragmentAccount_user_name);
        textViewAboutMe = view.findViewById(R.id.FragmentAccount_about_me_info);
        rateTextView = view.findViewById(R.id.FragmentAccount_rate);
        reviewsTextView = view.findViewById(R.id.FragmentAccount_reviews);
        noReviewsSectionLayout = view.findViewById(R.id.FragmentAccount_no_reviews_section);
        reviewerNameTextview = view.findViewById(R.id.FragmentAccount_review_user_name);
        reviewDateTextview = view.findViewById(R.id.FragmentAccount_review_date);
        reviewTextTextView = view.findViewById(R.id.FragmentAccount_review_review_text);
        reviewRateTextView = view.findViewById(R.id.FragmentAccount_review_rating);
        reviewerUserImage = view.findViewById(R.id.FragmentAccount_review_user_image);
        reviewsSectionLayout = view.findViewById(R.id.FragmentAccount_review_section);
        manageCarsButton = view.findViewById(R.id.FragmentAccount_manage_cars_button);
        haveCarsSectionLayout = view.findViewById(R.id.FragmentAccount_my_cars_section);
        dontHaveCarsSectionLayout = view.findViewById(R.id.FragmentAccount_car_section);
        myReviews = new ArrayList<>();
        myCarsList = new ArrayList<>();

        //Change TextView Color
        //textViewAboutMe.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.orange_color));


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserSettingsActivity.class);
                startActivity(intent);
            }
        });

        newCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCarSettingsActivity.class);
                startActivity(intent);
            }
        });
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReviewsActivity.class);
                UserBaseModel userBaseModel = new UserBaseModel(globalClass.getCurrentUser().getId(), globalClass.getCurrentUser().getName(), globalClass.getCurrentUser().getSurname(),
                        globalClass.getCurrentUser().getRate(), globalClass.getCurrentUser().getReviews(), globalClass.getCurrentUser().getPicture());
                intent.putExtra("userToReview", (Parcelable) userBaseModel);
                startActivity(intent);
            }
        });
        manageCarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CarsSettingsActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        getMyCars();
        getMyReviews();
        textViewUserName.setText(globalClass.getCurrentUser().getFullName());
        if ((int) globalClass.getCurrentUser().getRate() == globalClass.getCurrentUser().getRate())
            rateTextView.setText(String.valueOf((int) globalClass.getCurrentUser().getRate()));
        else
            rateTextView.setText(String.valueOf(globalClass.getCurrentUser().getRate()));
        reviewsTextView.setText(String.valueOf(globalClass.getCurrentUser().getReviews()));
        try {
            if (globalClass.getCurrentUser().getPicture() != null)
                Picasso.get().load(globalClass.getCurrentUser().getPicture()).into(userProfileImage);
        } catch (Exception e) {
            //  Block of code to handle errors
        }

        //TODO να βάλω τα στοιχεία του χρήστη rate και review
        if (globalClass.getCurrentUser().getAboutMe() == null || globalClass.getCurrentUser().getAboutMe().length() < 1)
            textViewAboutMe.setText(globalClass.getResources().getString(R.string.account_fragment_about_me));
        else {
            textViewAboutMe.setText(globalClass.getCurrentUser().getAboutMe());

        }
    }

    public void getMyCars() {
        new FellowTravellerAPI(globalClass).getCars(new UserCarsCallBack() {
            @Override
            public void onSuccess(ArrayList<CarModel> carList) {
                //myCarsList = carList;
                if (carList.size() > 0) {
                    myCarsList.clear();
                    if (carList.size() > 3) {
                        for (int i = 0; i < 3; i++) {
                            myCarsList.add(carList.get(i));
                        }
                    } else {
                        myCarsList = carList;
                    }

                    dontHaveCarsSectionLayout.setVisibility(View.GONE);
                    haveCarsSectionLayout.setVisibility(View.VISIBLE);
                    mRecyclerView = view.findViewById(R.id.FragmentAccount_my_cars_recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(globalClass.getApplicationContext());
                    mAdapter = new MyCarModelAdapter(myCarsList);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                } else {
                    haveCarsSectionLayout.setVisibility(View.GONE);
                    dontHaveCarsSectionLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    public void getMyReviews() {
        new FellowTravellerAPI(globalClass).getUserReviews(globalClass.getCurrentUser().getId(), new ReviewModelCallBack() {
            @Override
            public void onSuccess(ArrayList<ReviewModel> reviews) {
                myReviews = reviews;
                if (myReviews.size() > 0) {

                    noReviewsSectionLayout.setVisibility(View.GONE);
                    reviewsSectionLayout.setVisibility(View.VISIBLE);
                    reviewsButton.setVisibility(View.VISIBLE);
                    reviewerNameTextview.setText(myReviews.get(0).getUser().getFullName());
                    reviewDateTextview.setText(myReviews.get(0).getDate());
                    reviewRateTextView.setText(String.valueOf(myReviews.get(0).getRate()));
                    reviewTextTextView.setText(myReviews.get(0).getDescription());
                    try {
                        Picasso.get().load(myReviews.get(0).getUser().getPicture()).into(reviewerUserImage);
                    } catch (Exception e) {
                        //  Block of code to handle errors
                    }


                } else {
                    reviewsSectionLayout.setVisibility(View.GONE);
                    reviewsButton.setVisibility(View.GONE);
                    noReviewsSectionLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

}
