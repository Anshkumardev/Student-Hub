package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ChooseDanceClubActvity extends AppCompatActivity {

    CardView club1,club2;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dance_club_actvity);

        club1 = findViewById(R.id.club1);
        club2 = findViewById(R.id.club2);
        back = findViewById(R.id.backChooseDanceCLub);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        club1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DanceClubActivity.class);
                startActivity(intent);
            }
        });

        club2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ThaskaActivity.class);
                startActivity(intent);
            }
        });

    }
}