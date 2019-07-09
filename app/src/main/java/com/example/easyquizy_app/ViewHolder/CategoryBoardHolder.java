package com.example.easyquizy_app.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyquizy_app.Interface.ItemClickListener;
import com.example.easyquizy_app.R;

public class CategoryBoardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView topic_txt;
    public TextView score_txt;

    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CategoryBoardHolder(@NonNull View itemView) {
        super(itemView);
        topic_txt = (TextView) itemView.findViewById(R.id.category_name);
        score_txt = (TextView) itemView.findViewById(R.id.category_score);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

}
