package com.example.easyquizy_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class NewLogin extends AppCompatActivity {
    private static final String TAG = "NewLogin";

    private RadioGroup radioSexGroup;
    private String ch_sex;

    EditText name_input, age_input, email_ed, pass_ed;
    RadioGroup sexRG;
    Button enter_btn, login_now_btn;

    //Firebase
    //FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    //font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //declare the font
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.activity_login_page);

        radioSexGroup = (RadioGroup) findViewById(R.id.sexRadioGroup);

        //auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        enter_btn = findViewById(R.id.enter_btn);
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        login_now_btn = findViewById(R.id.login_now_btn);
        login_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

    }

    private void showLoginDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.sign_in));
        dialog.setMessage(getResources().getString(R.string.sign_in_using_email));

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        final String user = login_layout.findViewById(R.id.edEmail).toString();
        final String pwd = login_layout.findViewById(R.id.edPassword).toString();

        dialog.setView(login_layout);

        //set Button
        dialog.setPositiveButton(getResources().getString(R.string.sign_in), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Login
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user).exists()) {
                            if (!user.isEmpty()) {
                                User login = dataSnapshot.child(user).getValue(User.class);
                                if (login.getPassword().equals(pwd)) {
                                    Toast.makeText(NewLogin.this, "Login Successfully!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(NewLogin.this, TopicsSelectImgsActivity.class);
                                    Common.currentUser = login;
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(NewLogin.this, "Wrong Password!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(NewLogin.this, "Please enter your username!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(NewLogin.this, "User does not exists!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void register() {

        email_ed = findViewById(R.id.mail_et);
        pass_ed = findViewById(R.id.pass_et);
        name_input = findViewById(R.id.name_et);
        age_input = findViewById(R.id.age_et);
        //sexRG = findViewById(R.id.sexRadioGroup);

        //Add gender by String (ch_sex) here:
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.male_checkbox:
                // do operations specific to this selection
                ch_sex = getResources().getString(R.string.male);
                break;
            case R.id.female_checkbox:
                ch_sex = getResources().getString(R.string.female);
                break;

            default:
                ch_sex = getResources().getString(R.string.unknown_sex);
                break;
        }

        if (TextUtils.isEmpty(ch_sex)) {
            Toast.makeText(this, getResources().getString(R.string.choose_sex), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email_ed.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.enter_email), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass_ed.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.enter_pass), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(name_input.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.enter_name), Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(age_input.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.enter_age), Toast.LENGTH_LONG).show();
            return;
        }

        //Register new user

        final User user = new User();
        user.setEmail(email_ed.getText().toString());
        user.setPassword(pass_ed.getText().toString());
        user.setName(name_input.getText().toString());
        user.setAge(age_input.getText().toString());
        user.setGender(ch_sex);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getName()).exists())
                        Toast.makeText(NewLogin.this, "User Already Exist!", Toast.LENGTH_LONG).show();
                else
                {
                    users.child(user.getName())
                            .setValue(user);
                    Toast.makeText(NewLogin.this, "User Registered successfully!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewLogin.this, TopicsSelectImgsActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}