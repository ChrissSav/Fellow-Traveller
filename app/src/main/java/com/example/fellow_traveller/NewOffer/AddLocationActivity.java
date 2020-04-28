package com.example.fellow_traveller.NewOffer;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;

import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.PlaceAPiModel;
import com.example.fellow_traveller.PlacesAPI.PlaceAdapter;
import com.example.fellow_traveller.PlacesAPI.Models.PredictionsModel;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<PredictionsModel> places_list;
    private EditText editText;
    private ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        button_back = findViewById(R.id.AddLocationActivity_back_button_search);
        editText = findViewById(R.id.AddLocationActivity_EditText_search_place);

        editText.requestFocus();
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Log.i("addTextChangedListener", "afterTextChanged "+s);

                GetPlaces(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.i("addTextChangedListener", "beforeTextChanged");

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.i("addTextChangedListener", "onTextChanged");

            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (!editText.getText().toString().trim().isEmpty()) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result", editText.getText().toString());
                        setResult(RESULT_OK, resultIntent);
                        finish();

                    } else {
                        editText.setError("Δεν έχετε επιλέξει την αφετηρία σας");
                    }
                    return true;
                }

                return false;
            }
        });

    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.AddLocationActivity_RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PlaceAdapter(places_list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // SetNotificationsRead(mExampleList.get(position).getId(),position);
                editText.setText(places_list.get(position).getDescription());


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
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });


    }
}
