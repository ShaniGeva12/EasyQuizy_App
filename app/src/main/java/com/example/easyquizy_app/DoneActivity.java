package com.example.easyquizy_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.QuestionScore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.CookieHandler;

public class DoneActivity extends AppCompatActivity {

    Button btnTryAgain, btnToTopic;
    TextView txtResultScore, getTxtResultQuestion;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        txtResultScore = findViewById(R.id.txtTotalScore);
        getTxtResultQuestion = findViewById(R.id.txtTotalQuestion);

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

        //get data from bundle
        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            int score = extra.getInt("SCORE");
            int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer= extra.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE : %d", score));
            getTxtResultQuestion.setText(String.format("PASSED : %d / %d", correctAnswer, totalQuestion));

//            question_score.child(String.format("%s_%s", Common.currentUser.getName(), Common.categoryId))
//                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getName(),
//                            Common.categoryId),
//                            Common.currentUser.getName(),
//                            String.valueOf(score)));

        }

    }
}
