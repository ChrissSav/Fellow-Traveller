package com.example.fellow_traveller.Trips;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.Chat.ConversationAdapter;
import com.example.fellow_traveller.Chat.ConversationItem;
import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchsTabLayout extends Fragment {
    View view;
    private GlobalClass globalClass;
    private RecyclerView mRecyclerViewActive, mRecyclerViewNotActive;
    private ActiveTripsAdapter mAdapterActive, mAdapterNotActive;
    private RecyclerView.LayoutManager mLayoutManagerActive, mLayoutManagerNotActive;
    private ArrayList<TripModel> activeTrips = new ArrayList<>();
    private ArrayList<TripModel> notActiveTrips = new ArrayList<>();
    private TextView activeTripsTextview, notActiveTripsTextView;
    private ImageView notFoundImage;
    private Button searchButton;


    public SearchsTabLayout() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_searchs, container, false);
        //myContext = container.getContext();
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        activeTripsTextview = view.findViewById(R.id.fragment_trip_searchs_active_trips_textView);
        notActiveTripsTextView = view.findViewById(R.id.fragment_trip_searchs_completed_label);
        notFoundImage = view.findViewById(R.id.fragment_trip_searchs_image_not_found);
        searchButton = view.findViewById(R.id.fragment_trip_searchs_button);


        new FellowTravellerAPI(globalClass).getTripsAsPassenger( new SearchTripsCallback() {
            @Override
            public void onSuccess(ArrayList<TripModel> trips) {

                if (trips.size() > 0) {
                    for(int i = 0; i < trips.size(); i++){
                        if(trips.get(i).getActive())
                            activeTrips.add(trips.get(i));
                        else
                            notActiveTrips.add(trips.get(i));
                    }
                    if (activeTrips.size() > 0) {
                        mRecyclerViewActive = view.findViewById(R.id.fragment_trip_searchs_recycler_view);
                        mRecyclerViewActive.setHasFixedSize(true);
                        mLayoutManagerActive = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        mAdapterActive = new ActiveTripsAdapter(activeTrips);
                        mRecyclerViewActive.setLayoutManager(mLayoutManagerActive);
                        mRecyclerViewActive.setAdapter(mAdapterActive);
                        activeTripsTextview.setVisibility(View.VISIBLE);
                        notFoundImage.setVisibility(View.GONE);
                        searchButton.setVisibility(View.GONE);
                        mRecyclerViewActive.setVisibility(View.VISIBLE);
                        if (activeTrips.size() == 1)
                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTrips.size()) + " ενεργό ταξίδι");
                        else
                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTrips.size()) + " ενεργά ταξίδια");
                    }
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
