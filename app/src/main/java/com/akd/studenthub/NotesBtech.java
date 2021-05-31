package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NotesBtech extends AppCompatActivity {

    ImageView backImage;

    CardView ai,cs,it,ec,me,ee,ce,barch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_btech);


        backImage = findViewById(R.id.go_back4);

        ai = findViewById(R.id.aiCard);
        cs = findViewById(R.id.csCard);
        it = findViewById(R.id.itCard);
        ec = findViewById(R.id.ecCard);
        me = findViewById(R.id.meCard);
        ee = findViewById(R.id.eeCard);
        ce = findViewById(R.id.ceCard);
        barch = findViewById(R.id.barchCard);

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1HamscP4Kp4kSAN2QtrbniWrm7_1TpHT1?usp=sharing");
                intent.putExtra("course","ME");
                startActivity(intent);
            }
        });

        ee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1QjovwgbCMN_YdYUsmXb-7NttSvqSjtUx?usp=sharing");
                intent.putExtra("course","EE");
                startActivity(intent);
            }
        });

        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1kVuBkbTX177GMHnn1diKR0v5pFTNHe0G?usp=sharing");
                intent.putExtra("course","CE");
                startActivity(intent);
            }
        });

        barch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1eDoQq9s8Ip5rkTCTrLkBuhz0y-r5Y9QW?usp=sharing ");
                intent.putExtra("course","B Arch");
                startActivity(intent);
            }
        });

        ai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1_1y-rjofGZZtkWEB_N9RVSUPnq5OJiCA?usp=sharing");
                intent.putExtra("course","AI");
                startActivity(intent);
            }
        });

        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1ol6awCed50R3UrWQyWJye_rj-NtSR05R?usp=sharing");
                intent.putExtra("course","CS");
                startActivity(intent);
            }
        });

        it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1no6h1PEOTuO2Q2hN44x4mZ31srkRiCiC?usp=sharing");
                intent.putExtra("course","IT");
                startActivity(intent);
            }
        });

        ec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WebViewNotesActivity.class);
                intent.putExtra("url","https://drive.google.com/drive/folders/1tUb_I19MN-VX3E4S9mJj64RbZzTCy_lK?usp=sharing");
                intent.putExtra("course","EC");
                startActivity(intent);
            }
        });


        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}