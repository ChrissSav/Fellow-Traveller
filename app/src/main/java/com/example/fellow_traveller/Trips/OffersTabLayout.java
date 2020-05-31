package com.example.fellow_traveller.Trips;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.TripInvolvedCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import de.hdodenhof.circleimageview.CircleImageView;

public class OffersTabLayout extends Fragment {
    View view;
    private GlobalClass globalClass;
    private RecyclerView mRecyclerViewActive, mRecyclerViewNotActive;
    private ActiveTripsAdapter mAdapterActive;
    private CompletedTripsAdapter mAdapterNotActive;
    private RecyclerView.LayoutManager mLayoutManagerActive, mLayoutManagerNotActive;
    private ArrayList<TripInvolvedModel> activeTripsList = new ArrayList<>();
    private ArrayList<TripInvolvedModel> completedTripsList = new ArrayList<>();
    private TextView activeTripsTextview;
    private Button moreActiveTripsButton;
    private ConstraintLayout createTripLayoutButton;
    private ConstraintLayout activeTripsSectionLayout, noActiveTripsSectionLayout, completedTripsSectionLayout, cardLayoutSection;
    private TextView destinationCardText, dateCardText, timeCardText, priceCardText, creatorNameCardText, creatorRateCardText, myBookCardText;
    private CircleImageView creatorCardImage;

//    private ViewPager2 activeTripsViewPager;

    public OffersTabLayout() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_offers, container, false);
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        activeTripsTextview = view.findViewById(R.id.FragmentTrip_offers_active_trips_textView);
        createTripLayoutButton = view.findViewById(R.id.FragmentTrip_offers_no_trips_section_button_layout);
        activeTripsSectionLayout = view.findViewById(R.id.FragmentTrip_offers_active_trips_section);
        noActiveTripsSectionLayout = view.findViewById(R.id.FragmentTrip_offers_no_trips_section);
        completedTripsSectionLayout = view.findViewById(R.id.FragmentTrip_offers_completed_trips_section);

        destinationCardText = view.findViewById(R.id.FragmentTrip_offers_destinations_TextView);
        dateCardText = view.findViewById(R.id.FragmentTrip_offers_date_textView);
        timeCardText = view.findViewById(R.id.FragmentTrip_offers_time_textView);
        priceCardText = view.findViewById(R.id.FragmentTrip_offers_price_textView);
        creatorNameCardText = view.findViewById(R.id.FragmentTrip_offers_creator_name);
        creatorRateCardText = view.findViewById(R.id.FragmentTrip_offers_creator_rate_and_reviews);
        myBookCardText = view.findViewById(R.id.FragmentTrip_offers_my_book_details_textView);
        creatorCardImage = view.findViewById(R.id.FragmentTrip_offers_creator_image);
        moreActiveTripsButton = view.findViewById(R.id.FragmentTrip_offers_all_activeTrips_button);
        cardLayoutSection = view.findViewById(R.id.FragmentTrip_offers_card_section);


        new FellowTravellerAPI(globalClass).getTripsAsCreatorTest(new TripInvolvedCallBack() {
            @Override
            public void onSuccess(ArrayList<TripInvolvedModel> trips) {
                //Check if user has any trips
                if (trips.size() > 0) {
                    //Seperate trips to active and completed
                    for (int i = 0; i < trips.size(); i++) {
                        if (trips.get(i).getActive())
                            activeTripsList.add(trips.get(i));
                        else
                            completedTripsList.add(trips.get(i));
                    }
                    //check if user has active trips
                    if (activeTripsList.size() > 0) {

                        noActiveTripsSectionLayout.setVisibility(View.GONE);
                        activeTripsSectionLayout.setVisibility(View.VISIBLE);

                        //<------------Fill the cardview ------------>
                        destinationCardText.setText(activeTripsList.get(0).getDestFrom().getTitle() + " - " + activeTripsList.get(0).getDestTo().getTitle());
                        dateCardText.setText(activeTripsList.get(0).getDate());
                        timeCardText.setText(activeTripsList.get(0).getTime());
                        if (Math.round(activeTripsList.get(0).getPrice()) == activeTripsList.get(0).getPrice())
                            priceCardText.setText(Math.round(activeTripsList.get(0).getPrice()) + "€");
                        else
                            priceCardText.setText(activeTripsList.get(0).getPrice() + "€");

                        if (activeTripsList.get(0).getPassengers().size() == 0)
                            myBookCardText.setText("Δεν έχετε κάποιον επιβάτη ακόμα");
                        else if (activeTripsList.get(0).getPassengers().size() == 1)
                            myBookCardText.setText("Έχετε 1 επιβάτη");
                        else
                            myBookCardText.setText("Έχετε " + activeTripsList.get(0).getPassengers().size() + " επιβάτες");

                        creatorNameCardText.setText("(Εσείς) " + activeTripsList.get(0).getCreatorUser().getFullName());
                        creatorRateCardText.setText(activeTripsList.get(0).getCreatorUser().getRate() + " (" + activeTripsList.get(0).getCreatorUser().getReviews() + ")");
                        try {
                            Picasso.get().load(activeTripsList.get(0).getCreatorUser().getPicture()).into(creatorCardImage);

                        } catch (Exception e) {
                            //  Block of code to handle errors
                        }
                        if (activeTripsList.size() == 1)
                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTripsList.size()) + " ενεργό ταξίδι");
                        else
                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTripsList.size()) + " ενεργά ταξίδια");


                    } else {
                        activeTripsSectionLayout.setVisibility(View.GONE);
                        noActiveTripsSectionLayout.setVisibility(View.VISIBLE);
                    }

                    //Check if user has completed trips
                    if (completedTripsList.size() > 0) {

                        completedTripsSectionLayout.setVisibility(View.VISIBLE);

                        mRecyclerViewNotActive = view.findViewById(R.id.FragmentTrip_offers_completed_recycler_view);
                        mRecyclerViewNotActive.setHasFixedSize(true);
                        mLayoutManagerNotActive = new LinearLayoutManager(getActivity());
                        mAdapterNotActive = new CompletedTripsAdapter(completedTripsList);
                        mRecyclerViewNotActive.setLayoutManager(mLayoutManagerNotActive);
                        mRecyclerViewNotActive.setAdapter(mAdapterNotActive);
                        mAdapterNotActive.setOnItemClickListener(new CompletedTripsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
                                mainIntent.putExtra("isCompleted", true);
                                mainIntent.putExtra("isDriver", true);
                                mainIntent.putExtra("trip", completedTripsList.get(position));
                                startActivity(mainIntent);
                            }
                        });
                    } else {
                        completedTripsSectionLayout.setVisibility(View.GONE);
                    }

                } else
                    Toast.makeText(getActivity(), "No trips", Toast.LENGTH_SHORT).show();
                //activeTripsTextview.setText("Δεν έχετε κάποιο ενεργό ταξίδι αυτήν την στιγμή");
                activeTripsSectionLayout.setVisibility(View.GONE);
                completedTripsSectionLayout.setVisibility(View.GONE);
                noActiveTripsSectionLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

//        new FellowTravellerAPI(globalClass).getTripsAsCreator(new SearchTripsCallback() {
//            @Override
//            public void onSuccess(ArrayList<TripModel> trips) {
//                //Check if user has any trips
//                if (trips.size() > 0) {
//                    //Seperate trips to active and completed
//                    for(int i = 0; i < trips.size(); i++){
//                        if(!isCompleted(trips.get(i)))
//                            activeTripsList.add(trips.get(i));
//                        else
//                            completedTripsList.add(trips.get(i));
//                    }
//                    //check if user has active trips
//                    if (activeTripsList.size() > 0) {
//
//                        noActiveTripsSectionLayout.setVisibility(View.GONE);
//                        activeTripsSectionLayout.setVisibility(View.VISIBLE);
//
//                        mRecyclerViewActive = view.findViewById(R.id.fragment_trip_offers_recycler_view);
//                        mRecyclerViewActive.setHasFixedSize(true);
//                        mLayoutManagerActive = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        mAdapterActive = new ActiveTripsAdapter(activeTripsList);
//                        mRecyclerViewActive.setLayoutManager(mLayoutManagerActive);
//                        mRecyclerViewActive.setAdapter(mAdapterActive);
//
//                        mAdapterActive.setOnItemClickListener(new ActiveTripsAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
//                                mainIntent.putExtra("isCompleted", false);
//                                mainIntent.putExtra("isDriver", true);
//                                mainIntent.putExtra("trip", activeTripsList.get(position));
//                                startActivity(mainIntent);
//                            }
//                        });
//                        if (activeTripsList.size() == 1)
//                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTripsList.size()) + " ενεργό ταξίδι");
//                        else
//                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTripsList.size()) + " ενεργά ταξίδια");
//                    }else{
//                        activeTripsSectionLayout.setVisibility(View.GONE);
//                        noActiveTripsSectionLayout.setVisibility(View.VISIBLE);
//                    }
//
//                    //Chech if user has completed trips
//                    if(completedTripsList.size() > 0){
//
//                        completedTripsSectionLayout.setVisibility(View.VISIBLE);
//
//                        mRecyclerViewNotActive = view.findViewById(R.id.fragment_trip_offers_completed_recycler_view);
//                        mRecyclerViewNotActive.setHasFixedSize(true);
//                        mLayoutManagerNotActive = new LinearLayoutManager(getActivity());
//                        mAdapterNotActive = new CompletedTripsAdapter(completedTripsList);
//                        mRecyclerViewNotActive.setLayoutManager(mLayoutManagerNotActive);
//                        mRecyclerViewNotActive.setAdapter(mAdapterNotActive);
//                        mAdapterNotActive.setOnItemClickListener(new CompletedTripsAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
//                                mainIntent.putExtra("isCompleted", true);
//                                mainIntent.putExtra("isDriver", true);
//                                mainIntent.putExtra("trip", completedTripsList.get(position));
//                                startActivity(mainIntent);
//                            }
//                        });
//                    }else{
//                        completedTripsSectionLayout.setVisibility(View.GONE);
//                    }
//
//                }
//                else
//                    Toast.makeText(getActivity(),"No trips",Toast.LENGTH_SHORT).show();
//                //activeTripsTextview.setText("Δεν έχετε κάποιο ενεργό ταξίδι αυτήν την στιγμή");
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//
//            }
//        });

        createTripLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), NewOfferActivity.class);
                startActivity(mainIntent);
            }
        });

        moreActiveTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), ActiveTripsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("activeTripsList", activeTripsList);
                mainIntent.putExtra("isDriver", true);
                mainIntent.putExtras(bundle);
                startActivity(mainIntent);
            }
        });

        cardLayoutSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
                mainIntent.putExtra("isCompleted", false);
                mainIntent.putExtra("isDriver", true);
                mainIntent.putExtra("trip", activeTripsList.get(0));
                startActivity(mainIntent);
            }
        });
        return view;

    }

    public boolean isCompleted(TripInvolvedModel tripModel) {
        if (tripModel.getTimestamp() < System.currentTimeMillis() / 1000)
            return true;
        else
            return false;
    }
}

