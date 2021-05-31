package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akd.studenthub.Utills.Comment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CommentViewActivity extends AppCompatActivity {

    DatabaseReference CommentRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebaseFirestore;
    DatabaseReference reference;
    FirebaseRecyclerOptions<Comment>CommentOption;
    FirebaseRecyclerAdapter<Comment,CommentViewHolder>CommentAdapter;
    String postKey,timeAgo,username,profileImageUrl,postDescription;
    String currentUsername,currentUserProfileUri,userID;
    RecyclerView recyclerView;
    ImageView profileDp;
    TextView usernameComment,descriptionComment,timeAgoComment;
    EditText commentEditText;
    ImageView postComment;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        profileDp = findViewById(R.id.profileDpComment);
        usernameComment = findViewById(R.id.usernameTextViewComment);
        descriptionComment = findViewById(R.id.descriptionTextView);
        timeAgoComment = findViewById(R.id.timeAgoComment);
        commentEditText = findViewById(R.id.commentEditTextInView);
        postComment = findViewById(R.id.postCommentInView);

        CommentRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        recyclerView = findViewById(R.id.recyclerViewCommentSeperate);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(layoutManager);

        Intent data = getIntent();
        postKey = data.getStringExtra("postKey");
        timeAgo = data.getStringExtra("timeAgo");
        username = data.getStringExtra("username");
        profileImageUrl = data.getStringExtra("profileImageUrl");
        postDescription = data.getStringExtra("postDescription");

        Picasso.get().load(profileImageUrl).into(profileDp);
        usernameComment.setText(username);
        descriptionComment.setText(postDescription);
        timeAgoComment.setText(timeAgo);

        //---------------------------------Getting profile Image Uri--------------------------//

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                currentUserProfileUri = uri.toString();
            }
        });


        //---------------------------------Getting current username---------------------------//

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                currentUsername = (users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        LoadComment(postKey);

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEditText.getText().toString();
                if(comment.isEmpty())
                {
                    Toast.makeText(CommentViewActivity.this, "Please write something", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Date currentTime = Calendar.getInstance().getTime();
                    String UIDtime = currentTime.toString();
                    AddComment(postKey,CommentRef,UIDtime,comment);


                }
            }
        });


    }



    //---------------------------------------Uploading Comment to firebase ------------------------------------//

    private void AddComment(String postKey, DatabaseReference commentRef, String uiDtime, String comment) {

        String commentUserUid = mUser.getUid();

        HashMap hashMap = new HashMap();
        hashMap.put("username",currentUsername);
        hashMap.put("profileImageUrl",currentUserProfileUri);
        hashMap.put("comment",comment);
        hashMap.put("commentUserUid",commentUserUid);

        commentRef.child(postKey).child(uiDtime).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(CommentViewActivity.this, "Comment Added", Toast.LENGTH_SHORT).show();
                    //adapter.notifyDataSetChanged();
                    commentEditText.setText(null);
                }
                else
                {
                    Toast.makeText(CommentViewActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //----------------------------------------Getting Comment from firebase-----------------------------------//

    private void LoadComment(String postKey) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentOption = new FirebaseRecyclerOptions.Builder<Comment>().setQuery(CommentRef.child(postKey),Comment.class).build();
        CommentAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(CommentOption) {
            @Override
            protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull final Comment model) {
                Picasso.get().load(model.getProfileImageUrl()).into(holder.profileImage);
                holder.username.setText(model.getUsername());
                holder.comment.setText(model.getComment());

                holder.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String commentUserUid = model.getCommentUserUid();

                        //Toast.makeText(CommentViewActivity.this, "Working Fine!!..."+commentUserUid, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CommentViewActivity.this,ViewUserProfilesActivity.class);
                        intent.putExtra("UserKey",commentUserUid);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_comment,parent,false);
                return new CommentViewHolder(view);
            }
        };
        CommentAdapter.startListening();
        recyclerView.setAdapter(CommentAdapter);
    }
}