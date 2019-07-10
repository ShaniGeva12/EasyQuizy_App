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
import android.widget.Toast;

import com.example.easyquizy_app.Common.Common;
import com.example.easyquizy_app.Interface.ItemClickListener;
import com.example.easyquizy_app.Interface.RankingCallBack;
import com.example.easyquizy_app.Model.Category;
import com.example.easyquizy_app.Model.QuestionScore;
import com.example.easyquizy_app.Model.Ranking;
import com.example.easyquizy_app.ViewHolder.CategoryBoardHolder;
import com.example.easyquizy_app.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class RankingFragment extends Fragment {
    private static final String TAG = "RankingFragment";
    View myFragment;

    FirebaseDatabase database;
    DatabaseReference categories;
    DatabaseReference Question_Score;

    //int sum=0;
    RecyclerView listCategoryScores;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<QuestionScore, CategoryBoardHolder> adapter;

    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");
        Question_Score = database.getReference("Question_Score");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*DoneActivity activity = (DoneActivity) getActivity();
        String myDataFromActivity = activity.getMyData();*/

        myFragment = inflater.inflate(R.layout.fragment_ranking_new,container,false);
        listCategoryScores = (RecyclerView)myFragment.findViewById(R.id.score_recycler_view);
        listCategoryScores.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategoryScores.setLayoutManager(layoutManager);
        loadCategories();

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

        FirebaseRecyclerOptions<QuestionScore> options =
                new FirebaseRecyclerOptions.Builder<QuestionScore>()
                        .setQuery(Question_Score, QuestionScore.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<QuestionScore, CategoryBoardHolder>(options) {

            @Override
            public CategoryBoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_ranking, parent, false);

                return new CategoryBoardHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoryBoardHolder holder, int position, @NonNull final QuestionScore model) {
                // Bind the Category object to the CategoryViewHolder
                holder.topic_txt.setText(model.getCategoryName());
                holder.score_txt.setText(model.getScore());

                /*holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(),String.format("%s|%s",adapter.getRef(position).getKey(),model.getCategoryName()), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: " + String.format("%s|%s",adapter.getRef(position).getKey(),model.getCategoryName()));
                        //Intent startGame = new Intent(getActivity(), TopicStartActivity.class);
                        //Common.categoryId = adapter.getRef(position).getKey();

                        //startGame.putExtra("categoryId",adapter.getRef(position).getKey());
                        //startGame.putExtra("categoryName", model.getName());
                        //startGame.putExtra("categoryImage", model.getImage());
                        //startGame.putExtra("desc", model.getDescription());

                        //startActivity(startGame);

                    }
                });
            }*/
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getActivity(), String.format("%s|%s", adapter.getRef(position)
                                .getKey(), model.getCategoryName()), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            };

        adapter.notifyDataSetChanged();
        listCategoryScores.setAdapter(adapter);


}
}
