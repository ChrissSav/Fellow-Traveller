package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private View underlinePhone;
    private ImageButton eraseButton;
    private boolean numberFocusFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        globalClass = (GlobalClass) getApplicationContext();

        numberEditText = findViewById(R.id.ActivityPhone_number_editText);
        countryCodeSpinner = findViewById(R.id.ActivityPhone_country_code_spinner);
        registerButton = findViewById(R.id.ActivityPhone_register_button);
        loginButton = findViewById(R.id.ActivityPhone_login_button);
        underlinePhone = findViewById(R.id.ActivityPhone_section_underline);
        eraseButton = findViewById(R.id.ActivityPhone_erase_button);

        countryCodeList = new ArrayList<>();
        countryCodeList.add(new CountryCodeItem("+30", R.drawable.ic_greek_flag));
        mAdapter = new CountryCodeAdapter(this, countryCodeList);
        countryCodeSpinner.setAdapter(mAdapter);

        numberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    //Toast.makeText(getActivity(), "Get Focus", Toast.LENGTH_SHORT).show();
                    underlinePhone.setPressed(true);
                    numberFocusFlag = true;
                }else{
                    //Toast.makeText(getActivity(), "Lost Focus", Toast.LENGTH_SHORT).show();
                    underlinePhone.setPressed(false);
                    numberFocusFlag = false;
                }
            }
        });

        numberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    eraseButton.setVisibility(View.VISIBLE);
                else
                    eraseButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberEditText.setText("");
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

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(numberFocusFlag)
            underlinePhone.setPressed(true);
    }
}
