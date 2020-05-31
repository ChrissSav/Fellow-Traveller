package com.example.fellow_traveller.Trips;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.TripInvolvedCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchDestinationsActivity;
import com.example.fellow_traveller.SearchAndBook.SearchPassengersActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchsTabLayout extends Fragment {
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
    private ConstraintLayout searchLayoutButton;
    private ConstraintLayout activeTripsSectionLayout, noActiveTripsSectionLayout, completedTripsSectionLayout, cardLayoutSection;
    private TextView destinationCardText, dateCardText, timeCardText, priceCardText, creatorNameCardText, creatorRateCardText, myBookCardText;
    private CircleImageView creatorCardImage;

    //private ViewPager2 activeTripsViewPager,completedTripsViewPager;


    public SearchsTabLayout() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip_searchs, container, false);
        //myContext = container.getContext();
        globalClass = (GlobalClass) getActivity().getApplicationContext();
        searchLayoutButton = view.findViewById(R.id.FragmentTrip_searchs_no_trips_section_button_layout);
        activeTripsSectionLayout = view.findViewById(R.id.FragmentTrip_searchs_active_trips_section);
        noActiveTripsSectionLayout = view.findViewById(R.id.FragmentTrip_searchs_no_trips_section);
        completedTripsSectionLayout = view.findViewById(R.id.FragmentTrip_searchs_completed_trips_section);
        activeTripsTextview = view.findViewById(R.id.FragmentTrip_searchs_active_trips_textView);
        destinationCardText = view.findViewById(R.id.FragmentTrip_searchs_destinations_TextView);
        dateCardText = view.findViewById(R.id.FragmentTrip_searchs_date_textView);
        timeCardText = view.findViewById(R.id.FragmentTrip_searchs_time_textView);
        priceCardText = view.findViewById(R.id.FragmentTrip_searchs_price_textView);
        creatorNameCardText = view.findViewById(R.id.FragmentTrip_searchs_creator_name);
        creatorRateCardText = view.findViewById(R.id.FragmentTrip_searchs_creator_rate_and_reviews);
        myBookCardText = view.findViewById(R.id.FragmentTrip_searchs_my_book_details_textView);
        creatorCardImage = view.findViewById(R.id.FragmentTrip_searchs_creator_image);
        moreActiveTripsButton = view.findViewById(R.id.FragmentTrip_searchs_all_activeTrips_button);
        cardLayoutSection = view.findViewById(R.id.FragmentTrip_searchs_card_section);


        new FellowTravellerAPI(globalClass).getTripsAsPassengerTest(new TripInvolvedCallBack() {
            @Override
            public void onSuccess(ArrayList<TripInvolvedModel> trips) {
                //Check if users has any trips
                if (trips.size() > 0) {
                    //seperate them to active and completed
                    for (int i = 0; i < trips.size(); i++) {
                        if (trips.get(0).getActive())
                            activeTripsList.add(trips.get(i));
                        else
                            completedTripsList.add(trips.get(i));
                    }

                    //check if user has active trips
                    if (activeTripsList.size() > 0) {
                        noActiveTripsSectionLayout.setVisibility(View.GONE);
                        activeTripsSectionLayout.setVisibility(View.VISIBLE);

                        Collections.sort(activeTripsList, TripInvolvedModel.DateComparator);

                        //<------------Fill the cardview ------------>
                        destinationCardText.setText(activeTripsList.get(0).getDestFrom().getTitle() + " - " + activeTripsList.get(0).getDestTo().getTitle());
                        dateCardText.setText(activeTripsList.get(0).getDate());
                        timeCardText.setText(activeTripsList.get(0).getTime());
                        if (Math.round(activeTripsList.get(0).getPrice()) == activeTripsList.get(0).getPrice())
                            priceCardText.setText(Math.round(activeTripsList.get(0).getPrice()) + "€");
                        else
                            priceCardText.setText(activeTripsList.get(0).getPrice() + "€");

                        creatorNameCardText.setText(activeTripsList.get(0).getCreatorUser().getFullName());
                        creatorRateCardText.setText(activeTripsList.get(0).getCreatorUser().getRate() + " (" + activeTripsList.get(0).getCreatorUser().getReviews() + ")");
                        try {
                            Picasso.get().load(activeTripsList.get(0).getCreatorUser().getPicture()).into(creatorCardImage);

                        } catch (Exception e) {
                            //  Block of code to handle errors
                        }

                        fillMyBookDetails(activeTripsList.get(0), myBookCardText);
                        if (activeTripsList.size() == 1)
                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTripsList.size()) + " ενεργό ταξίδι");
                        else
                            activeTripsTextview.setText("Έχετε " + String.valueOf(activeTripsList.size()) + " ενεργά ταξίδια");
                    } else {
                        activeTripsSectionLayout.setVisibility(View.GONE);
                        noActiveTripsSectionLayout.setVisibility(View.VISIBLE);
                    }


                    //check if we have completed trips
                    if (completedTripsList.size() > 0) {
                        mRecyclerViewNotActive = view.findViewById(R.id.FragmentTrip_searchs_completed_recycler_view);
                        mRecyclerViewNotActive.setHasFixedSize(true);
                        mLayoutManagerNotActive = new LinearLayoutManager(getActivity());
                        mAdapterNotActive = new CompletedTripsAdapter(completedTripsList);
                        mRecyclerViewNotActive.setLayoutManager(mLayoutManagerNotActive);
                        mRecyclerViewNotActive.setAdapter(mAdapterNotActive);
                        completedTripsSectionLayout.setVisibility(View.VISIBLE);

                        mAdapterNotActive.setOnItemClickListener(new CompletedTripsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
                                mainIntent.putExtra("isCompleted", true);
                                mainIntent.putExtra("isDriver", false);
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
//        new FellowTravellerAPI(globalClass).getTripsAsPassenger( new SearchTripsCallback() {
//            @Override
//            public void onSuccess(ArrayList<TripModel> trips) {
//
//                //Check if users has any trips
//                if (trips.size() > 0) {
//                    //seperate them to active and completed
//                    for(int i = 0; i < trips.size(); i++){
//                        if(!isCompleted(trips.get(i)))
//                            activeTripsList.add(trips.get(i));
//                        else
//                            completedTripsList.add(trips.get(i));
//                    }
//                    //check if user has active trips
//                    if (activeTripsList.size() > 0) {
//                        mRecyclerViewActive = view.findViewById(R.id.fragment_trip_searchs_recycler_view);
//                        mRecyclerViewActive.setHasFixedSize(true);
//                        mLayoutManagerActive = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                        mAdapterActive = new ActiveTripsAdapter(activeTripsList);
//                        mRecyclerViewActive.setLayoutManager(mLayoutManagerActive);
//                        mRecyclerViewActive.setAdapter(mAdapterActive);
//                        noActiveTripsSectionLayout.setVisibility(View.GONE);
//                        activeTripsSectionLayout.setVisibility(View.VISIBLE);
//                        mAdapterActive.setOnItemClickListener(new ActiveTripsAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
//                                mainIntent.putExtra("isCompleted", false);
//                                mainIntent.putExtra("isDriver", false);
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
//                    //check if we have completed trips
//                    if(completedTripsList.size() > 0){
//                        mRecyclerViewNotActive = view.findViewById(R.id.fragment_trip_searchs_completed_recycler_view);
//                        mRecyclerViewNotActive.setHasFixedSize(true);
//                        mLayoutManagerNotActive = new LinearLayoutManager(getActivity());
//                        mAdapterNotActive = new CompletedTripsAdapter(completedTripsList);
//                        mRecyclerViewNotActive.setLayoutManager(mLayoutManagerNotActive);
//                        mRecyclerViewNotActive.setAdapter(mAdapterNotActive);
//                        completedTripsSectionLayout.setVisibility(View.VISIBLE);
//
//                        mAdapterNotActive.setOnItemClickListener(new CompletedTripsAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(int position) {
//                                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
//                                mainIntent.putExtra("isCompleted", true);
//                                mainIntent.putExtra("isDriver", false);
//                                mainIntent.putExtra("trip",  completedTripsList.get(position));
//                                startActivity(mainIntent);
//                            }
//                        });
//
//                    }
//                    else {
//                        completedTripsSectionLayout.setVisibility(View.GONE);
//                    }
//                }
//                else
//                    Toast.makeText(getActivity(),"No trips",Toast.LENGTH_SHORT).show();
//                    //activeTripsTextview.setText("Δεν έχετε κάποιο ενεργό ταξίδι αυτήν την στιγμή");
//
//
//            }
//
//            @Override
//            public void onFailure(String errorMsg) {
//
//            }
//        });
//
        searchLayoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), SearchDestinationsActivity.class);
                startActivity(mainIntent);
            }
        });

        moreActiveTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), ActiveTripsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("activeTripsList", activeTripsList);
                mainIntent.putExtra("isDriver", false);
                mainIntent.putExtras(bundle);
                startActivity(mainIntent);
            }
        });
        cardLayoutSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), TripPageDriverActivity.class);
                mainIntent.putExtra("isCompleted", false);
                mainIntent.putExtra("isDriver", false);
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

    public void fillMyBookDetails(TripInvolvedModel tripInvolvedModel, TextView myBooksDetails) {
        //Find my book details within the passengers
        for (int i = 0; i < tripInvolvedModel.getPassengers().size(); i++) {
            if(globalClass.getCurrentUser().getId() == tripInvolvedModel.getPassengers().get(i).getUser().getId()){

                //Check if have pet
                if(tripInvolvedModel.getPassengers().get(i).getPet()){
                    //Have pet
                    if(tripInvolvedModel.getPassengers().get(i).getBags() == 0)
                        myBooksDetails.setText("Κατοικίδιο");
                    else if(tripInvolvedModel.getPassengers().get(i).getBags() == 1)
                        myBooksDetails.setText("Κατοικίδιο και 1 αποσκευή");
                    else
                        myBooksDetails.setText("Κατοικίδιο και " + tripInvolvedModel.getPassengers().get(i).getBags() + " αποσκευές");

                }else{
                    //Don't have pet
                    if(tripInvolvedModel.getPassengers().get(i).getBags() == 0)
                        myBooksDetails.setText("Καμία επιλογή");
                    else if(tripInvolvedModel.getPassengers().get(i).getBags() == 1)
                        myBooksDetails.setText("Έχετε επιλέξει 1 αποσκευή");
                    else
                        myBooksDetails.setText("Έχετε επιλέξει " + tripInvolvedModel.getPassengers().get(i).getBags() + " αποσκευές");
                }
            }
        }
    }


}
