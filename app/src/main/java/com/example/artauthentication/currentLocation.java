package com.example.artauthentication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


public class currentLocation extends AppCompatActivity {

    //initialize variable
    public static final String NODE_USERS = "users";
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseAuth mAuth;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    Button camera;
    OutputStream outputStream;
    Button save;
    BitmapDrawable drawable;
    Bitmap bitmap;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    public static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Simplified Coding Notification";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

       // NotificationHelper.displayNotification(this,"title","body");

        mAuth = FirebaseAuth.getInstance();

        //assign variable
        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);


        //Initialize fusedLocationproviderclient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ActivityCompat.checkSelfPermission(currentLocation.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //When permission granted
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(currentLocation.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


        mButtonChooseImage = findViewById(R.id.button_choose_file);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                openFileChooser();

            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                uploadFile();

            }
        });
        mTextViewShowUploads.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openImagesActivity();

            }
        });


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }



        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){

                            String token = task.getResult().getToken();
                            saveToken(token);



                        }
                        else{


                        }
                    }
                });


        camera = findViewById(R.id.button_camera);
        save = findViewById(R.id.save);
        if(ContextCompat.checkSelfPermission(currentLocation.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(currentLocation.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*drawable = (BitmapDrawable)mImageView.getDrawable();
                bitmap = drawable.getBitmap();*/

                mImageView.buildDrawingCache();
                Bitmap bmp = mImageView.getDrawingCache();

                FileOutputStream outputStream = null;

                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + "/Adopt");
                directory.mkdir();
                String fileName = String.format("%d,jpg",System.currentTimeMillis());
                File outFile = new File(directory,fileName);

                Toast.makeText(currentLocation.this,"Image saved successfully in gallery.",Toast.LENGTH_SHORT).show();

                try {
                    outputStream = new FileOutputStream(outFile);
                    bmp.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    outputStream.flush();
                    outputStream.close();

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outFile));
                    sendBroadcast(intent);




                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }



    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize ocation
                Location location = task.getResult();
                FirebaseDatabase.getInstance().getReference().child("users").child("email").push().setValue(location);
                if (location != null) {
                    //Initialize geoCoder
                    Geocoder geocoder = new Geocoder(currentLocation.this,
                            Locale.getDefault());
                    //Initialize address list
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        //set latitude on TextView
                        textView1.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude :</b><br></font>"
                                        + addresses.get(0).getLatitude()
                        ));
                        //set Longitude on TextView
                        textView2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude :</b><br></font>"
                                        + addresses.get(0).getLongitude()
                        ));
                        //set country name
                        textView3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country Name :</b><br></font>"
                                        + addresses.get(0).getCountryName()
                        ));
                        //Set locality
                        textView4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Locality :</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));
                        //Set Address
                        textView5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Address :</b><br></font>"
                                        + addresses.get(0).getAddressLine(0)
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
            mImageView.setImageURI(mImageUri);
        }
        if(requestCode == 100){
            //Get Capture Image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //Set Capture Image to ImageView
            mImageView.setImageBitmap(captureImage);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mImageUri !=null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable(){
                                @Override
                                public void run(){
                                    mProgressBar.setProgress(0);
                                }
                            },500);
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString()); //use if testing...don't need this line.
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),downloadUrl.toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                            Toast.makeText(currentLocation.this,"Upload Successful" , Toast.LENGTH_LONG).show();
                           /* Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getStorage().getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);*/


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(currentLocation.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);

                        }
                    });

        }else{
            Toast.makeText(this,"No file selected", Toast.LENGTH_SHORT).show();
        }

    }
    private void openImagesActivity(){
        Intent intent = new Intent(this,ImagesActivity.class);
        startActivity(intent);
    }


    private void saveToken(String token){
            String email = mAuth.getCurrentUser().getEmail();

        User user = new User(email,token);
        DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference(NODE_USERS);

        dbUsers.child(mAuth.getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(currentLocation.this,"Token Saved", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    }
