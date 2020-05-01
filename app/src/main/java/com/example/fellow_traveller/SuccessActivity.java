package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

public class SuccessActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 500,DELAY = 1000;
    private TextView textViewHover, textViewTitle;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        title = getIntent().getStringExtra("title");
        textViewTitle = findViewById(R.id.SuccessActivity_textView_title);
        textViewHover = findViewById(R.id.SuccessActivity_textView_hover);
        textViewTitle.setText(title);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewHover.animate()
                        .translationX(textViewHover.getWidth())
                        .alpha(1.0f)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                finish();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });





            }
        }, SPLASH_TIME);

    }
}
