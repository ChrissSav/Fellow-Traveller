package com.example.fellow_traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChatConversationActivity extends AppCompatActivity {
    private EditText writeEdtText;
    private ConstraintLayout borderOfEdtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        writeEdtText = findViewById(R.id.write_et_chat);
        borderOfEdtText = findViewById(R.id.border_of_et);

        writeEdtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                borderOfEdtText.setBackgroundResource( R.drawable.write_message_et_black_chat);
            }
        });
    }
}
