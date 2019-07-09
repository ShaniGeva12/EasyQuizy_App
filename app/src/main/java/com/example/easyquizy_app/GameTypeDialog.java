package com.example.easyquizy_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameTypeDialog {

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
                activity.startActivity(homeIntent);

                dialog.dismiss();
            }
        });

        FrameLayout mDialogOnline = dialog.findViewById(R.id.frmOnline);
        mDialogOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getApplicationContext(),"Online / Login" ,Toast.LENGTH_SHORT).show();


//                if(currentUser != null) {
//                    loginBtn.setVisibility(View.GONE);
//            signupBtn.setVisibility(View.GONE);

//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                }

                    Intent homeIntent = new Intent(activity, LoginPageActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(homeIntent);

                dialog.cancel();
            }
        });

        dialog.show();
    }
}