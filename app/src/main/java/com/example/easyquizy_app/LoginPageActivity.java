package com.example.easyquizy_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginPageActivity extends AppCompatActivity {
    private static final String TAG = "LoginPageActivity";
    EditText name_input, age_input;
    RadioGroup sexRG;
    Button enter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        name_input = findViewById(R.id.name_et);
        age_input  = findViewById(R.id.age_et);
        sexRG      = findViewById(R.id.sexRadioGroup);
        enter_btn  = findViewById(R.id.enter_btn);
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOnClick();
            }
        });
    }

    private void enterOnClick() {

        String inputStr = name_input.getText().toString();
        if (inputStr != null && inputStr.length() > 0 && inputStr != " ") {            //dumb user handler: empty name
            //String msg = getResources().getString(R.string.empty_recipe_title);
            Toast.makeText(LoginPageActivity.this, "please enter your name", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "enterOnClick: dumb user handler: empty name value");
            return;
        }

        inputStr = age_input.getText().toString();
        if (inputStr != null && inputStr.length() > 0 && inputStr != " ") {            //dumb user handler: empty age
            //String msg = getResources().getString(R.string.empty_recipe_title);
            Toast.makeText(LoginPageActivity.this, "please enter your age", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "enterOnClick: dumb user handler: empty age value");
            return;
        }

        finish();
    }
}
