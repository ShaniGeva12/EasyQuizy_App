package com.example.easyquizy_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Interface.ItemClickListener;
import com.example.easyquizy_app.Model.Category;
import com.example.easyquizy_app.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment";
    View myFragment;


    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    //FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;

    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;

    public static CategoryFragment newInstance(){
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }

    LinearLayout prog_ly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_category,container,false);
        listCategory = (RecyclerView)myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        prog_ly = myFragment.findViewById(R.id.progressBar_ly);
        loadCategories();

        prog_ly.setVisibility(View.GONE);

        return myFragment;
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void loadCategories() {

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(categories, Category.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @Override
            public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_category, parent, false);

                return new CategoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(CategoryViewHolder holder, int position,final Category model) {
                // Bind the Category object to the CategoryViewHolder
                holder.category_name.setText(model.getName());

                //old usege
                //Picasso.with(getActivity()).load(model.getImage()).into(holder.category_image);

                Picasso.get().load(model.getImage())
                        .placeholder(R.drawable.loading_gr_wbg)
                        .error(R.drawable.error_loading_pic)
                        .into(holder.category_image);

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: " + String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()));
                        Intent startGame = new Intent(getActivity(), TopicStartActivity.class);
                        Common.categoryId = adapter.getRef(position).getKey();

                        //startGame.putExtra("categoryId",adapter.getRef(position).getKey());
                        startGame.putExtra("categoryName", model.getName());
                        startGame.putExtra("categoryImage", model.getImage());
                        startGame.putExtra("desc", model.getDescription());

                        startActivity(startGame);

                    }
                });
            }

            /*
             */

        };

        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);

    }
}
