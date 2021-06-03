package com.example.artauthentication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {



    Context c;
    ArrayList<Model> models;
    //parameterised constrcutor

    public MyAdapter(Context c,ArrayList<Model>models){
        this.c=c;
        this.models=models;
    }




    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null); //this line inflates our row

        return new MyHolder(view);//this returns view to our holder class
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {

        holder.mtittle.setText(models.get(position).getTittle());
        holder.minfo.setText(models.get(position).getDescription());
        holder.imageView.setImageResource(models.get(position).getImg());


        //used when we want to use one activity
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                String gTittle = models.get(position).getTittle();
                String gDesc = models.get(position).getDescription();//these objects gets data from previous activity
                BitmapDrawable bitmapDrawable = (BitmapDrawable)holder.imageView.getDrawable();//this will get our image from drawable

                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream(); //image will get stream and bytes

                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream); //it will compress our image

                byte[] bytes =stream.toByteArray();
                //get our data with intent

                /*Intent intent = new Intent(c,AnotherActivity.class);
                intent.putExtra("iTitle",gTittle);
                intent.putExtra("iDesc",gDesc);
                intent.putExtra("iImage",bytes);
                c.startActivity(intent);*/
            }
        });
        //to use diff activity use this logic
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Intent i;

                if(models.get(position).getTittle().equals("Founder : MANEKA GANDHI-1992")){
                        //then you can move another activity from if body
                     i=new Intent(c,Peopleforanimal.class);
                     c.startActivity(i);
                    }
                if(models.get(position).getTittle().equals("Founder : INGRID NEWKRIK AND ALEX PACHECO-1980")){
                    //then you can move another activity from if body
                    i=new Intent(c,peta.class);
                    c.startActivity(i);
                }
                if(models.get(position).getTittle().equals("Founder : V.SUNDARAM-1959")){
                    //then you can move another activity from if body
                    i=new Intent(c,bluecross.class);
                    c.startActivity(i);
                }
                if(models.get(position).getTittle().equals("Founder : Mrs.Ratty P.Javeri-1957")){
                    //then you can move another activity from if body
                    i=new Intent(c,kenel.class);
                    c.startActivity(i);
                }
                if(models.get(position).getTittle().equals("Founded in: 1998, New Delhi, INDIA")){
                    //then you can move another activity from if body
                    i=new Intent(c,wildlife.class);
                    c.startActivity(i);
                }
                if(models.get(position).getTittle().equals("Founder :Erika, Jim, Claire Abrams Myers-2002")){
                    //then you can move another activity from if body
                    i=new Intent(c,animalaid.class);
                    c.startActivity(i);
                }
                if(models.get(position).getTittle().equals("Founded in:1991, Alokparna Sengupta")){
                    //then you can move another activity from if body
                    i=new Intent(c,humane.class);
                    c.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
