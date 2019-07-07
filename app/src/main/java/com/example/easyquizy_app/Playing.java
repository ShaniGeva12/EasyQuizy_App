package com.example.easyquizy_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
