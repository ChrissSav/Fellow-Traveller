package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.CarDeleteCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CarModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.material.snackbar.Snackbar;

import static com.example.fellow_traveller.Utils.InputValidation.isValidPlate;

public class EditCarSettingsActivity extends AppCompatActivity {
    private Button editButton, deleteButton;
    private EditText EditTextCarBrand, EditTextCarModel, EditTextCarPlate, EditTextCarColor;
    private GlobalClass globalClass;
    private CarModel carModel;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_settings);

        globalClass = (GlobalClass) getApplicationContext();

        carModel = getIntent().getParcelableExtra("car");
        constraintLayout = findViewById(R.id.AddCarSettingsActivity_ConstraintLayout);

        editButton = findViewById(R.id.EditCarSettingsActivity_button_add);
        deleteButton = findViewById(R.id.EditCarSettings_delete_button);
        EditTextCarBrand = findViewById(R.id.EditCarSettings_car_et);
        EditTextCarModel = findViewById(R.id.EditCarSettings_model_et);
        EditTextCarPlate = findViewById(R.id.EditCarSettings_plate_et);
        EditTextCarColor = findViewById(R.id.EditCarSettings_color_et);

        fillFields(carModel);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckBrand() && CheckModel() && CheckPlate() && CheckColor()) {
                    Toast.makeText(EditCarSettingsActivity.this, "mpompes", Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCar();
            }
        });
    }

    public boolean CheckBrand() {
        if (EditTextCarBrand.getText().length() < 2) {
            EditTextCarBrand.setError("Υποχρεώτικο πεδίο");
            return false;
        } else {
            EditTextCarBrand.setError(null);
            return true;

        }
    }

    public boolean CheckModel() {
        if (EditTextCarModel.getText().length() < 2) {
            EditTextCarModel.setError("Υποχρεώτικο πεδίο");
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
            EditTextCarPlate.setError("Πρέπει να είναι της μορφής ΧΧΧ1234");
            return false;
        }
    }

    public boolean CheckColor() {
        if (EditTextCarColor.getText().length() < 2) {
            EditTextCarColor.setError("Υποχρεώτικο πεδίο");
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
                .make(constraintLayout, "Θες σιγουρα να το διαγραψεις;", Snackbar.LENGTH_LONG)
                .setAction("ΝΑΙ", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new FellowTravellerAPI(globalClass).deleteUserCar(carModel.getId(), new CarDeleteCallBack() {
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
