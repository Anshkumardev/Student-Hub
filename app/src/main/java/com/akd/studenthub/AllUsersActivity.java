package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    DocumentReference documentReference;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userID;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    Switch switchCompat;
    String searchBy = "Search_Username";
    EditText search_users;
    ImageView goBack;

    private List<Users> mUsers;



    FirebaseRecyclerOptions<Users> options;
    FirebaseRecyclerAdapter<Users,all_UsersViewHolder> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.allUsersRecyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();
        switchCompat = findViewById(R.id.searchSwitch);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsers = new ArrayList<>();


        goBack = findViewById(R.id.go_back);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //----------------------------Switching Between Search Methods-----------------------------//

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchCompat.isChecked()){
                    searchBy = "Search_Interest";
                    Toast.makeText(AllUsersActivity.this, "Search by Interests", Toast.LENGTH_SHORT).show();
                }
                else{
                    searchBy = "search_Username";
                    Toast.makeText(AllUsersActivity.this, "Search by Username", Toast.LENGTH_SHORT).show();
                }
            }
        });


        LoadUsers("");

        search_users = findViewById(R.id.searchUserEditText);

        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoadUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void LoadUsers(String s) {

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild(searchBy)
                .startAt(s)
                .endAt(s+"\uf8ff");


        options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(query,Users.class).build();
        adapter = new FirebaseRecyclerAdapter<Users, all_UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull all_UsersViewHolder holder, final int position, @NonNull Users model) {

                if (!mUser.getUid().equals(getRef(position).getKey())){

                    if (model.getProfileUrl().equals("default")){
                        holder.dpCirleImageView.setImageResource(R.drawable.profile);
                    }
                    else {
                        Picasso.get().load(model.getProfileUrl()).into(holder.dpCirleImageView);
                    }
                    holder.username.setText(model.getUsername());
                    holder.experties.setText(model.getInterest());


                }else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }

                boolean ischat = true;
                if(ischat){
                    if(model.getStatus().equals("online"))
                    {
                        holder.img_on.setVisibility(View.VISIBLE);
                        holder.img_off.setVisibility(View.GONE);
                    }
                    else
                    {
                        holder.img_on.setVisibility(View.GONE);
                        holder.img_off.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    holder.img_on.setVisibility(View.GONE);
                    holder.img_off.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AllUsersActivity.this,ViewUserProfilesActivity.class);
                        intent.putExtra("UserKey",getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public all_UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_all_users,parent,false);
                return new all_UsersViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

}