package com.example.easyquizy_app.Interface;

import android.view.View;

//created for implementing onClick at Recycler item

public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);

}
