package com.example.fellow_traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fellow_traveller.HomeFragments.HomeActivity;
import com.example.fellow_traveller.Register.RegisterContainerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    private EditText verifyEditText;
    private Button resendButton, verifyButton, cancelButton;
    private TextView numberTextView, countdownTextView;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private FirebaseAuth mAuth;
    private String phoneNumber;
    private long timeLeftInMillis = 60000; //One minute
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        verifyEditText = findViewById(R.id.ActivityVerification_editText_verification);
        resendButton = findViewById(R.id.ActivityVerification_resend_button);
        verifyButton = findViewById(R.id.ActivityVerification_verify_button);
        cancelButton = findViewById(R.id.ActivityVerification_cancel_button);
        numberTextView = findViewById(R.id.ActivityVerification_phone_number);
        countdownTextView = findViewById(R.id.ActivityVerification_countdown_textView);
        //firebase.auth().currentUser.unlink(firebase.auth.PhoneAuthProvider.PROVIDER_ID);
        //Toast.makeText(this, mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        //firebase.auth().currentUser.unlink(firebase.auth.PhoneAuthProvider.PROVIDER_ID)


        phoneNumber = getIntent().getStringExtra("phoneNumber");

        //Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();

        numberTextView.setText(phoneNumber.substring(0,3) + " " + phoneNumber.substring(3,6) + " " + phoneNumber.substring(6,9) + " " + phoneNumber.substring(9,13));

        //Validate code after 3 tries expires and need resent
        sendVerificationCode(phoneNumber);


        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyEditText.setText("");
                stopTimer();
                timeLeftInMillis = 60000;
                startTimer();
                resendVerificationCode(phoneNumber, resendToken);
            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verification = verifyEditText.getText().toString();
                if(verification.trim().isEmpty() || verification.length() < 6)
                    Toast.makeText(VerificationActivity.this, "Εισάγεται έγκυρο κωδικό επιβεβαίωσης", Toast.LENGTH_SHORT).show();
                else {
                    //TODO validate the input
                    verifyCode(verification);

                }
            }
        });



    }

    private void verifyCode(String code){
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        }catch (Exception e){
           Toast.makeText(this, "Ο κωδικός επιβεβαίωσης είναι λάθος", Toast.LENGTH_SHORT).show();
        }


    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent mainIntent = new Intent(VerificationActivity.this, RegisterContainerActivity.class);
                    mainIntent.putExtra("phoneNumber", phoneNumber);
                    //It may be do this to the final step of register
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    countDownTimer.cancel();
                    Toast.makeText(VerificationActivity.this, "Επιτυχής", Toast.LENGTH_SHORT).show();
                    startActivity(mainIntent);
                }else{
                    Toast.makeText(VerificationActivity.this, task.getException().getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallBack);
        startTimer();

    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //s is the verification id sent by the sms

            verificationId = s;
            resendToken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            //Auto-verification.... This doesn't work always
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyEditText.setText(code);
                //verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerificationActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }

            // Show a message and update the UI
            // ...
        }
    };

    public void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Toast.makeText(VerificationActivity.this, "Ο χρόνος του κωδικού επιβεβαίωσης έληξε", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
    public void stopTimer(){
        countDownTimer.cancel();
    }

    public void updateTimer(){
        int minutes = (int) timeLeftInMillis / 60000;
        int seconds = (int) timeLeftInMillis % 60000 / 1000;

        String timeleftText = "";

        if(minutes < 10) timeleftText = "0";
        timeleftText += "" + minutes;
        timeleftText += ":";
        if(seconds < 10) timeleftText += "0";
        timeleftText += "" + seconds;

        countdownTextView.setText(timeleftText);
    }
    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallBack, token);
//     a
    }

    @Override
    protected void onStart() {
        super.onStart();
        //if(FirebaseAuth.getInstance().getCurrentUser() != null)
    }
}
