package com.example.fellow_traveller.HomeFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.NotificationCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.NotificationModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Notification.NotificationAdapter;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.Trips.TripPageDriverActivity;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NotificationModel> notificationArrayList = new ArrayList<>();
    private GlobalClass globalClass;
    private int lastId = 0;
    private boolean connectToApi;
    private ProgressBar progressBar;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        connectToApi = true;
        globalClass = (GlobalClass) getActivity().getApplicationContext();


        progressBar = view.findViewById(R.id.NotificationFragment_progressBar);

        swipeRefreshLayout = view.findViewById(R.id.NotificationFragment_SwipeRefreshLayout);
        mRecyclerView = view.findViewById(R.id.NotificationFragment_RecyclerView);

        lastId = 0;


        if (notificationArrayList.size() > 0) {
            progressBar.setVisibility(View.GONE);
            lastId = notificationArrayList.get(notificationArrayList.size() - 1).getId();
            buildRecyclerView(false);
        } else {
            LoadNotifications(0);
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadNotifications(0);
            }
        });


        return view;
    }


    public void buildRecyclerView(boolean flag) {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NotificationAdapter(notificationArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (flag) {
            mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
        }
        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                if (notificationArrayList.get(position).getTypeOf().equals("passenger")) {
                    if (notificationArrayList.get(position).getHasRead()) {

                        Intent intent = new Intent(getActivity(), TripPageDriverActivity.class);
                        intent.putExtra("trip", notificationArrayList.get(position).getTrip());
                        startActivity(intent);
                    } else {
                        new FellowTravellerAPI(globalClass).setNotificationsRead(notificationArrayList.get(position).getId(), new StatusCallBack() {
                            @Override
                            public void onSuccess(String notificationModels) {
                                notificationArrayList.get(position).setHasRead(true);
                                mAdapter.notifyItemChanged(position);
                                Intent intent = new Intent(getActivity(), TripPageDriverActivity.class);
                                intent.putExtra("trip", notificationArrayList.get(position).getTrip());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if (notificationArrayList.size() > 0) {
                        lastId = notificationArrayList.get(notificationArrayList.size() - 1).getId();
                        LoadNotifications(lastId);
                    }


                }
            }
        });
    }

    public void LoadNotifications(final int id) {
        if (connectToApi) {
            connectToApi = false;
            new FellowTravellerAPI(globalClass).getNotificationsById(id, new NotificationCallBack() {
                @Override
                public void onSuccess(ArrayList<NotificationModel> notificationModels) {
                    if (notificationModels.size() > 0) {
                        if (id > 0) {
                            int position = notificationArrayList.size();
                            notificationArrayList.addAll(notificationModels);
                            buildRecyclerView(true);


                        } else {
                            notificationArrayList = notificationModels;
                            buildRecyclerView(false);

                        }
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    connectToApi = true;
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    connectToApi = true;
                    progressBar.setVisibility(View.GONE);

                }
            });

        }
    }

}













