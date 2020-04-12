package com.example.fellow_traveller.HomeFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.API.Status_Handling;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SearchAndBook.SearchActivity;
import com.example.fellow_traveller.Settings.SettingsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private TextView searchForTrip, welcome_user;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayout2;
    private View view;
    private GlobalClass globalClass;
    private CircleImageView circleImageView;
    private Button btn_new_offer;
    private RetrofitService retrofitService;
    private Retrofit retrofit;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        globalClass = (GlobalClass) getActivity().getApplicationContext();

        circleImageView= view.findViewById(R.id.image_icon_home);
        searchForTrip = view.findViewById(R.id.FragmentHome_textView_Search);
        constraintLayout = view.findViewById(R.id.Layout2);
        constraintLayout2 = view.findViewById(R.id.Layout3);
        welcome_user = view.findViewById(R.id.FragmentHome_textView_welcome_user);
        btn_new_offer = view.findViewById(R.id.FragmentHome_button_CreateTrip);

        welcome_user.setText("Γεια σου " + globalClass.getCurrent_user().getName() + ",");
        searchForTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(mainIntent);
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Το πατησες", Toast.LENGTH_SHORT).show();

                CheckConnection();
            }
        });
        btn_new_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getActivity(), NewOfferActivity.class);
                startActivity(mainIntent);
            }
        });
        return view;
    }

    public void CheckConnection() {

        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);

        JsonObject user_object = new JsonObject();
        user_object.addProperty("refresh_token", globalClass.getCurrent_user().getRefresh_token());
        Call<Status_Handling> call = retrofitService.Test();
        call.enqueue(new Callback<Status_Handling>() {
            @Override
            public void onResponse(Call<Status_Handling> call, Response<Status_Handling> response) {
                Log.i("SaveClass", "-2");
                if (!response.isSuccessful()) {
                    try {
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        Log.i("CheckConnection", "-1");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                Log.i("CheckConnection", "-1");
                Log.i("SaveClass", "-1");
                Log.i("SaveClass", "0");

                Log.i("SaveClass", "4");
                Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<Status_Handling> call, Throwable t) {
                Log.i("Register_Container", "onFailure: " + t.getMessage());
                Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_SHORT).show();

            }

        });
    }
}