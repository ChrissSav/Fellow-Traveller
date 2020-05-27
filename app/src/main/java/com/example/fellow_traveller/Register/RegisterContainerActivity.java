package com.example.fellow_traveller.Register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.Callbacks.UserRegisterCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserRegisterModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.fellow_traveller.Util.InputValidation.isValidEmail;

public class RegisterContainerActivity extends AppCompatActivity {

    private final int stages = 3;
    private Button buttonNext;
    private ImageButton btnBack;
    private FragmentManager fragmentManager;
    private Fragment fra;
    //private RegisterStagePhoneFragment registerStagePhoneFragment = new RegisterStagePhoneFragment();
    private RegisterStage1Fragment registerStage1Fragment = new RegisterStage1Fragment();
    private RegisterStage2Fragment registerStage2Fragment = new RegisterStage2Fragment();
    private RegisterStage3Fragment registerStage3Fragment = new RegisterStage3Fragment();
    private ProgressBar progressBar;
    private int num_num;
    private String userPhone = "+306940184085";
    private GlobalClass globalClass;
    private DatabaseReference userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_container);
        globalClass = (GlobalClass) getApplicationContext();

        //userPhone = getIntent().getStringExtra("phoneNumber");
        num_num = 100 / stages;

        progressBar = findViewById(R.id.RegisterActivity_progressBar);
        buttonNext = findViewById(R.id.RegisterActivity_button_next);
        btnBack = findViewById(R.id.RegisterActivity_imageButton);
        

        //Fragment Management
        fragmentManager = getSupportFragmentManager();
        progressBar.setProgress(num_num * registerStage1Fragment.getRank());
        fra = registerStage1Fragment;
        fragmentManager.beginTransaction().replace(R.id.RegisterActivity_frame_container, fra).commit();


        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if (fra.toString().equals("RegisterStagePhoneFragment")) {
                //     validateRegisterStagePhone();
                //  }
                if (fra.toString().equals("RegisterStage1Fragment")) {
                    validateRegisterStageEmail();
                } else if (fra.toString().equals("RegisterStage2Fragment") && registerStage2Fragment.validateFragment()) {
                    progressBar.setProgress(num_num * registerStage3Fragment.getRank());
                    fra = registerStage3Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                    buttonNext.setText("");

                } else if (fra.toString().equals("RegisterStage3Fragment") && registerStage3Fragment.validateFragment()) {
                    newRegisterUser();
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

        buttonNext.setText("");
        if (fra.toString().equals("RegisterStage3Fragment")) {
            progressBar.setProgress(num_num * registerStage2Fragment.getRank());
            fra = registerStage2Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("RegisterStage2Fragment")) {
            progressBar.setProgress(num_num * registerStage1Fragment.getRank());
            fra = registerStage1Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();
        }
       /* else if (fra.toString().equals("RegisterStage1Fragment")) {
            progressBar.setProgress(num_num * registerStagePhoneFragment.getRank());
            fra = registerStagePhoneFragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        }*/
        else {
            super.onBackPressed();
        }

    }

    public void newRegisterUser() {
        String email = registerStage1Fragment.getEmail();
        String password = registerStage2Fragment.getPassword();
        String firstName = registerStage3Fragment.getFirstName();
        String lastName = registerStage3Fragment.getLastName();

        UserRegisterModel user = new UserRegisterModel(firstName, lastName, email, password, userPhone.substring(3, 13));
        Log.i("userPhone", "userPhone: userPhone");

        new FellowTravellerAPI(globalClass).userRegister(user, new UserRegisterCallback() {
            @Override
            public void onSuccess(UserAuthModel user) {

                Save(user);
                firebaseRegister(String.valueOf(user.getId()), user.getName(), user.getSurname());
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(RegisterContainerActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void firebaseRegister(String id, String name, String surname) {
        userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", name);
        userMap.put("surname", surname);
        userMap.put("image", "default");

        userDatabase.setValue(userMap);

    }

    public void Save(UserAuthModel userAuth) {
        firebaseRegister(userAuth.getId() + "", userAuth.getName(), userAuth.getSurname());
        globalClass.SaveClass(userAuth);
        Intent intent = new Intent(RegisterContainerActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    public void validateRegisterStageEmail() {
        EditText editText = registerStage1Fragment.getEditText();
        if (isValidEmail(editText.getText())) {
            registerStage1Fragment.setErrorToEditText(null);

            new FellowTravellerAPI(globalClass).checkFieldIfExist("email", editText.getText().toString(), new StatusCallBack() {
                @Override
                public void onSuccess(String status) {
                    fra = registerStage2Fragment;
                    progressBar.setProgress(num_num * registerStage2Fragment.getRank());
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                }

                @Override
                public void onFailure(String errorMsg) {
                    registerStage1Fragment.setErrorToEditText(getResources().getString(R.string.ERROR_EMAIL_ALREADY_EXISTS));

                }
            });


        } else {
            registerStage1Fragment.setErrorToEditText(getResources().getString(R.string.ERROR_INVALID_EMAIL_FORMAT));
        }
    }


    /*public void validateRegisterStagePhone() {
        EditText editText = registerStagePhoneFragment.getEditText();
        if (isValidPhone(editText.getText().toString())) {
            registerStagePhoneFragment.setErrorToEditText(null);
            new FellowTravellerAPI(globalClass).checkFieldIfExist("phone", editText.getText().toString(), new StatusCallBack() {
                @Override
                public void onSuccess(String status) {
                    fra = registerStage1Fragment;
                    progressBar.setProgress(num_num * registerStage1Fragment.getRank());
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                }

                @Override
                public void onFailure(String errorMsg) {
                    registerStagePhoneFragment.setErrorToEditText(getResources().getString(R.string.ERROR_PHONE_ALREADY_EXISTS));

                }
            });

        } else {
            registerStagePhoneFragment.setErrorToEditText(getResources().getString(R.string.INVALID_PHONE_FORMAT));
        }
    }*/


}






















