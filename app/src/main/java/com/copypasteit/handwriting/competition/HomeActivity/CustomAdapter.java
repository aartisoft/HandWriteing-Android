package com.copypasteit.handwriting.competition.HomeActivity;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.copypasteit.handwriting.competition.R;
import com.copypasteit.handwriting.competition.ReadActivity.ReadActivity;
import com.copypasteit.handwriting.competition.main.model.Post;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    HomeActivity homeActivity;
    public List<Post> posts;

    public CustomAdapter(HomeActivity homeActivity, List<Post> posts) {
        this.homeActivity = homeActivity;
        this.posts = posts;
    }
    public void setData(List<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String dress_name = posts.get(position).getExamName();
                String dress_model_no = posts.get(position).getRoles();


                String dress_size = posts.get(position).getWriteingTxt();
                String dress_details = posts.get(position).getDate();
                String dress_image_url = posts.get(position).getRoles();


                //Intent to start activity
                Intent intent = new Intent(homeActivity, ReadActivity.class);
                //put data in intent
                intent.putExtra("dress_name", dress_name);
                intent.putExtra("dress_model_no", dress_model_no);
                intent.putExtra("dress_size", dress_size);
                intent.putExtra("dress_image_url", dress_image_url);
                intent.putExtra("dress_details", dress_details);
                //start activity

                homeActivity.startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        String dress_name = post.getExamName();
        String dress_model_no = post.getStatus();
        String date = post.getDate();
        String prize = post.getPrizeList();

        holder.dress_name.setText(dress_name);
        holder.dress_model_no.setText(dress_model_no);
        holder.date.setText("Last Date: "+date);
        holder.prize.setText("Prize: "+prize);

//        Glide
//                .with(homeActivity)
//                .load(dress_image_url)
//                .override(420, 320)
//                .centerCrop()
//                .placeholder(R.drawable.ic_spinner)
//                .into(holder.dress_image);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


}
