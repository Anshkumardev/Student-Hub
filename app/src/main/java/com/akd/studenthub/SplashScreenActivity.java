package com.akd.studenthub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    public static boolean started = false;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1500);

                    if (mAuth.getCurrentUser() != null){
                        Intent intent = new Intent(getApplication(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return;
                    }else{
                        Intent intent = new Intent(getApplication(),ChooseLoginRegistrationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });thread.start();



    }
}
