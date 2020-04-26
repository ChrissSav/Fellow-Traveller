package com.example.fellow_traveller.Utils;

import java.util.regex.Pattern;

public class InputValidation {

    public static boolean isValidEmail(CharSequence email) {
        if (email == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        Pattern regexPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]).+$");
        return regexPattern.matcher(password).matches();
    }

    public static boolean isValidPlate(String plate) {
        Pattern regexPattern = Pattern.compile("^[Α-Ω]{3}[0-9]{4}$");
        return regexPattern.matcher(plate).matches();
    }
}
