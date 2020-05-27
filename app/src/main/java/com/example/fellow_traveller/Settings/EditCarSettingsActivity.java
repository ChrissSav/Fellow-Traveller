package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarDeleteCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.CarRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UpdateCarModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.snackbar.Snackbar;

import static com.example.fellow_traveller.Util.InputValidation.isValidPlate;

public class EditCarSettingsActivity extends AppCompatActivity {
    private Button editButton, deleteButton;
    private EditText EditTextCarBrand, EditTextCarModel, EditTextCarPlate, EditTextCarColor;
    private GlobalClass globalClass;
    private CarModel carModel;
    private ConstraintLayout constraintLayout;
    private ImageButton imageButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_settings);

        globalClass = (GlobalClass) getApplicationContext();

        carModel = getIntent().getParcelableExtra("car");
        constraintLayout = findViewById(R.id.AddCarSettingsActivity_ConstraintLayout);
        imageButtonBack = findViewById(R.id.close_button_add_car_settings);
        editButton = findViewById(R.id.EditCarSettingsActivity_button_add);
        deleteButton = findViewById(R.id.EditCarSettings_delete_button);
        EditTextCarBrand = findViewById(R.id.EditCarSettings_car_et);
        EditTextCarModel = findViewById(R.id.EditCarSettings_model_et);
        EditTextCarPlate = findViewById(R.id.EditCarSettings_plate_et);
        EditTextCarColor = findViewById(R.id.EditCarSettings_color_et);

        fillFields(carModel);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckBrand() && CheckModel() && CheckPlate() && CheckColor()) {
                    UpdateCarModel updateCarModel = new UpdateCarModel(carModel.getId(),EditTextCarBrand.getText().toString(),
                            EditTextCarModel.getText().toString(),EditTextCarColor.getText().toString());
                    new FellowTravellerAPI(globalClass).updateUserCar(updateCarModel, new CarRegisterCallBack() {

                        @Override
                        public void onSuccess(CarModel car) {
                            Toast.makeText(EditCarSettingsActivity.this, "Επιτυχής επεξεργασία", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            Toast.makeText(EditCarSettingsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCar();
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

    public boolean CheckBrand() {
        if (EditTextCarBrand.getText().length() < 2) {
            EditTextCarBrand.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            EditTextCarBrand.setError(null);
            return true;

        }
    }

    public boolean CheckModel() {
        if (EditTextCarModel.getText().length() < 2) {
            EditTextCarModel.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            EditTextCarModel.setError(null);
            return true;
        }
    }

    public boolean CheckPlate() {
        if (isValidPlate(EditTextCarPlate.getText().toString())) {
            EditTextCarPlate.setError(null);
            return true;
        } else {
            EditTextCarPlate.setError(getResources().getString(R.string.ERROR_INVALID_PLATE_FORMAT));
            return false;
        }
    }

    public boolean CheckColor() {
        if (EditTextCarColor.getText().length() < 2) {
            EditTextCarColor.setError(getResources().getString(R.string.ERROR_REQUIRED_FIELD));
            return false;
        } else {
            EditTextCarColor.setError(null);
            return true;
        }
    }

    public void fillFields(CarModel carModel) {
        EditTextCarBrand.setText(carModel.getBrand());
        EditTextCarModel.setText(carModel.getModel());
        EditTextCarPlate.setText(carModel.getPlate());
        EditTextCarColor.setText(carModel.getColor());

    }

    public void deleteCar() {
        Snackbar snackbar = Snackbar
                .make(constraintLayout, getResources().getString(R.string.CONFIRM_DELETION), Snackbar.LENGTH_LONG)
                .setAction("ΝΑΙ", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new FellowTravellerAPI(globalClass).deleteCar(carModel.getId(), new CarDeleteCallBack() {
                            @Override
                            public void onSuccess(StatusHandleModel status) {
                                Toast.makeText(EditCarSettingsActivity.this, "Επιτυχής διαγραφή", Toast.LENGTH_SHORT).show();

                                onBackPressed();
                                finish();
                            }

                            @Override
                            public void onFailure(String errorMsg) {
                                Toast.makeText(EditCarSettingsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

        snackbar.show();

    }

}
