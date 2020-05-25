package com.example.fellow_traveller.HomeFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.ClientAPI.Callbacks.ReviewModelCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.ReviewModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Reviews.ReviewsActivity;
import com.example.fellow_traveller.Settings.AddCarSettingsActivity;
import com.example.fellow_traveller.Settings.UserSettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private ImageButton settingsButton;
    private Button newCarButton, reviewsButton;
    private TextView textViewUserName, textViewAboutMe, rateTextView, reviewsTextView;
    private CircleImageView userProfileImage, reviewerUserImage;
    private GlobalClass globalClass;
    private ArrayList<ReviewModel> myReviews;
    private ConstraintLayout noReviewsSection, reviewsSection;
    private TextView reviewerNameTextview, reviewDateTextview, reviewRateTextView, reviewTextTextView;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        userProfileImage = view.findViewById(R.id.FragmentAccount_user_image);
        settingsButton = view.findViewById(R.id.settings_button);
        newCarButton = view.findViewById(R.id.new_car_button_account);
        reviewsButton = view.findViewById(R.id.FragmentAccount_review_see_all_button);
        textViewUserName = view.findViewById(R.id.FragmentAccount_user_name);
        textViewAboutMe = view.findViewById(R.id.FragmentAccount_about_me_info);
        rateTextView = view.findViewById(R.id.FragmentAccount_rate);
        reviewsTextView = view.findViewById(R.id.FragmentAccount_reviews);
        noReviewsSection = view.findViewById(R.id.FragmentAccount_no_reviews_section);
        reviewerNameTextview = view.findViewById(R.id.FragmentAccount_review_user_name);
        reviewDateTextview = view.findViewById(R.id.FragmentAccount_review_date);
        reviewTextTextView = view.findViewById(R.id.FragmentAccount_review_review_text);
        reviewRateTextView = view.findViewById(R.id.FragmentAccount_review_out_of_five_tv);
        reviewerUserImage = view.findViewById(R.id.FragmentAccount_review_user_image);
        reviewsSection = view.findViewById(R.id.FragmentAccount_review_section);
        myReviews = new ArrayList<>();


        new FellowTravellerAPI(globalClass).getUserReviews(globalClass.getCurrentUser().getId(), new ReviewModelCallBack() {
            @Override
            public void onSuccess(ArrayList<ReviewModel> reviews) {
            myReviews = reviews;
            if(myReviews.size() > 0){
                noReviewsSection.setVisibility(View.GONE);
                reviewsSection.setVisibility(View.VISIBLE);
                reviewsButton.setVisibility(View.VISIBLE);
                reviewerNameTextview.setText(myReviews.get(0).getUser().getFullName());
                reviewDateTextview.setText(myReviews.get(0).getDate());
                reviewRateTextView.setText(String.valueOf(myReviews.get(0).getRate()));
                reviewTextTextView.setText(myReviews.get(0).getDescription());
                if(myReviews.get(0).getUser().getPicture() != null)
                    Picasso.get().load(myReviews.get(0).getUser().getPicture()).into(reviewerUserImage);
            }else{
                reviewsSection.setVisibility(View.GONE);
                reviewsButton.setVisibility(View.GONE);
                noReviewsSection.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

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
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        textViewUserName.setText(globalClass.getCurrentUser().getFullName());
        if(globalClass.getCurrentUser().getPicture() != null)
            Picasso.get().load(globalClass.getCurrentUser().getPicture()).into(userProfileImage);
       //TODO να βάλω τα στοιχεία του χρήστη rate και review

        if (globalClass.getCurrentUser().getAboutMe() == null || globalClass.getCurrentUser().getAboutMe().length() < 1)
            textViewAboutMe.setText(globalClass.getResources().getString(R.string.account_fragment_about_me));
        else {
            textViewAboutMe.setText(globalClass.getCurrentUser().getAboutMe());

        }

    }
}
