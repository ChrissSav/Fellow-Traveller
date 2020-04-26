package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fellow_traveller.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.wisnu.datetimerangepickerandroid.CalendarPickerView;

import java.awt.font.NumericShaper;
import java.util.Calendar;
import java.util.Date;

public class FiltersActivity extends AppCompatActivity {
    private SeekBar rangeBar;
    private TextView rangeBarTV;
    private Button sortByButton, dateButton, timeButton, seatsButton, ratingButton, resetButton, applyButton;
    private ImageButton closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        rangeBar = findViewById(R.id.slider_filters);
        rangeBarTV = findViewById(R.id.range_km_tv);
        sortByButton = findViewById(R.id.sort_by_button);
        dateButton = findViewById(R.id.date_button);
        timeButton = findViewById(R.id.time_button);
        seatsButton = findViewById(R.id.seats_button);
        resetButton = findViewById(R.id.reset_button);
        ratingButton = findViewById(R.id.rate_button);
        applyButton = findViewById(R.id.apply_button);
        closeButton = findViewById(R.id.close_button_filters);


        //MaterialDatePicker
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Επιλέξτε το εύρος των ημερών");
        final MaterialDatePicker materialDatePicker = builder.build();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            }
        });


        rangeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rangeBarTV.setText(progress + " χλμ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
