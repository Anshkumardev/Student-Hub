package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NavaidRizviSirDetails extends AppCompatActivity {

    ImageView backImage,whatsappImage,mailImage,linkedinImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navaid_rizvi_sir_details);

        backImage = findViewById(R.id.backImageMentorsDetails);
        linkedinImage = findViewById(R.id.linkedinImage);

        linkedinImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkedin(v);
            }
        });

        whatsappImage = findViewById(R.id.whatsappImage);
        mailImage = findViewById(R.id.mailImage);

        mailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail(v);
            }
        });

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
    }

    public void openWhatsApp(View view){
        try {
            String text = "";// Replace with your message.

            String toNumber = "919971475401"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void openMail(View view){
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "navaidrizvi@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            //TODO smth
        }
    }

    public void openLinkedin(View view){
        try{
            String mLinkedInurlString = "navaid-z-rizvi-ph-d-b46bb2b0/";
            String url = "https://www.linkedin.com/in/"+mLinkedInurlString;;
            Intent linkedInAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            linkedInAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            startActivity(linkedInAppIntent);
        }catch(ActivityNotFoundException e){
            //TODO smth
        }
    }
}