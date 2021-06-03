package com.example.artauthentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

public class new_adopt extends AppCompatActivity {

    GridLayout mainGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_adopt);


        mainGridLayout = findViewById(R.id.gridlayout);
        setSingleEvent(mainGridLayout);
    }

    private void setSingleEvent(GridLayout mainGridLayout){
        for(int i=0;i<mainGridLayout.getChildCount();i++)
        {
            CardView cardView  = (CardView)mainGridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(finalI ==0)
                    {
                        Intent intent = new Intent(new_adopt.this,Max.class);
                        startActivity(intent);

                    }
                    else if(finalI ==1)
                    {
                        Intent intent = new Intent(new_adopt.this,Bailey.class);
                        startActivity(intent);

                    }
                    else if(finalI ==2)
                    {
                        Intent intent = new Intent(new_adopt.this,Chloe.class);
                        startActivity(intent);

                    }
                    else if(finalI ==3)
                    {
                        Intent intent = new Intent(new_adopt.this,Snowy.class);
                        startActivity(intent);

                    }
                    else if(finalI ==4)
                    {
                        Intent intent = new Intent(new_adopt.this,Rock.class);
                        startActivity(intent);

                    }
                    else if(finalI ==5)
                    {
                        Intent intent = new Intent(new_adopt.this,Alex.class);
                        startActivity(intent);

                    }
                    else if(finalI ==6)
                    {
                        Intent intent = new Intent(new_adopt.this,rabbit.class);
                        startActivity(intent);

                    }
                }
            });
        }
    }
}
