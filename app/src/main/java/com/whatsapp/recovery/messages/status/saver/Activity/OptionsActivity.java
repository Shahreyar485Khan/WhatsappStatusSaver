package com.whatsapp.recovery.messages.status.saver.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.whatsapp.recovery.messages.status.saver.R;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnImage,btnVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        btnImage = findViewById(R.id.opt_images);
        btnVideo = findViewById(R.id.opt_video);

        btnVideo.setOnClickListener(this);
        btnImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.opt_images:{

                Intent intent = new Intent(this, MyPictureActivity.class);
                intent.putExtra("key","Images");
                startActivity(intent);

                break;
            }
            case R.id.opt_video:{

                Intent intent = new Intent(this, MyPictureActivity.class);
                intent.putExtra("key","Video");
                startActivity(intent);

                break;
            }

        }

    }
}
