package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DanceClubActivity extends AppCompatActivity {

    ImageView image1,image2,image3,whatsappImage,instaImage,youtubeImage;

    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance_club);

        backImage = findViewById(R.id.backImageDanceClub);
        whatsappImage = findViewById(R.id.whatsappImage);
        youtubeImage = findViewById(R.id.youtubeImage);
        instaImage = findViewById(R.id.instagramImage);

        whatsappImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp(v);
            }
        });
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        youtubeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoutube(v);
            }
        });


        instaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInsta(v);
            }
        });

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClubsImageViewActivity.class);
                intent.putExtra("IMAGE",R.drawable.d1);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClubsImageViewActivity.class);
                intent.putExtra("IMAGE",R.drawable.d2);
                startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClubsImageViewActivity.class);
                intent.putExtra("IMAGE",R.drawable.d3);
                startActivity(intent);
            }
        });

    }

    public void openWhatsApp(View view){
        try {
            String text = "";// Replace with your message.

            String toNumber = "919758063694"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is ???xxxxxxxxxx???, then you need to send ???91xxxxxxxxxx???.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openYoutube(View view){
        Intent intent=null;
        String url = "https://www.youtube.com/watch?v=s7xN0iw7_mY";
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    public void openInsta(View view){
        Uri uri = Uri.parse("https://instagram.com/trx_dance_crew?igshid=15a5dea96bext");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/trx_dance_crew?igshid=15a5dea96bext")));
        }
    }

}