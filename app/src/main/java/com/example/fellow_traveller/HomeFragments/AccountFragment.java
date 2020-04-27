package com.example.fellow_traveller.HomeFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Reviews.ReviewsActivity;
import com.example.fellow_traveller.Settings.AddCarSettingsActivity;
import com.example.fellow_traveller.Settings.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private ImageButton settingsButton;
    private Button newCarButton, reviewsButton;
    private TextView textViewUserName;
    private GlobalClass globalClass;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);



        settingsButton =  view.findViewById(R.id.settings_button);
        newCarButton = view.findViewById(R.id.new_car_button_account);
        reviewsButton = view.findViewById(R.id.fragment_account_reviews_button);
        textViewUserName = view.findViewById(R.id.user_name);




        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
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

    }
}
