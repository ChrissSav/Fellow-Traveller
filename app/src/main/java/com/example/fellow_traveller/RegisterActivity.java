package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.Register.RegisterContainerActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_reg;
    private Spinner spinner;
    private EditText editText;
    private ConstraintLayout constraintLayout;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        globalClass = (GlobalClass) getApplicationContext();
        btn_reg = findViewById(R.id.RegisterActivity_button_next);
        spinner = findViewById(R.id.RegisterActivity_spinner_num_code);
        editText = findViewById(R.id.RegisterActivity_editText_number);
        constraintLayout = findViewById(R.id.RegisterActivityConstraintLayout);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ValidateNum(editText.getText().toString())) {
                    new FellowTravellerAPI(globalClass).checkFieldIfExist("phone", editText.getText().toString(), new StatusCallBack() {
                        @Override
                        public void onSuccess(String status) {
                              Intent intent = new Intent(RegisterActivity.this, RegisterContainerActivity.class);
                        intent.putExtra("USER_PHONE", editText.getText().toString());
                        startActivity(intent);

                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            //Toast.makeText(RegisterActivity.this,  getResources().getString(R.string.ERROR_PHONE_ALREADY_EXISTS), Toast.LENGTH_SHORT).show();
                            editText.setError( getResources().getString(R.string.ERROR_PHONE_ALREADY_EXISTS));

                        }
                    });

                } else {

                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.INVALID_PHONE_FORMAT), Toast.LENGTH_SHORT).show();

                }
            }
        });


        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setError(null);
            }
        });
    }

    public Boolean ValidateNum(String num) {
        if (num.length() < 10) {
            return false;
        } else {
            return true;
        }

    }




}
