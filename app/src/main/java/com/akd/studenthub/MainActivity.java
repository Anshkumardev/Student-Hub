package com.akd.studenthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    TextView textMessage;
    DatabaseReference reference;
    String name;
    String greeting = null;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CircleImageView circleImageView;
    TextView greetingTextView;
    String userID;
    FragmentPagerAdapter adapterViewPager;
    BottomNavigationView bottomNav;

    boolean doubleBackToExitPressedOnce = false;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.tool_bar);

        View view = navigationView.inflateHeaderView(R.layout.header);
        greetingTextView =view.findViewById(R.id.greetingTextView);

        //-----------------------bottom nav listner------------------------------//
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
               new HomeFragment()).commit();


        reference = FirebaseDatabase.getInstance().getReference("Chats");

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        bottomNav.setSelectedItemId(R.id.bottom_nav_home);

        //---------------------------Setting Dp--------------------------//

        circleImageView = view.findViewById(R.id.dp);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(circleImageView);
            }
        });


        //-------------------------------Greeting by name-------------------------//
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                name = (users.getUsername());
                greetingTextView.setText("Hello, "+name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else { 

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nav_share:
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Student Hub");
                    String shareMessage="https://play.google.com/store/apps/details?id=com.akd.studenthub";
                    intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(intent,"Share by"));
                }catch (Exception e){
                    Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_rate_us:


                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.akd.studenthub");
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(i);
                }catch (Exception e){
                    Toast.makeText(this, "Unable to open", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.nav_profile:
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.all_users:
                Intent intent1 = new Intent(getApplicationContext(),AllUsersActivity.class);
                startActivity(intent1);
                finish();
                break;

            case R.id.nav_delete_account:

                new AlertDialog.Builder(this)
                        .setTitle("Delete this account?")
                        .setMessage("Your account will be permanently deleted and you won't be able to access this account again.")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProgressDialog dialogbox = ProgressDialog.show(MainActivity.this, "Deleting your account", "Please wait...", true);




                                mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {


                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        DatabaseReference currentUserDbUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
                                        DatabaseReference currentUserDbchatList = FirebaseDatabase.getInstance().getReference().child("Chatlist").child(userID);

                                        FirebaseAuth.getInstance().signOut();

                                        currentUserDbUsers.removeValue();
                                        currentUserDbchatList.removeValue();

                                        dialogbox.dismiss();
                                        if (task.isSuccessful()){


                                            Intent intent2 = new Intent(MainActivity.this,ChooseLoginRegistrationActivity.class);
                                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent2);

                                            Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();



                                        }else {
                                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //closes
                            }
                        })
                        .show();
                break;

            case R.id.nav_logout:

                new AlertDialog.Builder(this)
                        .setTitle("Logging Out")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                status("offline");
                                FirebaseAuth.getInstance().signOut();
                                Intent intent2 = new Intent(MainActivity.this, ChooseLoginRegistrationActivity.class);
                                startActivity(intent2);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //closes
                            }
                        })
                        .show();

                break;

            case R.id.about:
                Intent intent2 = new Intent(getApplicationContext(),AboutActivity.class);
                startActivity(intent2);
                finish();
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    //---------------Bottom Navigation Item Select-----------------------//

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {

                        case R.id.bottom_nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.bottom_nav_CommunityWall:
                            selectedFragment = new CommunityWall();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };



    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        if(mUser!=null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", status);

            reference.updateChildren(hashMap);
        }

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



