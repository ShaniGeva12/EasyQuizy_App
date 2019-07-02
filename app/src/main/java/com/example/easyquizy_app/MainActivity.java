package com.example.easyquizy_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        sendOnChannel1("title", "message");
        sendOnChannel2("title2", "message2");

    }

    void sentNotif(String notif_txt) {
        String channelId = null;
        if(Build.VERSION.SDK_INT >= 26) {

            channelId =  "some_channel_id" ;
            CharSequence channelName =  "Some Channel" ;
            int  importance = NotificationManager.IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel =  new  NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights( true );
            notificationChannel.setLightColor(Color.RED );
            notificationChannel.enableVibration( true );
            notificationChannel.setVibrationPattern( new long []{ 100 ,  200 ,  300 ,  400 ,  500 ,  400 ,  300 ,  200 ,  400 });

            //manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,channelId);
        builder.setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Notification title")
                .setContentText("Notification extra text body...");

        builder.setPriority(Notification.PRIORITY_MAX);

        Intent intent = new Intent(MainActivity.this,TopicStartActivity.class);
        intent.putExtra("notif_txt",notif_txt);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //Adding action  - note must work with NotificatioComapt
        Intent actionIntent = new Intent(MainActivity.this,TopicStartActivity.class);
        actionIntent.putExtra("notif_txt",notif_txt);
        PendingIntent playPendingIntent = PendingIntent.getActivity(MainActivity.this,1,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,"Play",playPendingIntent));

        Notification notification = builder.build();

        notification.defaults = Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        //manager.notify(NOTIF_ID,notification);

    }


    public void sendOnChannel1(String i_title, String i_message) {
        String title = i_title;
        String message = i_message;

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(String i_title, String i_message) {
        String title = i_title;
        String message = i_message;

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(android.R.drawable.star_off)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }
}
