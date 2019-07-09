package com.example.easyquizy_app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.example.easyquizy_app.Model.App.CHANNEL_1_ID;
import static com.example.easyquizy_app.Model.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NotificationManagerCompat notificationManager;

//    NotificationManager manager;
//    final int NOTIF_ID = 1;

    //Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference users;

    //font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.DarkyTheme_NoActionBar);     //return from splash
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //declare the font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.activity_main);

        Button play = findViewById(R.id.enter_btn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameTypeDialog alert = new GameTypeDialog();
                alert.showDialog(MainActivity.this);

                //going to playing page to fix problems
//                Intent myIntent = new Intent(getBaseContext(),   PlayingActivity.class);
//                startActivity(myIntent);
            }
        });


        //Notification HANDLER
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
//        sendOnChannel1("title", "message");
        sendOnChannel2("title2", "message2");

        //animation
        ImageView imageView = findViewById(R.id.animation_image_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();

        animationDrawable.start();

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //User cUser = mAuth.getCurrentUser();

        //firebase START
        if(currentUser != null) { //TODO make that shit work
            String name2 = currentUser.getDisplayName();
            String email2 = currentUser.getEmail();
            String uId = currentUser.getUid();

            //String uId = currentUser.getUid();

            mDatabase = FirebaseDatabase.getInstance();
            users = mDatabase.getReference("Users");

           users.child(uId).setValue(name2);


            //Common.currentUser = new User(email, name);
            Log.d(TAG, "---------------------------------------------------------" );
            Log.d(TAG, "onStart: email " + email2 + " name " + name2 );
            Log.d(TAG, "uID " + uId );
            Log.d(TAG, "---------------------------------------------------------" );
            //--------------------------------------------

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference user = database.getReference("user").child(currentUser.getUid());
            final String[] name = new String[1];
            user.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name[0] = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            String email = currentUser.getEmail();
            final String[] password = new String[1];
            user.child("password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    password[0] = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            final String[] age = new String[1];
            user.child("age").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    age[0] = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            final String[] gender = new String[1];
            user.child("gender").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gender[0] = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            for (UserInfo profile : currentUser.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name1 = profile.getDisplayName();
                String email1 = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }

            Log.d(TAG, "onStart: email " + email + " password " + password[0] + " name " + name[0] + " age " + age[0] + " gender " + gender[0]);


            Common.currentUser = new User(email, password[0], name[0], age[0], gender[0]);
            updateUI(currentUser);
        }
    }


    private void updateUI(FirebaseUser currentUser) {
        //TODO shani
    }

    public void sendOnChannel1(String i_title, String i_message) {
        String title = i_title;
        String message = i_message;

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent playIntent = new Intent(this, TopicStartActivity.class);
        PendingIntent contentPlayIntent = PendingIntent.getActivity(this,
                0, playIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Play", contentPlayIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(String i_title, String i_message) {
        String title = i_title;
        String message = i_message;

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_noti_log_p)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        //TODO Shani - change icon
        notificationManager.notify(2, notification);
    }
}
