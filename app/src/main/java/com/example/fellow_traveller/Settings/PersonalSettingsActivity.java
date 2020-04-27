package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.LoginActivity;
import com.example.fellow_traveller.MainActivity;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.regex.Pattern;

public class PersonalSettingsActivity extends AppCompatActivity {
    private Button changeImageButton;
    private ImageButton imageButtonClose, imageButtonUpdate;
    private CircleImageView profilePicture;
    private Uri mImageUri;
    private EditText editTextFirstName, editTextLastName, editTextAboutMe, editTextNumber;
    private GlobalClass globalClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);
        globalClass = (GlobalClass) getApplicationContext();

        changeImageButton = findViewById(R.id.change_image_button);
        profilePicture = findViewById(R.id.profile_picture);
        imageButtonClose = findViewById(R.id.PersonalSettingsActivity_button_close);
        imageButtonUpdate = findViewById(R.id.PersonalSettingsActivity_button_update);


        editTextFirstName = findViewById(R.id.PersonalSettingsActivity_editText_first_name);
        editTextLastName = findViewById(R.id.PersonalSettingsActivity_editText_last_name);
        editTextAboutMe = findViewById(R.id.PersonalSettingsActivity_editText_about_me);
        editTextNumber = findViewById(R.id.PersonalSettingsActivity_editText_phone);


        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChooseFile(view);
            }
        });

        fillFields();
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namesValidation() && phoneValidation()) {
                    updateUser(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                            editTextAboutMe.getText().toString(), editTextNumber.getText().toString());
                }

            }
        });
    }


    public void onChooseFile(View v) {

        CropImage.activity().start(PersonalSettingsActivity.this);
    }

    // TODO remove to Utils
    public boolean phoneValidation() {
        String phone = editTextNumber.getText().toString();
        Pattern regexPattern = Pattern.compile("^[6][9][0-9]{8}$");
        if (!regexPattern.matcher(phone).matches()) {
            Toast.makeText(this, getResources().getString(R.string.INVALID_PHONE_FORMAT), Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }

    public boolean namesValidation() {
        if (editTextFirstName.getText().length() < 3 || editTextLastName.getText().length() < 3) {
            Toast.makeText(this, "Ελέγξετε τα πεδία.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {


            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                profilePicture.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception e = result.getError();
                Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void fillFields() {

        new FellowTravellerAPI(globalClass).getUserInfo(new UserAuthCallback() {
            @Override
            public void onSuccess(UserAuthModel user) {
                editTextFirstName.setText(user.getName());
                editTextLastName.setText(user.getSurname());
                editTextAboutMe.setText(user.getAboutMe());
                editTextNumber.setText(user.getPhone());
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    public void updateUser(String name, String lastName, String aboutMe, String phone) {

        new FellowTravellerAPI(globalClass).updateUserInfo(name, lastName, "", aboutMe+"", phone, new UserAuthCallback() {
            @Override
            public void onSuccess(UserAuthModel user) {
                user.setSessionId(globalClass.getCurrentUser().getSessionId());
                globalClass.setCurrentUser(user);

                DeleteSharedPreferences();
                SaveClass(user);
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(PersonalSettingsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void SaveClass(UserAuthModel userAuth) {
        SharedPreferences mPrefs = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAuth);
        editor.putString(getResources().getString(R.string.USER_INFO), json);
        editor.apply();

    }


    public void DeleteSharedPreferences() {
        SharedPreferences settings = getSharedPreferences("shared preferences", MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}


