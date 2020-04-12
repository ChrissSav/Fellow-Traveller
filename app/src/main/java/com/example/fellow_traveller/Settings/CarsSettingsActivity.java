package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fellow_traveller.Chat.MessageItem;
import com.example.fellow_traveller.Chat.MessagesAdapter;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class CarsSettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<MyCarItem> carsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_settings);

        carsList.add(new MyCarItem("Lamborghini, Huracan 720LP - ΤΣΖ 3954"));
        carsList.add(new MyCarItem("Lamborghini, Huracan Spyder - ΤΞΣ 3264"));
        carsList.add(new MyCarItem("Lamborghini, Urus - ΙΓΞ 3853"));


        //Build Recycler View
        mRecyclerView = findViewById(R.id.my_cars_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyCarAdapter(carsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
