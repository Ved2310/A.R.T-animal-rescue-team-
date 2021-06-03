package com.example.artauthentication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText musername2,mpassword2;
    Button mlogButton2;
    TextView mcreateButton,mforgotPassword;
    ProgressBar progressBar2;
    FirebaseAuth fAuth;
    CheckBox showpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        musername2 = findViewById(R.id.username2);
        mpassword2 = findViewById(R.id.password2);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        mlogButton2 = findViewById(R.id.logButton2);
        mcreateButton = findViewById(R.id.createButton);
        mforgotPassword = findViewById(R.id.forgotPassword);

        mlogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username2 = musername2.getText().toString().trim();
                String password2 = mpassword2.getText().toString().trim();

                if(TextUtils.isEmpty(username2))
                {
                    musername2.setError("username is required");
                    return;
                }
                if(TextUtils.isEmpty(password2))
                {
                    mpassword2.setError("Password is required");
                    return;
                }
                if(mpassword2.length() < 8)
                {
                    mpassword2.setError("Password must be of atleast 8 letters");
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                //authenticate the user
                fAuth.signInWithEmailAndPassword(username2,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "logged in succesfully", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                        else
                        {
                            Toast.makeText(Login.this, "Oppss...error"+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
        mcreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.artauthentication.Register.class));
            }
        });
        mforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText reset = new EditText((v.getContext()));
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter your email to recieve reset link");
                passwordResetDialog.setView(reset);

                passwordResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and send reset link
                        String mail = reset.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error Reset Link Is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dailog
                    }
                });
                passwordResetDialog.create().show();
            }
        });

        showpassword = findViewById(R.id.showpass);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mpassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    mpassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }
}