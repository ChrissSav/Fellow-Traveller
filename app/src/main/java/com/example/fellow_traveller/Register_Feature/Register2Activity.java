package com.example.fellow_traveller.Register_Feature;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.fellow_traveller.R;

public class Register2Activity extends AppCompatActivity {

    private final int stages = 4;
    private Button button_next;
    private ImageButton btn_back;
    private FragmentManager fragmentManager;
    private Fragment fra;
    private RegisterStage1Fragment registerStage1Fragment;
    private RegisterStage2Fragment registerStage2Fragment;
    private RegisterStage3Fragment registerStage3Fragment;
    private RegisterStage4Fragment registerStage4Fragment;
    private ProgressBar progressBar;
    private int num_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        num_num = 100 / stages;

        registerStage1Fragment = new RegisterStage1Fragment();
        registerStage2Fragment = new RegisterStage2Fragment();
        registerStage3Fragment = new RegisterStage3Fragment();
        registerStage4Fragment = new RegisterStage4Fragment();

        progressBar = findViewById(R.id.RegisterActivity_progressBar);
        button_next = findViewById(R.id.RegisterActivity_button_next);
        btn_back = findViewById(R.id.RegisterActivity_imageButton);

        fragmentManager = getSupportFragmentManager();

        progressBar.setProgress(num_num * registerStage1Fragment.getRank());
        fra = registerStage1Fragment;
        fragmentManager.beginTransaction().replace(R.id.RegisterActivity_frame_container, fra).commit();
        button_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fra.toString().equals("RegisterStage1Fragment") && registerStage1Fragment.isOk()) {
                    fra = registerStage2Fragment;
                    progressBar.setProgress(num_num * registerStage2Fragment.getRank());
                    //button_next.setText("Αποστολή");
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                } else if (fra.toString().equals("RegisterStage2Fragment")) {
                    progressBar.setProgress(num_num * registerStage3Fragment.getRank());
                    fra = registerStage3Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();

                } else if (fra.toString().equals("RegisterStage3Fragment")) {
                    progressBar.setProgress(num_num * registerStage4Fragment.getRank());
                    fra = registerStage4Fragment;
                    fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.RegisterActivity_frame_container, fra).commit();
                    button_next.setText("Complete");
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (fra.toString().equals("RegisterStage4Fragment")) {
            button_next.setText("Next");
            progressBar.setProgress(num_num * registerStage3Fragment.getRank());
            fra = registerStage3Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("RegisterStage3Fragment")) {
            progressBar.setProgress(num_num * registerStage2Fragment.getRank());
            fra = registerStage2Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        } else if (fra.toString().equals("RegisterStage2Fragment")) {
            progressBar.setProgress(num_num * registerStage1Fragment.getRank());
            fra = registerStage1Fragment;
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.RegisterActivity_frame_container, fra).commit();

        }

    }
}
