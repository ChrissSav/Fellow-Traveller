package com.example.fellow_traveller.SearchAndBook;

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
import com.example.fellow_traveller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView destinationAutoComplete;
    private ImageButton backButton, eraseButton;
    private ImageView searchIcon;
    private DatabaseReference userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        destinationAutoComplete = findViewById(R.id.autocomplete_search_destination);
        backButton = findViewById(R.id.back_button_search);
        eraseButton = findViewById(R.id.erase_search);
        searchIcon = findViewById(R.id.search_icon);

        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("1");
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name","Spuros");
        userMap.put("surname", "Rantoglou");
        userMap.put("image","default");

        userDatabase.setValue(userMap);

        destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(SearchActivity.this, android.R.layout.simple_list_item_1));

        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    if(!destinationAutoComplete.getText().toString().trim().isEmpty()){
                    Intent mainIntent = new Intent(SearchActivity.this, Search2Activity.class);
                    mainIntent.putExtra("FromPlace",destinationAutoComplete.getText().toString());
                    startActivity(mainIntent);
                    }else{
                        destinationAutoComplete.setError("Δεν έχετε επιλέξει την αφετηρία σας");
                    }
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
