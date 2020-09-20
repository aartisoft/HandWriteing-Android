package com.copypasteit.handwriting.competition.GalleryActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.copypasteit.handwriting.competition.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import org.sufficientlysecure.htmltextview.HtmlTextView;


public class ViewHolderGallery extends RecyclerView.ViewHolder {

    TextView nameWiner;
    View mView;
    HtmlTextView htmlTextView;
    ScrollGalleryView scrollGalleryView;
    //LinearLayout linearLayout;

    private ViewHolderGallery.ClickListener mClickListener;

    public ViewHolderGallery(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        //ScrollGalleryView  = itemView.findViewById(R.id.scroll_gallery_view);
        //post_image = itemView.findViewById(R.id.SmallimageId);
        htmlTextView = itemView.findViewById(R.id.html_text1);

        nameWiner = itemView.findViewById(R.id.textView3);

        //linearLayout = itemView.findViewById(R.id.contect_id);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public interface ClickListener{
        void  onItemClick(View view, int position);
    }

    public void setOnClickListener(ViewHolderGallery.ClickListener clickListener){
        mClickListener = clickListener;
    }


}
