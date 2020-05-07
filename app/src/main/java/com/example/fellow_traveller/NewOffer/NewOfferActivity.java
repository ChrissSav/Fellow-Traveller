package com.example.fellow_traveller.NewOffer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CreateTripModel;
import com.example.fellow_traveller.ClientAPI.Models.DestinationModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.PlacesAPI.CallBack.PlaceApiResultCallBack;
import com.example.fellow_traveller.PlacesAPI.Models.ResultModel;
import com.example.fellow_traveller.PlacesAPI.PlaceApiConnection;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SuccessActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.fellow_traveller.Util.SomeMethods.dateTimeToTimestamp;


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
    private TextView textViewTitleStage6;
    private ProgressBar progressBar;
    private int num_num;
    private DatabaseReference userDatabase;

    private GlobalClass globalClass;


    private DestinationModel destinationModelFrom;
    private DestinationModel destinationModelTo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        num_num = 100 / stages;
        globalClass = (GlobalClass) getApplicationContext();


        textViewTitleStage6 = findViewById(R.id.NewOfferActivity_textView_stage_6);
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
                if (fra.toString().equals("NewOfferStage1Fragment") && newOfferStage1Fragment.validateFragment()) {
                    fra = newOfferStage2Fragment;
                    progressBar.setProgress(num_num * newOfferStage2Fragment.getRank());
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

                    newOfferStage6Fragment.setFrom(newOfferStage1Fragment.getDestFrom());
                    newOfferStage6Fragment.setTo(newOfferStage1Fragment.getDestTo());

                    newOfferStage6Fragment.setDate(newOfferStage2Fragment.getDate());
                    newOfferStage6Fragment.setTime(newOfferStage2Fragment.getTime());


                    newOfferStage6Fragment.setSeats(newOfferStage3Fragment.getSeats());
                    newOfferStage6Fragment.setBags(newOfferStage3Fragment.getBags());


                    newOfferStage6Fragment.setPets(newOfferStage3Fragment.getPets());
                    newOfferStage6Fragment.setPrice(newOfferStage4Fragment.getPrice() + " " + getResources().getString(R.string.euro_symbol));

                    newOfferStage6Fragment.setCar(newOfferStage3Fragment.getCar());
                    newOfferStage6Fragment.setMsg(newOfferStage5Fragment.getMsg());

                    fra = newOfferStage6Fragment;
                    textViewTitleStage6.setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.NewOfferActivity_frame_container, fra).commit();
                    buttonNext.setText("Καταχώρηση");

                } else if (fra.toString().equals("NewOfferStage6Fragment")) {
                    getDestinationsModel(newOfferStage1Fragment.getDestFromModel().getPlaceId(),
                            newOfferStage1Fragment.getDestToModel().getPlaceId());
                   // tripRegister();
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getDateFromFragment(String tag) {
        textViewTitleStage6.setVisibility(View.GONE);
        buttonNext.setText("Επόμενο");
        switch (tag) {
            case "1":
                progressBar.setProgress(num_num * newOfferStage1Fragment.getRank());
                fra = newOfferStage1Fragment;
                break;
            case "2":
                progressBar.setProgress(num_num * newOfferStage2Fragment.getRank());
                fra = newOfferStage2Fragment;
                break;
            case "3":
                progressBar.setProgress(num_num * newOfferStage3Fragment.getRank());
                fra = newOfferStage3Fragment;
                break;
            case "4":
                progressBar.setProgress(num_num * newOfferStage4Fragment.getRank());
                fra = newOfferStage4Fragment;
                break;
            case "5":
                progressBar.setProgress(num_num * newOfferStage5Fragment.getRank());
                fra = newOfferStage5Fragment;
                break;
            default:
                break;
        }
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.NewOfferActivity_frame_container, fra).commit();


    }

    @Override
    public void onBackPressed() {
        textViewTitleStage6.setVisibility(View.GONE);
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
        //String dest_from = newOfferStage1Fragment.getDestFrom();
       // String dest_to = newOfferStage1Fragment.getDestTo();

        String date = newOfferStage2Fragment.getDate();
        String time = newOfferStage2Fragment.getTime();
        Boolean pet = newOfferStage3Fragment.getPetsBoolean();
        int max_seats = Integer.parseInt(newOfferStage3Fragment.getSeats());


        int max_bags = Integer.parseInt(newOfferStage3Fragment.getBags());
        int car_id = newOfferStage3Fragment.getCarObject().getId();

        Float price = Float.parseFloat(newOfferStage4Fragment.getPrice());
        String msg = newOfferStage5Fragment.getMsg();

        // Create trip object from model
        CreateTripModel trip = new CreateTripModel(destinationModelFrom, destinationModelTo, dateTimeToTimestamp(date, time),
                pet, max_seats, max_bags,
                msg, price, car_id);

        new FellowTravellerAPI(globalClass).createTrip(trip, new TripRegisterCallBack() {
            @Override
            public void onSuccess(StatusHandleModel status) {
                Intent intent = new Intent(NewOfferActivity.this, SuccessActivity.class);
                intent.putExtra("title", getResources().getString(R.string.success_add));
                createConversationForTrip();
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(NewOfferActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void createConversationForTrip() {  {

//            userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
//            HashMap<String, String> userMap = new HashMap<>();

//            userMap.put("name", name);
//            userMap.put("surname", surname);
//            userMap.put("image", "default");

//            userDatabase.setValue(userMap);
//
        }
    }

    public void getDestinationsModel(final String placeIdFrom,final String  placeIdTo) {
        new PlaceApiConnection(globalClass, true).getLatLonFromPlace(placeIdFrom, new PlaceApiResultCallBack() {
            @Override
            public void onSuccess(ResultModel resultModel) {
                destinationModelFrom = new DestinationModel(resultModel.getPlaceΙd(),newOfferStage1Fragment.getDestFrom()
                        , resultModel.getGeometry().getLocation().getLatitude(), resultModel.getGeometry().getLocation().getLongitude());

                new PlaceApiConnection(globalClass, true).getLatLonFromPlace(placeIdTo, new PlaceApiResultCallBack() {
                    @Override
                    public void onSuccess(ResultModel resultModel) {
                        destinationModelTo = new DestinationModel(resultModel.getPlaceΙd(), newOfferStage1Fragment.getDestTo()
                                , resultModel.getGeometry().getLocation().getLatitude(), resultModel.getGeometry().getLocation().getLongitude());

                        tripRegister();

                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Toast.makeText(NewOfferActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(NewOfferActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }

}












