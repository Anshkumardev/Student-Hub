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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AddNewPostActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    DatabaseReference postRef;
    FirebaseUser mUser;
    ImageView addPhotoImageView,close,imagePost;
    Button postButton;
    Uri imageUri;
    EditText postText;
    private static final int REQUEST_CODE = 101;
    ProgressDialog mloadingBar;
    StorageReference postImageRef,storageReference;
    String profileUrl,username,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        addPhotoImageView = findViewById(R.id.addPhotoImageView);
        close = findViewById(R.id.close);
        postButton = findViewById(R.id.post);
        imagePost = findViewById(R.id.postImage);
        postText = findViewById(R.id.postTextEditText);
        postRef = FirebaseDatabase.getInstance().getReference().child("posts");
        mloadingBar = new ProgressDialog(this);
        postImageRef = FirebaseStorage.getInstance().getReference().child("PostImages");


        //---------------------------------Getting current username---------------------------//

        String userID = mUser.getUid();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                username = (users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //---------------------------------Getting profile Image Uri--------------------------//

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profileUrl = uri.toString();
            }
        });

        //--------------------------------------Post Button-----------------------------------//

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPost();
            }
        });

        //-------------------------------------------Close button-------------------------------------//

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);*/
                onBackPressed();
            }
        });

        //----------------------------------Add image to view---------------------------------//

        addPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity().setAspectRatio(1,1).start(AddNewPostActivity.this);
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);*/
            }
        });
    }

    private void AddPost() {
        String postDesc = postText.getText().toString();
        postDesc = postDesc.replaceAll("\n", " ");
        if(postDesc.isEmpty())
        {
            postText.setError("Please write something about your post");
        }
        else if(imageUri==null)
        {
            Toast.makeText(this,"Please select an image",Toast.LENGTH_SHORT).show();
        }
        else
        {
            mloadingBar.setTitle("Uploading Post");
            mloadingBar.setCanceledOnTouchOutside(false);
            mloadingBar.show();

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            final String strDate = formatter.format(date);
            final String UID = mUser.getUid();


            final String finalPostDesc = postDesc;
            postImageRef.child(mUser.getUid()+strDate).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        postImageRef.child(mUser.getUid()+strDate).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {



                                HashMap hashMap = new HashMap();
                                hashMap.put("postDate",strDate);
                                hashMap.put("postImageURL",uri.toString());
                                hashMap.put("postDesc", finalPostDesc);
                                hashMap.put("userProfileImageURL",profileUrl);
                                hashMap.put("username",username);
                                hashMap.put("UID",UID);

                                postRef.child(strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {
                                            mloadingBar.dismiss();
                                            Toast.makeText(AddNewPostActivity.this, "Post uploaded", Toast.LENGTH_SHORT).show();
                                            imagePost.setImageDrawable(getDrawable(R.drawable.demoimage));
                                            postText.setText(null);
                                        }
                                        else
                                        {
                                            mloadingBar.dismiss();
                                            onBackPressed();
                                            Toast.makeText(AddNewPostActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                    }
                    else
                    {
                        mloadingBar.dismiss();
                        Toast.makeText(AddNewPostActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                imagePost.setImageURI(imageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

