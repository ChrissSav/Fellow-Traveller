package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.wisnu.datetimerangepickerandroid.CalendarPickerView;

import java.awt.font.NumericShaper;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class FiltersActivity extends AppCompatActivity {
    private SeekBar rangeBar;
    private TextView rangeBarTV;
    private Button sortByButton, dateButton, timeButton, seatsButton, ratingButton, resetButton, applyButton;
    private ImageButton closeButton;
    private final long five_years_forward = (1000*60*60*24*365*5);
    private long startRange = 0, endRange = 0;

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


        //<----------Initialize Calender------------->
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        //long leftConstraint = MaterialDatePicker.thisMonthInUtcMilliseconds();

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(today);

        calendar.setTimeInMillis(today);

        calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        long left = calendar.getTimeInMillis();
        calendar.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        long right = calendar.getTimeInMillis();

        //<------ Finished initialization of Calender------->

        //Calendar Constraints
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setStart(left);
        constraintBuilder.setEnd(right);
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        //MaterialDatePicker
        final MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Επιλέξτε το εύρος των ημερών");
        builder.setCalendarConstraints(constraintBuilder.build());


        final MaterialDatePicker<Pair<Long,Long>> materialDatePicker = builder.build();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Toast.makeText(FiltersActivity.this, "Start: " + selection.first + " End: " + selection.second, Toast.LENGTH_SHORT).show();
                dateButton.setText(materialDatePicker.getHeaderText());

                //Parse the selections
                startRange = selection.first;
                endRange = selection.second;
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Don't parse Values
                startRange = 0;
                endRange = 0;
                dateButton.setText("Όρισε ημ/νία");

                //Reset selections
                Pair setDefault = new Pair(null,null);
                builder.setSelection(setDefault);
                builder.build();
                Toast.makeText(FiltersActivity.this, materialDatePicker.getSelection()+ "", Toast.LENGTH_SHORT).show();

            }
        });


        //Range bar change listener
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
