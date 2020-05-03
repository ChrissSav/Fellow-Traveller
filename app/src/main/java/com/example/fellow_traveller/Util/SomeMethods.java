package com.example.fellow_traveller.Util;


import android.view.View;

import com.example.fellow_traveller.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.core.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SomeMethods {

    public static long dateTimeToTimestamp(String date, String time) {
        long p = Long.parseLong("0");
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date parsedDate = dateFormat.parse(date + " " + time);
            return parsedDate.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void createSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setActionTextColor(view.getContext().getResources().getColor(R.color.colorPrimary))
                .show();
        return;
    }
}