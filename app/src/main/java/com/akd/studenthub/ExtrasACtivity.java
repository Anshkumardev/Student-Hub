package com.akd.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExtrasACtivity extends AppCompatActivity {


    ImageView backImage,gmail;
    EditText suggestionsEditText;
    Button sendSuggestion;
    FirebaseUser mUser;
    TextView notes,Qpapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras_a_ctivity);

        backImage = findViewById(R.id.backImageExtras);
        notes = findViewById(R.id.notesHyperlink);
        Qpapers = findViewById(R.id.QpapersHyperlink);
        Qpapers.setMovementMethod(LinkMovementMethod.getInstance());
        notes.setMovementMethod(LinkMovementMethod.getInstance());
        gmail = findViewById(R.id.mailImageExtras);
        suggestionsEditText = findViewById(R.id.editTextSuggestions);

        sendSuggestion = findViewById(R.id.sendSuggestion);

        sendSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailbody = suggestionsEditText.getText().toString();

                Toast.makeText(ExtrasACtivity.this, "Choose Gmail", Toast.LENGTH_SHORT).show();

                /*Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "gbustudenthub@gmail.com"));

                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion");
                intent.putExtra(Intent.EXTRA_TEXT, emailbody);

                startActivity(intent);
                suggestionsEditText.setText("");*/
                String[] TO = {"gbustudenthub@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Suggestion");
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailbody);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ExtrasACtivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

                suggestionsEditText.setText("");

                //Toast.makeText(ExtrasACtivity.this, "Choose gmail", Toast.LENGTH_SHORT).show();

            }
        });



        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMail(v);
            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void openMail(View view){
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "gbustudenthub@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "your_subject");
            intent.putExtra(Intent.EXTRA_TEXT, "your_text");
            startActivity(intent);

        }catch(ActivityNotFoundException e){
            //TODO smth
        }
    }

}