package com.example.fellow_traveller.Settings;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fellow_traveller.ClientAPI.Callbacks.UserAuthCallback;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.UserAuthModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.ClientAPI.Models.UserUpdateModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.iceteck.silicompressorr.FileUtils;
import com.iceteck.silicompressorr.SiliCompressor;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.fellow_traveller.Util.InputValidation.isValidPhone;

public class PersonalSettingsActivity extends AppCompatActivity {
    private Button changeImageButton;
    private ImageButton imageButtonClose, imageButtonUpdate;
    private CircleImageView profilePicture;
    private Uri mImageUri;
    private EditText firstNameEditText, lastNameEditText, aboutMeEditText, phoneNumberEditText;
    private GlobalClass globalClass;
    private DatabaseReference updateUserInfo;
    private StorageReference mStorageRef;
    private ImageView editImageView;
    private UserAuthModel userAuth;
    private String newImage = "";
    private ProgressBar imageProgressBar;
    private File tempImagefile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);
        globalClass = (GlobalClass) getApplicationContext();

        changeImageButton = findViewById(R.id.change_image_button);
        profilePicture = findViewById(R.id.ActivityProfile_user_image);
        imageButtonClose = findViewById(R.id.PersonalSettingsActivity_button_close);
        imageButtonUpdate = findViewById(R.id.PersonalSettingsActivity_button_update);
        editImageView = findViewById(R.id.PersonalSettingsActivity_edit_imageview);
        imageProgressBar = findViewById(R.id.ActivityPersonalSettings_progress_bar);


        firstNameEditText = findViewById(R.id.PersonalSettingsActivity_editText_first_name);
        lastNameEditText = findViewById(R.id.PersonalSettingsActivity_editText_last_name);
        aboutMeEditText = findViewById(R.id.PersonalSettingsActivity_editText_about_me);
        phoneNumberEditText = findViewById(R.id.PersonalSettingsActivity_editText_phone);

        mStorageRef = FirebaseStorage.getInstance().getReference("userImages");


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
                    updateUser(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                            aboutMeEditText.getText().toString(), phoneNumberEditText.getText().toString());
                }
            }
        });
    }


    public void onChooseFile(View v) {
        CropImage.activity().start(PersonalSettingsActivity.this);
    }

    public boolean phoneValidation() {
        String phone = phoneNumberEditText.getText().toString();
        if (!isValidPhone(phone)) {
            Toast.makeText(this, getResources().getString(R.string.INVALID_PHONE_FORMAT), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public boolean namesValidation() {
        if (firstNameEditText.getText().length() < 3 || lastNameEditText.getText().length() < 3) {
            Toast.makeText(this, "Ελέγξετε τα πεδία.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO get profile pic from user device...
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                //profilePicture.setImageURI(mImageUri);
                imageProgressBar.setVisibility(View.VISIBLE);
                //uploadImage();
                compressUriImage();
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
                userAuth = user;
                firstNameEditText.setText(user.getName());
                lastNameEditText.setText(user.getSurname());
                aboutMeEditText.setText(user.getAboutMe());
                phoneNumberEditText.setText(user.getPhone());
                Toast.makeText(PersonalSettingsActivity.this, user.getPicture(), Toast.LENGTH_SHORT).show();
                //Picasso.load(user.getPicture()).into(profilePicture);
                //TODO we dont need to call the API to get the users, put extra from the account and have the text watchers on create
                textWatchers();
            }

            @Override
            public void onFailure(String errorMsg) {
                // TODO use resource strings to fill the fields when data from server is unavailable
            }
        });
    }


    public void updateUser(final String firstName, final String lastName, String aboutMe, String phoneNumber) {
        UserUpdateModel user = new UserUpdateModel(firstName, lastName, newImage, aboutMe, phoneNumber);
        new FellowTravellerAPI(globalClass).updateUserInfo(user, new UserAuthCallback() {
            @Override
            public void onSuccess(UserAuthModel user) {
                Objects.requireNonNull(user).setSessionId(globalClass.getCurrentUser().getSessionId());
                globalClass.setCurrentUser(Objects.requireNonNull(user));
                updateUserInfoOnFirebase(firstName, lastName);
                DeleteSharedPreferences();
                SaveClass(Objects.requireNonNull(user));
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(PersonalSettingsActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserInfoOnFirebase(String firstName, String lastName) {
        updateUserInfo= FirebaseDatabase.getInstance().getReference().child("Users").child(String.valueOf(globalClass.getCurrentUser().getId()));

        HashMap<String, String> userHashMap = new HashMap<>();

        userHashMap.put("image",  "default");
        userHashMap.put("name",  firstName);
        userHashMap.put("surname",  lastName);

        updateUserInfo.setValue(userHashMap);
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

    public void textWatchers(){
        firstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.equals(userAuth.getName())) {
                    editImageView.setVisibility(View.INVISIBLE);
                    imageButtonUpdate.setVisibility(View.VISIBLE);
                }else{
                    imageButtonUpdate.setVisibility(View.INVISIBLE);
                    editImageView.setVisibility(View.VISIBLE);
                }
            }
        });
        lastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.equals(userAuth.getSurname())) {
                    editImageView.setVisibility(View.INVISIBLE);
                    imageButtonUpdate.setVisibility(View.VISIBLE);
                }else{
                    imageButtonUpdate.setVisibility(View.INVISIBLE);
                    editImageView.setVisibility(View.VISIBLE);
                }
            }
        });
        aboutMeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.equals(userAuth.getAboutMe())) {
                    editImageView.setVisibility(View.INVISIBLE);
                    imageButtonUpdate.setVisibility(View.VISIBLE);
                }else {
                    imageButtonUpdate.setVisibility(View.INVISIBLE);
                    editImageView.setVisibility(View.VISIBLE);
                }
            }
        });
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.equals(userAuth.getPhone())) {
                    editImageView.setVisibility(View.INVISIBLE);
                    imageButtonUpdate.setVisibility(View.VISIBLE);
                }else{
                    imageButtonUpdate.setVisibility(View.INVISIBLE);
                    editImageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //Get the extension of the image
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadImage(Uri imageUri){

        //Storage the image in Firebase
        if(imageUri != null){
            //TODO possibility for same name if user changed his time or name of image
            //TODO dont upload large images
            final StorageReference fileReference = mStorageRef.child("user-" + globalClass.getCurrentUser().getId());
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newImage = uri.toString();

                            Log.i("ImageInside", uri.toString());
                            loadImageToImageView();
                            tempImagefile.delete();
                            Toast.makeText(PersonalSettingsActivity.this, "Η φωτογραφία ανέβηκε επιτυχώς", Toast.LENGTH_SHORT).show();
                        }
                    });

                 Log.i("Image", taskSnapshot.getUploadSessionUri().toString());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    tempImagefile.delete();
                    Toast.makeText(PersonalSettingsActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //We get progress from uploading the image file
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    //TODO add progress bar
                    imageProgressBar.setProgress((int) progress);
                }
            });

        }else{
            Toast.makeText(this, "Δεν επιλέξατε έγκυρη εικόνα", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadImageToImageView(){
        Picasso.get().load(newImage).into(profilePicture);
        imageProgressBar.setVisibility(View.GONE);
    }

    public void compressUriImage(){
        if(mImageUri != null){
            tempImagefile = new File(SiliCompressor.with(this).compress(FileUtils.getPath(this, mImageUri), new File(this.getCacheDir(), "temp")));
            Uri compressedUri = Uri.fromFile(tempImagefile);
            uploadImage(compressedUri);
        }
    }
}


