package com.example.easyquizy_app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.QuestionScore;
import com.example.easyquizy_app.Model.SoundPlayer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.easyquizy_app.Model.App.CHANNEL_1_ID;
import static com.example.easyquizy_app.Model.App.CHANNEL_2_ID;

public class DoneActivity extends AppCompatActivity {
    public static final String EXTRA_OFFLINE_FLAG = "com.example.easyquizy_app.OFFLINE_FLAG";

    int offline_flag;
    Button btnTryAgain, btnToTopic;
    TextView txtResultScore, getTxtResultQuestion, title_txt;

    FirebaseDatabase database;
    DatabaseReference question_score;

    private String msg_rate;
    private RatingBar ratingBar;

    private SoundPlayer sound;

    String topicScore= "check passing data";
    String topicName;


    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        Intent intent = getIntent();
        offline_flag = intent.getIntExtra(PlayingActivity.EXTRA_OFFLINE_FLAG , 0);

        notificationManager = NotificationManagerCompat.from(getApplicationContext());
//        sendOnChannel1("title", "message");
        sendOnChannel2("Play online", getResources().getString(R.string.online_channel_desc));

        //Firebase
        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        //sound init
        sound = new SoundPlayer(this);

        //Views
        txtResultScore = findViewById(R.id.txtTotalScore);
        getTxtResultQuestion = findViewById(R.id.txtTotalQuestion);
        title_txt = findViewById(R.id.title_txt);

        btnTryAgain = findViewById(R.id.play_more_btn);
        btnToTopic = findViewById(R.id.back_to_topics_btn);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, PlayingActivity.class);
                intent.putExtra(EXTRA_OFFLINE_FLAG, offline_flag);
                startActivity(intent);
                finish();
            }
        });
        btnToTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, TopicsSelectImgsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(EXTRA_OFFLINE_FLAG,offline_flag);
                startActivity(intent);
                finish();
            }
        });

        //----------------Rating-----------------------
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                msg_rate = getResources().getString(R.string.rating_tnx) + " " + String.valueOf(rating) + " " + getResources().getString(R.string.starts);
                Toast.makeText(DoneActivity.this, msg_rate , Toast.LENGTH_SHORT).show();
            }
        });
        //----------------------------------------

        Bundle_extra();

    }

    private void Bundle_extra() {
        //get data from bundle
        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer= extra.getInt("CORRECT");

            String str = getResources().getString(R.string.score) + " : " + score;
            txtResultScore.setText(str);
            str = getResources().getString(R.string.pased) + " : " + correctAnswer + " / " +totalQuestion;
            getTxtResultQuestion.setText(str);

            if(correctAnswer < totalQuestion/2) {
                str = getResources().getString(R.string.next_time);
                sound.playFailSound();
            }
            else {
                str = getResources().getString(R.string.good_job);
                sound.playApplauseSound();
            }
            title_txt.setText(str);

//            question_score.child(String.format("%s_%s", Common.currentUser.getName(), Common.categoryId))
//                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getName(),
//                            Common.categoryId),
//                            Common.currentUser.getName(),
//                            String.valueOf(score)));

            if(Common.categoryId == "01")
                topicName = "Math";
            if(Common.categoryId == "02")
                topicName = "Harry Potter";
            if(Common.categoryId == "03")
                topicName = "Countries";
            if(Common.categoryId == "04")
                topicName = "Food";
            if(Common.categoryId == "05")
                topicName = "Sport";



            //Upload to DB
            //QuestionScore(String question_Score, String user, String score,String CategoryName)
            question_score.child(String.format("%s_%s", Common.currentUser.getName(),Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getName(),Common.categoryId)
                            ,Common.currentUser.getName(),String.valueOf(score),topicName));
        }
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
                .setSmallIcon(R.drawable.ic_noti_log_p)
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

        notificationManager.notify(2, notification);
    }
}
