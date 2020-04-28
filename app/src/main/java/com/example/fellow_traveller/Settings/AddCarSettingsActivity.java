package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import static com.example.fellow_traveller.Util.InputValidation.isValidPlate;

public class AddCarSettingsActivity extends AppCompatActivity {

    private EditText EditTextCarBrand, EditTextCarModel, EditTextCarPlate, EditTextCarColor;
    private Button buttonAdd;
    private ImageButton buttonBack;
    private GlobalClass globalClass;
    private String previousWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_settings);


        globalClass = (GlobalClass) getApplicationContext();

        EditTextCarBrand = findViewById(R.id.AddCarSettingsActivity_EditText_brand);
        EditTextCarModel = findViewById(R.id.AddCarSettingsActivity_EditText_model);
        EditTextCarPlate = findViewById(R.id.AddCarSettingsActivity_EditText_plate);
        EditTextCarColor = findViewById(R.id.AddCarSettingsActivity_EditText_color);
        buttonAdd = findViewById(R.id.EditCarSettingsActivity_button_add);
        buttonBack = findViewById(R.id.close_button_add_car_settings);

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

        EditTextCarPlate.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(8)
        });
        EditTextCarPlate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 2 && count == 3) {
                    EditTextCarPlate.append("-");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 4) {
                    EditTextCarPlate.setInputType(InputType.TYPE_CLASS_NUMBER);

                } else {
                    EditTextCarPlate.setInputType(InputType.TYPE_CLASS_TEXT);
                }

            }


        });
    }

    public boolean checkBrand() {
        if (EditTextCarBrand.getText().length() < 2) {
            EditTextCarBrand.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            EditTextCarBrand.setError(null);
            return true;

        }
    }

    public boolean checkModel() {
        if (EditTextCarModel.getText().length() < 2) {
            EditTextCarModel.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            EditTextCarModel.setError(null);
            return true;
        }
    }

    public boolean checkPlate() {
        if (isValidPlate(EditTextCarPlate.getText().toString())) {
            EditTextCarPlate.setError(null);
            return true;
        } else {
            EditTextCarPlate.setError(getResources().getString(R.string.ERROR_INVALID_PLATE_FORMAT));
            return false;
        }
    }

    public boolean checkColor() {
        if (EditTextCarColor.getText().length() < 2) {
            EditTextCarColor.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            EditTextCarColor.setError(null);
            return true;
        }
    }


    public void newRegisterCar() {
        new FellowTravellerAPI(globalClass).addUserCar(EditTextCarBrand.getText().toString(), EditTextCarModel.getText().toString(),
                EditTextCarPlate.getText().toString(), EditTextCarColor.getText().toString(), new CarRegisterCallBack() {
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