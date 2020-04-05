package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.fellow_traveller.PlacesAPI.PlaceAutocompleteAdapter;

public class Search2Activity extends AppCompatActivity {
    private AutoCompleteTextView destinationAutoComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        destinationAutoComplete = findViewById(R.id.autocomplete_search_destination);

        destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(Search2Activity.this, android.R.layout.simple_list_item_1));
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
}
