package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DramaticsActivity extends AppCompatActivity {

    ImageView image1,image2,image3,whatsappImage,instaImage;

    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dramatics);

        backImage = findViewById(R.id.backImageDanceClub);
        whatsappImage = findViewById(R.id.whatsappImage);
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
                intent.putExtra("IMAGE",R.drawable.dr1);
                startActivity(intent);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClubsImageViewActivity.class);
                intent.putExtra("IMAGE",R.drawable.dr2);
                startActivity(intent);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ClubsImageViewActivity.class);
                intent.putExtra("IMAGE",R.drawable.dr3);
                startActivity(intent);
            }
        });

    }

    public void openWhatsApp(View view){
        try {
            String text = "";// Replace with your message.

            String toNumber = "918528585865"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openInsta(View view){
        Uri uri = Uri.parse("https://instagram.com/dishayanclubgbu?igshid=17p4h4l0btwpv");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/dishayanclubgbu?igshid=17p4h4l0btwpv")));
        }
    }
}