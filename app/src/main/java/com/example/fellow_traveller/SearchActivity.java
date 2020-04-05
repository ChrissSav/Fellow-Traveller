package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.PlacesAPI.PlaceAutocompleteAdapter;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView destinationAutoComplete;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        destinationAutoComplete = findViewById(R.id.autocomplete_search_destination);
        backButton = findViewById(R.id.back_button_search);
        eraseButton = findViewById(R.id.erase_search);
        searchIcon = findViewById(R.id.search_icon);

        destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(SearchActivity.this, android.R.layout.simple_list_item_1));

        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    Intent mainIntent = new Intent(SearchActivity.this, Search2Activity.class);
                    startActivity(mainIntent);
                    return true;
                }

                return false;
            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destinationAutoComplete.setText("");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(SearchActivity.this, HomeActivity.class);
                startActivity(mainIntent);
            }
        });

        if(destinationAutoComplete.getText().toString().matches("")){
            eraseButton.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.INVISIBLE);
        }else{
            eraseButton.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.INVISIBLE);
        }
    }


}
