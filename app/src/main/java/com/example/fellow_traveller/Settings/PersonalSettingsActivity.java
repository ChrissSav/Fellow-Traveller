package com.example.fellow_traveller.Settings;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fellow_traveller.R;
import com.theartofdev.edmodo.cropper.CropImage;

public class PersonalSettingsActivity extends AppCompatActivity {
    private Button changeImageButton;
    private CircleImageView profilePicture;
    private Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);

        changeImageButton =  findViewById(R.id.change_image_button);
        profilePicture = findViewById(R.id.profile_picture);

        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onChooseaFile(view);
            }
        });
    }



    public void onChooseaFile(View v){

        CropImage.activity().start(PersonalSettingsActivity.this);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){



            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode==RESULT_OK){
                mImageUri = result.getUri();
                profilePicture.setImageURI(mImageUri);
            }else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

}


