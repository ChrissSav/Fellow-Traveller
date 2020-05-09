package com.example.fellow_traveller.HomeFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.NotificationCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Notification.NotificationAdapter;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Trips.TripPageActivity;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NotificationModel> notificationArrayList = new ArrayList<>();
    private GlobalClass globalClass;
    private int lastId = 0;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();


        mRecyclerView = view.findViewById(R.id.NotificationFragment_RecyclerView);

        if (notificationArrayList.size() > 0) {
            lastId = notificationArrayList.get(notificationArrayList.size() - 1).getId();
            buildRecyclerView();
        }
        LoadNotifications();
        return view;
    }


    public void buildRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NotificationAdapter(notificationArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                new FellowTravellerAPI(globalClass).setNotificationsRead(notificationArrayList.get(position).getId(), new StatusCallBack() {
                    @Override
                    public void onSuccess(String notificationModels) {
                        //Intent intent = new Intent(getActivity(), TripPageActivity.class);
                        //intent.putExtra("trip",notificationArrayList.get(position).getTrip());
                        //startActivity(intent);
                        notificationArrayList.get(position).setHasRead(true);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(getActivity(), "position : " + position, Toast.LENGTH_SHORT).show();


            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    lastId = notificationArrayList.get(notificationArrayList.size() - 1).getId();
                    LoadNotifications();

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void LoadNotifications() {
        new FellowTravellerAPI(globalClass).getNotificationsById(lastId, new NotificationCallBack() {
            @Override
            public void onSuccess(ArrayList<NotificationModel> notificationModels) {
                if (notificationArrayList.size() > 0) {
                    notificationArrayList.addAll(notificationModels);
                    mAdapter.notifyDataSetChanged();

                } else {
                    notificationArrayList = notificationModels;
                    buildRecyclerView();
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}













