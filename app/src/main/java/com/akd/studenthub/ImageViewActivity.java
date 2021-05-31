package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {

    ImageView imageView,goBack;
    TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        usernameTextView = findViewById(R.id.usernameTextView);
        goBack = findViewById(R.id.goBackFullImage);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageView = findViewById(R.id.imageView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_FULLSCREEN );

        String url = getIntent().getStringExtra("url");
        String username = getIntent().getStringExtra("username");
        Picasso.get().load(url).into(imageView);

        usernameTextView.setText(username);





    }
}