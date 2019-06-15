package com.example.easyquizy_app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

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
    }
}
