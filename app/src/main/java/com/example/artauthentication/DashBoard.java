package com.example.artauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashBoard extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MyAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);





        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.dashbaord);
        //perfform item seected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.dashbaord:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
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



        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // it will create recycler view in linear layout

        myAdapter =new MyAdapter(this,getMyList());
        mRecyclerView.setAdapter(myAdapter);



    }

    private ArrayList<Model> getMyList(){

        ArrayList<Model> models = new ArrayList<>();

        Model m=new Model();
        m.setTittle("Founder : MANEKA GANDHI-1992");
        m.setDescription("People for Animals regularly works with various other animals welfare and rights organizations such as Humane Society International (HSI) and People for the Ethical Treatment of Animals (PETA).");
        m.setImg(R.drawable.explore1);
        models.add(m);


         m=new Model();
        m.setTittle("Founder : INGRID NEWKRIK AND ALEX PACHECO-1980");
        m.setDescription("Animals are not ours to experiment on, eat, wear, use for entertainment, or abuse in any other way.");
        m.setImg(R.drawable.peta);
        models.add(m);


         m=new Model();
        m.setTittle("Founder : V.SUNDARAM-1959");
        m.setDescription("'Animals—It's their world too'.\n The Blue Cross of India is an animal welfare charity based in Chennai, India. It was established in 1959 by Captain V. Sundaram, his wife Usha and their three children, in Chennai. The society was formally registered in 1964 under the Societies Registration Act.");
        m.setImg(R.drawable.bluecross);
        models.add(m);


         m=new Model();
        m.setTittle("Founder : Mrs.Ratty P.Javeri-1957");
        m.setDescription("Dogs for a Good Life, A Good Life for Dogs. \nThe Indian National Kennel Club is a registry of purebred dogs in India. Beyond maintaining its pedigree registry, this kennel club also promotes and sanctions events for purebred dogs, including dog shows and specialty shows. ");
        m.setImg(R.drawable.kenel);
        models.add(m);


         m=new Model();
        m.setTittle("Founded in: 1998, New Delhi, INDIA");
        m.setDescription("The Wildlife Trust of India (WTI) is an Indian nature conservation organisation to conserve wildlife and its habitat and to work for the welfare of individual wild animals. WTI has been credited for achieving conservation milestones such as Recovering population of critically endangered species,Reducing Human-Animal Conflict,Rescue and Rehabilitation of Animals.");
        m.setImg(R.drawable.wildlife);
        models.add(m);

        m=new Model();
        m.setTittle("Founder :Erika, Jim, Claire Abrams Myers-2002");
        m.setDescription("Throughout India, animals including dogs, cows, cats and donkeys live on the streets. Most cities don't have hospitals for owner-less animals, which means injured or ill animals often die from treatable conditions.");
        m.setImg(R.drawable.animald);
        models.add(m);

        m=new Model();
        m.setTittle("Founded in:1991, Alokparna Sengupta");
        m.setDescription("Humane Society International/India works to help animals on farms through our campaign against factory farming and street animals through our innovative Dog and Cat Welfare program, and responds to animals in need in the aftermath of disasters.HSI/India is proud to be a part of Humane Society International—one of the largest animal protection organizations in the world.");
        m.setImg(R.drawable.humane);
        models.add(m);

        return models;


    }



    }
