package com.example.artauthentication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imageView;
    TextView mtittle,minfo;
    ItemClickListener itemClickListener;

    MyHolder(@NonNull View itemView) {
        super(itemView);

        this.imageView=itemView.findViewById(R.id._image1);
        this.mtittle=itemView.findViewById(R.id._tittle);
        this.minfo=itemView.findViewById(R.id.info);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        this.itemClickListener.onItemClickListener(v,getLayoutPosition());

    }
    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener = ic;
    }
}
