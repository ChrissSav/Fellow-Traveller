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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.RetrofitAPIEndpoints;
import com.example.fellow_traveller.ClientAPI.RetrofitAPIEndpoints;
import com.example.fellow_traveller.PlaceAutocomplete.AddLocationActivity;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAPiModel;
import com.example.fellow_traveller.PlaceAutocomplete.PlaceAdapter;
import com.example.fellow_traveller.PlaceAutocomplete.PredictionsModel;
import com.example.fellow_traveller.PlaceAutocomplete.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAutocompleteAdapter;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Search2Activity extends AppCompatActivity {
    // private AutoCompleteTextView destinationAutoComplete;
    private EditText destinationAutoComplete;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    private ConstraintLayout suggestSection, resultsSection;
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private RetrofitAPIEndpoints retrofitService;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);







        destinationAutoComplete = findViewById(R.id.autocomplete_search_destination);
        backButton = findViewById(R.id.back_button_search);
        eraseButton = findViewById(R.id.erase_search);
        searchIcon = findViewById(R.id.search_icon);
        suggestSection = findViewById(R.id.ActivitySearch_suggest_section);
        resultsSection = findViewById(R.id.ActivitySearch_results_section);

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


        //destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(Search2Activity.this, android.R.layout.simple_list_item_1));
        Intent intent = getIntent();
        final String fromString = intent.getExtras().getString("FromPlace");

        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH) {
                    if(!destinationAutoComplete.getText().toString().trim().isEmpty()){
                        Intent mainIntent = new Intent(Search2Activity.this, SearchResultsActivity.class);
                        mainIntent.putExtra("ToPlace",destinationAutoComplete.getText().toString());
                        mainIntent.putExtra("FromPlace",fromString);
                        startActivity(mainIntent);
                    }else{
                        destinationAutoComplete.setError("Δεν έχετε επιλέξει τον προορισμό σας");
                    }

                    return true;
                }

                return false;
            }
        });
    }
    public void GetPlaces(String input) {

        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.PLACE_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitAPIEndpoints.class);
        String key = getResources().getString(R.string.PLACE_KEY);
        String language = getResources().getString(R.string.PLACE_LANGUAGE);
        String country ="country:gr";
        Call<PlaceAPiModel> call = retrofitService.getPlaces(input, key,language,country);
        call.enqueue(new Callback<PlaceAPiModel>() {
            @Override
            public void onResponse(Call<PlaceAPiModel> call, Response<PlaceAPiModel> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                PlaceAPiModel placeAPi = response.body();
                places_list = placeAPi.getPredictions();
                buildRecyclerView();


            }



            @Override
            public void onFailure(Call<PlaceAPiModel> call, Throwable t) {
                Log.i("GetPlaces", "onFailure "+t.getMessage());

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
            }
        });
    }
}
