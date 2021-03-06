package com.akd.studenthub;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MyViewHolder1 extends RecyclerView.ViewHolder {

    ImageView profileImagePost,postImage,likeImage,commentImage,postComment;
    TextView profileUsernamePost,timeAgo,postDescription,likeCounter,commentCounter,viewComments;
    EditText commentEditText;

    public MyViewHolder1(@NonNull View itemView) {
        super(itemView);

        postComment = itemView.findViewById(R.id.postComment);
        commentEditText = itemView.findViewById(R.id.commentEditText);
        profileImagePost = itemView.findViewById(R.id.profileImagePost);
        postImage = itemView.findViewById(R.id.postImageCW);
        profileUsernamePost = itemView.findViewById(R.id.profileUrenamePost);
        timeAgo = itemView.findViewById(R.id.timeAgo);
        postDescription = itemView.findViewById(R.id.postDescription);
        likeImage = itemView.findViewById(R.id.likeImage);
        commentImage = itemView.findViewById(R.id.commentImage);
        likeCounter = itemView.findViewById(R.id.likeCounter);
        commentCounter = itemView.findViewById(R.id.commentCounter);
        viewComments = itemView.findViewById(R.id.ViewComments);

        //viewComments.setText("View All "+ commentCounter + "Comments");

    }



    //--------------------------------Counting likes----------------------------------//

    public void countLike(String postKey, final String uid, DatabaseReference likeRef) {
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int totalLikes = (int) snapshot.getChildrenCount();
                    likeCounter.setText(totalLikes+"");
                }
                else
                {
                    likeCounter.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(uid).exists())
                {
                    likeImage.setColorFilter(Color.rgb(41,128,185));
                }
                else
                {
                    likeImage.setColorFilter(Color.GRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //-----------------------------------Counting comments-------------------------------//

    public void countComments(String postKey, String uid, DatabaseReference commentRef) {

        commentRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int totalComments = (int) snapshot.getChildrenCount();
                    commentCounter.setText(totalComments+"");
                    viewComments.setText("View all "+ totalComments + " comments");

                }
                else
                {
                    commentCounter.setText("0");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
