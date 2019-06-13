package com.example.easyquizy_app;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHolder>  {
    // private final Context context;
    private final String str = "not useful right now";
    private final Context context;
    private ArrayList<Topic> mDataset_topics;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView topicName;
        ImageView topicImg;
        Button playTopic;


        public MyViewHolder(View itemView) {
            super(itemView);
            //this.context = current;

            cv = (CardView)itemView.findViewById(R.id.card_view);
            topicName = (TextView)itemView.findViewById(R.id.topic_name_txt);
            topicImg = itemView.findViewById(R.id.topic_img);
            playTopic = itemView.findViewById(R.id.play_btn);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public TopicAdapter(Context current,Topic[] myDataset) {
        this.context = current;

        mDataset_topics = new ArrayList<>();
        for(int i=0; i<myDataset.length; i++)
        {
            mDataset_topics.add(myDataset[i]);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TopicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.topicName.setText(mDataset_topics.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset_topics.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
