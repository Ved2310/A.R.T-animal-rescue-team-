package com.example.artauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Settings extends AppCompatActivity {


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        listView = findViewById(R.id.list_view);

        String[] values = new String[]{
                "Help","Contact Us","Feedback","About Us"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){

                    Intent intent = new Intent(view.getContext(), Help.class);
                    startActivity(intent);
                }

                if(position ==1){

                    Intent intent = new Intent(view.getContext(), ContactUs.class);
                    startActivity(intent);
                }



                if(position ==2){

                    Intent intent = new Intent(view.getContext(), DeveloperInfo.class);
                    startActivity(intent);
                }

                if(position ==3){

                    Intent intent = new Intent(view.getContext(), boutus.class);
                    startActivity(intent);
                }

            }
        });
    }
}
