package com.example.easyquizy_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Toast;

public class Playing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        Intent intent = getIntent();
        String playMode = intent.getStringExtra("gameType");
        if(playMode.equals("spec")) {
            String player = intent.getStringExtra("player");
            Toast.makeText(this, "play mode: " + playMode + "\nyou play against - " + player, Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "play mode: " + playMode, Toast.LENGTH_SHORT).show();


        Toast.makeText(this, "play mode: " + playMode, Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(Playing.this, "Current value is " + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
