package com.example.artauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mfullName,musername,mpassword,mphoneNo;
   Button mregButton;
   TextView mcreateButton2;
   FirebaseAuth fAuth;
   ProgressBar progressBar;
   FirebaseFirestore fstore;
   String userID;
   CheckBox showp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mfullName  = findViewById(R.id.fullName);
        musername  = findViewById(R.id.username);
        mpassword  = findViewById(R.id.password);
        mphoneNo = findViewById(R.id.phoneNo);
        mregButton = findViewById(R.id.regButton);
        mcreateButton2 = findViewById(R.id.createButton2);

        fAuth       = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        mregButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              final String username = musername.getText().toString().trim();
                                              final String password = mpassword.getText().toString().trim();
                                              final String phoneNo = mphoneNo.getText().toString().trim();
                                              final String fullN = mfullName.getText().toString();
                                              if (TextUtils.isEmpty(username)) {
                                                  musername.setError("username is required");
                                                  return;
                                              }
                                              if (TextUtils.isEmpty(password)) {
                                                  mpassword.setError("Password is required");
                                                  return;
                                              }
                                              if (password.length() < 8) {
                                                  mpassword.setError("Password must be of atleast 8 letters");
                                                  return;
                                              }
                                              progressBar.setVisibility(View.VISIBLE);

                                              // register the user in firebase

                                              fAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                  @Override
                                                  public void onComplete(@NonNull Task<AuthResult> task) {
                                                      if (task.isSuccessful()) {

                                                          FirebaseUser fuser = fAuth.getCurrentUser();
                                                          fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                              @Override
                                                              public void onSuccess(Void aVoid) {

                                                                  Toast.makeText(Register.this,"Verification Email Has been Sent!!",Toast.LENGTH_SHORT).show();

                                                              }
                                                          }).addOnFailureListener(new OnFailureListener() {
                                                              @Override
                                                              public void onFailure(@NonNull Exception e) {

                                                                  Log.d(TAG,"on failure: Email not sent" + e.getMessage());

                                                              }
                                                          });



                                                          Toast.makeText(Register.this, "user created", Toast.LENGTH_LONG).show();
                                                          FirebaseDatabase.getInstance().getReference().child("users").child("email").push().setValue(username);
                                                          userID = fAuth.getCurrentUser().getUid();
                                                          DocumentReference documentReference = fstore.collection("users").document(userID);
                                                          Map<String,Object> user = new HashMap<>();
                                                          user.put("fName",fullN);
                                                          user.put("uname",username);
                                                          user.put("phone",phoneNo);
                                                          documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                              @Override
                                                              public void onSuccess(Void aVoid) {
                                                                  Log.d(TAG,"onSucess: user profile is created for"+ userID);

                                                              }
                                                          });
                                                          startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                      } else {
                                                          Toast.makeText(Register.this, "Oppss...error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                          progressBar.setVisibility(View.GONE);
                                                      }
                                                  }

                                              });
                                          }
                                      });
                mcreateButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                });

                         showp = findViewById(R.id.show);
        showp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    mpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });






    }
        }

