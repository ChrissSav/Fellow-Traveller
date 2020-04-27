package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
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
    private Button  dateButton, priceButton, seatsButton, petsButton, bagsButton, resetButton, applyButton;
    private ImageButton closeButton;
    private Spinner sortSpinner;
    private final long five_years_forward = (1000*60*60*24*365*5);
    private long startRange = 0, endRange = 0;

    private final String CLICK_COLOR = "#1C1C1C";
    private final String TITLE_PET = "Επέλεξε ...            ";
    private final String TITLE_SEAT = "Θέσεις ...";
    private final String TITLE_BAGS = "Αποσκεύες ...";

    private String petTitle = TITLE_PET;
    private String seatTitle = TITLE_SEAT;
    private String bagsTitle = TITLE_BAGS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        rangeBar = findViewById(R.id.slider_filters);
        rangeBarTV = findViewById(R.id.range_km_tv);
        sortSpinner = findViewById(R.id.ActivityFilter_sort_spinner);
        priceButton = findViewById(R.id.price_button);
        dateButton = findViewById(R.id.date_button);
        seatsButton = findViewById(R.id.seats_button);
        resetButton = findViewById(R.id.reset_button);
        petsButton = findViewById(R.id.pets_button);
        applyButton = findViewById(R.id.apply_button);
        closeButton = findViewById(R.id.close_button_filters);
        bagsButton = findViewById(R.id.bags_button);

        //Text Color
        if (petsButton.getText() != TITLE_PET) {
            petsButton.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (bagsButton.getText() != TITLE_BAGS) {
            bagsButton.setTextColor(Color.parseColor(CLICK_COLOR));
        }
        if (seatsButton.getText() != TITLE_SEAT) {
            seatsButton.setTextColor(Color.parseColor(CLICK_COLOR));
        }

        String[] items = new String[]{"     Πιο σχετική", "    Με βάση τιμή", "Με βάση απόσταση"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        sortSpinner.setAdapter(adapter);
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



        seatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogForSeats();
            }
        });
        bagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogForBags();
            }
        });
        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogForPrice();
            }
        });
        petsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petsButton.setTextColor(Color.parseColor(CLICK_COLOR));
                if (petsButton.getText().equals(TITLE_PET) || petsButton.getText().equals("Δεν επιτρέπονται")) {
                    petsButton.setText("Επιτρέπονται");
                    return;
                }
                if (petsButton.getText().equals("Επιτρέπονται")) {
                    petsButton.setText("Δεν επιτρέπονται");
                    return;
                }
            }
        });

    }
    public void openDialogForSeats() {

        final Dialog myDialog = new Dialog(FiltersActivity.this, R.style.Theme_Dialog);
        myDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.number_choose);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);

        //get Elements
        Button button = myDialog.findViewById(R.id.choose_num_button);
        ImageButton increase = myDialog.findViewById(R.id.choose_num_imageButton_plus);
        ImageButton decrease = myDialog.findViewById(R.id.choose_num_imageButton_minus);
        final TextView textView_number = myDialog.findViewById(R.id.choose_num_textView_number);
        TextView textView_title = myDialog.findViewById(R.id.choose_num_textView_title);
        if (!seatsButton.getText().equals(TITLE_SEAT)) {
            textView_number.setText(seatsButton.getText().toString());
        }

        textView_title.setText("Ελάχιστες θέσεις που θέλετε");



        myDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
                seatsButton.setTextColor(Color.parseColor(CLICK_COLOR));
                seatsButton.setText(textView_number.getText().toString());
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
            }
        });

    }
    public void openDialogForBags() {

        final Dialog myDialog = new Dialog(FiltersActivity.this,
                R.style.Theme_Dialog);
        myDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.number_choose);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);

        //get Elements
        Button button = myDialog.findViewById(R.id.choose_num_button);
        ImageButton increase = myDialog.findViewById(R.id.choose_num_imageButton_plus);
        ImageButton decrease = myDialog.findViewById(R.id.choose_num_imageButton_minus);
        final TextView textView_number = myDialog.findViewById(R.id.choose_num_textView_number);
        TextView textView_title = myDialog.findViewById(R.id.choose_num_textView_title);
        if (!bagsButton.getText().equals(TITLE_BAGS)) {
            textView_number.setText(bagsButton.getText().toString());
        }

        textView_title.setText("Ελάχιστες αποσκευές που θέλετε");



        myDialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.dismiss();
                bagsButton.setTextColor(Color.parseColor(CLICK_COLOR));
                bagsButton.setText(textView_number.getText().toString());

            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
            }
        });

    }
    public void openDialogForPrice() {

        final Dialog myDialog = new Dialog(FiltersActivity.this, R.style.Theme_Dialog);
        myDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.filter_price_range_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);

        // get elements
        final TextView tvMin = (TextView) myDialog.findViewById(R.id.filter_price_start);
        final TextView tvMax = (TextView) myDialog.findViewById(R.id.filter_price_end);
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) myDialog.findViewById(R.id.rangeSeekbar1);

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("€" + String.valueOf(minValue));
                tvMax.setText("€" + String.valueOf(maxValue));
            }
        });
        myDialog.show();






    }
    public void Increase(TextView textView) {
        int current_num = Integer.parseInt(textView.getText().toString());
        textView.setText((current_num + 1) + "");

    }
    public void Decrease(TextView textView) {
        int current_num = Integer.parseInt(textView.getText().toString());
        if (current_num > 0)
            textView.setText((current_num - 1) + "");
    }

    public void initializeCalendar(){

    }


}
