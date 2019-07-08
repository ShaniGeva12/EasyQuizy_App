package com.example.easyquizy_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; // 1 sec
    final static long TIMEOUT = 7000; // 7 sec
    int progressValue = 0, lifes = 3;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, currectAnswer;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    //Views
    SeekBar seekBar;
    ProgressBar timerProgressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, question_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //
        Intent intent = getIntent();
        String playMode = intent.getStringExtra("gameType");
        if (playMode.equals("spec")) {
            String player = intent.getStringExtra("player");
            Toast.makeText(this, "play mode: " + playMode + "\nyou play against - " + player, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "play mode: " + playMode, Toast.LENGTH_SHORT).show();
        //

        //Firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        //Views
        txtScore = findViewById(R.id.txtScore);
        //txtQuestionNum = findViewById(R.id.txtTotalQuestion);
        question_txt = findViewById(R.id.question_txt);
        timerProgressBar = findViewById(R.id.timerProgressBar);
        seekBar = findViewById(R.id.seekBar);
        question_image = findViewById(R.id.animation_image_view);   //TODO based on question type!!!
        btnA = findViewById(R.id.ans01_btn);
        btnB = findViewById(R.id.ans02_btn);
        btnC = findViewById(R.id.ans03_btn);
        btnD = findViewById(R.id.ans04_btn);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        //seek bar - progress bar in the quiz
        SeekBar seekBar = findViewById(R.id.seekBar);
        if (seekBar != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Write code to perform some action when progress is changed.
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Write code to perform some action when touch is started.
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Write code to perform some action when touch is stopped.
                    Toast.makeText(PlayingActivity.this, "Current value is " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //seek bar handler END
    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();
        if(index < totalQuestion) //still have question in list
        {
            Button clickedBtn = (Button)v;
            if(clickedBtn.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                //Choose correct answer
                score+=10;
                currectAnswer++;
                showQuestion(++index);  //next question
            }
            else
            {
                if(--lifes == 0) {
                    //Choose wrong answer
                    Intent intent = new Intent(this, DoneActivity.class);
                    Bundle dataSend = new Bundle();
                    dataSend.putInt("SCORE", score);
                    dataSend.putInt("TOTAL", totalQuestion);
                    dataSend.putInt("CORRECT", currectAnswer);
                    intent.putExtras(dataSend);
                    startActivity(intent);
                    finish();
                }
                else {
                    updateLifeUI();
                }
            }

            txtScore.setText(String.format("%d", score));
        }
        else {
            Intent intent = new Intent(this, DoneActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", currectAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }

    }

    private void showQuestion(int i) {
        if (index < totalQuestion) {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            timerProgressBar.setProgress(0);
            progressValue = 0;

            if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                //If is image
                Picasso.get()
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                //question_txt.setVisibility(View.GONE);
            } else {
                question_txt.setText(Common.questionList.get(index).getQuestion());

                question_image.setVisibility(View.GONE);
                question_txt.setVisibility(View.VISIBLE);
            }

            btnA.setText(Common.questionList.get(index).getAnswerA());
            btnB.setText(Common.questionList.get(index).getAnswerB());
            btnC.setText(Common.questionList.get(index).getAnswerC());
            btnD.setText(Common.questionList.get(index).getAnswerD());

            mCountDown.start();     //start timer
        }
        else {
            //If it is final question
            Intent intent = new Intent(this, DoneActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", currectAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();
        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerProgressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                showQuestion(++index);
            }
        };
        showQuestion(++index);
    }

    private void updateLifeUI() {
        //TODO shani
    }
}