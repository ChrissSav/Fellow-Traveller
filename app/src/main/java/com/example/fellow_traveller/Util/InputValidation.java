package com.example.fellow_traveller.Util;

import com.example.fellow_traveller.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class InputValidation {

    public static boolean isValidEmail(CharSequence email) {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ArrayList<Integer> isValidPassword(String password) {
        Pattern passwordLowerCasePattern = Pattern.compile("(?=.*[a-z]).+");
        Pattern passwordUpperCasePattern = Pattern.compile("(?=.*[A-Z]).+");
        Pattern passwordDigitsPattern = Pattern.compile("(?=.*\\d).+");
        Pattern passwordSpecialCharsPattern = Pattern.compile("(?=.+[~!@#$%^&*()_+-=|<>?{}\\[\\]]).+");

        ArrayList<Integer> errors = new ArrayList<>();

        if (password.length() < 8) {
            errors.add(R.string.PASSWORD_COMPLEXITY_LENGTH);
        }
        if (!passwordLowerCasePattern.matcher(password).matches()) {
            errors.add(R.string.PASSWORD_COMPLEXITY_LOWERCASE_LETTER);
        }
        if (!passwordUpperCasePattern.matcher(password).matches()) {
            errors.add(R.string.PASSWORD_COMPLEXITY_UPPERCASE_LETTER);
        }
        if (!passwordDigitsPattern.matcher(password).matches()) {
            errors.add(R.string.PASSWORD_COMPLEXITY_DIGIT);
        }
        if (!passwordSpecialCharsPattern.matcher(password).matches()) {
            errors.add(R.string.PASSWORD_COMPLEXITY_SPECIAL_CHARACTER);
        }

        return errors;
    }

    public static boolean isValidPlate(String plate) {
        Pattern regexPattern = Pattern.compile("^[Α-Ω]{3}[0-9]{4}$");
        return regexPattern.matcher(plate).matches();
    }
}
