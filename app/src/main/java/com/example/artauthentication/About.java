package com.example.artauthentication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    TextView fullname,email,phone,verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    ImageView profileImage;
    Button changeProfileImage,resendCode;
    StorageReference storageReference;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.about);
        //perfform item seected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.dashbaord:
                        startActivity(new Intent(getApplicationContext()
                                , DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        return true;
                }
                return false;
            }
        });

        phone=findViewById(R.id.phone);
        fullname=findViewById(R.id.name);
        email=findViewById(R.id.email);
        profileImage=findViewById(R.id.imageView);
        changeProfileImage=findViewById(R.id.changeProfile);
        resendCode = findViewById(R.id.resendCode);
        verifyMsg=findViewById(R.id.verifyMsg);



        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        userId=fAuth.getCurrentUser().getUid();
        final FirebaseUser user= fAuth.getCurrentUser();

        if(!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {


                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(v.getContext(),"Verification Email Has been Sent!!",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d("tag","on failure: Email not sent" + e.getMessage());

                        }
                    });


                }
            });

        }else{
            verifyMsg.setVisibility(View.INVISIBLE);
            resendCode.setVisibility(View.INVISIBLE);

        }

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                phone.setText(documentSnapshot.getString("phone"));
                fullname.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("uname"));


            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });


        button = (Button) findViewById(R.id.button_setting);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openActivitySettings();
            }
        });


    }


    public void openActivitySettings(){
        Intent intent = new Intent (this,Settings.class);
        startActivity(intent);
    }



    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode,int resultCode,@androidx.annotation.Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {

        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       Picasso.get().load(uri).into(profileImage);
                   }
               });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(About.this,"Failed.",Toast.LENGTH_SHORT).show();
            }
        });
    }

}