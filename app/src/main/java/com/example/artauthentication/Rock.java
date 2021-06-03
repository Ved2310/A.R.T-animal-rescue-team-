package com.example.artauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Rock extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button pleaseadopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock);

        textView = findViewById(R.id.textrock);
        imageView = findViewById(R.id.imagerock);
        pleaseadopt = findViewById(R.id.btnrock);

        pleaseadopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ourEmail ="art.rescue.7@gmail.com";
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setType("message/rfc822");
                send.setPackage("com.google.android.gm");
                startActivity(send);
            }
        });
    }
}
