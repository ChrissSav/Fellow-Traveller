package com.example.fellow_traveller.HomeFragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchDestinationsActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private TextView welcomeUser;
    private View view;
    private GlobalClass globalClass;
    private CircleImageView circleImageView;
    private Button searchButton;
    private ConstraintLayout offerLayoutButton;


    public HomeFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        welcomeUser = view.findViewById(R.id.FragmentHome_user_welcome_textView);
        searchButton = view.findViewById(R.id.FragmentHome_search_button);
        offerLayoutButton = view.findViewById(R.id.FragmentHome_offer_section);


        welcomeUser.setText(globalClass.getCurrentUser().getName() + ",");
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(getActivity(), SearchDestinationsActivity.class);
                startActivity(mainIntent);
            }
        });

        offerLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(), NewOfferActivity.class);
                startActivity(mainIntent);
            }
        });

        return view;
    }

}