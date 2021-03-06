package com.example.easyquizy_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Model.Question;
import com.example.easyquizy_app.ViewHolder.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Collections;
import java.util.Locale;

public class TopicStartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    public static final String EXTRA_FRAGMENT_FLAG = "com.example.easyquizy_app.FRAGMENT_FLAG";
    public static final String EXTRA_OFFLINE_FLAG = "com.example.easyquizy_app.OFFLINE_FLAG";
    public static final String EXTRA_CATEGORY_NAME = "com.example.easyquizy_app.CATEGORY_NAME";
    private static final String TAG = "TopicStartActivity";
    int frag_flag;
    int offline_flag;

    Button singleBtn, randBtn;
    ImageView categoryImage;

    //firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    String IMAGE_URL = null;

    private String msg_rate;
    private RatingBar ratingBar;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_start);

        //----------------Rating-----------------------
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                msg_rate =
                        getResources().getString(R.string.rating_tnx) + " " +
                                String.valueOf(rating) + " " + getResources().getString(R.string.starts);
                Toast.makeText(TopicStartActivity.this, msg_rate , Toast.LENGTH_SHORT).show();
            }
        });
        //----------------------------------------

        //get intents
        Intent intent = getIntent();
        offline_flag = intent.getIntExtra(MyAdapter.EXTRA_OFFLINE_FLAG , 0);

        Log.d(TAG, "---------------------TopicsStartActivity------------------------------------" );
        Log.d(TAG, " offline_flag = [" + offline_flag + "]");
        Log.d(TAG, "---------------------------------------------------------" );

        singleBtn = findViewById(R.id.single_player_btn);

        if(offline_flag==0)
        {
            //firebase START
            database = FirebaseDatabase.getInstance();
            questions = database.getReference("Questions");
            loadQuestion(Common.categoryId);
            //firebase END

            //categoryImage = findViewById(R.id.topic_img);

            //singleBtn = findViewById(R.id.single_player_btn);
            randBtn = findViewById(R.id.single_player_btn);
        /*singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlePlayerOnClick();
            }
        });*/

            //title & description setter START
            final String category = intent.getStringExtra("categoryName");
            String desc = intent.getStringExtra("desc");

            randBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startGame = new Intent(TopicStartActivity.this, PlayingActivity.class);
                    //startGame.putExtra("gameType", "random");
                    startGame.putExtra("Topic", category);
                    startActivity(startGame);
                    finish();
                }
            });


            //category image displaying
            //New Way
            Picasso.get().load(getIntent().getExtras().getString("categoryImage"))
                    .placeholder(R.drawable.loading_gr_wbg)
                    .error(R.drawable.error_loading_pic)
                    .into((ImageView) findViewById(R.id.topic_img));

            final TextView title = findViewById(R.id.topic_name_txt);
            TextView description = findViewById(R.id.topic_description_txt);
            title.setText(category);
            description.setText(desc);
            //title & description setter END

        /*spinner handler START
        Spinner spinner = (Spinner) findViewById(R.id.players_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_players_arr, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spinner handler END*/

            //Navigation Drawer
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);
            //END Navigation Drawer
        }

        else
        {
            //title & description setter START
            //final String category = intent.getStringExtra("categoryName");
            //String desc = intentfinal String category.getStringExtra("desc");
            category = intent.getStringExtra(MyAdapter.EXTRA_CATEGORY_NAME);

            //loadQuestion(Common.categoryId);

            //category image displaying
            if(category.equals(getResources().getString(R.string.math))) {
                Picasso.get().load(R.drawable.math_img)
                        .placeholder(R.drawable.loading_gr_wbg)
                        .error(R.drawable.error_loading_pic)
                        .into((ImageView) findViewById(R.id.topic_img));
            }

            else
            {
                Picasso.get().load(R.drawable.countries_img)
                        .placeholder(R.drawable.loading_gr_wbg)
                        .error(R.drawable.error_loading_pic)
                        .into((ImageView) findViewById(R.id.topic_img));
            }

            final TextView title = findViewById(R.id.topic_name_txt);
            TextView description = findViewById(R.id.topic_description_txt);
            title.setText(category);
            //description.setText(desc);

            singleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent startGame = new Intent(TopicStartActivity.this, PlayingActivity.class);
                    //startGame.putExtra("Topic", category);
                    startGame.putExtra(EXTRA_OFFLINE_FLAG, offline_flag);
                    startGame.putExtra(EXTRA_CATEGORY_NAME, category);
                    startActivity(startGame);
                    finish();
                }
            });
        }
    }

    private void loadQuestion(String categoryId) {

        if(Common.questionList.size() > 0)
            Common.questionList.clear();

        String device_language = Locale.getDefault().getDisplayLanguage().toString();
        if(device_language.contentEquals("עברית") ||
                device_language.contentEquals("Hebrew") ||
                device_language.contentEquals("hebrew") ||
                device_language.contentEquals("iw_IL") ||
                device_language.contentEquals("iw") )
        {
            //change something in the ui
            questions.child("he").orderByChild("CategoryId").equalTo(categoryId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                            {
                                Question ques = postSnapshot.getValue(Question.class);
                                Common.questionList.add((ques));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
        else {
            questions.child("en").orderByChild("CategoryId").equalTo(categoryId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Question ques = postSnapshot.getValue(Question.class);
                                Common.questionList.add((ques));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }

        //Random list
        Collections.shuffle(Common.questionList);
    }


    //Navigation Drawer funcs START
    @Override
    public void onBackPressed() {
        if(offline_flag==0) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.topic_start2, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment selectedFragment = CategoryFragment.newInstance();

        if (id == R.id.nav_home) {
            // Handle the home action

            Intent homeIntent = new Intent(TopicStartActivity.this, TopicsSelectImgsActivity.class);
           // homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            /*
            selectedFragment = CategoryFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
            fragmentTransaction.commit();
            */
        }

        else if (id == R.id.nav_score_board) {
            frag_flag = 1;
            Intent homeIntent = new Intent(TopicStartActivity.this, TopicsSelectImgsActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            homeIntent.putExtra(EXTRA_FRAGMENT_FLAG , frag_flag);
            startActivity(homeIntent);

            /*
            selectedFragment = RankingFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
            fragmentTransaction.commit();
            */
        }

        else if (id == R.id.nav_log_out) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

            Intent homeIntent = new Intent(TopicStartActivity.this, MainActivity.class);
            //homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        else if (id == R.id.nav_about) {
            aboutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Navigation Drawer funcs END

    //spinner func START
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0)
            return;

        String player = parent.getItemAtPosition(position).toString()+position;
        Toast.makeText(getApplicationContext(),"" + player + " is selected\nget ready" , Toast.LENGTH_LONG).show();

        Intent startGame = new Intent(TopicStartActivity.this, PlayingActivity.class);
        startGame.putExtra("gameType", "spec");
        startGame.putExtra("player", player);
        startActivity(startGame);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getApplicationContext(),"select your game mode" , Toast.LENGTH_LONG).show();
    }
    //spinner func END

    private void aboutDialog() {
        String msg =
                "in this app you can\n" +
                        "search a recipe on app and even online,\n" +
                        "add a recipe to app,\n" +
                        "order a recipe from our chef,\n" +
                        "add ordering to your calendar,\nmake an alarm to remind you\n" +
                        "and even contact our chef";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What can you do here?");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
    }

}

//needed for category image displaying
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}