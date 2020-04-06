package com.example.fellow_traveller.SearchAndBook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fellow_traveller.R;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    private ArrayList<PaymentItem> paymentMethodsList;
    private PaymentAdapter mAdapter;
    private Spinner paymentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        paymentSpinner = findViewById(R.id.book_payment_spinner);

        fillList();

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

    }

    private void fillList() {

        paymentMethodsList = new ArrayList<>();
        paymentMethodsList.add(new PaymentItem("Πιστωτική κάρτα", R.drawable.ic_card));
        paymentMethodsList.add(new PaymentItem("Μετρητά", R.drawable.ic_cash));
        paymentMethodsList.add(new PaymentItem("PayPal", R.drawable.ic_paypal));
    }
}
