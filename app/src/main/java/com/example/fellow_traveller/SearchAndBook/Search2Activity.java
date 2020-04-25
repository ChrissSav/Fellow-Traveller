package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fellow_traveller.PlaceAutocomplete.AddLocationActivity;
import com.example.fellow_traveller.PlacesAPI.PlaceAutocompleteAdapter;
import com.example.fellow_traveller.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Search2Activity extends AppCompatActivity {
   // private AutoCompleteTextView destinationAutoComplete;
    private Button from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);


        from = findViewById(R.id.search2_button_from);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search2Activity.this, AddLocationActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        //destinationAutoComplete = findViewById(R.id.search2_autocomplete_start);

//        destinationAutoComplete.setAdapter(new PlaceAutocompleteAdapter(Search2Activity.this, android.R.layout.simple_list_item_1));
//        Intent intent = getIntent();
//        final String fromString = intent.getExtras().getString("FromPlace");
//
//        destinationAutoComplete.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//                if(i== EditorInfo.IME_ACTION_SEARCH) {
//                    if(!destinationAutoComplete.getText().toString().trim().isEmpty()){
//                        Intent mainIntent = new Intent(Search2Activity.this, SearchResultsActivity.class);
//                        mainIntent.putExtra("ToPlace",destinationAutoComplete.getText().toString());
//                        mainIntent.putExtra("FromPlace",fromString);
//                        startActivity(mainIntent);
//                    }else{
//                        destinationAutoComplete.setError("Δεν έχετε επιλέξει τον προορισμό σας");
//                    }
//
//                    return true;
//                }
//
//                return false;
//            }
//        });
    }
}
