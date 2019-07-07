package com.example.easyquizy_app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.example.easyquizy_app.Model.App.CHANNEL_1_ID;
import static com.example.easyquizy_app.Model.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;

//    NotificationManager manager;
//    final int NOTIF_ID = 1;

    //font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.DarkyTheme_NoActionBar);     //return from splash
        super.onCreate(savedInstanceState);

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
            }
        });


        //Notification HANDLER
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
//        sendOnChannel1("title", "message");
        sendOnChannel2("title2", "message2");

        //animation
        ImageView imageView = findViewById(R.id.animation_image_view);
        AnimationDrawable animationDrawable = (AnimationDrawable)imageView.getDrawable();

        animationDrawable.start();

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

        //TODO Shani - change icon
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
