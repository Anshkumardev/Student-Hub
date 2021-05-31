package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ViewUserProfilesActivity extends AppCompatActivity {


    TextView Username,Email,Phone,Course,Year,Interest,Experties,Student_Occupation;

    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser mUser;
    StorageReference storageReference;
    ImageView profileImageView;
    TextView profileUsernameTextView;
    String userID,profileImageUri,username;
    Button sendMessageButton;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profiles);



        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //-------------------------------------getting clicked user ID----------------------------------//


        final String UserID = getIntent().getStringExtra("UserKey");




        Username = findViewById(R.id.ViewprofileUsernameTextView);
        Email = findViewById(R.id.ViewprofileEmailTextView);
        Phone = findViewById(R.id.ViewprofilePhoneTextView);
        Course = findViewById(R.id.ViewprofileCourseTextView);
        Year = findViewById(R.id.ViewprofileYearTextView);
        Interest = findViewById(R.id.ViewprofileInterestTextView);
        Experties = findViewById(R.id.ViewprofileExperienceTextView);
        Student_Occupation = findViewById(R.id.ViewprofileStudentOccupationTextView);


        //-----------------------------Retrieving data from database----------------------------//

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                username = (users.getUsername());
                Username.setText(username);
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

        //String userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Username.setText(value.getString("Username"));
                username = (String) value.get("Username");
                Email.setText(value.getString("Email"));
                Phone.setText(value.getString("Phone"));
                Course.setText(value.getString("Course"));
                Year.setText(value.getString("Year"));
                Interest.setText(value.getString("Interest"));
                Experties.setText(value.getString("Experties"));
                Student_Occupation.setText(value.getString("Student_Occupation"));
            }
        });*/


        /*--------------------------------------Setting DP----------------------------------------*/

        profileImageView = findViewById(R.id.ViewprofileImageView);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+UserID+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                profileImageUri = uri.toString();

                Picasso.get().load(uri).into(profileImageView);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ViewUserProfilesActivity.this,ImageViewActivity.class);
                intent.putExtra("url",profileImageUri);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });


    }
}