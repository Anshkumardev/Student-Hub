package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NotesActivity extends AppCompatActivity {

    ImageView backImage;
    CardView btechCard,bcaCard,mcaCard,biotechCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        btechCard = findViewById(R.id.btechCard);
        bcaCard = findViewById(R.id.bscCard);
        mcaCard = findViewById(R.id.mscCard);
        biotechCard = findViewById(R.id.biotechCard);



        backImage = findViewById(R.id.go_back3);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btechCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NotesBtech.class);
                startActivity(intent);

            }
        });

        bcaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1Q-tV412MfxuB0eHS8h6U8rQ5TwzWt6tJ?usp=sharing");
                intent.putExtra("course","BCA");
                startActivity(intent);
            }
        });

        biotechCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1Wnc2P87nIAyGiZ_MN-ebu-lCYHAOhw0Z?usp=sharing");
                intent.putExtra("course","Biotech");
                startActivity(intent);
            }
        });

        mcaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1MNbmaFN0atvnOkA808JOrtwevK7N_kpd?usp=sharing");
                intent.putExtra("course","MCA");
                startActivity(intent);
            }
        });
    }
}