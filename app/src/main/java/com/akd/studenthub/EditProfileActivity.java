package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;


public class EditProfileActivity extends AppCompatActivity {


    ImageView editProfileImageView;
    Button editPicButton;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    Button saveButton;
    EditText editUsernameEditText;
    EditText editEmailEditText;
    EditText editPhoneNoEditText;
    EditText editCourseEditText;
    EditText editYearEditText;
    EditText editInterestEditText;
    EditText editExperienceEditText;
    EditText editStudentOccupationEditText;
    String userID;
    String profileUrl;


    public void saveButtonOnClick(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving profile");
        progressDialog.setCancelable(false);
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profileUrl = uri.toString();
            }
        });

        //----------------------updating profile data----------------------//

        final String Username = editUsernameEditText.getText().toString();
        final String Phone = editPhoneNoEditText.getText().toString();
        final String Course = editCourseEditText.getText().toString();
        final String Year = editYearEditText.getText().toString();
        final String Interest = editInterestEditText.getText().toString();
        final String Experties = editExperienceEditText.getText().toString();
        final String Student_Occupation = editStudentOccupationEditText.getText().toString();
        final String Email = editEmailEditText.getText().toString();
        user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                //----------------------------updating data to firebase database--------------------//


                    mAuth = FirebaseAuth.getInstance();
                    userID = mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                    Map userInfo = new HashMap();
                    userInfo.put("id",userID);
                    userInfo.put("Username",Username);
                    userInfo.put("Email",Email);
                    userInfo.put("Phone",Phone);
                    userInfo.put("Course",Course);
                    userInfo.put("Year",Year);
                    userInfo.put("Interest",Interest);
                    userInfo.put("Experties",Experties);
                    userInfo.put("Student_Occupation",Student_Occupation);
                    userInfo.put("ProfileUrl",profileUrl);
                    userInfo.put("Search_Username",Username.toLowerCase());
                    userInfo.put("Search_Interest",Interest.toLowerCase());

                    currentUserDb.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    });




                /*userID = mAuth.getCurrentUser().getUid();

                DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();

                user.put("id",userID);
                user.put("Username",Username);
                user.put("Email",Email);
                user.put("Phone",Phone);
                user.put("Course",Course);
                user.put("Year",Year);
                user.put("Interest",Interest);
                user.put("Experties",Experties);
                user.put("Student_Occupation",Student_Occupation);
                user.put("ProfileUrl",profileUrl);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                });*/
                Toast.makeText(EditProfileActivity.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





        //Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        Intent data = getIntent();
        String Username = data.getStringExtra("Username");
        String Email = data.getStringExtra("Email");
        String Phone = data.getStringExtra("Phone");
        String Course = data.getStringExtra("Course");
        String Year = data.getStringExtra("Year");
        String Interests = data.getStringExtra("Interests");
        String Experties = data.getStringExtra("Experties");
        String Student_Occupation = data.getStringExtra("Student_Occupation");

        //Log.i("data", Username + " " + Email + " " + Phone);

        editProfileImageView = findViewById(R.id.editProfileImageView);
        editUsernameEditText = findViewById(R.id.editUsernameEditText);
        editEmailEditText = findViewById(R.id.editEmailEditText);
        editPhoneNoEditText = findViewById(R.id.editPhoneNoEditText);
        editCourseEditText = findViewById(R.id.editCourseEditText);
        editYearEditText = findViewById(R.id.editYearEditText);
        editInterestEditText = findViewById(R.id.editInterestsEditText);
        editExperienceEditText = findViewById(R.id.editExperienceEditText);
        editStudentOccupationEditText = findViewById(R.id.editStudentOccupationEditText);

        editProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setAspectRatio(1,1).start(EditProfileActivity.this);
                //Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(openGalleryIntent,1000);
            }
        });

        editUsernameEditText.setText(Username);
        editEmailEditText.setText(Email);
        editPhoneNoEditText.setText(Phone);
        editCourseEditText.setText(Course);
        editYearEditText.setText(Year);
        editInterestEditText.setText(Interests);
        editExperienceEditText.setText(Experties);
        editStudentOccupationEditText.setText(Student_Occupation);



        /*    ----------------------------updating profile image--------------------------*/

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(editProfileImageView);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uploadImageToFirebase(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //--------------------------------upload image to firebase storage---------------------------------//
        final StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        Picasso.get().load(uri).into(editProfileImageView);
                        profileUrl = uri.toString();

                        mAuth = FirebaseAuth.getInstance();
                        userID = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                        Map userInfo = new HashMap();
                        userInfo.put("ProfileUrl",profileUrl);

                        currentUserDb.updateChildren(userInfo);

                    }
                });

                progressDialog.dismiss();
                Toast.makeText(EditProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }


}