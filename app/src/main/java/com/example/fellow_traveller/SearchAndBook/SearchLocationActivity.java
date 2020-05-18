package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAdapter;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchLocationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private EditText destinationsEditText;
    private ImageButton backButton, clearButton;
    private ProgressBar progressBar;
    private DestinationModel destinationModelToReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        destinationsEditText = findViewById(R.id.ActivitySearchLocation_edit_text);
        backButton = findViewById(R.id.ActivitySearchLocation_back_button);
        clearButton = findViewById(R.id.ActivitySearchLocation_erase_button);
        progressBar = findViewById(R.id.ActivitySearchLocation_progress_bar);

        destinationsEditText.requestFocus();
        destinationsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.i("addTextChangedListener", "beforeTextChanged");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.i("addTextChangedListener", "onTextChanged");
                if (!charSequence.toString().trim().isEmpty()) {
                    clearButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.INVISIBLE);
                }
                GetPlaces(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Log.i("addTextChangedListener", "afterTextChanged "+s);


            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationsEditText.setText("");
            }
        });
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.ActivitySearchLocation_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlaceAdapter(places_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                destinationsEditText.setText(places_list.get(position).getDescription());
                Intent resultIntent = new Intent();
                destinationModelToReturn = new DestinationModel(places_list.get(position).getPlaceId(), places_list.get(position).getDescription(), Float.valueOf(0), Float.valueOf(0));
                resultIntent.putExtra("resultPredictionsModel", destinationModelToReturn);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public void GetPlaces(String input) {

        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        new PlaceApiConnection(globalClass).getPlaces(input,new PlaceApiCallBack() {
            @Override
            public void onSuccess(PlaceAPiModel placeAPiModel) {
                places_list = placeAPiModel.getPredictions();
                buildRecyclerView();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String errorMsg) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
