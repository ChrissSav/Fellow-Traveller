package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fellow_traveller.Models.Car;
import com.example.fellow_traveller.R;

public class AddCarSettingsActivity extends AppCompatActivity {

    private EditText car_brand,car_model,car_plate,car_color;
    private Button button_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_settings);

        car_brand = findViewById(R.id.AddCarSettingsActivity_EditText_brand);
        car_model = findViewById(R.id.AddCarSettingsActivity_EditText_model);
        car_plate = findViewById(R.id.AddCarSettingsActivity_EditText_plate);
        car_color = findViewById(R.id.AddCarSettingsActivity_EditText_color);
        button_add = findViewById(R.id.AddCarSettingsActivity_button_add);


        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                Car car = new Car(1,car_brand.getText().toString(),car_model.getText().toString()
                        ,car_plate.getText().toString(),car_color.getText().toString());
                resultIntent.putExtra("car",car);

                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });
    }
}
