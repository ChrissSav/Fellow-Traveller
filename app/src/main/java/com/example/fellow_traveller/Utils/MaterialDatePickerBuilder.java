package com.example.fellow_traveller.Utils;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class MaterialDatePickerBuilder {

    public static MaterialDatePicker.Builder buildMaterialDatePicker(boolean isRangePicker) {
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

        // set calendar constraints
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setStart(left);
        constraintBuilder.setEnd(right);
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder;

        // see if we have to return either a rangePicker or a datePicker
        if (isRangePicker) {
            builder = MaterialDatePicker.Builder.dateRangePicker();
            builder.setTitleText("Επιλέξτε το εύρος των ημερών");
        } else {
            builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Επιλέξτε ημερομηνία");
        }

        builder.setCalendarConstraints(constraintBuilder.build());
        return builder;
    }

}
