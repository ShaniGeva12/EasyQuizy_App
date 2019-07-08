package com.example.easyquizy_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;

public class TopicStartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    Button singleBtn, randBtn;
    ImageView categoryImage;

    //firebase
    FirebaseDatabase database;
    DatabaseReference category;

    String IMAGE_URL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_start);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        //categoryImage = findViewById(R.id.topic_img);

        singleBtn = findViewById(R.id.single_player_btn);
        randBtn = findViewById(R.id.random_player_btn);
        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singlePlayerOnClick();
            }
        });
        randBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomPlayerOnClick();
            }
        });

        //title & description setter
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String desc = intent.getStringExtra("desc");
        String categoryId = intent.getStringExtra("categoryId");

        //category image displaying
        new DownloadImageTask((ImageView) findViewById(R.id.topic_img))
                .execute(getIntent().getExtras().getString("categoryImage"));

        TextView title = findViewById(R.id.topic_name_txt);
        TextView description = findViewById(R.id.topic_description_txt);
        title.setText(category);
        description.setText(desc);

        /* not working
        if(categoryId == "01")
            IMAGE_URL = "https://images.theconversation.com/files/207820/original/file-20180226-140213-yox11e.jpg";
        if(categoryId == "02")
            IMAGE_URL = "https://www.irishcentral.com/uploads/article/129506/cropped_MI_main_harry_potter.jpg";
        if(categoryId == "03")
            IMAGE_URL = "https://www.worldatlas.com/r/w728-h425-c728x425/upload/0f/59/b2/untitled-design-275.jpg";
        if(categoryId == "04")
            IMAGE_URL = "https://i.kinja-img.com/gawker-media/image/upload/s--vHt6tbFa--/c_scale,f_auto,fl_progressive,q_80,w_800/xjmx1csashjww8j8jwyh.jpg";
        if(categoryId == "05")
            IMAGE_URL = "http://alexedmans.com/wp-content/uploads/2017/03/Sports.jpg";

        if (IMAGE_URL != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(IMAGE_URL);

            Glide.with(this)
                    .load(ref)
                    .into(categoryImage);
        }
        */

        //title & description setter END

        //spinner handler START
        Spinner spinner = (Spinner) findViewById(R.id.players_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choose_players_arr, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spinner handler END

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

    private void randomPlayerOnClick() {
        Intent startGame = new Intent(TopicStartActivity.this, PlayingActivity.class);
        startGame.putExtra("gameType", "random");
        startActivity(startGame);
    }

    private void singlePlayerOnClick() {
        Intent startGame = new Intent(TopicStartActivity.this, PlayingActivity.class);
        startGame.putExtra("gameType", "single");
        startActivity(startGame);
    }

    //Navigation Drawer funcs START
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

}

//needed for category image displaying
class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
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