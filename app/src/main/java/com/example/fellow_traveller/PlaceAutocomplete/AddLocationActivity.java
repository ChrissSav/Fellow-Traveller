package com.example.fellow_traveller.PlaceAutocomplete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.Notification.NotificationAdapter;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.Search2Activity;
import com.example.fellow_traveller.SearchAndBook.SearchActivity;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddLocationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PlaceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Predictions> places_list;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
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

        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.PLACE_URL))
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);
        String key = getResources().getString(R.string.PLACE_KEY);
        String language = getResources().getString(R.string.PLACE_LANGUAGE);
        String country ="country:gr";
        Call<PlaceAPi> call = retrofitService.getPlaces(input, key,language,country);
        call.enqueue(new Callback<PlaceAPi>() {
            @Override
            public void onResponse(Call<PlaceAPi> call, Response<PlaceAPi> response) {
                if (!response.isSuccessful()) {

                    return;
                }

                PlaceAPi placeAPi = response.body();
                places_list = placeAPi.getPredictions();
                buildRecyclerView();


            }

            @Override
            public void onFailure(Call<PlaceAPi> call, Throwable t) {
                Log.i("GetPlaces", "onFailure "+t.getMessage());

            }
        });
    }
}
