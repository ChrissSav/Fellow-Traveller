package com.example.fellow_traveller.SearchAndBook;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
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
    private CrystalSeekbar rangeBar;
    private TextView rangeBarTV;
    private Button dateButton, priceButton, seatsButton, resetButton, applyButton;
    private ImageButton closeButton;
    private Spinner sortSpinner;
    private int priceStartFinal = 0, priceEndFinal = 200, rangeFinal = 0, seatsFinal = 0, bagsFinal = 0;
    private long startDateFinal, endDateFinal;
    private ArrayList<PaymentItem> petsChoicesList;
    private PaymentAdapter mAdapter;
    private Spinner petsSpinner;
    private String petTitle = TITLE_PET;
    private String seatTitle = TITLE_SEAT;
    private String bagsTitle = TITLE_BAGS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        rangeBar = findViewById(R.id.ActivityFilters_range_seekbar);
        rangeBarTV = findViewById(R.id.ActivityFilters_range_radius_tv);
        sortSpinner = findViewById(R.id.ActivityFilters_sort_by_spinner);
        priceButton = findViewById(R.id.ActivityFilters_price_button);
        dateButton = findViewById(R.id.ActivityFilters_date_button);
        resetButton = findViewById(R.id.ActivityFilters_reset_button);
        applyButton = findViewById(R.id.ActivityFilters_apply_button);
        closeButton = findViewById(R.id.ActivityFilters_close_button);
        petsSpinner = findViewById(R.id.ActivityFilters_pets_spinner);

        petsChoicesList = new ArrayList<>();
        petsChoicesList.add(new PaymentItem("Όλες", R.drawable.ic_bone));
        petsChoicesList.add(new PaymentItem("Με κατοικίδιο", R.drawable.ic_bone));
        petsChoicesList.add(new PaymentItem("Χωρίς κατοικίδιο", R.drawable.ic_bone));


        mAdapter = new PaymentAdapter(this, petsChoicesList);
        petsSpinner.setAdapter(mAdapter);


        String[] items = new String[]{"     Πιο σχετική", "    Με βάση τιμή", "Με βάση απόσταση"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sortSpinner.setAdapter(adapter);


        openDialogForSeats();
        openDialogForBags();
        initializeCalendar();


        //Range bar change listener
        rangeBar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                if (value.intValue() == 0) {
                    rangeBarTV.setText("Εμφάνιση όλων");
                    rangeFinal = value.intValue();
                } else {
                    rangeBarTV.setText("Εύρος " + value + " χλμ");
                    rangeFinal = value.intValue();
                }

            }
        });


        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogForPrice();
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FiltersActivity.this, "Ημερομηνία " + startDateFinal + "-" + endDateFinal + "Τιμή " + priceStartFinal + "-" + priceEndFinal + rangeFinal + seatsFinal + bagsFinal, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openDialogForSeats() {

        //get Elements
        ImageButton decrease = findViewById(R.id.ActivityFilters_seats_minus_button);
        ImageButton increase = findViewById(R.id.ActivityFilters_seats_plus_button);
        final TextView textView_number = findViewById(R.id.ActivityFilters_seats_value_tv);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Increase(textView_number);
                seatsFinal = Integer.valueOf(textView_number.getText().toString());
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
                seatsFinal = Integer.valueOf(textView_number.getText().toString());
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

            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decrease(textView_number);
                bagsFinal = Integer.valueOf(textView_number.getText().toString());

            }
        });


    }

    public void openDialogForPrice() {

        final Dialog myDialog = new Dialog(FiltersActivity.this, R.style.Theme_Dialog);
        myDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setContentView(R.layout.filter_price_range_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.setCancelable(true);
        myDialog.setCanceledOnTouchOutside(true);

        // get elements
        final TextView tvMin = myDialog.findViewById(R.id.filter_price_start);
        final TextView tvMax = myDialog.findViewById(R.id.filter_price_end);
        final Button selectButton = myDialog.findViewById(R.id.filter_price_button);
        final CrystalRangeSeekbar rangeSeekbar = myDialog.findViewById(R.id.rangeSeekbar1);

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText("€" + minValue);
                tvMax.setText("€" + maxValue);
                priceStartFinal = minValue.intValue();
                priceEndFinal = maxValue.intValue();
            }
        });
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priceButton.setText("€" + priceStartFinal + " - " + "€" + priceEndFinal);
                myDialog.dismiss();
                Toast.makeText(FiltersActivity.this, "Start: " + priceStartFinal + " End: " + priceEndFinal, Toast.LENGTH_SHORT).show();
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

    public void initializeCalendar() {  //<----------Initialize Calender------------->
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

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {


                dateButton.setText(materialDatePicker.getHeaderText());

                //Parse the selections
                startDateFinal = selection.first;
                endDateFinal = selection.second;
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
                //Reset selections
                Pair setDefault = new Pair(null, null);
                builder.setSelection(setDefault);
                builder.build();
                Toast.makeText(FiltersActivity.this, "Start: " + startDateFinal + " End: " + endDateFinal, Toast.LENGTH_SHORT).show();

            }
        });


    }


}
