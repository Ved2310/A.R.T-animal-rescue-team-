package com.example.artauthentication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {

    TextView mtittle,mdesc;
    ImageView mimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);


        ActionBar actionBar= getSupportActionBar();

        mtittle = findViewById(R.id._tittleother);
        mdesc = findViewById(R.id.infoother);
        mimageView = findViewById(R.id._imageother);

        //now get our data from intent in which we put our data

        Intent intent = getIntent();

        String mTittle = intent.getStringExtra("iTitle");
        String mdescription = intent.getStringExtra("iDesc");

        byte[] mBytes = getIntent().getByteArrayExtra("iImage");

        Bitmap bitmap = BitmapFactory.decodeByteArray(mBytes,0,mBytes.length);
        actionBar.setTitle(mTittle); //which title we get from previous activity that will set in our actionbar

        mtittle.setText(mTittle);
        mdesc.setText(mdescription);
        mimageView.setImageBitmap(bitmap);

    }
}
