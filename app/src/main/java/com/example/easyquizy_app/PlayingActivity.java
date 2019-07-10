package com.example.easyquizy_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.Question;
import com.example.easyquizy_app.Model.SoundPlayer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PlayingActivity";

    final static long INTERVAL = 1000; // 1 sec
    final static long TIMEOUT = 15000; // 15 sec
    int progressValue = 0, lifes = 3;

    CountDownTimer mCountDown;

    int index = 0, score = 0, thisQuestion = 0, totalQuestion, correctAnswer;


    private SoundPlayer sound;
    //Firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    //Views
    private RatingBar hearts_ratingBar;
    SeekBar seekBar;
    ProgressBar timerProgressBar;
    ImageView question_image;
    Button btnA, btnB, btnC, btnD;
    Button quit_btn;
    TextView txtScore, txtQuestionNum, question_txt,categoryName;
    int offline_flag;
    String category;
    Question[] questionsArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        Random rand = new Random();

        questionsArr = new Question[4];

        Intent intent = getIntent();
        offline_flag = intent.getIntExtra(TopicStartActivity.EXTRA_OFFLINE_FLAG , 0);

        if(offline_flag==1)
        {
            category = intent.getStringExtra(TopicStartActivity.EXTRA_CATEGORY_NAME);
            categoryName = findViewById(R.id.topic_name_txt);
            categoryName.setText(category);

            //Question[] questions = new Question[4];
            if(category.equals(getResources().getString(R.string.math)))
            {
                // Obtain a number between [0 - 49].
                //int n = rand.nextInt(50);

                int a, b, c, answer;
                for(int i=0; i<questionsArr.length;i++) {
                    a = rand.nextInt(20);
                    b = rand.nextInt(5) + 1;
                    c = rand.nextInt(10);
                    answer = a + b * c;
                    String q = " " + a + " + " + b + " * " + c + " = ? ";
                    //int answerA, int answerB, int answerC, int answerD, int correctAnswer
                    questionsArr[i] = new Question(q, b, answer, a+20,b*c ,answer);
                }
            }

            else
            {
                questionsArr[0] = new Question();
                questionsArr[1] = new Question();
                questionsArr[2] = new Question();
                questionsArr[3] = new Question();
            }
        }

        else {
            category = intent.getStringExtra("Topic");
            categoryName = findViewById(R.id.topic_name_txt);
            categoryName.setText(category);
            //Firebase
            database = FirebaseDatabase.getInstance();
            questions = database.getReference("Questions");
        }


        //Views
        txtScore = findViewById(R.id.txtScore);
        txtQuestionNum = findViewById(R.id.txtTotalQuestion);
        question_txt = findViewById(R.id.question_txt);
        categoryName = findViewById(R.id.topic_name_txt);

        timerProgressBar = findViewById(R.id.timerProgressBar);
        seekBar = findViewById(R.id.seekBar);

        question_image = findViewById(R.id.question_img);   //TODO based on questions type!!!
        btnA = findViewById(R.id.ans01_btn);
        btnB = findViewById(R.id.ans02_btn);
        btnC = findViewById(R.id.ans03_btn);
        btnD = findViewById(R.id.ans04_btn);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        //----------------Lifes rating-----------------------
        hearts_ratingBar = (RatingBar) findViewById(R.id.hearts_ratingBar);
        //hearts_ratingBar.setEnabled(false);
        hearts_ratingBar.setRating(3);
        //----------------------------------------

        sound = new SoundPlayer(this);

        //get quit game btn
        quit_btn = findViewById(R.id.quit_btn);
        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: add "Are you sure?" dialog
                ExitGameDialog alert = new ExitGameDialog();
                alert.showDialog(PlayingActivity.this);
            }
        });

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
        if(index < totalQuestion) //still have questions in list
        {
            Button clickedBtn = (Button)v;
            Log.d(TAG, "onClick: button " + clickedBtn.getText());
            Log.d(TAG, "onClick: Correct Answer " + Common.questionList.get(index).getCorrectAnswer());
            if(clickedBtn.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                //Choose correct answer
                score+=10;
                correctAnswer++;
                showQuestion(++index);  //next questions
            }
            else
            {
                if(--lifes <= 0) {
                    updateLifeUI();
                    //Choose wrong answer
                    Log.d(TAG, "onClick: wrong answer. you're out");
                    Intent intent = new Intent(this, DoneActivity.class);
                    Bundle dataSend = new Bundle();
                    dataSend.putInt("SCORE", score);
                    dataSend.putInt("TOTAL", totalQuestion);
                    dataSend.putInt("CORRECT", correctAnswer);
                    intent.putExtras(dataSend);
                    startActivity(intent);
                    finish();
                }
                else {
                    updateLifeUI();
                    showQuestion(++index);  //next questions
                }
            }

            txtScore.setText(String.format("%d", score));
        }
        /*else {
            Intent intent = new Intent(this, DoneActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }*/

    }

    private void showQuestion(int index) {
        if (index < totalQuestion) {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            timerProgressBar.setProgress(0);
            progressValue = 0;

            if (offline_flag==0)
            {
                if (Common.questionList.get(index).getIsImageQuestion().equals("true")) {
                    //If is image
                    Picasso.get()
                            .load(Common.questionList.get(index).getQuestion())
                            .placeholder(R.drawable.loading_gr_wbg)
                            .error(R.drawable.error_loading_pic)
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
            }

            else {
                question_txt.setText(questionsArr[index].getQuestion());
                question_image.setVisibility(View.GONE);
                question_txt.setVisibility(View.VISIBLE);

                btnA.setText("" + questionsArr[index].getAnswerAi());
                btnB.setText("" + questionsArr[index].getAnswerBi());
                btnC.setText("" + questionsArr[index].getAnswerCi());
                btnD.setText("" + questionsArr[index].getAnswerDi());
            }
            mCountDown.start();     //start timer
        }
        else {
            //If it is final questions
            Log.d(TAG, "showQuestion: final questions");
            Intent intent = new Intent(this, DoneActivity.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(offline_flag==0)
        {
        totalQuestion = Common.questionList.size();
        }
        else {

            totalQuestion = 4;
        }
        Log.d(TAG, "onResume: totalQuestion " + totalQuestion);
        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerProgressBar.setProgress(progressValue);
                progressValue++;

                //Log.v("Log_tag", "Tick of Progress"+ progressValue+ millisUntilFinished);
                int tmp = (progressValue*7); //15 goes 6.666 times in 100%
                timerProgressBar.setProgress(tmp);
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);

                timerProgressBar.setProgress(0);
                lifes--;
                updateLifeUI();
            }
        };
        showQuestion(index);

    }

    private void updateLifeUI() {
        //Toast.makeText(this, "wrong answer. you have " + lifes + " lifes left", Toast.LENGTH_SHORT).show();
        hearts_ratingBar.setRating(lifes);
        sound.playHeartBreakSound();
    }
}
