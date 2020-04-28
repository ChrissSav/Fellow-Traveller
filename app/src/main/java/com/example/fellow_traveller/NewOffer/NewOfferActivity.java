package com.example.fellow_traveller.NewOffer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewOfferActivity extends AppCompatActivity {
    private final int stages = 6;
    private Button buttonNext;
    private ImageButton btnBack;
    private FragmentManager fragmentManager;
    private Fragment fra;
    private NewOfferStage1Fragment newOfferStage1Fragment = new NewOfferStage1Fragment();
    private NewOfferStage2Fragment newOfferStage2Fragment = new NewOfferStage2Fragment();
    private NewOfferStage3Fragment newOfferStage3Fragment = new NewOfferStage3Fragment();
    private NewOfferStage4Fragment newOfferStage4Fragment = new NewOfferStage4Fragment();
    private NewOfferStage5Fragment newOfferStage5Fragment = new NewOfferStage5Fragment();
    private NewOfferStage6Fragment newOfferStage6Fragment = new NewOfferStage6Fragment();

    private ProgressBar progressBar;
    private int num_num;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        num_num = 100 / stages;
        globalClass = (GlobalClass) getApplicationContext();


        globalClass = (GlobalClass) getApplicationContext();


        progressBar = findViewById(R.id.NewOfferActivity_progressBar);
        buttonNext = findViewById(R.id.NewOfferActivity_button_next);
        btnBack = findViewById(R.id.NewOfferActivity_imageButton);


        progressBar.setProgress(num_num * newOfferStage1Fragment.getRank());

        //Fragment Management


        fragmentManager = getSupportFragmentManager();
        fra = newOfferStage1Fragment;
        fragmentManager.beginTransaction().replace(R.id.NewOfferActivity_frame_container, fra).commit();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("fff", fra.toString());
                if (fra.toString().equals("NewOfferStage1Fragment") && newOfferStage1Fragment.validateFragment()) {
                    fra = newOfferStage2Fragment;
                    progressBar.setProgress(num_num * newOfferStage2Fragment.getRank());
                    //buttonNext.setText("Αποστολή");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();
                } else if (fra.toString().equals("NewOfferStage2Fragment") && newOfferStage2Fragment.validateFragment()) {
                    progressBar.setProgress(num_num * newOfferStage3Fragment.getRank());
                    fra = newOfferStage3Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();

                } else if (fra.toString().equals("NewOfferStage3Fragment") && newOfferStage3Fragment.validateFragment()) {
                    progressBar.setProgress(num_num * newOfferStage4Fragment.getRank());
                    newOfferStage4Fragment.setNum_of_passengers(Integer.parseInt(newOfferStage3Fragment.getSeats()));
                    fra = newOfferStage4Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();

                } else if (fra.toString().equals("NewOfferStage4Fragment") && newOfferStage4Fragment.validateFragment()) {
                    progressBar.setProgress(num_num * newOfferStage5Fragment.getRank());
                    fra = newOfferStage5Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();

                } else if (fra.toString().equals("NewOfferStage5Fragment") && newOfferStage4Fragment.validateFragment()) {
                    progressBar.setProgress(num_num * newOfferStage6Fragment.getRank());
                    newOfferStage6Fragment.setFrom(newOfferStage1Fragment.getDest_from());
                    newOfferStage6Fragment.setTo(newOfferStage1Fragment.getDest_to());

                    newOfferStage6Fragment.setDate(newOfferStage2Fragment.getDate());
                    newOfferStage6Fragment.setTime(newOfferStage2Fragment.getTime());


                    newOfferStage6Fragment.setSeats(newOfferStage3Fragment.getSeats());
                    newOfferStage6Fragment.setBags(newOfferStage3Fragment.getBags());


                    newOfferStage6Fragment.setPets(newOfferStage3Fragment.getPets());
                    newOfferStage6Fragment.setPrice(newOfferStage4Fragment.getPrice() + " " + getResources().getString(R.string.euro_symbol));

                    newOfferStage6Fragment.setCar(newOfferStage3Fragment.getCar());
                    newOfferStage6Fragment.setMsg(newOfferStage5Fragment.getMsg());

                    fra = newOfferStage6Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();
                    buttonNext.setText("Καταχώρηση");

                } else if (fra.toString().equals("NewOfferStage6Fragment")) {
                    tripRegister();
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (fra.toString().equals("NewOfferStage6Fragment")) {
            progressBar.setProgress(num_num * newOfferStage5Fragment.getRank());
            buttonNext.setText("Επόμενο");
            fra = newOfferStage5Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("NewOfferStage5Fragment")) {
            progressBar.setProgress(num_num * newOfferStage4Fragment.getRank());
            fra = newOfferStage4Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("NewOfferStage4Fragment")) {
            progressBar.setProgress(num_num * newOfferStage3Fragment.getRank());
            fra = newOfferStage3Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("NewOfferStage3Fragment")) {
            progressBar.setProgress(num_num * newOfferStage2Fragment.getRank());
            fra = newOfferStage2Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("NewOfferStage2Fragment")) {
            progressBar.setProgress(num_num * newOfferStage1Fragment.getRank());
            fra = newOfferStage1Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        } else {
            super.onBackPressed();
        }

    }


    public void tripRegister() {
        String dest_from = newOfferStage1Fragment.getDest_from();
        String dest_to = newOfferStage1Fragment.getDest_to();

        String date = newOfferStage2Fragment.getDate();
        String time = newOfferStage2Fragment.getTime();
        String pet = newOfferStage3Fragment.getPets();
        int max_seats = Integer.parseInt(newOfferStage3Fragment.getSeats());


        int max_bags = Integer.parseInt(newOfferStage3Fragment.getBags());
        int car_id = newOfferStage3Fragment.getCarObject().getId();

        Float price = Float.parseFloat(newOfferStage4Fragment.getPrice());
        String msg = newOfferStage5Fragment.getMsg();

        new FellowTravellerAPI(globalClass).tripRegister(dest_from, dest_to, pet, max_seats, max_bags, car_id,
                price, dateTimeToTimestamp(date, time), msg, new TripRegisterCallBack() {
                    @Override
                    public void onSuccess(StatusHandleModel status) {
                        // TODO take generic message from a resource file.
                        Toast.makeText(NewOfferActivity.this, "Επιτυχείς καταχώρηση", Toast.LENGTH_SHORT).show();
                        finish();
                        onBackPressed();

                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Toast.makeText(NewOfferActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public long dateTimeToTimestamp(String date, String time) {
        long p = Long.parseLong("0");
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            Date parsedDate = dateFormat.parse(date + " " + time);
            return parsedDate.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return p;
    }
}
