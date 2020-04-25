package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAdapter;
import com.example.fellow_traveller.PlaceAutocomplete.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAutocompleteAdapter;
import com.example.fellow_traveller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    private EditText destinationAutoComplete;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    private ConstraintLayout suggestSection, resultsSection;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
   // private RetrofitService retrofitService;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (!destinationAutoComplete.getText().toString().trim().isEmpty()) {
                        Intent mainIntent = new Intent(SearchActivity.this, Search2Activity.class);
                        mainIntent.putExtra("FromPlace", destinationAutoComplete.getText().toString());
                        startActivity(mainIntent);
                    } else {
                        destinationAutoComplete.setError("Δεν έχετε επιλέξει την αφετηρία σας");
                    }
                    return true;
                }

                return false;
            }
        });

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

//        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.PLACE_URL))
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        retrofitService = retrofit.create(RetrofitService.class);
//        String key = getResources().getString(R.string.PLACE_KEY);
//        String language = getResources().getString(R.string.PLACE_LANGUAGE);
//        String country ="country:gr";
//        Call<PlaceAPi> call = retrofitService.getPlaces(input, key,language,country);
//        call.enqueue(new Callback<PlaceAPi>() {
//            @Override
//            public void onResponse(Call<PlaceAPi> call, Response<PlaceAPi> response) {
//                if (!response.isSuccessful()) {
//
//                    return;
//                }
//
//                PlaceAPi placeAPi = response.body();
//                places_list = placeAPi.getPredictions();
//                buildRecyclerView();
//
//
//            }
//
//            @Override
//            public void onFailure(Call<PlaceAPi> call, Throwable t) {
//                Log.i("GetPlaces", "onFailure "+t.getMessage());
//
//            }
//        });
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
            }
        });
    }
}
