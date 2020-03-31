package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fellow_traveller.Register.RegisterContainerActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_reg;
    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_reg = findViewById(R.id.RegisterActivity_button_next);
        spinner = findViewById(R.id.RegisterActivity_spinner_num_code);
        editText = findViewById(R.id.RegisterActivity_editText_number);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValidateNum(editText.getText().toString())){
                    Intent intent = new Intent(RegisterActivity.this, RegisterContainerActivity.class);
                    intent.putExtra("USER_PHONE", editText.getText().toString());
                    startActivity(intent);
                    finish();
                }else{

                }
            }
        });
    }

    public Boolean ValidateNum(String num){
        if(num.length()<10){
            return false;
        }
        else {
            return true;
        }

    }
}
