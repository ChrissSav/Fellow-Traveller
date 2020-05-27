package com.example.fellow_traveller.Reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.ClientAPI.Callbacks.StatusCallBack;
import com.example.fellow_traveller.ClientAPI.FellowTravellerAPI;
import com.example.fellow_traveller.ClientAPI.Models.CreateReviewModel;
import com.example.fellow_traveller.ClientAPI.Models.TripInvolvedModel;
import com.example.fellow_traveller.ClientAPI.Models.TripModel;
import com.example.fellow_traveller.ClientAPI.Models.UserBaseModel;
import com.example.fellow_traveller.Models.GlobalClass;
import com.example.fellow_traveller.R;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import static com.example.fellow_traveller.Util.SomeMethods.currentTimeStamp;

public class WriteReviewActivity extends AppCompatActivity {
    private ScaleRatingBar ratingBar;
    private float rate = 0;
    private Button submitButton;
    private EditText commentSection;
    private GlobalClass globalClass;
    private TripInvolvedModel trip;
    private UserBaseModel user;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        globalClass = (GlobalClass) getApplicationContext();

        user = getIntent().getParcelableExtra("user");
        trip = getIntent().getParcelableExtra("trip");

        ratingBar = findViewById(R.id.write_review_simpleRatingBar);
        submitButton = findViewById(R.id.write_review_submit_button);
        commentSection = findViewById(R.id.write_review_et);
        textViewTitle = findViewById(R.id.write_review_textView_title);

        String tempTitle = getResources().getString(R.string.write_review_title);
        tempTitle = tempTitle.replace("user", user.getFullName());
        tempTitle = tempTitle.replace("from", trip.getDestFrom().getTitle());
        tempTitle = tempTitle.replace("to", trip.getDestTo().getTitle());
        tempTitle = tempTitle.replace("date", trip.getDate());

        textViewTitle.setText(tempTitle);
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
                if (Validate(commentSection, rate)) {
                    //Toast.makeText(WriteReviewActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    sendReview();
                    //finish();
                } else {
                    Toast.makeText(WriteReviewActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void sendReview() {
        CreateReviewModel createReviewModel = new CreateReviewModel(commentSection.getText().toString().trim(), (int) rate, currentTimeStamp(), user.getId(), trip.getId());

        new FellowTravellerAPI(globalClass).addUserReview(createReviewModel, new StatusCallBack() {
            @Override
            public void onSuccess(String status) {
                Toast.makeText(WriteReviewActivity.this, "Η αξιολόγηση σας καταχωρήθηκε", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String errorMsg) {
                if (errorMsg.equals("test")) {
                    Toast.makeText(WriteReviewActivity.this, getResources().getString(R.string.ERROR_REVIEW_CANT_REGISTER_THE_REVIEW), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1500);
                } else {
                    Toast.makeText(WriteReviewActivity.this, "Κάτι, πήγε στραβά με την καταχώρηση, οι ειδικευμένες ζέβρες μας θα το επιλύσουν αμέσως", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean Validate(EditText et, float rate) {

        String comment = et.getText().toString();
        if (comment.trim().isEmpty() || rate == 0) {
            return false;
        } else {
            return true;
        }
    }

}
