package com.example.fellow_traveller.HomeFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Trips.OffersTabLayout;
import com.example.fellow_traveller.Trips.SearchsTabLayout;
import com.example.fellow_traveller.Trips.TripsViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripFragment extends Fragment {
    View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean flag = true;

    public TripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_trip, container, false);
        tabLayout = view.findViewById(R.id.fragmentTrip_TabLayout);
        viewPager = view.findViewById(R.id.fragmentTrip_ViewPager);


            TripsViewPagerAdapter tripsViewPagerAdapter = new TripsViewPagerAdapter(getChildFragmentManager());
            //Attaching fragments to tabLayout
            tripsViewPagerAdapter.addFragment(new SearchsTabLayout(), "Αναζητήσεις");
            tripsViewPagerAdapter.addFragment(new OffersTabLayout(), "Προσφορές");

            viewPager.setAdapter(tripsViewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);


        return view;
    }

}
