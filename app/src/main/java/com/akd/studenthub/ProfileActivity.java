package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class  ProfileActivity extends AppCompatActivity {

    TextView Username,Email,Phone,Course,Year,Interest,Experties,Student_Occupation;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    ImageView profileImageView;
    TextView profileUsernameTextView;
    String userID;
    Button changePasswordButton;
    FirebaseUser user;
    ImageView backImage;




    public void onEditProfileButtonClicked(View view){
        Intent intent = new Intent(this,EditProfileActivity.class);
        intent.putExtra("Username",Username.getText().toString());
        intent.putExtra("Email",Email.getText().toString());
        intent.putExtra("Phone",Phone.getText().toString());
        intent.putExtra("Course",Course.getText().toString());
        intent.putExtra("Year", Year.getText().toString());
        intent.putExtra("Interests",Interest.getText().toString());
        intent.putExtra("Experties",Experties.getText().toString());
        intent.putExtra("Student_Occupation",Student_Occupation.getText().toString());

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backImage = findViewById(R.id.backImage);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Username = findViewById(R.id.profileUsernameTextView);
        Email = findViewById(R.id.profileEmailTextView);
        Phone = findViewById(R.id.profilePhoneTextView);
        Course = findViewById(R.id.profileCourseTextView);
        Year = findViewById(R.id.profileYearTextView);
        Interest = findViewById(R.id.profileInterestTextView);
        Experties = findViewById(R.id.profileExperienceTextView);
        Student_Occupation = findViewById(R.id.profileStudentOccupationTextView);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        //-----------------------------Retrieving data from database----------------------------//

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Username.setText(users.getUsername());
                Email.setText(users.getEmail());
                Phone.setText(users.getPhone());
                Course.setText(users.getCourse());
                Year.setText(users.getYear());
                Interest.setText(users.getInterest());
                Experties.setText(users.getExperties());
                Student_Occupation.setText(users.getStudent_Occupation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //----------------------------retrieving data from firebasefirestore---------------------//


        /*mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Username.setText(value.getString("Username"));
                Email.setText(value.getString("Email"));
                Phone.setText(value.getString("Phone"));
                Course.setText(value.getString("Course"));
                Year.setText(value.getString("Year"));
                Interest.setText(value.getString("Interest"));
                Experties.setText(value.getString("Experties"));
                Student_Occupation.setText(value.getString("Student_Occupation"));
            }
        });*/


        profileImageView = findViewById(R.id.profileImageView);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });


        //-------------------------------Reset Password----------------------------//

        user = mAuth.getCurrentUser();

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ProfileActivity.this, "Login Again before resetting password", Toast.LENGTH_SHORT).show();

                final EditText resetPassword = new EditText(v.getContext());

                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter New Password");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileActivity.this,"Password Reset Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this,"Password Reset Failed",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                });

                passwordResetDialog.create().show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}