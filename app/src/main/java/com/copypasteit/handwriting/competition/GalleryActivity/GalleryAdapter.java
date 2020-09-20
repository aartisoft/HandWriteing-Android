package com.copypasteit.handwriting.competition.GalleryActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.copypasteit.handwriting.competition.R;
import com.copypasteit.handwriting.competition.main.model.Result;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<ViewHolderGallery> {

    GalleryActivity galleryActivity;
    public List<Result> posts;
    private ScrollGalleryView galleryView;

    public GalleryAdapter(GalleryActivity galleryActivity, List<Result> posts) {
        this.galleryActivity = galleryActivity;
        this.posts = posts;
    }
    public void setData(List<Result> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderGallery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_recycler,  parent, false);
        final ViewHolderGallery viewHolder = new ViewHolderGallery(itemView);

        viewHolder.setOnClickListener(new ViewHolderGallery.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                String dress_price = posts.get(position).getBikeEngineCc();
//                String dress_name = posts.get(position).getBikeName();
                Toast.makeText(galleryActivity, "Please Wait We Will Contact With You.", Toast.LENGTH_LONG).show();


                //for diaglog show
//                Dialog myDialog  = new Dialog(galleryActivity);
//                myDialog.setTitle(dress_price);
//                myDialog.setContentView(R.layout.dialog_contact);
//                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                myDialog.show();


            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGallery holder, int position) {
        Result post = posts.get(position);
        String winer = post.getParName();
        String html = post.getWriterDetails();


        holder.nameWiner.setText(winer);
        holder.htmlTextView.setHtml(html);


//        galleryView = ScrollGalleryView
//                .from(holder.scrollGalleryView)
//                .settings(
//                        GallerySettings
//                                .from(galleryActivity.getSupportFragmentManager())
//                                .thumbnailSize(100)
//                                .enableZoom(true)
//                                .build()
//                )
//                .add(image(dress_image_url))
//                .add(image(dress_image_url))
//                .build();

//        Glide
//                .with(galleryActivity)
//                .load(dress_image_url)
//                .override(520, 420)
//                .centerCrop()
//                .placeholder(R.drawable.ic_spinner)
//                .into(holder.photoView);


    }


    @Override
    public int getItemCount() {
        return posts.size();
    }


}
