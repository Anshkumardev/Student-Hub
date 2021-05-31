package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class JoinSocietiesActivity extends AppCompatActivity {

    ImageView backImage;
    CardView danceClub,musicClub,dramaticsClub,photoCard,fashionClub,technoClub,yogaClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_societies);

        yogaClub = findViewById(R.id.YogaCard);
        photoCard = findViewById(R.id.photoCard);
        backImage = findViewById(R.id.backImage1);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        technoClub = findViewById(R.id.technoClub);
        danceClub = findViewById(R.id.danceClub);
        musicClub = findViewById(R.id.musicClub);
        dramaticsClub = findViewById(R.id.dramaticsClub);
        fashionClub = findViewById(R.id.fashionCard);

        yogaClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),YogaActivity.class);
                startActivity(intent);
            }
        });

        technoClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TechnoClubActivity.class);
                startActivity(intent);
            }
        });

        fashionClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FashionActivity.class);
                startActivity(intent);
            }
        });

        photoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PhotographyActivity.class);
                startActivity(intent);
            }
        });

        dramaticsClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DramaticsActivity.class);
                startActivity(intent);
            }
        });

        musicClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MusicClubActivity.class);
                startActivity(intent);
            }
        });

        danceClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChooseDanceClubActvity.class);
                startActivity(intent);
            }
        });

    }
}