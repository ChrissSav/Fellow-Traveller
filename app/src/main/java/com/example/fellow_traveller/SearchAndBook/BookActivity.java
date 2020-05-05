package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.R;
import com.example.fellow_traveller.SplashActivity;
import com.example.fellow_traveller.SuccessActivity;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    private ArrayList<PaymentItem> paymentMethodsList;
    private PaymentAdapter mAdapter;
    private Spinner paymentSpinner;
    private Switch petsSwitch;
    private TextView havePetWithMeTextView, bagsCurrentTextView;
    private ImageButton increaseBagsButton, decreaseBagsButton;
    private boolean havePet = false;
    private int currentBags = 0, maxAvailableBags;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        paymentSpinner = findViewById(R.id.book_payment_spinner);
        petsSwitch = findViewById(R.id.BookActivity_pets_switch);
        havePetWithMeTextView = findViewById(R.id.BookActivity_have_pet_tv);
        bagsCurrentTextView = findViewById(R.id.ActivityFilters_seats_value_tv);
        increaseBagsButton = findViewById(R.id.ActivityFilters_seats_plus_button);
        decreaseBagsButton = findViewById(R.id.ActivityFilters_seats_minus_button);
        nextButton = findViewById(R.id.next_book_button);

        maxAvailableBags = 3;

        getUserBags();




        fillList();

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
                Intent mainIntent = new Intent(BookActivity.this, SuccessActivity.class);
                mainIntent.putExtra("title",getResources().getString(R.string.success_search));
                startActivity(mainIntent);
                finish();
            }
        });
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
