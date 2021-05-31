package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class QuestionPaperActivity extends AppCompatActivity {

    ImageView backImage;
    CardView btechCard,bcaCard,mcaCard,biotechCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);

        btechCard = findViewById(R.id.btechCard);
        bcaCard = findViewById(R.id.bscCard);
        mcaCard = findViewById(R.id.mscCard);
        biotechCard = findViewById(R.id.biotechCard);
        backImage = findViewById(R.id.go_back6);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btechCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1bUTN2xsQtxUiu2y51xvRZkNWmzfAjZjA?usp=sharing");
                intent.putExtra("course","Btech");
                startActivity(intent);
            }
        });

        bcaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/15bdXbHBi9G-6_Ocx-4Qwl8DRNTRtPgIo?usp=sharing");
                intent.putExtra("course","BCA");
                startActivity(intent);
            }
        });

        biotechCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1DvLoPDMD_U73l5aWzEAStHxyhJqMhxIF?usp=sharing");
                intent.putExtra("course","Biotech");
                startActivity(intent);
            }
        });

        mcaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1GTcl5lTKoowL0eXdK03ADBNZdBp-OSlE?usp=sharing");
                intent.putExtra("course","MCA");
                startActivity(intent);
            }
        });
    }
}