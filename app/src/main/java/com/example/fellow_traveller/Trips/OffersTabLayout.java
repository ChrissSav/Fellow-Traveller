package com.example.fellow_traveller.Trips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OffersTabLayout extends Fragment {
    View view;
    private GlobalClass globalClass;
    private RecyclerView mRecyclerViewActive;
    private ActiveTripsAdapter mAdapterActive;
    private RecyclerView.LayoutManager mLayoutManagerActive;
    private ArrayList<TripModel> activeTrips = new ArrayList<>();
    private ArrayList<TripModel> notActiveTrips = new ArrayList<>();
    private TextView activeTripsTextview;
    private ImageView notFoundImage;
    private Button searchButton;

    public OffersTabLayout() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_offers, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        activeTripsTextview = view.findViewById(R.id.fragment_trip_offers_active_trips_textView);
        notFoundImage = view.findViewById(R.id.fragment_trip_offers_image_not_found);
        searchButton = view.findViewById(R.id.fragment_trip_offers_button);

        new FellowTravellerAPI(globalClass).getTripsAsCreator(new SearchTripsCallback() {
            @Override
            public void onSuccess(ArrayList<TripModel> trips) {
                if (trips.size() > 0) {
                    for(int i = 0; i < trips.size(); i++){
                        if(trips.get(i).getActive())
                            activeTrips.add(trips.get(i));
                        else
                            notActiveTrips.add(trips.get(i));
                    }
                    mRecyclerViewActive = view.findViewById(R.id.fragment_trip_offers_recycler_view);
                    mRecyclerViewActive.setHasFixedSize(true);
                    mLayoutManagerActive = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    mAdapterActive = new ActiveTripsAdapter(activeTrips);
                    mRecyclerViewActive.setLayoutManager(mLayoutManagerActive);
                    mRecyclerViewActive.setAdapter(mAdapterActive);
                    activeTripsTextview.setVisibility(View.VISIBLE);
                    notFoundImage.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                    mRecyclerViewActive.setVisibility(View.VISIBLE);
                    if (activeTrips.size()==1)
                        activeTripsTextview.setText("Έχετε " + String.valueOf(activeTrips.size()) + " ενεργό ταξίδι");
                    else
                        activeTripsTextview.setText("Έχετε " + String.valueOf(activeTrips.size()) + " ενεργά ταξίδια");

                }
                else
                    Toast.makeText(getActivity(),"No trips",Toast.LENGTH_SHORT).show();
                //activeTripsTextview.setText("Δεν έχετε κάποιο ενεργό ταξίδι αυτήν την στιγμή");
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

        return view;
    }
    
}
