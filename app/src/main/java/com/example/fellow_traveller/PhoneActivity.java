package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.NewOffer.NewOfferActivity;
import com.example.fellow_traveller.Register.CountryCodeAdapter;
import com.example.fellow_traveller.Register.CountryCodeItem;
import com.example.fellow_traveller.SearchAndBook.PaymentAdapter;
import com.example.fellow_traveller.SearchAndBook.PaymentItem;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static com.example.fellow_traveller.Util.InputValidation.isValidPhone;

public class PhoneActivity extends AppCompatActivity {
    private EditText numberEditText;
    private Button registerButton, loginButton;
    private Spinner countryCodeSpinner;
    private ArrayList<CountryCodeItem> countryCodeList;
    private CountryCodeAdapter mAdapter;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        globalClass = (GlobalClass) getApplicationContext();

        numberEditText = findViewById(R.id.ActivityPhone_number_editText);
        countryCodeSpinner = findViewById(R.id.ActivityPhone_country_code_spinner);
        registerButton = findViewById(R.id.ActivityPhone_register_button);
        loginButton = findViewById(R.id.ActivityPhone_login_button);

        countryCodeList = new ArrayList<>();
        countryCodeList.add(new CountryCodeItem("+30", R.drawable.ic_greek_flag));
        mAdapter = new CountryCodeAdapter(this, countryCodeList);
        countryCodeSpinner.setAdapter(mAdapter);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateRegisterStagePhone();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(PhoneActivity.this, LoginActivity.class);
                startActivity(mainIntent);
            }
        });
    }

    public void validateRegisterStagePhone() {

        if (isValidPhone(numberEditText.getText().toString())) {

            new FellowTravellerAPI(globalClass).checkFieldIfExist("phone", numberEditText.getText().toString(), new StatusCallBack() {
                @Override
                public void onSuccess(String status) {
                    Intent mainIntent = new Intent(PhoneActivity.this, VerificationActivity.class);
                    String code = "+30";            //TODO get the code from spinner from each country
                    String number = numberEditText.getText().toString();
                    mainIntent.putExtra("phoneNumber", code + number);

                    startActivity(mainIntent);
                }

                @Override
                public void onFailure(String errorMsg) {
                    Toast.makeText(PhoneActivity.this, getResources().getString(R.string.ERROR_PHONE_ALREADY_EXISTS), Toast.LENGTH_SHORT).show();


                }
            });

        } else {
            Toast.makeText(this,getResources().getString(R.string.INVALID_PHONE_FORMAT), Toast.LENGTH_SHORT).show();

        }
    }


}
