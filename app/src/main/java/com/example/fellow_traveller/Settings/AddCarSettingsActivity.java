package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.API.RetrofitService;
import com.example.fellow_traveller.LoginActivity;
import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.PlaceAutocomplete.AddLocationActivity;
import com.example.fellow_traveller.R;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCarSettingsActivity extends AppCompatActivity {

    private EditText car_brand, car_model, car_plate, car_color;
    private Button button_add;
    private ImageButton button_back;
    private RetrofitService retrofitService;
    private Retrofit retrofit;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_settings);


        globalClass = (GlobalClass) getApplicationContext();

        car_brand = findViewById(R.id.AddCarSettingsActivity_EditText_brand);
        car_model = findViewById(R.id.AddCarSettingsActivity_EditText_model);
        car_plate = findViewById(R.id.AddCarSettingsActivity_EditText_plate);
        car_color = findViewById(R.id.AddCarSettingsActivity_EditText_color);
        button_add = findViewById(R.id.AddCarSettingsActivity_button_add);
        button_back = findViewById(R.id.close_button_add_car_settings);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckBrand() && CheckModel() && CheckPlate() && CheckColor())
                    RegisterCar();


            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();


            }
        });
    }

    public boolean CheckBrand() {
        if (car_brand.getText().length() < 2) {
            car_brand.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            car_brand.setError(null);
            return true;

        }
    }

    public boolean CheckModel() {
        if (car_model.getText().length() < 2) {
            car_model.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            car_model.setError(null);
            return true;
        }
    }

    public boolean CheckPlate() {
        if (car_plate.getText().length() < 2) {
            car_plate.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            car_plate.setError(null);
            return true;
        }
    }

    public boolean CheckColor() {
        if (car_color.getText().length() < 2) {
            car_color.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            car_color.setError(null);
            return true;
        }
    }

    public void RegisterCar() {
        retrofit = new Retrofit.Builder().baseUrl(getResources().getString(R.string.API_URL)).client(globalClass.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create()).build();
        retrofitService = retrofit.create(RetrofitService.class);

        JsonObject car_object = new JsonObject();
        car_object.addProperty("brand", car_brand.getText().toString());
        car_object.addProperty("model", car_model.getText().toString());
        car_object.addProperty("plate", car_plate.getText().toString());
        car_object.addProperty("color", car_color.getText().toString());

        Call<Car> call = retrofitService.registerCar(car_object);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (!response.isSuccessful()) {
                    try {
                        Toast.makeText(AddCarSettingsActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return;
                }
                Car car = response.body();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("car", car);
                setResult(RESULT_OK, resultIntent);
                finish();

            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {

            }
        });
    }
}
