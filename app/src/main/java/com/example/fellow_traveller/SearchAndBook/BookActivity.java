package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.SearchTripsCallback;
import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.TripRegisterCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CreatePassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.PassengerModel;
import com.example.fellow_traveller.ClientAPI.Models.StatusHandleModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;

import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SuccessActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BookActivity extends AppCompatActivity {
    private ArrayList<PaymentItem> paymentMethodsList;
    private PaymentAdapter mAdapter;
    private Spinner paymentSpinner;
    private Switch petsSwitch;
    private TextView destStartTextView, destEndTextView, dateTextView, timeTextView, seatsTextView, dateMonthTextView, timePmTextView, seatsDefinitionTextView, priceTextView;
    private TextView havePetWithMeTextView, bagsCurrentTextView;
    private ImageButton increaseBagsButton, decreaseBagsButton;
    private boolean havePet = false;
    private int currentBags = 0, maxAvailableBags;
    private TripModel tripModel;
    private Button nextButton;
    private GlobalClass globalClass;
    private DatabaseReference tripsDatabase, tripsAndParticipantsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        globalClass = (GlobalClass) getApplicationContext();

        paymentSpinner = findViewById(R.id.ActivityBook_payment_spinner);
        petsSwitch = findViewById(R.id.ActivityBook_pets_switch);
        havePetWithMeTextView = findViewById(R.id.ActivityBook_have_pet_textView);
        bagsCurrentTextView = findViewById(R.id.ActivityBook_seats_value_tv);
        increaseBagsButton = findViewById(R.id.ActivityBook_seats_plus_button);
        decreaseBagsButton = findViewById(R.id.ActivityBook_seats_minus_button);
        nextButton = findViewById(R.id.ActivityBooks_next_button);
        destStartTextView = findViewById(R.id.ActivityBook_start_textView);
        destEndTextView = findViewById(R.id.ActivityBook_start_textView);
        dateTextView = findViewById(R.id.ActivityBook_date_textView);
        timeTextView = findViewById(R.id.ActivityBook_time_textView);
        seatsTextView = findViewById(R.id.ActivityBook_seats_textView);
        dateMonthTextView = findViewById(R.id.ActivityBook_date_month_textView);
        timePmTextView = findViewById(R.id.ActivityBook_time_pm_textView);
        seatsDefinitionTextView = findViewById(R.id.ActivityBook_seats_definition_textView);
        priceTextView = findViewById(R.id.ActivityBook_price_textView);


        tripModel = getIntent().getParcelableExtra("trip");

        maxAvailableBags = tripModel.getMaxBags();

        getUserBags();

        destStartTextView.setText(tripModel.getDestTo().getTitle());
        destEndTextView.setText(tripModel.getDestFrom().getTitle());

        timeTextView.setText(tripModel.getTime());
        seatsTextView.setText(String.valueOf(tripModel.getMaxSeats()));
        if(tripModel.getMaxSeats() > 1)
            seatsDefinitionTextView.setText("Θέσεις");
        else
            seatsDefinitionTextView.setText("Θέση");

        Date currentDate = new Date(tripModel.getTimestamp()*1000);
        DateFormat day = new SimpleDateFormat("dd");
        DateFormat month = new SimpleDateFormat("MMM");
        DateFormat pm = new SimpleDateFormat("a");
        dateTextView.setText(day.format(currentDate));
        dateMonthTextView.setText(month.format(currentDate));
        timePmTextView.setText(pm.format(currentDate));

        //Delete 0 decimals
        if (tripModel.getPrice().intValue() == tripModel.getPrice())
            priceTextView.setText(tripModel.getPrice().intValue() + getResources().getString(R.string.euro_symbol));
        else
            priceTextView.setText(tripModel.getPrice() + getResources().getString(R.string.euro_symbol));

        fillList();
        if(tripModel.getPet()){
        petsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    havePetWithMeTextView.setText("Ναι, έχω");
                    havePet = true;
                }
                else {
                    havePetWithMeTextView.setText("Όχι, δεν έχω");
                    havePet = false;
                }
            }
        });
        }else{
            petsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Toast.makeText(BookActivity.this, "Ο οδηγός δεν επιτρέπει κατοικίδιο", Toast.LENGTH_SHORT).show();
                    havePet = false;
                    petsSwitch.setChecked(false);

                }
            });
        }

        mAdapter = new PaymentAdapter(this, paymentMethodsList);
        paymentSpinner.setAdapter(mAdapter);

        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PaymentItem clickedItem = (PaymentItem) adapterView.getItemAtPosition(i);
                String method = clickedItem.getPaymentMethod();
                Toast.makeText(BookActivity.this, "Επιλέχθηκε " + method + " για πληρωμή", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                CreatePassengerModel createPassengerModel = new CreatePassengerModel(tripModel.getId(), currentBags, havePet);

                new FellowTravellerAPI(globalClass).addPassengerToTrip(createPassengerModel, new StatusCallBack() {
                    @Override
                    public void onSuccess(String status) {
                        //Toast.makeText(BookActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, SuccessActivity.class);
                        intent.putExtra("title", getResources().getString(R.string.success_search));
                        assignPassengerToTripConversation();
                        assignTripToPassengersConversation();

                        assignTripToCreatorConversation();
                        assignCreatorToTripConversation();
                        startActivity(intent);
                        finish();

                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Toast.makeText(BookActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });
    }

    private void assignTripToPassengersConversation() {
        tripsAndParticipantsDatabase = FirebaseDatabase.getInstance().getReference().child("TripsAndParticipants").child(String.valueOf(tripModel.getId())).child(String.valueOf(globalClass.getCurrentUser().getId()));

        HashMap<String, Object> tripsAndParticipantsMap = new HashMap<>();
        tripsAndParticipantsMap.put("userId",  globalClass.getCurrentUser().getId());

        tripsAndParticipantsDatabase.setValue(tripsAndParticipantsMap);
    }
    private void assignTripToCreatorConversation() {
        tripsAndParticipantsDatabase = FirebaseDatabase.getInstance().getReference().child("TripsAndParticipants").child(String.valueOf(tripModel.getId())).child(String.valueOf(tripModel.getCreatorUser().getId()));

        HashMap<String, Object> tripsAndParticipantsMap = new HashMap<>();
        tripsAndParticipantsMap.put("userId",  tripModel.getCreatorUser().getId());

        tripsAndParticipantsDatabase.setValue(tripsAndParticipantsMap);
    }


    private void assignPassengerToTripConversation() {
            tripsDatabase = FirebaseDatabase.getInstance().getReference().child("Trips").child(String.valueOf(globalClass.getCurrentUser().getId())).child(String.valueOf(tripModel.getId()));

            HashMap<String, Object> tripsMap = new HashMap<>();
            tripsMap.put("date", System.currentTimeMillis() / 1000);
            tripsMap.put("seen", true);
            tripsMap.put("tripId", tripModel.getId());
            tripsMap.put("tripName", tripModel.getDestFrom().getTitle() + " - " + tripModel.getDestTo().getTitle());


            tripsDatabase.setValue(tripsMap);

    }
    private void assignCreatorToTripConversation() {
        tripsDatabase = FirebaseDatabase.getInstance().getReference().child("Trips").child(String.valueOf(tripModel.getCreatorUser().getId())).child(String.valueOf(tripModel.getId()));

        HashMap<String, Object> tripsMap = new HashMap<>();
        tripsMap.put("date", System.currentTimeMillis() / 1000);
        tripsMap.put("seen", true);
        tripsMap.put("tripId", tripModel.getId());
        tripsMap.put("tripName", tripModel.getDestFrom().getTitle() + " - " + tripModel.getDestTo().getTitle());


        tripsDatabase.setValue(tripsMap);

    }

    private void getUserBags() {
        increaseBagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentBags < maxAvailableBags) {
                    currentBags++;
                    bagsCurrentTextView.setText(String.valueOf(currentBags));

                }else
                    Toast.makeText(BookActivity.this, "Ο μέγιστος αριθμός αποσκευών που μπορείτε να επιλέξετε για αυτό το ταξίδι είναι " + String.valueOf(maxAvailableBags), Toast.LENGTH_SHORT).show();

            }
        });
        decreaseBagsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentBags > 0) {
                    currentBags--;
                    bagsCurrentTextView.setText(String.valueOf(currentBags));

                }

            }
        });
    }

    private void fillList() {

        paymentMethodsList = new ArrayList<>();
        paymentMethodsList.add(new PaymentItem("Πιστωτική κάρτα", R.drawable.ic_card));
        paymentMethodsList.add(new PaymentItem("Μετρητά", R.drawable.ic_cash_filled));
        paymentMethodsList.add(new PaymentItem("PayPal", R.drawable.ic_paypal));
    }
}
