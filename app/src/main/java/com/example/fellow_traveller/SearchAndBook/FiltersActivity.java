package com.example.fellow_traveller.SearchAndBook;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;

import com.example.fellow_traveller.ClientAPI.Models.FilterModel;
import com.example.fellow_traveller.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class FiltersActivity extends AppCompatActivity {
    private final long five_years_forward = (1000 * 60 * 60 * 24 * 365 * 5);
    private final String CLICK_COLOR = "#1C1C1C";
    private final String TITLE_PET = "Επέλεξε ...            ";
    private final String TITLE_SEAT = "Θέσεις ...";
    private final String TITLE_BAGS = "Αποσκεύες ...";
    boolean dateValidator = false, priceValidator = false, rangeValidator = false, seatsValidator = false, bagsValidator = false;
    private ConstraintLayout mainConstraintLayout;
    private CrystalSeekbar kmRangeSeekBar;
    private CrystalRangeSeekbar priceRangeSeekBar;
    private TextView kmRangeBarTV,priceRangeTV ;
    private Button dateButton, priceButton, petsButton, seatsButton, resetButton, applyButton;
    private ImageButton closeButton;
    private Spinner sortSpinner;
    private int priceStartFinal = 0, priceEndFinal = 100, rangeFinal = 0, seatsFinal = 0, bagsFinal = 0;
    private long startDateFinal, endDateFinal;
    private ArrayList<PaymentItem> petsChoicesList;
    private PaymentAdapter mAdapter;
    private Spinner petsSpinner;
    private String petTitle = TITLE_PET;
    private String seatTitle = TITLE_SEAT;
    private String bagsTitle = TITLE_BAGS;
    private FilterModel selectedFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        kmRangeSeekBar = findViewById(R.id.ActivityFilters_km_range_seekbar);
        kmRangeBarTV = findViewById(R.id.ActivityFilters_km_range_radius_tv);
        sortSpinner = findViewById(R.id.ActivityFilters_sort_by_spinner);

        dateButton = findViewById(R.id.ActivityFilters_date_button);
        resetButton = findViewById(R.id.ActivityFilters_reset_button);
        applyButton = findViewById(R.id.ActivityFilters_apply_button);
        closeButton = findViewById(R.id.ActivityFilters_close_button);
        priceRangeTV = findViewById(R.id.ActivityFilters_price_range_radius_tv);
        priceRangeSeekBar = findViewById(R.id.ActivityFilters_price_range_seekbar);
        petsButton = findViewById(R.id.ActivityFilters_pets_button);
        mainConstraintLayout = findViewById(R.id.ActivityFilters_main_constraint_layout);



        selectedFilters = new FilterModel();



        String[] items = new String[]{"     Πιο σχετική", "    Με βάση τιμή", "Με βάση απόσταση"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sortSpinner.setAdapter(adapter);

        openDialogForPrice();
        openDialogForSeats();
        openDialogForBags();
        initializeCalendar();


        petsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                openDialogForPets();
            }
        });
        //Range bar change listener
        kmRangeSeekBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                if (value.intValue() == 0) {
                    kmRangeBarTV.setText("Εμφάνιση όλων");
                    rangeFinal = value.intValue();
                    selectedFilters.setRange(null);
                } else {
                    kmRangeBarTV.setText("Εύρος " + value + " χλμ");
                    rangeFinal = value.intValue();
                    selectedFilters.setRange(value.intValue());
                }

            }
        });



        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FiltersActivity.this, "Ημερομηνία " + startDateFinal + "-" + endDateFinal + "Τιμή " + priceStartFinal + "-" + priceEndFinal + rangeFinal + seatsFinal + bagsFinal, Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("resultFilterModel", selectedFilters);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
    //Set background Color requires higher API
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    private void openDialogForPets() {
        final Dialog myDialog = new Dialog(FiltersActivity.this, R.style.Theme_Dialog);
        Window window = myDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.filter_pets_allowed_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mainConstraintLayout.setForeground(new ColorDrawable(getResources().getColor(R.color.greyBlack_transparent_color)));
        window.setAttributes(wlp);
        myDialog.show();

        final Button allButton = myDialog.findViewById(R.id.ActivityFilters_date_button);
        final Button allowedButton = myDialog.findViewById(R.id.ActivityFilters_pets_allowed_button);
        final Button notAllowedButton = myDialog.findViewById(R.id.ActivityFilters_pets_not_allowed_button);


        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petsButton.setText("Όλα");
                mainConstraintLayout.setForeground(null);
                selectedFilters.setHavePet(null);
                myDialog.dismiss();

            }
        });
        allowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petsButton.setText("Με κατοικίδιο");
                selectedFilters.setHavePet(true);
                mainConstraintLayout.setForeground(null);
                myDialog.dismiss();
            }
        });
        notAllowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petsButton.setText("Χωρίς κατοικίδιο");
                selectedFilters.setHavePet(false);
                mainConstraintLayout.setForeground(null);
                myDialog.dismiss();
            }

        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mainConstraintLayout.setForeground(null);
            }
        });


    }

    public void openDialogForSeats() {

        //get Elements
        ImageButton decrease = findViewById(R.id.ActivityBook_seats_minus_button);
        ImageButton increase = findViewById(R.id.ActivityBook_seats_plus_button);
        final TextView textView_number = findViewById(R.id.ActivityBook_seats_value_tv);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
                seatsFinal = Integer.valueOf(textView_number.getText().toString());
                selectedFilters.setBagsMin(seatsFinal);
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
                seatsFinal = Integer.valueOf(textView_number.getText().toString());
                selectedFilters.setBagsMin(seatsFinal);
            }
        });

    }

    public void openDialogForBags() {

        //get Elements
        ImageButton increase = findViewById(R.id.ActivityFilters_bags_plus_button);
        ImageButton decrease = findViewById(R.id.ActivityFilters_bags_minus_button);
        final TextView textView_number = findViewById(R.id.ActivityFilters_bags_value_tv);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
                bagsFinal = Integer.valueOf(textView_number.getText().toString());
                selectedFilters.setBagsMin(bagsFinal);

            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
                bagsFinal = Integer.valueOf(textView_number.getText().toString());
                selectedFilters.setBagsMin(bagsFinal);

            }
        });


    }

    public void openDialogForPrice() {

//        final Dialog myDialog = new Dialog(FiltersActivity.this, R.style.Theme_Dialog);
//        myDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.setContentView(R.layout.filter_pets_allowed_dialog);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        myDialog.setCancelable(true);
//        myDialog.setCanceledOnTouchOutside(true);




        // set listener
        priceRangeSeekBar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                if(minValue.intValue() == 0 && maxValue.intValue() == 100){
                    priceRangeTV.setText("Εμφάνιση όλων");
                    priceStartFinal = minValue.intValue();
                    priceEndFinal = maxValue.intValue();
                    selectedFilters.setPriceMin(null);
                    selectedFilters.setPriceMax(null);
                }else{
                priceRangeTV.setText(minValue.intValue() + " - " + maxValue.intValue() + " €" /*R.string.euro_symbol*/ );
                priceStartFinal = minValue.intValue();
                priceEndFinal = maxValue.intValue();
                    selectedFilters.setPriceMin(minValue.intValue());
                    selectedFilters.setPriceMax(maxValue.intValue());
                }
            }
        });
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

    public void initializeCalendar() {  //<----------Initialize Calender------------->
          // get builder from utility class
//        MaterialDatePicker.Builder builder = MaterialDatePickerBuilder.buildMaterialDatePicker(true);
//        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        // TODO you can replace from here
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


        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();
        // TODO to here by uncommenting the first two lines.

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {


                dateButton.setText(materialDatePicker.getHeaderText());

                //Parse the selections
                startDateFinal = selection.first;
                endDateFinal = selection.second;
//                selectedFilters.setTimestampMin(Integer.valueOf(Math.toIntExact(startDateFinal)));
//                selectedFilters.setTimestampMax(Integer.valueOf(Math.toIntExact(endDateFinal)));
                Toast.makeText(FiltersActivity.this, "Start: " + startDateFinal + " End: " + endDateFinal, Toast.LENGTH_SHORT).show();
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Don't parse Values

                dateButton.setText("Όρισε ημ/νία");
                startDateFinal = 0;
                endDateFinal = 0;

//                selectedFilters.setTimestampMin(null);
//                selectedFilters.setTimestampMax(null);
                //Reset selections
                Pair setDefault = new Pair(null, null);
                builder.setSelection(setDefault);
                builder.build();
                Toast.makeText(FiltersActivity.this, "Start: " + startDateFinal + " End: " + endDateFinal, Toast.LENGTH_SHORT).show();

            }
        });


    }


}
