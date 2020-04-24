package com.example.fellow_traveller.HomeFragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Notification.NotificationAdapter;
import com.example.fellow_traveller.Notification.NotificationItem;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

import retrofit2.Retrofit;


public class NotificationFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NotificationItem> notificationList;
    private RetrofitService retrofitService;
    private Retrofit retrofit;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        LoadNotifications();
        return view;
    }


    public void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.NotificationFragment_RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NotificationAdapter(notificationList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // SetNotificationsRead(mExampleList.get(position).getId(),position);
                Toast.makeText(getActivity(), "position : " + position, Toast.LENGTH_SHORT).show();


            }
        });
    }


    public void LoadNotifications() {
        /*retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);*/
        notificationList = new ArrayList<>();
        NotificationItem notificationItem = new NotificationItem();
        notificationItem.setUser(new UserBaseModel("Φωτης", "Πεχλιβανης"));
        notificationItem.setStatus("read");
        notificationList.add(notificationItem);
        notificationItem = new NotificationItem();
        notificationItem.setStatus("notread");
        notificationItem.setUser(new UserBaseModel("Σπυρος", "Ραντογλου"));
        notificationList.add(notificationItem);
        buildRecyclerView();
    }

}
