package com.example.artauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeveloperInfo extends AppCompatActivity {

    private EditText ouremail , subject , message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_info);

        ouremail = findViewById(R.id.email);
        subject=findViewById(R.id.subject);
        message= findViewById(R.id.message);
        send= findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ourSubjectFeedback = subject.getText().toString();
                String ourMessageFeedback = message.getText().toString();
                String ourEmail = ouremail.getText().toString();
                String[] email_divide =ourEmail.split(",");
                Intent send = new Intent(Intent.ACTION_SEND);
                send.putExtra(Intent.EXTRA_EMAIL , email_divide);
                send.putExtra(Intent.EXTRA_SUBJECT , ourSubjectFeedback);
                send.putExtra(Intent.EXTRA_TEXT , ourMessageFeedback);
                send.setType("message/rfc822");
                send.setPackage("com.google.android.gm");
                startActivity(send);

            }
});
    }
    }