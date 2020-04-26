package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
//import com.example.fellow_traveller.API.RetrofitService;
//import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

public class AddCarSettingsActivity extends AppCompatActivity {

    private EditText carBrand, carModel, carPlate, carColor;
    private Button buttonAdd;
    private ImageButton buttonBack;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_settings);


        globalClass = (GlobalClass) getApplicationContext();

        carBrand = findViewById(R.id.AddCarSettingsActivity_EditText_brand);
        carModel = findViewById(R.id.AddCarSettingsActivity_EditText_model);
        carPlate = findViewById(R.id.AddCarSettingsActivity_EditText_plate);
        carColor = findViewById(R.id.AddCarSettingsActivity_EditText_color);
        buttonAdd = findViewById(R.id.AddCarSettingsActivity_button_add);
        buttonBack = findViewById(R.id.close_button_add_car_settings);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBrand() && checkModel() && checkPlate() && checkColor())
                    newRegisterCar();
            }
        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBrand() && checkModel() && checkPlate() && checkColor())
                    newRegisterCar();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


    }

    public boolean checkBrand() {
        if (carBrand.getText().length() < 2) {
            carBrand.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            carBrand.setError(null);
            return true;

        }
    }

    public boolean checkModel() {
        if (carModel.getText().length() < 2) {
            carModel.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            carModel.setError(null);
            return true;
        }
    }

    public boolean checkPlate() {
        if (carPlate.getText().toString().matches("[Α-Ω][Α-Ω][Α-Ω]\\s[0-9][0-9][0-9][0-9]")) {
            carPlate.setError(null);
            return true;
        } else {
            carPlate.setError("Πρέπει να είναι της μορφής ΧΧΧ 1234");
            return false;
        }
    }

    public boolean checkColor() {
        if (carColor.getText().length() < 2) {
            carColor.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            carColor.setError(null);
            return true;
        }
    }


    public void newRegisterCar() {
        new FellowTravellerAPI(globalClass).carRegister(carBrand.getText().toString(), carModel.getText().toString(),
                carPlate.getText().toString(), carColor.getText().toString(), new CarRegisterCallBack() {
                    @Override
                    public void onSuccess(CarModel car) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("car", car);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Toast.makeText(AddCarSettingsActivity.this, errorMsg, Toast.LENGTH_LONG).show();

                    }
                });
    }
}
















