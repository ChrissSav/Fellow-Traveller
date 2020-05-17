package com.example.fellow_traveller.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.MapDirections.MapsRouteActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private TextView searchForTrip, welcome_user;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayout2;
    private View view;
    private GlobalClass globalClass;
    private CircleImageView circleImageView;
    private Button btnΝewΟffer, mycrazybutton;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        circleImageView= view.findViewById(R.id.image_icon_home);
        searchForTrip = view.findViewById(R.id.FragmentHome_textView_Search);
        constraintLayout = view.findViewById(R.id.Layout2);
        constraintLayout2 = view.findViewById(R.id.Layout3);
        welcome_user = view.findViewById(R.id.FragmentHome_textView_welcome_user);
        btnΝewΟffer = view.findViewById(R.id.FragmentHome_button_CreateTrip);
        mycrazybutton = view.findViewById(R.id.mycrazybutton);

        mycrazybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(), MapsRouteActivity.class);
                startActivity(mainIntent);
            }
        });

        welcome_user.setText("Γεια σου " + globalClass.getCurrentUser().getName() + ",");
        searchForTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(mainIntent);
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Το πατησες", Toast.LENGTH_SHORT).show();
            }
        });
        btnΝewΟffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(), NewOfferActivity.class);
                startActivity(mainIntent);
            }
        });
        return view;
    }

}