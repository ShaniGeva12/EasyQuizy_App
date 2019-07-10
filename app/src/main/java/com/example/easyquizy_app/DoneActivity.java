package com.example.easyquizy_app;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.Question;
import com.example.easyquizy_app.Model.QuestionScore;
import com.example.easyquizy_app.Model.SoundPlayer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoneActivity extends AppCompatActivity {

    Button btnTryAgain, btnToTopic;
    TextView txtResultScore, getTxtResultQuestion, title_txt;

    FirebaseDatabase database;
    DatabaseReference question_score;

    private String msg_rate;
    private RatingBar ratingBar;

    private SoundPlayer sound;

    String topicScore= "check passing data";

    String topicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

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
                startActivity(intent);
                finish();
            }
        });
        btnToTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, TopicsSelectImgsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

            txtResultScore.setText(String.format("SCORE : %d", score));
            getTxtResultQuestion.setText(String.format("PASSED : %d / %d", correctAnswer, totalQuestion));

            String str;
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



            //Uplaod to DB
            //QuestionScore(String question_Score, String user, String score,String CategoryName)
            question_score.child(String.format("%s_%s", Common.currentUser.getEmail(),Common.categoryId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getName(),Common.categoryId)
                            ,Common.currentUser.getName(),String.valueOf(score),topicName));
        }
    }

    public String getMyData() {
        return topicScore;
    }
}
