package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAdapter;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText destinationAutoComplete;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    private ConstraintLayout suggestSection, resultsSection;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        globalClass = (GlobalClass) getApplicationContext();

        destinationAutoComplete = findViewById(R.id.autocomplete_search_destination);
        backButton = findViewById(R.id.back_button_search);
        eraseButton = findViewById(R.id.erase_search);
        searchIcon = findViewById(R.id.search_icon);
        suggestSection = findViewById(R.id.ActivitySearch_suggest_section);
        resultsSection = findViewById(R.id.ActivitySearch_results_section);

        //Alternative way to create places API with adapter
        //destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(SearchActivity.this, android.R.layout.simple_list_item_1));


        //If we search for something suggest section disappears
        destinationAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().trim().isEmpty()) {
                    suggestSection.setVisibility(View.GONE);
                    eraseButton.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.INVISIBLE);
                    resultsSection.setVisibility(View.VISIBLE);
                } else {
                    suggestSection.setVisibility(View.VISIBLE);
                    eraseButton.setVisibility(View.INVISIBLE);
                    searchIcon.setVisibility(View.VISIBLE);
                    resultsSection.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().trim().isEmpty()) {
                    suggestSection.setVisibility(View.GONE);
                    eraseButton.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.INVISIBLE);
                    resultsSection.setVisibility(View.VISIBLE);

                } else {
                    suggestSection.setVisibility(View.VISIBLE);
                    eraseButton.setVisibility(View.INVISIBLE);
                    searchIcon.setVisibility(View.VISIBLE);
                    resultsSection.setVisibility(View.GONE);

                }
                GetPlaces(editable.toString());

            }
        });


        //Event Listener for search action of user keyboard
//         destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//
//                if (i == EditorInfo.IME_ACTION_SEARCH) {
//                    if (!destinationAutoComplete.getText().toString().trim().isEmpty()) {
//                        Intent mainIntent = new Intent(SearchActivity.this, Search2Activity.class);
//                        mainIntent.putExtra("FromPlace", destinationAutoComplete.getText().toString());
//                        startActivity(mainIntent);
//                    } else {
//                        destinationAutoComplete.setError("Δεν έχετε επιλέξει την αφετηρία σας");
//                    }
//                    return true;
//                }
//
//                return false;
//            }
//        });

        //Erase button to clear text
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationAutoComplete.setText("");
            }
        });

        //Back Button to Home Activity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(mainIntent);
            }
        });


    }
    public void GetPlaces(String input) {

        new PlaceApiConnection(globalClass).getPlaces(input,new PlaceApiCallBack() {
            @Override
            public void onSuccess(PlaceAPiModel placeAPiModel) {
                places_list = placeAPiModel.getPredictions();
                buildRecyclerView();
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.ActivitySearch_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlaceAdapter(places_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // SetNotificationsRead(mExampleList.get(position).getId(),position);
                destinationAutoComplete.setText(places_list.get(position).getDescription());
                Intent mainIntent = new Intent(SearchActivity.this, Search2Activity.class);
                mainIntent.putExtra("FromPlace", destinationAutoComplete.getText().toString());
                startActivity(mainIntent);
            }
        });
    }
}
