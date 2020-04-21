package com.example.fellow_traveller.NewOffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.fellow_traveller.R;

public class NewOfferActivity extends AppCompatActivity {
    private final int stages = 5;
    private Button button_next;
    private ImageButton btn_back;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        num_num = 100 / stages;


        progressBar = findViewById(R.id.NewOfferActivity_progressBar);
        button_next = findViewById(R.id.NewOfferActivity_button_next);
        btn_back = findViewById(R.id.NewOfferActivity_imageButton);


        progressBar.setProgress(num_num * newOfferStage1Fragment.getRank());

        //Fragment Management


        fragmentManager = getSupportFragmentManager();
        fra = newOfferStage1Fragment;
        fragmentManager.beginTransaction().replace(R.id.NewOfferActivity_frame_container, fra).commit();

        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("fff",fra.toString());
                if (fra.toString().equals("NewOfferStage1Fragment")){// && newOfferStage1Fragment.isOk()) {
                    fra = newOfferStage2Fragment;
                    progressBar.setProgress(num_num * newOfferStage2Fragment.getRank());
                    //button_next.setText("Αποστολή");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();
                } else if (fra.toString().equals("NewOfferStage2Fragment")){// && newOfferStage2Fragment.isOk() ) {
                    progressBar.setProgress(num_num * newOfferStage3Fragment.getRank());
                    fra = newOfferStage3Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();

                }else if (fra.toString().equals("NewOfferStage3Fragment") ){//&& newOfferStage3Fragment.isOk() ) {
                    progressBar.setProgress(num_num * newOfferStage4Fragment.getRank());
                    newOfferStage4Fragment.setNum_of_passengers(Integer.parseInt(newOfferStage3Fragment.getSeats()));
                    fra = newOfferStage4Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();

                }else if (fra.toString().equals("NewOfferStage4Fragment")) {// && newOfferStage4Fragment.isOk()) {
                    progressBar.setProgress(num_num * newOfferStage5Fragment.getRank());
                    fra = newOfferStage5Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();

                }
                else if (fra.toString().equals("NewOfferStage5Fragment")) {// && newOfferStage4Fragment.isOk()) {
                    progressBar.setProgress(num_num * newOfferStage6Fragment.getRank());
                    newOfferStage6Fragment.setFrom(newOfferStage1Fragment.getDest_from());
                    newOfferStage6Fragment.setTo(newOfferStage1Fragment.getDest_to());

                    newOfferStage6Fragment.setDate(newOfferStage2Fragment.getDate());
                    newOfferStage6Fragment.setTime(newOfferStage2Fragment.getTime());


                    newOfferStage6Fragment.setSeats(newOfferStage3Fragment.getSeats());
                    newOfferStage6Fragment.setBags(newOfferStage3Fragment.getBags());


                    newOfferStage6Fragment.setPets(newOfferStage3Fragment.getPets());
                    newOfferStage6Fragment.setPrice(newOfferStage4Fragment.getPrice()+" "+getResources().getString(R.string.euro_symbol));

                    newOfferStage6Fragment.setCar(newOfferStage3Fragment.getCar());
                    newOfferStage6Fragment.setMsg(newOfferStage5Fragment.getMsg());

                    fra = newOfferStage6Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();
                    button_next.setText("Καταχώρηση");

                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (fra.toString().equals("NewOfferStage6Fragment")) {
            progressBar.setProgress(num_num * newOfferStage5Fragment.getRank());
            button_next.setText("Επόμενο");
            fra = newOfferStage5Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        }
        else if (fra.toString().equals("NewOfferStage5Fragment")) {
            progressBar.setProgress(num_num * newOfferStage4Fragment.getRank());
            fra = newOfferStage4Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("NewOfferStage4Fragment")) {
            progressBar.setProgress(num_num * newOfferStage3Fragment.getRank());
            fra = newOfferStage3Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        }
        else if (fra.toString().equals("NewOfferStage3Fragment")) {
            progressBar.setProgress(num_num * newOfferStage2Fragment.getRank());
            fra = newOfferStage2Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        }
        else if (fra.toString().equals("NewOfferStage2Fragment")) {
            progressBar.setProgress(num_num * newOfferStage1Fragment.getRank());
            fra = newOfferStage1Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();

        }else {
            super.onBackPressed();
        }

    }
}
