package com.example.artauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    Button mlogout;
    Button mrescue;
    Button madopt;
    Button mdonate;
    Timer timer ;
    TextView txt;
    ImageView img;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView7);
        TextView textView1 = (TextView) findViewById(R.id.textView8);
        mlogout = findViewById(R.id.logout);
        mrescue = findViewById(R.id.rescue);
        madopt = findViewById(R.id.adopt);
        mdonate = findViewById(R.id.donate);



       mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        mrescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),currentLocation.class));
                finish();
            }
        });

        madopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),new_adopt.class));
                finish();
            }
        });

        mdonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Donate.class));
                finish();
            }
        });




        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //perfform item seected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.dashbaord:
                        startActivity(new Intent(getApplicationContext()
                                ,DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext()
                                ,About.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        txt = findViewById(R.id.text1);
        img= findViewById(R.id.imageView6);
        refreshLayout = findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showRandom();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                } , 1*100);

            }
        });


    }
    Facts f01 = new Facts(R.drawable.images , "* Intentional cruelty to animals is strongly correlated with other crimes, including violence against people.\n " + "\n" +
            " * Hoarding behavior often victimizes animals. Sufferers of a hoarding disorder may impose severe neglect on animals by housing far more than they are able to"  +
            "               \n") ;
    Facts f02 = new Facts(R.drawable.state , "* Every state has laws against animal cruelty.\n"+" * However, officials must know about a problem before they can act on it.\n"+" * They often rely on reports from observant cat-lovers for help.\n" +"\n" +
            "            \n");
    Facts f03 = new Facts(R.drawable.chicken , "* Chickens are arguably the most abused animals on the planet.\n"+" * In the United States, approximately 9 billion chickens are killed for their flesh each year, and 305 million hens are used for their eggs.");
    Facts f04 = new Facts(R.drawable.cow , "* Given the chance, cows nurture their young and form lifelong friendships with one another.\n"+ " * They play games and have a wide range of emotions and personality traits.");
    Facts f05 = new Facts(R.drawable.horse , "* Horse abuse is the cause of suffering or harm upon a horse for any reason other than self-defense.\n"+" * There are federal and state laws that address animal abuse and cruelty.\n ");
    Facts f06 = new Facts(R.drawable.six,"* A 2017 study showed that 89% of women who had companion animals during an abusive relationship reported that their animals were threatened, harmed, or killed by their abusive partner.");
    Facts f07 = new Facts(R.drawable.seven,"* According to a survey, women in domestic violence shelters were 11 times more likely to report animal abuse by their partners than was a comparison group of women not experiencing violence.");
    Facts[] factarray = new Facts[]{f01,f02 , f03 , f04 , f05,f06,f07};
    public void showRandom(){
        shufflefacts();
        img.setImageResource(factarray[0].getmImage());
        txt.setText(factarray[0].getmFact());
    }
    public void shufflefacts(){
        Collections.shuffle(Arrays.asList(factarray));







    }
}