package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.example.fellow_traveller.PlacesAPI.PlaceAutocompleteAdapter;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView destinationAutoComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        destinationAutoComplete = findViewById(R.id.autocomplete_search_destination);
        destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(SearchActivity.this, android.R.layout.simple_list_item_1));
    }


}
