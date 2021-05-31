package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    Button signupButton;
    EditText emailEditText;
    EditText passwordEditText;
    EditText usernameEditText;
    String userID;
    FirebaseFirestore firebaseFirestore;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    public void signUp(View view){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging you in");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString()).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(!task.isSuccessful()){
                    Toast.makeText(getApplication(), "Signup Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    String userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    Map userInfo = new HashMap();
                    userInfo.put("id",userId);
                    userInfo.put("Username",usernameEditText.getText().toString());
                    userInfo.put("Email",emailEditText.getText().toString());
                    userInfo.put("ProfileUrl","default");
                    userInfo.put("Search_Username",usernameEditText.getText().toString().toLowerCase());

                    currentUserDb.updateChildren(userInfo);

                    progressDialog.dismiss();

                }

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(getApplication(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        signupButton = findViewById(R.id.button2);
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        passwordEditText = findViewById(R.id.editTextTextPassword2);
        usernameEditText = findViewById(R.id.editTextTextPersonName);

        mAuth = FirebaseAuth.getInstance();

        /*signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString()).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(!task.isSuccessful()){
                            Toast.makeText(getApplication(), "Signup Error", Toast.LENGTH_SHORT).show();

                        }else {
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                            Map userInfo = new HashMap();
                            userInfo.put("id",userId);
                            userInfo.put("Username",usernameEditText.getText().toString());
                            userInfo.put("Email",emailEditText.getText().toString());
                            userInfo.put("ProfileUrl","default");
                            userInfo.put("Search_Username",usernameEditText.getText().toString().toLowerCase());

                            currentUserDb.updateChildren(userInfo);


                        }

                    }
                });
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}