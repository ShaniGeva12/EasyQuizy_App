package com.example.easyquizy_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.support.constraint.Constraints.TAG;

public class GameTypeDialog {
    public static final String EXTRA_OFFLINE_FLAG = "com.example.easyquizy_app.OFFLINE_FLAG";

    //Firebase
    private FirebaseAuth mAuth;

    int offline_flag=0;

    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_game_type_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FrameLayout mDialogOffline = dialog.findViewById(R.id.frmOffline);
        mDialogOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getApplicationContext(),"Offline / TopicsSelect" ,Toast.LENGTH_SHORT).show();

                Intent homeIntent = new Intent(activity, TopicsSelectImgsActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                offline_flag=1;
                homeIntent.putExtra(EXTRA_OFFLINE_FLAG, offline_flag);

                Log.d(TAG, "---------------------Dialog------------------------------------" );
                Log.d(TAG, " offline_flag = [" + offline_flag + "]");
                Log.d(TAG, "---------------------------------------------------------" );

                activity.startActivity(homeIntent);

                dialog.dismiss();
            }
        });

        FrameLayout mDialogOnline = dialog.findViewById(R.id.frmOnline);
        mDialogOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastStr;
                Intent homeIntent;
                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    homeIntent = new Intent(activity, TopicsSelectImgsActivity.class);
                    offline_flag = 0;
                    homeIntent.putExtra(EXTRA_OFFLINE_FLAG , offline_flag);

                    Log.d(TAG, "---------------------Dialog------------------------------------" );
                    Log.d(TAG, " offline_flag = [" + offline_flag + "]");
                    Log.d(TAG, "---------------------------------------------------------" );
                    toastStr = activity.getResources().getString(R.string.wlcm_back);
                }
                else
                {
                    homeIntent = new Intent(activity, LoginPageActivity.class);
                    toastStr = "Online / Login";
                }


                Toast.makeText(activity.getApplicationContext(),toastStr ,Toast.LENGTH_SHORT).show();

                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(homeIntent);

                dialog.cancel();
            }
        });

        dialog.show();
    }
}