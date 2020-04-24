package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fellow_traveller.R;

public class EditCarSettingsActivity extends AppCompatActivity {
    private Button editButton, deleteButton;
    private EditText car_brand, car_model, car_plate, car_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_settings);

        editButton = findViewById(R.id.EditCarSettingsActivity_button_add);
        deleteButton = findViewById(R.id.EditCarSettings_delete_button);
        car_brand = findViewById(R.id.EditCarSettings_car_et);
        car_model = findViewById(R.id.EditCarSettings_model_et);
        car_plate = findViewById(R.id.EditCarSettings_plate_et);
        car_color = findViewById(R.id.EditCarSettings_color_et);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckBrand() && CheckModel() && CheckPlate() && CheckColor()){
                    //body
                }

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        if (car_plate.getText().toString().matches("[Α-Ω][Α-Ω][Α-Ω]\\s[0-9][0-9][0-9][0-9]")) {
            car_plate.setError(null);
            return true;
        } else {
            car_plate.setError("Πρέπει να είναι της μορφής ΧΧΧ 1234");
            return false;
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
}
