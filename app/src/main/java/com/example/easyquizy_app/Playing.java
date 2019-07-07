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
        Toast.makeText(this, "play mode: " + playMode, Toast.LENGTH_SHORT).show();
    }
}
