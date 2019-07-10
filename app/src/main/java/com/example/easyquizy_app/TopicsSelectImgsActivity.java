package com.example.easyquizy_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;

import com.example.easyquizy_app.Common.Common;
import com.google.firebase.auth.FirebaseAuth;

public class TopicsSelectImgsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TopicsSelectImgActivity";
    BottomNavigationView bottomNavigationView;

    //Firebase
    private FirebaseAuth mAuth;

    int frag_flag;
    int offline_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_select_imgs);

        //get intents
        Intent intent = getIntent();
        frag_flag = intent.getIntExtra(TopicStartActivity.EXTRA_FRAGMENT_FLAG , 0);
        offline_flag = intent.getIntExtra(GameTypeDialog.EXTRA_OFFLINE_FLAG , 0);

        //trying to send data to categories fragment
        Bundle bundle = new Bundle();
        bundle.putInt("FlagOff", offline_flag);

        // set Fragmentclass Arguments
        final CategoryFragment cf = new CategoryFragment();
        cf.setArguments(bundle);

        Log.d(TAG, "---------------------TopicsSelectImgsActivity------------------------------------" );
        Log.d(TAG, " offline_flag = [" + offline_flag + "]");
        Log.d(TAG, "---------------------------------------------------------" );


        //------------------------------------------------------

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.actionCategory:
                        selectedFragment = CategoryFragment.newInstance(cf);
                        break;
                    case R.id.actionRanking:
                        selectedFragment = RankingFragment.newInstance();
                        break;

                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
                fragmentTransaction.commit();
                return true;
            }

        });
        setDefaultFragment();

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

        //Header of Navigation Drawer
        //----------------------------------------------------------------
        View headerView = navigationView.getHeaderView(0);

        TextView userNameTv = headerView.findViewById(R.id.username_txt);
        TextView userEmailTv = headerView.findViewById(R.id.mail_txt);


        //user name
        String userName = Common.currentUser.getName();
        userNameTv.setText(userName);


        //email
          String userEmail = Common.currentUser.getEmail();
          userEmailTv.setText(userEmail);
        //----------------------------------------------------------------

    }

    public int getOffline_flag() {
        return offline_flag;
    }

    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(frag_flag == 1)
            fragmentTransaction.replace(R.id.frame_layout, RankingFragment.newInstance());
        else
            fragmentTransaction.replace(R.id.frame_layout, CategoryFragment.newInstance());

        fragmentTransaction.commit();
    }


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
        getMenuInflater().inflate(R.menu.topics_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the TopicsSelectImgsActivity/Up button, so long
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


            selectedFragment = CategoryFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
            fragmentTransaction.commit();

        }

        else if (id == R.id.nav_score_board) {
            selectedFragment = RankingFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_log_out) {
            mAuth.signOut();

            Intent homeIntent = new Intent(TopicsSelectImgsActivity.this, MainActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        else if (id == R.id.nav_about) {
            aboutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


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
