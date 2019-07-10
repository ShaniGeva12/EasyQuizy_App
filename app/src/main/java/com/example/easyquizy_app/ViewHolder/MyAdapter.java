package com.example.easyquizy_app.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyquizy_app.Model.Category;
import com.example.easyquizy_app.R;
import com.example.easyquizy_app.TopicStartActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static final String EXTRA_OFFLINE_FLAG = "com.example.easyquizy_app.OFFLINE_FLAG";

   // private final Context context;
   // private final String str;
    private final Context context;
    View myFragment;
    private ArrayList<Category> mDataset_category;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        ImageView category_image;
        TextView category_name;

        //TextView personAge;
        //public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            //this.context = current;

            category_image = itemView.findViewById(R.id.category_image);
            category_name = itemView.findViewById(R.id.category_name);

        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context current, Category[] myDataset, View myFragment) {
        this.context = current;

        mDataset_category = new ArrayList<>();
        for(int i=0;i<myDataset.length;i++)
        {
            mDataset_category.add(myDataset[i]);
        }
        this.myFragment = myFragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element


        // Bind the Category object to the CategoryViewHolder

       holder.category_name.setText( mDataset_category.get(position).getName() );

        if(position==0) {
            Picasso.get().load(R.drawable.math_img)
                    .placeholder(R.drawable.loading_gr_wbg)
                    .error(R.drawable.error_loading_pic)
                    .into(holder.category_image);
        }
        else {
            Picasso.get().load(R.drawable.countries_img)
                    .placeholder(R.drawable.loading_gr_wbg)
                    .error(R.drawable.error_loading_pic)
                    .into(holder.category_image);
        }


        holder.category_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, String.format("%s|%s", MyAdapter.getRef(position).getKey(), model.getName()), Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "onClick: " + String.format("%s|%s", adapter.getRef(position).getKey(), model.getName()));
                Intent startGame = new Intent(context, TopicStartActivity.class);
                //Common.categoryId = MyAdapter.
                        //getRef(position).getKey();

                //startGame.putExtra("categoryId",adapter.getRef(position).getKey());
                startGame.putExtra("categoryName", mDataset_category.get(position).getName());
                //startGame.putExtra("categoryImage", model.getImage());
                startGame.putExtra("desc", mDataset_category.get(position).getDescription() );
                startGame.putExtra("offline", 1 );

                context.startActivity(startGame);

            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset_category.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
