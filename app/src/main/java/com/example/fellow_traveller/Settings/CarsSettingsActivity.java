package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fellow_traveller.Chat.ChatConversationActivity;
import com.example.fellow_traveller.Chat.ConversationAdapter;
import com.example.fellow_traveller.Chat.MessageItem;
import com.example.fellow_traveller.Chat.MessagesAdapter;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserCarsCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchDetailsActivity;
import com.example.fellow_traveller.SearchAndBook.SearchResultsActivity;
import com.example.fellow_traveller.SearchAndBook.SearchResultsAdapter;

import java.util.ArrayList;

public class CarsSettingsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyCarAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<CarModel> carsList;
    private Button addCarButton;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_settings);
        globalClass = (GlobalClass) getApplicationContext();

        // carsList.add(new MyCarItem("Lamborghini, Huracan 720LP - ΤΣΖ 3954"));
        // carsList.add(new MyCarItem("Lamborghini, Huracan Spyder - ΤΞΣ 3264"));
        //carsList.add(new MyCarItem("Lamborghini, Urus - ΙΓΞ 3853"));


        addCarButton = findViewById(R.id.new_car_button_settings_car);


        addCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(CarsSettingsActivity.this, AddCarSettingsActivity.class);
                startActivity(mainIntent);

            }
        });





    }

    public void buildRecycleView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyCarAdapter(carsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyCarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mainIntent = new Intent(CarsSettingsActivity.this, EditCarSettingsActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    public void getUserCars() {
        new FellowTravellerAPI(globalClass).getUserCars(new UserCarsCallBack() {
            @Override
            public void onSuccess(ArrayList<CarModel> carList) {
                carsList = carList;
                buildRecycleView();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
}















