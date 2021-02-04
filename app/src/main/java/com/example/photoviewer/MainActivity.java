package com.example.photoviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    Button b;
    int flag = 0;

    public void UploadActivity (View v) {
        Intent i = new Intent(this, UploadActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.imageView1);
        b = (Button) findViewById(R.id.button1);

        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (flag == 0){
                    img.setImageResource(R.drawable.photo_1);
                    flag = 1;
                } else if (flag == 1){
                    img.setImageResource(R.drawable.photo_2);
                    flag = 2;
                } else if (flag == 2){
                    img.setImageResource(R.drawable.photo_3);
                    flag = 0;
                }
            }
        });
    }

}