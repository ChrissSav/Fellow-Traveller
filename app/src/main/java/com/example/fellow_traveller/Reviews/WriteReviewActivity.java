package com.example.fellow_traveller.Reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CreateReviewModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

public class WriteReviewActivity extends AppCompatActivity {
    private ScaleRatingBar ratingBar;
    private float rate= 0;
    private Button submitButton;
    private EditText commentSection;
    private GlobalClass globalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        globalClass = (GlobalClass) getApplicationContext();

        ratingBar = findViewById(R.id.write_review_simpleRatingBar);
        submitButton = findViewById(R.id.write_review_submit_button);
        commentSection = findViewById(R.id.write_review_et);


        ratingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(WriteReviewActivity.this, "New rate is: " + rating, Toast.LENGTH_SHORT).show();
                rate = rating;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validate(commentSection, rate)){
                    Toast.makeText(WriteReviewActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    sendReview();
                    finish();
                }else{
                    Toast.makeText(WriteReviewActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void sendReview() {
        CreateReviewModel createReviewModel = new CreateReviewModel(commentSection.getText().toString().trim(), (int) rate, System.currentTimeMillis()/1000, 9);
        
        new FellowTravellerAPI(globalClass).addUserReview(createReviewModel, new StatusCallBack() {
            @Override
            public void onSuccess(String status) {
                Toast.makeText(WriteReviewActivity.this, "Η αξιολόγηση σας καταχωρήθηκε", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String errorMsg) {
                Toast.makeText(WriteReviewActivity.this, "Κάτι, πήγε στραβά με την καταχώρηση, οι ειδικευμένες ζέβρες μας θα το επιλύσουν αμέσως", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  boolean Validate(EditText et, float rate){

        String comment = et.getText().toString();
        if(comment.trim().isEmpty() || rate==0){
            return false;
        }else{
            return true;
        }
    }

}
