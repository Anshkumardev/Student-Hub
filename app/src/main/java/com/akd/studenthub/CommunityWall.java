package com.akd.studenthub;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akd.studenthub.Utills.Comment;
import com.akd.studenthub.Utills.Posts;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class CommunityWall extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference,profileRef;
    ImageView dpImageView , addpostImageView;
    EditText editText;
    DatabaseReference postRef,LikeRef,CommentRef;
    Button postButton;
    private static final int REQUEST_CODE = 101;
    Uri imageUri;
    FirebaseRecyclerAdapter<Posts,MyViewHolder1>adapter;
    FirebaseRecyclerOptions<Posts>options;
    RecyclerView recyclerView;
    String username,userID,profileImgaeUrl;
    FirebaseRecyclerOptions<Comment>CommentOption;
    FirebaseRecyclerAdapter<Comment,CommentViewHolder>CommentAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_communitywall,container,false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        addpostImageView = view.findViewById(R.id.addPostImageVIew);
        postRef = FirebaseDatabase.getInstance().getReference().child("posts");
        recyclerView = view.findViewById(R.id.chatFragmentRecyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        LikeRef = FirebaseDatabase.getInstance().getReference().child("likes");
        CommentRef = FirebaseDatabase.getInstance().getReference().child("Comments");


        addpostImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddNewPostActivity.class);
                startActivity(intent);
            }
        });


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

       /* mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                username = (value.getString("Username"));
            }
        });*/


        //---------------------------------Setting DP----------------------------------//

        dpImageView = view.findViewById(R.id.dp1);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profileImgaeUrl = uri.toString();
                Picasso.get().load(uri).into(dpImageView);
            }
        });

        LoadPost();
        
        return view;
    }

    private void LoadPost() {

        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(postRef,Posts.class).build();
        adapter = new FirebaseRecyclerAdapter<Posts, MyViewHolder1>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyViewHolder1 holder, final int position, @NonNull final Posts model) {
                final String  postKey = getRef(position).getKey();

                final String postDescription = model.getPostDesc();
                holder.postDescription.setText(postDescription);

                final String timeAgo = calculateTimeAgo(model.getPostDate());
                holder.timeAgo.setText(timeAgo);

                final String username = model.getUsername();
                holder.profileUsernamePost.setText(username);

                Picasso.get().load(model.getPostImageURL()).into(holder.postImage);

                final String profileImageUrl = model.getUserProfileImageURL();
                Picasso.get().load(profileImageUrl).into(holder.profileImagePost);


                final String postUserUID = model.getUID();

                holder.countLike(postKey,mUser.getUid(),LikeRef);
                holder.countComments(postKey,mUser.getUid(),CommentRef);

                holder.likeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LikeRef.child(postKey).child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists())
                                {
                                    LikeRef.child(postKey).child(mUser.getUid()).removeValue();
                                    holder.likeImage.setColorFilter(Color.GRAY);
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    LikeRef.child(postKey).child(mUser.getUid()).setValue("like");
                                    holder.likeImage.setColorFilter(Color.BLUE);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                holder.postComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comment = holder.commentEditText.getText().toString();
                        if(comment.isEmpty())
                        {
                            Toast.makeText(getContext(), "Please write something", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Date currentTime = Calendar.getInstance().getTime();
                            String UIDtime = currentTime.toString();
                            AddComment(holder,postKey,CommentRef,UIDtime,comment);
                        }
                    }
                });

                holder.profileUsernamePost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),ViewUserProfilesActivity.class);
                        intent.putExtra("UserKey",postUserUID);
                        startActivity(intent);

                    }
                });

                holder.commentImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),CommentViewActivity.class);
                        intent.putExtra("postKey",postKey);
                        intent.putExtra("timeAgo",timeAgo);
                        intent.putExtra("username",username);
                        intent.putExtra("profileImageUrl",profileImageUrl);
                        intent.putExtra("postDescription",postDescription);
                        startActivity(intent);
                    }
                });

                holder.viewComments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),CommentViewActivity.class);
                        intent.putExtra("postKey",postKey);
                        intent.putExtra("timeAgo",timeAgo);
                        intent.putExtra("username",username);
                        intent.putExtra("profileImageUrl",profileImageUrl);
                        intent.putExtra("postDescription",postDescription);
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_post,parent,false);
                return new MyViewHolder1(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }

    private void AddComment(final MyViewHolder1 holder, String postKey, DatabaseReference commentRef, String uiDtime, String comment) {


        final String commentUserUid = mUser.getUid();

        HashMap hashMap = new HashMap();
        hashMap.put("username",username);
        hashMap.put("profileImageUrl",profileImgaeUrl);
        hashMap.put("comment",comment);
        hashMap.put("commentUserUid",commentUserUid);

        commentRef.child(postKey).child(uiDtime).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Comment Added", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    holder.commentEditText.setText(null);
                }
                else
                {
                    Toast.makeText(getContext(), ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //----------------------------------Calculate post time Ago-------------------------------//



    private String calculateTimeAgo(String postDate) {



        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-6:30"));
        try {
            long time = sdf.parse(postDate).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return (String) ago;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}
