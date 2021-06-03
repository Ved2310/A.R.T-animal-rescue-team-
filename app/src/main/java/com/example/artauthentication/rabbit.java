package com.example.artauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class rabbit extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button pleaseadopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rabbit);

        textView = findViewById(R.id.textrabbit);
        imageView = findViewById(R.id.imagerabbit);
        pleaseadopt = findViewById(R.id.btnrabbit);

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

