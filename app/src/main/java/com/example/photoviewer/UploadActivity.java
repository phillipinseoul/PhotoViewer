package com.example.photoviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mChooseBtn;

    String pathImage;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // VIEWS
        mImageView = findViewById(R.id.imageView);
        mChooseBtn = findViewById(R.id.UploadBtn);

        // Rotate on Image Click
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF,
                        .5f, RotateAnimation.RELATIVE_TO_SELF,
                        .5f);

                rotateAnimation.setDuration(1000);
                mImageView.startAnimation(rotateAnimation);
            }
        });

        // Handle Button Click
        mChooseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                // Check Runtime Permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        // Permission NOT granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // Show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        // Permission already granted
                        pickImageFromGallery();
                    }
                }
                else {
                    // System OS is less than Marshmallow
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery() {
        // Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    // Handle the result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted
                    pickImageFromGallery();
                }
                else {
                    // Permission was denied
                    Toast.makeText(this, "Permission was denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Handle the result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path = "";
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            // Retrieve image path
            Uri mImageURI = data.getData();
            pathImage = mImageURI.getPath();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageURI);

                // Set image to image view
                // mImageView.setImageBitmap(BitmapFactory.decodeFile(path));
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void BackToMain (View v) {
        Intent i = new Intent(new Intent(getApplicationContext(), MainActivity.class));
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }






}