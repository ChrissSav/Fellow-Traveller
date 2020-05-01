package com.example.fellow_traveller.Util;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class InputValidation {

    public static boolean isValidEmail(CharSequence email) {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ArrayList<Integer> isValidPassword(String password) {
        final Integer missingDigit = 0, missingLowerCase = 1,
                missingUpperCase = 2, missingSpecialChar = 3,
                missingRequiredLength = 4, MINIMUM_PASSWORD_LENGTH = 8;
        Pattern passwordLowerCasePattern = Pattern.compile("(?=.*[a-z]).+");
        Pattern passwordUpperCasePattern = Pattern.compile("(?=.*[A-Z]).+");
        Pattern passwordDigitsPattern = Pattern.compile("(?=.*\\d).+");
        Pattern passwordSpecialCharsPattern = Pattern.compile("(?=.+[~!@#$%^&*()_+=|<>?{};\\[\\]]).+");

        ArrayList<Integer> errors = new ArrayList<>();

        if (MINIMUM_PASSWORD_LENGTH.compareTo(password.length()) > 0) {
            errors.add(missingRequiredLength);
        }
        if (!passwordLowerCasePattern.matcher(password).matches()) {
            errors.add(missingLowerCase);
        }
        if (!passwordUpperCasePattern.matcher(password).matches()) {
            errors.add(missingUpperCase);
        }
        if (!passwordDigitsPattern.matcher(password).matches()) {
            errors.add(missingDigit);
        }
        if (!passwordSpecialCharsPattern.matcher(password).matches()) {
            errors.add(missingSpecialChar);
        }

        return errors;
    }

    public static boolean isValidPlate(String plate) {
        Pattern regexPattern = Pattern.compile("^[Α-Ω]{3}-[0-9]{4}$");
        return regexPattern.matcher(plate).matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern regexPattern = Pattern.compile("^[6][9][0-9]{8}$");
        return regexPattern.matcher(phone).matches();
    }

    public static boolean validatePasswordComplexity(String password, ArrayList<TextView> passwordComplexityRequirementsTextViews) {
        // validates password complexity while user is typing in the password input field
        ArrayList<Integer> errors = isValidPassword(password);
        TextView complexityRequirementTextView;
        Boolean isValidPass = null;

        for (int i = 0; i < 5; i++) {
            // extract TextView from the array
            complexityRequirementTextView = passwordComplexityRequirementsTextViews.get(i);
            // check if there's any errors
            if (!errors.isEmpty() && errors.contains(i)) {
                // current TextView should be changed to indicate there's a unfulfilled complexity requirement
                complexityRequirementTextView.setTypeface(complexityRequirementTextView.getTypeface(), Typeface.BOLD_ITALIC);
                // current input doesn't password doesn't pass complexity requirements yet
                isValidPass = false;
            } else {
                // clear any error that was left from the previous check and isn't needed anymore
                complexityRequirementTextView.setTypeface(complexityRequirementTextView.getTypeface(), Typeface.ITALIC);
                // input passes complexity check
                isValidPass = true;
            }
        }

        return isValidPass;
    }

}
